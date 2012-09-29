package LuceneSearch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class spellCheck {

	private FSDirectory directory;
	private IndexWriterConfig config;
	private String dicFile = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/thedic.txt";
	private static final String INDEX_DIR = "/Users/Azira/Documents/Assignment/anyoung/index";
	private static final int DEFAULT_RESULT_SIZE = 50;
	private Set<String> wordDic = new TreeSet<String>();

	public spellCheck() throws IOException {

		directory = FSDirectory.open(new File(INDEX_DIR));
		config = new IndexWriterConfig(Version.LUCENE_36, null);
	}

	/**
	 * Find the correct (closest) query for the user
	 * 
	 * The use of "Did you mean?" is you used here
	 * 
	 * @param query
	 * @param searcher
	 * @return newQuery
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public List correctWords(String userQuery)
			throws FileNotFoundException, IOException, ParseException {

		SpellChecker spell = new SpellChecker(directory);
		File dictionary = new File(dicFile);
		spell.indexDictionary(new PlainTextDictionary(dictionary), config, true);

		// Adding dictionary to temporary list
		addWords();

		List newQuery = new ArrayList();
		String[] queryword = null;
		String[] suggestions;
		String[] spellSuggest = null;
		String query1 = null;

		// make query to lowercase to easy find incorrect words
		userQuery = userQuery.toLowerCase();
		String query = userQuery;

		// check if input is a sentence
		if (query.contains(" ")) {
			queryword = query.split(" ");
			// check each word whether it is in dictionary
			for (int i = 0; i < queryword.length; i++) {

				// check word if in wordDic
				if (!(wordDic.contains(queryword[i]))) {
					System.out.println(queryword[i]);
					spellSuggest = spell.suggestSimilar(queryword[i], 1);
					// if (spellSuggest != null) {
					// correctedQuery = spellSuggest[0];
					// }

					// replacing the wrong word with the suggested words
					query = query.replace(queryword[i], spellSuggest[0]);

				}

			}

			newQuery.add(query);

			// System.out.println("1" + newQuery.get(0));

			// check if query words is changed
			if (newQuery.get(0) != userQuery) {
				return newQuery;
			} else {
				return null;
			}

		} else {
			if (!(wordDic.contains(query))) {
				suggestions = spell.suggestSimilar(query, 3);
				System.out.println(suggestions[0] + " " + suggestions[1] + " "
						+ suggestions[2]);
				// add to newQuery arraylist
				newQuery.add(suggestions[0]);
				newQuery.add(suggestions[1]);
				newQuery.add(suggestions[2]);

				return newQuery;
			} else {
				return null;
			}
		}

	}

	/**
	 * Temporary List to store all Dictionary words for Halyuuu search engine
	 * 
	 * @throws IOException
	 */
	private void addWords() throws IOException {
		// Add Dictionary words in Has
		FileInputStream fstream = new FileInputStream(dicFile);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = br.readLine()) != null) {

			wordDic.add(line);

		}

	}

}
