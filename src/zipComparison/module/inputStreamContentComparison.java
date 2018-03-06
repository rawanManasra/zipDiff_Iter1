package zipComparison.module;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/* compare the content of 2 inputStream files*/

public class inputStreamContentComparison {

	static MessageDigest sha256 = null;

	/* check if 2 input files have the same SHA2-256 digest on file contents */
	public Boolean compare2files(InputStream file1, InputStream file2) {
		return hashFileStream(file1).equals(hashFileStream(file2));
	}

	/**
	 * Computes a SHA2-256 digest on the file contents.
	 * 
	 * @param file12
	 *            The input stream to process
	 * @return The SHA2-256 digest of the file contents encoded in Base64 encoding
	 *         (not URL safe). Returns null if the hash digest fails.
	 */

	public String hashFileStream(InputStream stream) {
		if (sha256 == null) {
			try {
				sha256 = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// this should never happen
				e.printStackTrace();
				return null;
			}
		}
		try {
			// reset the digest state machine
			sha256.reset();

			// open the stream and hash the contents
			byte[] buf = new byte[4096];
			int bytesRead = 0;
			while ((bytesRead = stream.read(buf)) > 0) {
				sha256.update(buf, 0, bytesRead);
			}
			byte[] digest = sha256.digest();
			return Base64.getEncoder().encodeToString(digest);
		} catch (IOException iox) {
			System.out.println("Error computing hash on the input stream");
		}
		return null;
	}

}
