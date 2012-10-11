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
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class spellCheck {

	private File directory;
	private File dictionary;
	private File dictionary_word;
	private IndexWriterConfig config;
	private String DIC_FILE = "/Users/Azira/Documents/Assignment/anyong/src/LuceneSearch/dictionary.txt";
	private String DIC_WORD = "/Users/Azira/Documents/Assignment/anyong/src/LuceneSearch/thedic.txt";
	private static final String INDEX_DIR = "/Users/Azira/Documents/Assignment/anyong/index";
	private Set<String> wordDic = new TreeSet<String>();

	public spellCheck() throws IOException {

		directory = new File(INDEX_DIR);
		dictionary = new File(DIC_FILE);
		dictionary_word = new File(DIC_WORD);
		config = new IndexWriterConfig(Version.LUCENE_40, null);
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

	public List correctWords(String userQuery) {

		if (!directory.exists() || !dictionary.exists()) {
			System.out
					.println("Error: dictionary directory could not be found "
							+ directory);
			return null;
		} else {

			try {

				Searcher searcher = new Searcher();
				List<List<String>> dramaList = searcher.findByTitle(userQuery);
				// check if search not returns null
				if (dramaList.isEmpty()) {
				
					String query = userQuery.toLowerCase();

					if (query.contains(" ")) {
						List<String> newQuery = new ArrayList<String>();
						SpellChecker spell = new SpellChecker(
								FSDirectory.open(directory));

						spell.indexDictionary(new PlainTextDictionary(
								dictionary), config, true);

						String[] suggestions = spell.suggestSimilar(query, 3);
						// if there's no suggestions
						if (suggestions.length == 0) {
							return null;
						} else {

							// add to newQuery arraylist
							for (int i = 0; i < suggestions.length; i++) {
								newQuery.add(suggestions[i]);

							}

							return newQuery;
						}

					} else {
						List<String> newQuery = new ArrayList<String>();
						SpellChecker spell = new SpellChecker(
								FSDirectory.open(directory));

						spell.indexDictionary(new PlainTextDictionary(
								dictionary_word), config, true);

						String[] suggestions = spell.suggestSimilar(query, 3);
						// if there's no suggestions
						if (suggestions.length == 0) {
							return null;
						} else {

							// add to newQuery arraylist
							for (int i = 0; i < suggestions.length; i++) {
								newQuery.add(suggestions[i]);

							}

							return newQuery;
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
		return null;

	}

	/**
	 * Temporary List to store all Dictionary words
	 * 
	 * @throws IOException
	 */
	private void addWords() throws IOException {
		// Add Dictionary words in Has
		FileInputStream fstream = new FileInputStream(DIC_FILE);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = br.readLine()) != null) {

			wordDic.add(line);

		}

	}

}
