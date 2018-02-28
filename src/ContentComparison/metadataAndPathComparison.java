package ContentComparison;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class metadataAndPathComparison {

	/* return true if the input files have the same name, false otherwise. */
	public boolean metadataTest(ZipEntry entry1, ZipEntry entry2) {
		return entry1.getName().equals(entry2.getName());
	}

	/*
	 * return true if the 2 input zipEntries have the same relative path to the root
	 * of the zip archive, false otherwise
	 */
	public boolean pathTest(ZipEntry entry1, ZipEntry entry2, ZipFile zip1, ZipFile zip2) {
		// calculate the path to zip1
		Path p11 = Paths.get(zip1.getName()).toAbsolutePath().getRoot();
		// calculate the relative path from entry 1 to zip1
		Path p12 = Paths.get(entry1.getName()).toAbsolutePath().relativize(p11);
		// calculate the path to zip2
		Path p21 = Paths.get(zip1.getName()).toAbsolutePath().getRoot();
		// calculate the relative path from entry2 to zip2
		Path p22 = Paths.get(entry1.getName()).toAbsolutePath().relativize(p21);
		// equalization!
		return (p12.equals(p22));

	}

}
