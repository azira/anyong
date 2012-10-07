package anyong;

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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.lucene.queryParser.ParseException;

public class downloads {

	// location where the index will be stored.
	private static final String wikipedia = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/awikipedia.txt";
	private static final String asianwiki = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/asianwiki.txt";
	private static final String daddicts = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/d-addicts.txt";

	public static void main(String[] args) throws ParseException, IOException {
		
		getSynopsis("Arang and The Magistrate");
	
	}
	
	
	/**
	 * Get description of website
	 * 
	 * @param weburl
	 * @return webcontents without HTML codes
	 * @throws IOException
	 */
	public static String getSynopsis(String title) throws IOException {
		try {
			//System.out.println(title);
			FileInputStream fstream;
			fstream = new FileInputStream(wikipedia);
		
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		String weburl = null;
		HashMap <String, String> dramaList = new HashMap<String, String>();
		while ((line = br.readLine()) != null) {
				if (line.contains("/**")) {
					continue;
				}
				
				String[] drama = line.split("::");
				dramaList.put(drama[0], drama[1]);
		}
		
		System.out.println(dramaList.get(title));
		
		if (weburl == null) {
			return "No website summary available";
		} else {
			Document doc = Jsoup.connect(weburl).timeout(0).get();
			String content = doc.text();
			int genreStart = content.lastIndexOf("Synopsis");
			int genreEnd = content.lastIndexOf("Cast");
			String webcontent = content.substring(genreStart, genreEnd);
			System.out.println(webcontent);
			return webcontent;
		}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "No website summary available";
	}

	
}
