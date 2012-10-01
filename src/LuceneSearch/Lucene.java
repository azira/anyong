package LuceneSearch;

/**
 * 
 * @author Hazirah Hamdani

 * Title: anyoung
 * Description: Korea drama search using Lucene
 * 
 * Tutorial from 
 * http://fazlansabar.blogspot.com.au/2012/06/apache-lucene-tutorial-lucene-for-text.html
 * Credit: http://lucene.apache.org/core/
 */
import edu.depauw.csc.dcheeseman.wgetjava.*;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.lucene.queryParser.ParseException;

public class Lucene {

	// location where the index will be stored.
	private static final String INDEX_DIR = "/Users/Azira/Documents/Assignment/anyoung/index";
	private static final String dataFiles = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data";
	private static ArrayList<File> queue = new ArrayList<File>();

	public static void main(String[] args) throws ParseException, IOException {
		// Remember to comment if already have index
		// indexList();

		// creating the Searcher to the same index location as the Indexer
		Searcher searcher = new Searcher();

		String content = getImageLink("http://asianwiki.com/Love_Rain_(Korean_Drama)");
		
		String query = "love";

		// Check if it returns empty

		// Spell check query
		spellCheck checker = new spellCheck();

		List spellCheck = checker.correctWords(query);
		if (spellCheck != null) {
			System.out.println("The Query was: " + query);
			System.out.println("Do you mean: ");

			for (int i = 0; i < spellCheck.size(); i++) {
				System.out.println(spellCheck.get(i) + " ");

			}

		} else {

			List<List<String>> dramaList = searcher.findByTitle(query);
			// if dramaList returns null = index files does not exist

			// if dramaList returns empty - no results
			if (dramaList.isEmpty()) {
				System.out.println("Could not find " + query);
			}

			// print results if found
			else {
				System.out.println("You results are:");
				for (int i = 0; i < dramaList.size(); i++) {
					List drama = dramaList.get(i);
					System.out.println(drama.get(0));
					System.out.println(drama.get(1));
				}
			}
		}
	}

	public static void indexList() throws IOException {
		// deleting indexes
		File indexF = new File(INDEX_DIR);
		// check if directory exists
		if (indexF.exists()) {
			delete(indexF);
		}

		// Add Files need to be indexed
		addFiles(new File(dataFiles));

		Indexer indexer = new Indexer(INDEX_DIR);
		// Each file, index line by line
		for (File f : queue) {
			FileReader file = null;

			try {

				file = new FileReader(f);
				FileInputStream fstream = new FileInputStream(f);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);

				BufferedReader dramaList = new BufferedReader(
						new InputStreamReader(in));
				String drama;
				// Read File Line By Line

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
					indexDrama indexItem = new indexDrama(title, weburl);
					indexer.index(indexItem);

				}

			} catch (Exception e) {
				System.out.println("Could not add: " + f);
			} finally {
				file.close();
			}
		}

		System.out.println("Successfully added index files");
		// close the index to enable them index
		indexer.close();

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

	/**
	 * Deleting index directory if creating new index
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				System.out.println("Directory is deleted : "
						+ file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File deleteFile = new File(file, temp);

					// recursive delete
					delete(deleteFile);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	/**
	 * Get description of website
	 * 
	 * @param weburl
	 * @return webcontents without HTML codes
	 * @throws IOException
	 */
	public static String getDramaText(String weburl) throws IOException {
		String asianwiki = "asianwiki";
		String wikipedia = "wikipedia";
		String daddicts = "d-addicts";
		Document doc = Jsoup.connect(weburl).timeout(0).get();
		if (weburl.contains(asianwiki) || (weburl.contains(wikipedia))) {
			String content = doc.text();
			int plotLoc = content.lastIndexOf("Plot");
			String webcontent = content.substring(plotLoc + 5, plotLoc + 300)
					+ "...";
			return webcontent;
		}

		if (weburl.contains(daddicts)) {
			String content = doc.text();
			int SypnosisLoc = content.lastIndexOf("Synopsis");
			String webcontent = content.substring(SypnosisLoc + 9,
					SypnosisLoc + 300) + "...";
			return webcontent;
		}

		return "No website summary available";
	}

	/***
	 * Get photo to be used as thumbnail
	 * 
	 * if no pic available in website, view no pic
	 * 
	 * @param weburl
	 * @return
	 * @throws IOException
	 */

	public static String getImageLink(String weburl) throws IOException {
		String asianwiki = "asianwiki";
		String wikipedia = "wikipedia";
		String daddicts = "d-addicts";
		try {
			Document doc = Jsoup.connect(weburl).timeout(0).get();
			Elements content = doc.getElementsByTag("img");
			if ((content.size() == 0)) {
				return "images/nopic.png";

			} else {

				String imageSrc = content.get(0).toString();

				if (weburl.contains(asianwiki)) {
					int imageStart = imageSrc.indexOf("http://");
					int imageEnd = imageSrc.lastIndexOf(".jpg") + 4;
					imageSrc = imageSrc.substring(imageStart, imageEnd);
					System.out.println(imageSrc);
					return imageSrc;
				}

				if (weburl.contains(daddicts)) {
					return "images/nopic.png";
//
//					int imageStart = imageSrc.indexOf("/static");
//					int imageEnd = imageSrc.lastIndexOf(".jpg") + 4;
//					
//					if (imageSrc.contains("JPG")) {
//						imageEnd = imageSrc.lastIndexOf(".JPG") + 4;
//					}
//					imageSrc = imageSrc.substring(imageStart, imageEnd);
//					URL url1 = new URL(weburl);
//
//					imageSrc = "http://" + url1.getHost() + imageSrc;
//					System.out.println(imageSrc);
//					return imageSrc;

				}

				if (weburl.contains(wikipedia)) {
					return "images/nopic.png";
				}

				return "images/nopic.png";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "images/nopic.png";
			
		}

	}
}
