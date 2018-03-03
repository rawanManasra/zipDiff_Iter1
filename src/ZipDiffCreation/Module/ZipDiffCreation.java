package ZipDiffCreation.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ContentComparison.Module.inputStreamContentComparison;
import ContentComparison.Module.metadataAndPathComparison;

public class ZipDiffCreation {
	public ZipFile zip1 = null;
	public ZipFile zip2 = null;

	public ZipDiffCreation(String name1, String name2) throws IOException {
		ZipFile z1 = new ZipFile(name1);
		ZipFile z2 = new ZipFile(name2);
		zip1 = z1;
		zip2 = z2;
	}

	public static ArrayList<ZipEntry> copyToArrayList(ZipFile zip) {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		ArrayList<ZipEntry> arr = new ArrayList<ZipEntry>(zip.size());
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			arr.add(entry);
		}
		return arr;
	}

	public void cmp() throws IOException {
		ArrayList<ZipEntry> arr1 = new ArrayList<ZipEntry>(copyToArrayList(zip1));
		ArrayList<ZipEntry> arr2 = new ArrayList<ZipEntry>(copyToArrayList(zip2));
		// System.out.println(arr1 + " " + arr2);
		findIdenticalFiles(arr1, arr2);
		// System.out.println(arr1 + " " + arr2);
		System.out.println("------------------------------");
		findSameContentsNamesDifferentPaths(arr1, arr2);
		// System.out.println(arr1 + " " + arr2);
		System.out.println("------------------------------");

		findSameContentPathDifferentNames(arr1, arr2);
		// System.out.println(arr1 + " " + arr2);
		System.out.println("------------------------------");

		findSameContentDifferentNamesPaths(arr1, arr2);
		System.out.println("------------------------------");

		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			ZipEntry entry1 = it1.next();
			if (!entry1.isDirectory()) {
				System.out.println(
						"file: " + metadataAndPathComparison.GetEntryName(entry1) + " is only in the first archive");
			}

		}
		for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
			ZipEntry entry2 = it2.next();
			if (!entry2.isDirectory()) {
				System.out.println(
						"file: " + metadataAndPathComparison.GetEntryName(entry2) + " is only in the second archive");
			}

		}

	}

	public void findSameContentDifferentNamesPaths(ArrayList<ZipEntry> arr1, ArrayList<ZipEntry> arr2)
			throws IOException {
		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			ZipEntry entry1 = it1.next();
			for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
				ZipEntry entry2 = it2.next();
				InputStream stream1 = zip1.getInputStream(entry1);
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison();
				if (compare2streams.compare2files(stream1, stream2)) {
					if (!metadataAndPathComparison.metadataTest(entry1, entry2)) {
						System.out.println("Different Names:");
						System.out.println("file 1 name: " + metadataAndPathComparison.GetEntryName(entry1));
						System.out.println("file 2 name: " + metadataAndPathComparison.GetEntryName(entry2));
						if (!metadataAndPathComparison.pathTest(entry1, entry2, zip1, zip2)) {
							System.out.println("Different Paths:");
							System.out
									.println("file 1 path: " + metadataAndPathComparison.GetRelativePath(zip1, entry1));
							System.out
									.println("file 2 path: " + metadataAndPathComparison.GetRelativePath(zip2, entry2));
							it1.remove();
							it2.remove();
							break;

						}
					}
				}
			}
		}

	}

	public void findSameContentPathDifferentNames(ArrayList<ZipEntry> arr1, ArrayList<ZipEntry> arr2)
			throws IOException {
		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			ZipEntry entry1 = it1.next();
			for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
				ZipEntry entry2 = it2.next();
				InputStream stream1 = zip1.getInputStream(entry1);
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison();
				if (compare2streams.compare2files(stream1, stream2)) {
					if (metadataAndPathComparison.pathTest(entry1, entry2, zip1, zip2)) {
						if (!metadataAndPathComparison.metadataTest(entry1, entry2)) {
							System.out.println("Different Names:");
							System.out.println("file 1 name: " + metadataAndPathComparison.GetEntryName(entry1));
							System.out.println("file 2 name: " + metadataAndPathComparison.GetEntryName(entry2));
							it1.remove();
							it2.remove();
							break;

						}
					}
				}
			}
		}

	}

	public void findSameContentsNamesDifferentPaths(ArrayList<ZipEntry> arr1, ArrayList<ZipEntry> arr2)
			throws IOException {
		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			ZipEntry entry1 = it1.next();
			for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
				ZipEntry entry2 = it2.next();
				InputStream stream1 = zip1.getInputStream(entry1);
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison();
				if (compare2streams.compare2files(stream1, stream2)) {
					if (metadataAndPathComparison.metadataTest(entry1, entry2)) {
						if (!metadataAndPathComparison.pathTest(entry1, entry2, zip1, zip2)) {
							System.out.println("Different Paths:");
							System.out
									.println("file 1 path: " + metadataAndPathComparison.GetRelativePath(zip1, entry1));
							System.out
									.println("file 2 path: " + metadataAndPathComparison.GetRelativePath(zip2, entry2));
							it1.remove();
							it2.remove();
							break;

						}
					}
				}
			}
		}
	}

	public void findIdenticalFiles(ArrayList<ZipEntry> arr1, ArrayList<ZipEntry> arr2) throws IOException {
		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			ZipEntry entry1 = it1.next();
			for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
				ZipEntry entry2 = it2.next();
				InputStream stream1 = zip1.getInputStream(entry1);
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison();
				if (compare2streams.compare2files(stream1, stream2)) {
					if (metadataAndPathComparison.metadataTest(entry1, entry2)) {
						if (metadataAndPathComparison.pathTest(entry1, entry2, zip1, zip2)) {
							System.out.println("Identical " + metadataAndPathComparison.GetEntryName(entry1) + " "
									+ metadataAndPathComparison.GetEntryName(entry2));
							it1.remove();
							it2.remove();
							break;
						}
					}
				}
			}
		}
	}
}
