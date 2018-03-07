package patchCreationAndApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import Helpers.Module.AppConstants;
import zipComparison.module.metadataAndPathComparison;
import zipComparison.module.zipContentComparison;

public class patchApplication extends PatchCreation {
	static String path = "C:\\Users\\EMAN\\Desktop\\zipz";

	public ZipFile zip3;
	public String zip3Name;
	zipContentComparison cmp = new zipContentComparison();
	String folder1Name = folderName(zip1.getName());
	String folder2Name = folderName(zip2.getName());

	private String folderName(String name) {
		return name.substring(0, name.length() - 4);
	}

	public patchApplication(String name1, String name2, String p, String name3) throws IOException {
		super(name1, name2, p);
		ZipFile zip = new ZipFile(name3);
		zip3Name = name3;
		this.zip3 = zip;
	}

	public String[] readDeltaFile(ZipFile zip3) throws IOException {
		if (!zipContentComparison.zipComparison(zip1, zip3)) {
			System.out.println("the tow archives must be content metadata path identical.");
			return null;
		}
		ZipFile DeltaZip = new ZipFile(path + "\\tmp" + "\\Delta_from_" + metadataAndPathComparison.GetZipFileName(zip1)
				+ "_to_" + metadataAndPathComparison.GetZipFileName(zip2) + ".zip");
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
		return ops;
	}

	public void findEntriesWeNeed(String[] names, HashMap<String, String> firstArchiveFilesNames,
			HashMap<String, String> secondArchiveFilesNames) throws IOException {
		int secondArchiveIndex = 0;
		for (int i = 1; i < names.length; i++) {
			if (names[i].equals(AppConstants.SOCEND_ARCHIVE)) {
				secondArchiveIndex = i;
				break;
			}
			firstArchiveFilesNames.put(names[i], names[i]);
		}
		for (int i = secondArchiveIndex + 1; i < names.length; i++) {
			secondArchiveFilesNames.put(names[i], names[i]);
		}

	}

	public void applyPatching() throws IOException {
		HashMap<String, String> firstArchiveFilesNames = new HashMap<String, String>();
		HashMap<String, String> secondArchiveFilesNames = new HashMap<String, String>();
		String[] deltaContent = readDeltaFile(zip3);
		findEntriesWeNeed(deltaContent, firstArchiveFilesNames, secondArchiveFilesNames);
		zip3.close();
		File file = new File(zip3.getName());
		file.delete();
		ZipOutputStream zip3 = new ZipOutputStream(new FileOutputStream(zip3Name));
		byte[] buf = new byte[4096];
		Enumeration<? extends ZipEntry> entries1 = zip1.entries();
		Enumeration<? extends ZipEntry> entries2 = zip2.entries();
		while (entries1.hasMoreElements()) {
			ZipEntry entry = entries1.nextElement();
			if (firstArchiveFilesNames.containsKey(entry.getName())) {
				continue;
			} else {
				ZipEntry en = new ZipEntry(entry.getName());
				zip3.putNextEntry(en);
				InputStream in = zip1.getInputStream(entry);
				int len;
				while ((len = in.read(buf)) > 0) {
					zip3.write(buf, 0, len);
				} // Complete the entry
				zip3.closeEntry();
				in.close();
			}
		}
		while (entries2.hasMoreElements()) {
			ZipEntry entry = entries2.nextElement();
			if (!secondArchiveFilesNames.containsKey(entry.getName())) {
				continue;
			} else {
				ZipEntry en = new ZipEntry(entry.getName());
				zip3.putNextEntry(en);
				InputStream in = zip2.getInputStream(entry);
				int len;
				while ((len = in.read(buf)) > 0) {
					zip3.write(buf, 0, len);
				} // Complete the entry
				zip3.closeEntry();
				in.close();
			}
		}
		zip3.close();
	}

	public static void getAllFiles(File dir, List<File> fileList) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			fileList.add(file);
			if (file.isDirectory()) {
				getAllFiles(file, fileList);
			}
		}
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
		// copyFile(arr2);
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


	public void unzip(String name) throws IOException {
		byte[] buffer = new byte[4096];
		ZipFile zip = new ZipFile(name);
		String folderName = path + "\\" + metadataAndPathComparison.GetZipFileName(zip);

		File folder = new File(path + "\\" + folderName);
		folder.mkdir();
		ZipInputStream zis = new ZipInputStream(new FileInputStream(name));
		// get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();
		while (ze != null) {
			String fileName = ze.getName();
			File newFile = new File(folderName + File.separator + fileName);
			new File(newFile.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}
}
