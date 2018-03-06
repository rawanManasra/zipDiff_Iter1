package zipComparison.module;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class metadataAndPathComparison {

	/* return true if the input files have the same name, false otherwise. */
	public static Boolean metadataTest(ZipEntry entry1, ZipEntry entry2) {
		return GetEntryName(entry1).equals(GetEntryName(entry2));
	}

	/*
	 * return true if the 2 input zipEntries have the same relative path to the root
	 * of the zip archive, false otherwise
	 */
	public static Boolean pathTest(ZipEntry entry1, ZipEntry entry2, ZipFile zip1, ZipFile zip2) {
		return GetRelativePath(zip1, entry1).equals(GetRelativePath(zip2, entry2));

	}

	public static String GetRelativePath(ZipFile zip, ZipEntry entry) {
		String[] names = entry.getName().split("/");
		String path = "";
		for (int i = 0; i < names.length - 1; i++) {
			path += names[i];
			path += "/";

		}
		return path;
	}

	public static String GetEntryName(ZipEntry entry) {
		String[] names = entry.getName().split("\\\\");
		return (names[names.length - 1]);
	}

	public static String GetZipFileName(ZipFile zip) {
		String[] names = zip.getName().split("\\\\");
		String[] name = names[names.length - 1].split("\\.");
		if (!name[1].equals("zip"))
			return "";
		return name[0];

	}

}
