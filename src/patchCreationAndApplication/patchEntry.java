package patchCreationAndApplication;

import java.util.zip.ZipEntry;

/* this class creates an entry for patch application,
 *  it uses operation type field to indicate the operation
 *   type which we need to apply later*/

public class patchEntry {

	ZipEntry entry;
	String opType;

	public patchEntry(ZipEntry entry, String opType) {
		this.entry = entry;
		this.opType = opType;
	}

	@Override
	public String toString() {
		return entry.getName() + "  " + opType;
	}

	public ZipEntry getEntry() {
		return entry;
	}

	public void setEntry(ZipEntry entry) {
		this.entry = entry;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
}
