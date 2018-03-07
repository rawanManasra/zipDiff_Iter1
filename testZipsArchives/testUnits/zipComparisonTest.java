package testUnits;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import zipComparison.module.zipContentComparison;

class zipComparisonTest {

	@Test
	void test() throws IOException {
		readZips rz = new readZips();
		/* testing identical archives */
		assertTrue(zipContentComparison.zipComparison(rz.zip1, rz.zip11),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip2, rz.zip22),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip3, rz.zip33),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip4, rz.zip44),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip5, rz.zip55),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip6, rz.zip66),
				"Zip Comparison Failed : the 2 archives are identical");
		assertTrue(zipContentComparison.zipComparison(rz.zip7, rz.zip77),
				"Zip Comparison Failed : the 2 archives are identical");
		/* testing not identical archives */
		assertFalse(zipContentComparison.zipComparison(rz.zip1, rz.zip2));
		assertFalse(zipContentComparison.zipComparison(rz.zip1, rz.zip3));
		assertFalse(zipContentComparison.zipComparison(rz.zip3, rz.zip44));
		assertFalse(zipContentComparison.zipComparison(rz.zip5, rz.zip22));
		assertFalse(zipContentComparison.zipComparison(rz.zip6, rz.zip7));
		assertFalse(zipContentComparison.zipComparison(rz.zip4, rz.zip55));
	}

}
