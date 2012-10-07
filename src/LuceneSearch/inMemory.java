package LuceneSearch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class inMemory {
	// location where the index will be stored.

	private static final String dataFiles = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data";
	private static ArrayList<File> queue = new ArrayList<File>();
	private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	private static QueryParser titleParser;
	private static final int DEFAULT_RESULT_SIZE = 50;

	public static void main(String[] args) {
		// Construct a RAMDirectory to hold the in-memory representation
		// of the index.
		NIOFSDirectory directory = new NIOFSDirectory(null);
		try {
			IndexWriter writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_36,
							new StandardAnalyzer(Version.LUCENE_36)));

			Document doc = new Document();
			// Add Files need to be indexed
			addFiles(new File(dataFiles));

			// Each file, index line by line
			for (File f : queue) {
				FileReader file = null;

				String drama;
				// Read File Line By Line

				file = new FileReader(f);
				FileInputStream fstream = new FileInputStream(f);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);

				BufferedReader dramaList = new BufferedReader(
						new InputStreamReader(in));

				while ((drama = dramaList.readLine()) != null) {
					// Skip if contains the headings
					if (drama.contains("/**")) {
						continue;
					}
					// System.out.println(drama);
					String[] kname = drama.split("::");

					String title = kname[0];
					String weburl = kname[1];

					// creating the indexer and indexing current items
					doc.add(new Field("title", title, Field.Store.YES,
							Field.Index.ANALYZED));
					doc.add(new Field("weburl", weburl, Field.Store.YES,
							Field.Index.NOT_ANALYZED_NO_NORMS));

					writer.addDocument(doc);
				}
			}
			writer.close();

			// Build an IndexSearcher using the in-memory index
			
			IndexSearcher searcher = new IndexSearcher(reader);

			// run queries
			 Query query = new TermQuery(new Term("partnum3", "Q36"));   
	            TopDocs rs = searcher.search(query, null, 10);
	            System.out.println(rs.totalHits);

	            Document firstHit = searcher.doc(rs.scoreDocs[0].doc);
	            System.out.println(firstHit.getField("title").name());

			searcher.close();
			directory.close();
			// check spell check first
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void search(IndexSearcher searcher, String queryString) {
		// Build a Query object
		QueryParser parser = new QueryParser(Version.LUCENE_36, "title",
				analyzer);
		Query query;
		try {
			query = parser.parse(queryString);

			// Search for the query

			ScoreDoc[] hits = searcher.search(query, null, 50).scoreDocs;

			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = searcher.doc(hits[i].doc);
				System.out.println(hitDoc.get("title"));
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add files under directory
	 * 
	 * @param file
	 */
	private static void addFiles(File file) {

		if (!file.exists()) {
			System.out.println(file + " does not exist.");
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFiles(f);
			}
		} else {
			String filename = file.getName().toLowerCase();
			if (filename.endsWith(".txt")) {
				queue.add(file);
			}
		}
	}

}
