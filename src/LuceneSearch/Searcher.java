package LuceneSearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Searcher {

	private IndexSearcher searcher;
	private QueryParser titleParser;
	private String indexDir = "/Users/Azira/Documents/Assignment/anyoung/index";
	private static final int DEFAULT_RESULT_SIZE = 50;
	
	public Searcher() throws IOException {

	}

	/**
	 * Find title form index items
	 * 
	 * @param queryString
	 *            - the query string to search for
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws ParseException 
	 */
	public List<List<String>> findByTitle(String phrase) 
			throws CorruptIndexException, IOException, ParseException  {
		// open the index directory to search
		searcher = new IndexSearcher(IndexReader.open(FSDirectory
				.open(new File(indexDir))));
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

		// defining the query parser to search items by title field.
		titleParser = new QueryParser(Version.LUCENE_36, indexDrama.TITLE,
				analyzer);

		// create query from the incoming query string.
		titleParser.setPhraseSlop(1);
		
		Query query = titleParser.parse("\"" + phrase + "\"");
		query.setBoost(1.5f);
		// execute the query and get the results
		ScoreDoc[] queryResults = searcher.search(query, DEFAULT_RESULT_SIZE).scoreDocs;
		
		
		List<List<String>> dramaList = new ArrayList<List<String>>();
		// process the results
		for (ScoreDoc scoreDoc : queryResults) {
			Document doc = searcher.doc(scoreDoc.doc);

			List<String> list = new ArrayList<String>();
			list.add(doc.get(indexDrama.TITLE));
			list.add(doc.get(indexDrama.WEBURL));
			dramaList.add(list);
		}

		return dramaList;
	}

	/**
	 * Close searcher
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		searcher.close();
	}
}
