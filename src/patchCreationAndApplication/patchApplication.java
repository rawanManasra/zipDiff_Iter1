package patchCreationAndApplication;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import Helpers.Module.AppConstants;
import zipComparison.module.metadataAndPathComparison;
import zipComparison.module.zipContentComparison;

public class patchApplication extends PatchCreation {
	public ZipFile zip3;
	zipContentComparison cmp = new zipContentComparison();

	public patchApplication(String name1, String name2, String p, String name3) throws IOException {
		super(name1, name2, p);
		ZipFile zip = new ZipFile(name3);
		this.zip3 = zip;
	}

	public String[] readDeltaFile(ZipFile zip3) throws IOException {
		if (!zipContentComparison.zipComparison(zip1, zip3)) {
			System.out.println("the tow archives must be content metadata path identical.");
			return null;
		}
		ZipFile DeltaZip = new ZipFile(path + "\\Delta_from_" + metadataAndPathComparison.GetZipFileName(zip1) + "_to_"
				+ metadataAndPathComparison.GetZipFileName(zip2) + ".zip");
		Enumeration<? extends ZipEntry> entries = DeltaZip.entries();
		String delta = "";
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			InputStream stream = DeltaZip.getInputStream(entry);
			StringWriter writer = new StringWriter();
			IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			delta = writer.toString();

		}
		DeltaZip.close();
		String[] ops = delta.split(System.getProperty(AppConstants.NEW_LINE));
		System.out.println(ops[0]);
		return ops;
	}

	public String CopyZipEntryContent(ZipEntry entry, ZipFile zip) throws IOException {
		InputStream stream = zip.getInputStream(entry);
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
		String content = writer.toString();
		return content;
	}

	public ArrayList<ZipEntry> findEntriesWeNeed(String[] names) {
		ArrayList<String> firstArchiveFilesNames = new ArrayList<String>();
		ArrayList<String> secondArchiveFilesNames = new ArrayList<String>();
		int secondArchiveIndex = 0;
		for (int i = 1; i < names.length; i++) {
			if (names[i].equals(AppConstants.SOCEND_ARCHIVE)) {
				secondArchiveIndex = i;
				break;
			}
			firstArchiveFilesNames.add(names[i]);
		}
		for (int i = secondArchiveIndex + 1; i < names.length; i++) {
			secondArchiveFilesNames.add(names[i]);
		}

		ArrayList<ZipEntry> arr3 = new ArrayList<ZipEntry>();
		return arr3;
	}

	public static HashMap<String, patchEntry> arrayListToHashMap(ArrayList<patchEntry> arr) {
		HashMap<String, patchEntry> map = new HashMap<String, patchEntry>();
		for (Iterator<patchEntry> it = arr.iterator(); it.hasNext();) {
			patchEntry entry = it.next();
			map.put(entry.getEntry().getName(), entry);
		}
		return map;
	}

	public Boolean ApplyPatch() throws IOException {

		if (!zipContentComparison.zipComparison(zip3, zip1)) {

			System.out.println("the to archives must be content metadata path identical.");
			return false;
		}
		copyFile(arr2);
		return true;
	}

	public void copyFile(ArrayList<patchEntry> arr) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip3.getName()));

		for (patchEntry pentry : arr) {
			ZipEntry entry = new ZipEntry(pentry.getEntry().getName());
			zos.putNextEntry(entry);

			BufferedInputStream bis = new BufferedInputStream(zip2.getInputStream(entry));
			while (bis.available() > 0) {
				zos.write(bis.read());
			}
			zos.closeEntry();

			bis.close();
		}
		zos.finish();
		zos.close();
		zip2.close();
	}
}
