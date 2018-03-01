
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.io.fs.FileUtils;

import ContentComparison.Module.metadataAndPathComparison;
import ZipDiffCreation.Module.ZipDiffCreation;

public class NetworkToGraph {

	private static final String DB_PATH = "C:\\Users\\rawan\\Desktop\\zipDiffIter1\\zipDiff_Iter1\\data.db";
	private static final String FILE_NAME = "file name: ";
	private static final String FILE_PATH = "file path: ";
	GraphDatabaseService graphDb;
	Index<Node> zipEntry;

	public void createDb(ArrayList<ZipEntry> entries1, ArrayList<ZipEntry> entries2, ZipFile zip1, ZipFile zip2) {
		clearDb();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));
		zipEntry = graphDb.index().forNodes("switchs");
		registerShutdownHook(graphDb);
		addEntriesToGraph(entries1, zip1);
		addEntriesToGraph(entries2, zip2);
		System.out.println(graphDb);
		graphDb.shutdown();

		// graphDb.shutdown();
	}

	private void clearDb() {
		try {
			FileUtils.deleteRecursively(new File(DB_PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	public void addEntriesToGraph(ArrayList<ZipEntry> entries, ZipFile zip) {
		Iterator<ZipEntry> itr = entries.iterator();
		while (itr.hasNext()) {
			ZipEntry tmp = itr.next();
			Transaction tx = graphDb.beginTx();
			try {
				Node n = graphDb.createNode();
				n.setProperty(FILE_NAME, metadataAndPathComparison.GetEntryName(tmp));
				n.setProperty(FILE_PATH, metadataAndPathComparison.GetRelativePath(zip, tmp));
				System.out.println(zipEntry);
				zipEntry.add(n, FILE_NAME, metadataAndPathComparison.GetEntryName(tmp));
				tx.success();
			} finally {
				tx.close();
			}
		}
	}

	public static void main(String args[]) throws IOException {
		ZipFile zip1 = new ZipFile("C:\\Users\\rawan\\Desktop\\file2.zip");
		ZipFile zip2 = new ZipFile("C:\\Users\\rawan\\Desktop\\file2copy.zip");
		ArrayList<ZipEntry> arr1 = new ArrayList<ZipEntry>(ZipDiffCreation.copyToArrayList(zip1));
		ArrayList<ZipEntry> arr2 = new ArrayList<ZipEntry>(ZipDiffCreation.copyToArrayList(zip2));
		NetworkToGraph nn = new NetworkToGraph();
		nn.createDb(arr1, arr2, zip1, zip2);
		// ZipDiffCreation cmp2zip = new ZipDiffCreation(zip1, zip2);
		// cmp2zip.cmp();

	}
}