package testUnits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipFile;

/* zip(i) is equal to zip(ii), and the rest are different */
public class readZips {
	public ZipFile zip1, zip2, zip3, zip4, zip5, zip6, zip7, zip11, zip22, zip33, zip44, zip55, zip66, zip77;
	public ZipFile pzip1, pzip2, pzip3, pzip4, pzip5, pzip6, pzip7, pzip11, pzip22, pzip33, pzip44, pzip55, pzip66,
			pzip77;

	public String path = "C:\\Users\\rawan\\Desktop\\zipDiffIter1\\zipDiff_Iter1\\testZipsArchives\\";
	public String patchPath = "C:\\Users\\rawan\\Desktop\\zipDiffIter1\\zipDiff_Iter1\\testZipsArchives\\patch\\";
	public ArrayList<ZipFile> zips;
	public ArrayList<ZipFile> pzips;

	public readZips() throws IOException {
		// path of zips file on my disk -> in order for this test to run on your
		// computer just change the path
		zip1 = new ZipFile(path + "1.zip");
		zip2 = new ZipFile(path + "2.zip");
		zip3 = new ZipFile(path + "3.zip");
		zip4 = new ZipFile(path + "4.zip");
		zip5 = new ZipFile(path + "5.zip");
		zip6 = new ZipFile(path + "6.zip");
		zip7 = new ZipFile(path + "7.zip");
		zip11 = new ZipFile(path + "11.zip");
		zip22 = new ZipFile(path + "22.zip");
		zip33 = new ZipFile(path + "33.zip");
		zip44 = new ZipFile(path + "44.zip");
		zip55 = new ZipFile(path + "55.zip");
		zip66 = new ZipFile(path + "66.zip");
		zip77 = new ZipFile(path + "77.zip");

		zips = new ArrayList<ZipFile>();
		zips.add(zip1);
		zips.add(zip2);
		zips.add(zip3);
		zips.add(zip4);
		zips.add(zip5);
		zips.add(zip6);
		zips.add(zip7);
		zips.add(zip11);
		zips.add(zip22);
		zips.add(zip33);
		zips.add(zip44);
		zips.add(zip55);
		zips.add(zip66);
		zips.add(zip77);

		pzip1 = new ZipFile(patchPath + "1.zip");
		pzip2 = new ZipFile(patchPath + "2.zip");
		pzip3 = new ZipFile(patchPath + "3.zip");
		pzip4 = new ZipFile(patchPath + "4.zip");
		pzip5 = new ZipFile(patchPath + "5.zip");
		pzip6 = new ZipFile(patchPath + "6.zip");
		pzip7 = new ZipFile(patchPath + "7.zip");
		pzip11 = new ZipFile(patchPath + "11.zip");
		pzip22 = new ZipFile(patchPath + "22.zip");
		pzip33 = new ZipFile(patchPath + "33.zip");
		pzip44 = new ZipFile(patchPath + "44.zip");
		pzip55 = new ZipFile(patchPath + "55.zip");
		pzip66 = new ZipFile(patchPath + "66.zip");
		pzip77 = new ZipFile(patchPath + "77.zip");

		pzips = new ArrayList<ZipFile>();
		pzips.add(zip1);
		pzips.add(zip2);
		pzips.add(zip3);
		pzips.add(zip4);
		pzips.add(zip5);
		pzips.add(zip6);
		pzips.add(zip7);
		pzips.add(zip11);
		pzips.add(zip22);
		pzips.add(zip33);
		pzips.add(zip44);
		pzips.add(zip55);
		pzips.add(zip66);
		pzips.add(zip77);

	}
}
