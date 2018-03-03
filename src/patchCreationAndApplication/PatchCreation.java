package patchCreationAndApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import ContentComparison.Module.inputStreamContentComparison;
import ContentComparison.Module.metadataAndPathComparison;
import Helpers.Module.AppConstants;

public class PatchCreation {
	ArrayList<patchEntry> arr1;
	ArrayList<patchEntry> arr2;
	ZipFile zip1;
	ZipFile zip2;
	String path;

	public PatchCreation(String name1, String name2, String p) throws IOException {
		ZipFile z1 = new ZipFile(name1);
		ZipFile z2 = new ZipFile(name2);
		this.arr1 = arrayListPatchCreation(z1);
		this.arr2 = arrayListPatchCreation(z2);
		zip1 = z1;
		zip2 = z2;
		if (p != null && p != "")
			path = p;
		else
			path = AppConstants.DEFAULT_PATH;
		EnterOperationTypes();
		compressPatchFile();
	}

	private ArrayList<patchEntry> arrayListPatchCreation(ZipFile zip) {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		ArrayList<patchEntry> arr = new ArrayList<patchEntry>();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			patchEntry pentry = new patchEntry(entry, AppConstants.INIT);
			arr.add(pentry);
		}
		return arr;
	}

	public void EnterOperationTypes() throws IOException {
		for (Iterator<patchEntry> it1 = arr1.iterator(); it1.hasNext();) {
			patchEntry entry1 = it1.next();
			for (Iterator<patchEntry> it2 = arr2.iterator(); it2.hasNext();) {
				patchEntry entry2 = it2.next();
				InputStream stream1 = zip1.getInputStream(entry1.getEntry());
				InputStream stream2 = zip2.getInputStream(entry2.getEntry());
				inputStreamContentComparison compare2streams = new inputStreamContentComparison();
				// identical entries:
				// identical content
				if (compare2streams.compare2files(stream1, stream2)) {
					if (metadataAndPathComparison.metadataTest(entry1.getEntry(), entry2.getEntry())) {
						if (metadataAndPathComparison.pathTest(entry1.getEntry(), entry2.getEntry(), zip1, zip2)) {
							entry1.setOpType(AppConstants.SKIP);
							entry2.setOpType(AppConstants.SKIP);
						}
					}
				}
			}

		}
	}

	public void compressPatchFile() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstants.FIRST_ARCHIVE);
		sb.append(System.getProperty(AppConstants.NEW_LINE));
		for (Iterator<patchEntry> it1 = arr1.iterator(); it1.hasNext();) {
			patchEntry entry1 = it1.next();
			if (entry1.getOpType().equals(AppConstants.SKIP))
				continue;
			else if (entry1.getOpType().equals(AppConstants.INIT)) {
				entry1.setOpType(AppConstants.REMOVAL);
				sb.append(entry1.getEntry().getName());
				sb.append(System.getProperty(AppConstants.NEW_LINE));

			}
		}
		sb.append(AppConstants.SOCEND_ARCHIVE);
		sb.append(System.getProperty(AppConstants.NEW_LINE));
		for (Iterator<patchEntry> it2 = arr2.iterator(); it2.hasNext();) {
			patchEntry entry2 = it2.next();
			if (entry2.getOpType().equals(AppConstants.SKIP))
				continue;
			else if (entry2.getOpType().equals(AppConstants.INIT)) {
				entry2.setOpType(AppConstants.ADDITION);
				sb.append(entry2.getEntry().getName());
				sb.append(System.getProperty(AppConstants.NEW_LINE));

			}
		}
		File DeltaZip = new File(path + "\\Delta_from_" + metadataAndPathComparison.GetZipFileName(zip1) + "_to_"
				+ metadataAndPathComparison.GetZipFileName(zip2) + ".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(DeltaZip));
		ZipEntry deltaFile = new ZipEntry(AppConstants.DELTA_FILE_NAME);
		out.putNextEntry(deltaFile);
		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();
		out.close();
	}

}
