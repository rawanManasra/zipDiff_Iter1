package ReadZipFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ContentComparison.inputStreamContentComparison;
import ContentComparison.metadataAndPathComparison;

public class compareTowZip {
	public ZipFile zip1 = null;
	public ZipFile zip2 = null;

	public compareTowZip(ZipFile z1, ZipFile z2) {
		zip1 = z1;
		zip2 = z2;
	}

	public ArrayList<ZipEntry> copyToArrayList(ZipFile zip) {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		ArrayList<ZipEntry> arr = new ArrayList<ZipEntry>(zip.size());
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			arr.add(entry);
		}
		return arr;
	}

	public void cmp() throws IOException {
		int count1 = 0;
		int count2 = 0;
		metadataAndPathComparison metadataAndPath = new metadataAndPathComparison();
		ArrayList<ZipEntry> arr1 = new ArrayList<ZipEntry>(copyToArrayList(zip1));
		ArrayList<ZipEntry> arr2 = new ArrayList<ZipEntry>(copyToArrayList(zip2));
		for (Iterator<ZipEntry> it1 = arr1.iterator(); it1.hasNext();) {
			count2 = count1;
			ZipEntry entry1 = it1.next();
			InputStream stream1 = zip1.getInputStream(entry1);
			for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
				ZipEntry entry2 = it2.next();
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison(stream1, stream2);
				/* check if the content of 2 entries are the same */
				if (compare2streams.compare2files()) {
					/* check if the 2 entries has the same name */
					if (metadataAndPath.metadataTest(entry1, entry2)) {
						// TODO get path for each entry and what does it mean by path, because each
						// entry is in a different archive
						if (metadataAndPath.pathTest(entry1, entry2, zip1, zip2)) {
							System.out.println("Identical " + entry1.getName() + " " + entry2.getName());
						} else {
							System.out.println("Different Paths: ");
							System.out.println(Paths.get(entry1.getName().toString()));
							System.out.println(Paths.get(entry2.getName().toString()));
						}
					} else if (!entry1.getName().equals(entry2.getName())) {
						System.out.println("Different files names");
						System.out.println("file 1 name: " + entry1.getName());
						System.out.println("file 2 name: " + entry2.getName());
					}

					it1.remove();
					it2.remove();
					count1++;
					break;
				}
			}
			if (count1 > count2)
				continue;
			System.out.println(entry1.getName() + " is only in zip1");
			if (it1.hasNext()) {
				it1.next();
				it1.remove();
			}
		}
		for (Iterator<ZipEntry> it2 = arr2.iterator(); it2.hasNext();) {
			System.out.println(it2.next().getName() + " is only in zip2");
		}
	}

	public static void main(String args[]) throws IOException {
		ZipFile zip1 = new ZipFile("C:\\Users\\rawan\\Desktop\\file2copy.zip");
		ZipFile zip2 = new ZipFile("C:\\Users\\rawan\\Desktop\\file2.zip");
		compareTowZip cmp2zip = new compareTowZip(zip1, zip2);
		cmp2zip.cmp();
		// Path p = Paths.get(zip1.getName()).toAbsolutePath().getRoot();
		// final Enumeration<? extends ZipEntry> entries = zip1.entries();
		// while (entries.hasMoreElements()) {
		// final ZipEntry entry = entries.nextElement();
		// Path p2 = Paths.get(entry.getName()).toAbsolutePath().relativize(p);
		// System.out.println(p2.toString());
		// Path p3 = Paths.get(entry.getName());
		// System.out.println(p3.toString());
		// }
	}

}
