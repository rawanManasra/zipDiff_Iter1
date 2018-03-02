package ContentComparison.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class zipContentComparison {
	public ZipFile zip1;
	public ZipFile zip2;

	public zipContentComparison(ZipFile z1, ZipFile z2) {
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

	public Boolean compareTowZips() throws IOException {

		ArrayList<ZipEntry> arr1 = new ArrayList<ZipEntry>(copyToArrayList(zip1));
		ArrayList<ZipEntry> arr2 = new ArrayList<ZipEntry>(copyToArrayList(zip2));
		if (arr1.size() != arr2.size()) {
			return false;
		}
		int count1 = arr1.size();
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
							it1.remove();
							it2.remove();
							count1--;
							break;
						}
					}
				}
			}

		}
		return (count1 == 0 ? true : false);
	}

	// public static void main(String args[]) throws IOException {
	// ZipFile zip1 = new ZipFile("D:\\zipCon.zip");
	// ZipFile zip2 = new ZipFile("D:\\zipConcopy.zip");
	// zipContentComparison comp = new zipContentComparison(zip1, zip2);
	// if (comp.compareTowZips())
	// System.out.println("identical");
	// else
	// System.out.println("not identical");
	//
	// }
}
