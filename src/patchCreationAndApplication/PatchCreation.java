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

import Helpers.Module.AppConstants;
import zipComparison.module.inputStreamContentComparison;
import zipComparison.module.metadataAndPathComparison;

/**
 * this class creates a compressed file which is the patch between 2 input
 * archives and store it in a given path
 */
public class PatchCreation {
	ArrayList<patchEntry> arr1;
	ArrayList<patchEntry> arr2;
	ZipFile zip1;
	ZipFile zip2;
	String path;

	/*
	 * Constructor: given the names of the archives and the path to put the
	 * delta/patch file
	 */
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
	}

	/*
	 * input: zip file -- output: array list of patch entries with each one of them
	 * is initiated with "init" value
	 */
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

	/*
	 * this function finds all identical files in the 2 archives and marks their
	 * operation types as "SKIP"
	 */
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

	/*
	 * this function finds all files in both archives which their operation types
	 * are still init - that means that the "init" files in the first archive are
	 * for removal and the "init" files in the second archive are for addition
	 */
	public void compressPatchFile() throws IOException {
		// create a string and append to it the data that we need
		StringBuilder sb = new StringBuilder();
		// append "First Archive" which is a sign where to start
		sb.append(AppConstants.FIRST_ARCHIVE);
		sb.append(System.getProperty(AppConstants.NEW_LINE));
		// go over all the entries in the first archive and skip the ones with "skip"
		// and the rest, change their operation types to removal and add their names to
		// the string
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
		// go over all the entries in the second archive and skip the ones with "skip"
		// and the rest, change their operation types to addition and add their names to
		// the string
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
		// create a zipFile which its name contain the names of the 2 archives and store
		// it in the path that was given -- this zip file only contains one file which
		// is the delta between the 2 input archives
		File DeltaZip = new File(path + "\\Delta_from_" + metadataAndPathComparison.GetZipFileName(zip1) + "_to_"
				+ metadataAndPathComparison.GetZipFileName(zip2) + ".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(DeltaZip));
		// create a zip entry - delta
		ZipEntry deltaFile = new ZipEntry(AppConstants.DELTA_FILE_NAME);
		// add the entry to the delta zip file
		out.putNextEntry(deltaFile);
		// write the string that we built to this entry
		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		// close the entries and the zipfile
		out.closeEntry();
		out.close();
	}

	/*
	 * this function creates the patch at 2 steps: first: it calls
	 * EnterOperationTypes() function which is responsible for deciding which file
	 * to add or remove second: it calls compressPatchFile() function which creates
	 * the patch file and add the appropriate data to it
	 */
	public void CreatePatch() throws IOException {
		EnterOperationTypes();
		compressPatchFile();
	}

	// public static void main(String args[]) throws IOException {
	// readZips rz = new readZips();
	// for (ZipFile z : rz.pzips) {
	// for (ZipFile zs : rz.pzips) {
	// String name1 = z.getName();
	// String name2 = zs.getName();
	// PatchCreation pc = new PatchCreation(name1, name2, rz.patchPath + "tmp\\");
	// pc.CreatePatch();
	// }
	// }
	// }

}
