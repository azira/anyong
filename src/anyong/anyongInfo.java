package anyong;

/**
 * 
 * @author Hazirah Hamdani
 *
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.StringUtils;


public class anyongInfo {

	// location where the index will be stored.
	private static final String wikipedia = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/awikipedia.txt";
	private static final String asianwiki = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/asianwiki.txt";
	private static final String daddicts = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data/d-addicts.txt";

	public static void main(String[] args) {

		getDramaInfo("http://wiki.d-addicts.com/Faith");
		getRatings("http://asianwiki.com/Faith_(Korean_Drama)");

	}

	/**
	 * Get description of website
	 * 
	 * @param weburl
	 * @return webcontents without HTML codes
	 * @throws IOException
	 */
	public static List getDramaInfo(String weburl) {
		// Get from d-addicts - Summary, genre, episodes, broadcastnetwork &
		// casts
		List dramaInfo = new ArrayList();

		if (weburl == null) {
			return null;
		} else {
			try {
				Document doc = Jsoup.parse(new URL(weburl).openStream(), "UTF-8", weburl);
					

				String content = doc.text();
				//System.out.println(content);

				// get summary
				int synopStart = content.lastIndexOf("Synopsis");
				int synopEnd = content.lastIndexOf("Cast");
				String synopsis = content.substring(synopStart + 9, synopEnd);
				dramaInfo.add(synopsis);
				//System.out.println(synopsis);

				// get genre
				int genreStart = content.lastIndexOf("Genre");
				int genreEnd = content.lastIndexOf("Episodes");
				String genre = content.substring(genreStart+7, genreEnd);
				dramaInfo.add(genre);
				//System.out.println(genre);

				// get episodes
				int epiEnd = content.lastIndexOf("Broad");
				String episodes = content.substring(genreEnd+10, genreEnd+9+4);
				dramaInfo.add(episodes);
				//System.out.println(episodes);
				
				// get broadcastnetwork
				String network = content.substring(epiEnd-4, epiEnd);
				dramaInfo.add(network);
				//System.out.println(network);
				
				// get casts
				String htmlSource = doc.html();
				int castStart = htmlSource.lastIndexOf("Cast");
				int castEnd = htmlSource.lastIndexOf("name=\"Production_Credits\"");
				String casts = htmlSource.substring(castStart,castEnd);
				
				String[] theCasts = casts.split("\n");
				List newCasts = new ArrayList();
				for(int i=2; i < theCasts.length-2; i++) {
					if (theCasts[i].equals("\n")) {
						continue;
					}
					String tmp = theCasts[i].replaceAll("\\<.*?\\>", "");
					tmp = tmp.replace(",", "");
					tmp = tmp.replace("(???)","");
					newCasts.add(StringUtils.stripToNull(tmp));
				}
				//System.out.println(newCasts);
				casts = StringUtils.join(newCasts, ",");
				
				dramaInfo.add(casts);
				
				return dramaInfo;
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	/**
	 * Get description of website
	 * 
	 * @param weburl
	 * @return webcontents without HTML codes
	 * @throws IOException
	 */
	public static String getRatings(String weburl) {
		if (weburl == null) {
			return null;
		} else {
		
		
		try {
		Document doc = Jsoup.connect(weburl).timeout(0).get();
		
		String content = doc.text();
		int ratingsStart = content.lastIndexOf("rating: ");
		int ratingsEnd = content.lastIndexOf("votes)");
		String ratings = content.substring(ratingsStart+8, ratingsEnd) + "votes)";
		//System.out.println(ratings);
		return ratings;
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		}

	}
}
