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
import Helpers.Module.OperationType;;

public class PatchCreation {
	ArrayList<patchEntry> arr1;
	ArrayList<patchEntry> arr2;
	ZipFile zip1;
	ZipFile zip2;
	String path;

	public PatchCreation(ZipFile z1, ZipFile z2, String p) throws IOException {
		this.arr1 = arrayListPatchCreation(z1);
		this.arr2 = arrayListPatchCreation(z2);
		zip1 = z1;
		zip2 = z2;
		if (p != null && p != "")
			path = p;
		else
			path = "D:";

		EnterOperationTypes();
		compressPatchFile();
	}

	private ArrayList<patchEntry> arrayListPatchCreation(ZipFile zip) {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		ArrayList<patchEntry> arr = new ArrayList<patchEntry>();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			patchEntry pentry = new patchEntry(entry, OperationType.INIT);
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
							entry1.setOpType(OperationType.SKIP);
							entry2.setOpType(OperationType.SKIP);
						}
					}
				}
			}

		}
	}

	public void compressPatchFile() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Archive 1:");
		sb.append(System.getProperty("line.separator"));
		for (Iterator<patchEntry> it1 = arr1.iterator(); it1.hasNext();) {
			patchEntry entry1 = it1.next();
			if (entry1.getOpType().equals(OperationType.SKIP))
				continue;
			else if (entry1.getOpType().equals(OperationType.INIT)) {
				entry1.setOpType(OperationType.REMOVAL);
				sb.append(entry1.getEntry().getName());
				sb.append(System.getProperty("line.separator"));

			}
		}
		sb.append("Archive 2:");
		sb.append(System.getProperty("line.separator"));
		for (Iterator<patchEntry> it2 = arr2.iterator(); it2.hasNext();) {
			patchEntry entry2 = it2.next();
			if (entry2.getOpType().equals(OperationType.SKIP))
				continue;
			else if (entry2.getOpType().equals(OperationType.INIT)) {
				entry2.setOpType(OperationType.ADDITION);
				sb.append(entry2.getEntry().getName());
				sb.append(System.getProperty("line.separator"));

			}
		}
		File f = new File(path + "\\Delta.zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("delta.txt");
		out.putNextEntry(e);
		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();
		out.close();
	}
	// ArrayList<patchEntry> array = new ArrayList<patchEntry>();

	public static void main(String args[]) throws IOException {
		ZipFile zip1 = new ZipFile("D:\\zipCon.zip");
		ZipFile zip2 = new ZipFile("D:\\zipConcopy.zip");
		PatchCreation pc = new PatchCreation(zip1, zip2, "");
		pc.EnterOperationTypes();
		System.out.println(pc.arr1);
		System.out.println(pc.arr2);
		System.out.println(zip1.getName());
		// pc.compressPatch();
		// PatchCreation arr = new PatchCreation();
		// System.out.println(arr.arrayListPatchCreation(zip1));
	}
}
