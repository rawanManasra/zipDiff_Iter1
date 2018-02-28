package ReadZipFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class readZipFile {
	private static final Logger LOGGER = Logger.getLogger("ZipReader");

	// Expands the zip file passed as argument 1, into the
	// directory provided in argument 2
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			LOGGER.error("zipreader zipfile outputdir");
			return;
		}

		// create a buffer to improve copy performance later.
		byte[] buffer = new byte[2048];

		Path outDir = Paths.get(args[1]);

		try (
				// we open the zip file using a java 7 try with resources block so
				// that we don't need a finally.
				ZipInputStream stream = new ZipInputStream(new FileInputStream(args[0]))) {
			LOGGER.info("Zip file: " + args[0] + " has been opened");

			// now iterate through each file in the zip archive. The get
			// next entry call will return a ZipEntry for each file in
			// the stream
			ZipEntry entry;
			while ((entry = stream.getNextEntry()) != null) {
				// We can read the file information from the ZipEntry.
				String fileInfo = String.format("Entry: [%s] len %d added %TD", entry.getName(), entry.getSize(),
						new Date(entry.getTime()));
				LOGGER.info(fileInfo);

				Path filePath = outDir.resolve(entry.getName());

				// Now we can read the file data from the stream. We now
				// treat the stream like a usual input stream reading from
				// it until it returns 0 or less.
				try (FileOutputStream output = new FileOutputStream(filePath.toFile())) {
					LOGGER.info("Writing file: " + filePath);
					int len;
					while ((len = stream.read(buffer)) > 0) {
						output.write(buffer, 0, len);
					}
				}
			}
		}
	}
}
