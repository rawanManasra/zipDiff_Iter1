package ReadZipFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ContentCoparison.inputStreamContentComparison;

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
		ArrayList<ZipEntry> arr1 = copyToArrayList(zip1);
		ArrayList<ZipEntry> arr2 = copyToArrayList(zip2);
		int count = 0;

		for (ZipEntry entry1 : arr1) {
			InputStream stream1 = zip1.getInputStream(entry1);
			for (ZipEntry entry2 : arr2) {
				InputStream stream2 = zip2.getInputStream(entry2);
				inputStreamContentComparison compare2streams = new inputStreamContentComparison(stream1, stream2);
				if (compare2streams.compare2files()) {
					count++;
					arr1.remove(entry1);
					arr1.remove(entry2);
				}

			}
		}

	}
}
