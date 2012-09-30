package LuceneSearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
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
	private String INDEX_DIR = "/Users/Azira/Documents/Assignment/anyoung/index";
	private static final int DEFAULT_RESULT_SIZE = 50;
	private File DIRECTORY;

	public Searcher() throws IOException {

		DIRECTORY = new File(INDEX_DIR);

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
			throws CorruptIndexException, IOException, ParseException {
		List<List<String>> dramaList = new ArrayList<List<String>>();
		// Check if index directory does not exist
		if (!DIRECTORY.exists()) {
			System.out.println("Error: could not find directory " + DIRECTORY);
			return null;
		} else {
			
			// lowercase input
			phrase = phrase.toLowerCase();

			// open the index directory to search
			searcher = new IndexSearcher(IndexReader.open(FSDirectory
					.open(DIRECTORY)));
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

			// defining the query parser to search items by title field.
			titleParser = new QueryParser(Version.LUCENE_36, indexDrama.TITLE,
					analyzer);
		

			if (!phrase.contains(" ")) {
				Term t = new Term(indexDrama.TITLE, phrase);
				Query query = new TermQuery(t);
				// Getting it sorted in relevance
				TopFieldDocs docs = searcher.search(query, DEFAULT_RESULT_SIZE,
						Sort.RELEVANCE);
				for (ScoreDoc scoreDoc : docs.scoreDocs) {
					Document doc = searcher.doc(scoreDoc.doc);
					List<String> list = new ArrayList<String>();
					list.add(doc.get(indexDrama.TITLE));
					list.add(doc.get(indexDrama.WEBURL));
					dramaList.add(list);
				}
			}
			// for more than 1 query term
			else {
				// create query from the incoming query string.
				titleParser.setPhraseSlop(1);
				Query query = titleParser.parse("\"" + phrase + "\"");
				TopFieldDocs topDocs = searcher.search(query,
						DEFAULT_RESULT_SIZE, Sort.RELEVANCE);
				for (ScoreDoc match : topDocs.scoreDocs) {
					Document doc = searcher.doc(match.doc);
					List<String> list = new ArrayList<String>();
					list.add(doc.get(indexDrama.TITLE));
					list.add(doc.get(indexDrama.WEBURL));
					dramaList.add(list);
				}
			}
			//close searcher
			searcher.close();
			return dramaList;
		}
	}

	
}