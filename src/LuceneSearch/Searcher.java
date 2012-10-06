package LuceneSearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.NIOFSDirectory;
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

	public Searcher() throws IOException {
		//File file = new File(INDEX_DIR);
		//DIRECTORY = FSDirectory.open(file);
		searcher = new IndexSearcher(DirectoryReader.open(NIOFSDirectory.open(new File(INDEX_DIR))));  
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
		//DirectoryReader reader = DirectoryReader.open(DIRECTORY);
		//searcher = new IndexSearcher(reader);

        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		
		
		// Check if index directory does not exist
	
			
			// lowercase input
			phrase = phrase.toLowerCase();
	
		
	
			// defining the query parser to search items by title field.
			titleParser = new QueryParser(Version.LUCENE_40, indexDrama.TITLE,
					analyzer);
		
	
			if (!phrase.contains(" ")) {
				Term t = new Term(indexDrama.TITLE, phrase);
				Query query = new TermQuery(t);
				// Getting it sorted in relevance
				TopFieldDocs docs = searcher.search(query, DEFAULT_RESULT_SIZE, Sort.RELEVANCE);
				for (ScoreDoc scoreDoc : docs.scoreDocs) {
					Document doc = searcher.doc(scoreDoc.doc);
					List<String> list = new ArrayList<String>();
					list.add(doc.get(indexDrama.TITLE));
					list.add(doc.get(indexDrama.WEBURL));
					list.add(doc.get(indexDrama.FILENAME));
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
					list.add(doc.get(indexDrama.FILENAME));
					//Lucene.createImg(doc.get("weburl"));
					dramaList.add(list);
				}
			}
			//close searcher
		
		
			return dramaList;
		}
	

	
}