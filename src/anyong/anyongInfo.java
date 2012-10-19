package anyong;

/**
 * 
 * @author Hazirah Hamdani
 * File name: anyongInfo
 * Description: To get information of drama for the Drama Information webpage
 *
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.commons.lang.StringUtils;

public class anyongInfo {

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
				Document doc = Jsoup.parse(new URL(weburl).openStream(),
						"UTF-8", weburl);

				String content = doc.text();
				// System.out.println(content);

				// get summary
				int synopStart = content.lastIndexOf("Synopsis");
				int synopEnd = content.lastIndexOf("Cast");
				if ((synopStart == -1) || (synopEnd == -1)) {
					dramaInfo.add("No information available");
					
				} else {
					String synopsis = content.substring(synopStart + 9,
							synopEnd);
					dramaInfo.add(synopsis);
					//System.out.println(synopsis);
				}

				// get genre
				int genreStart = content.lastIndexOf("Genre");
				int genreEnd = content.lastIndexOf("Episodes");
				
				if ((genreStart == -1) || (genreEnd == -1) || (genreEnd < genreStart)){
					dramaInfo.add("No information available");

				} else {
					String genre = content.substring(genreStart + 7, genreEnd);
					dramaInfo.add(genre);
					//System.out.println(genre);
				}

				// get episodes
				int epiEnd = content.lastIndexOf("Broad");
				if ((epiEnd == -1) || (genreEnd == -1)) {
					dramaInfo.add("No information available");

				} else {
					String episodes = content.substring(genreEnd + 10,
							genreEnd + 9 + 4);
					dramaInfo.add(episodes);
					//System.out.println(episodes);
				}

				// get broadcastnetwork
				String network = content.substring(epiEnd - 4, epiEnd);
				if ((epiEnd == -1)) {
					dramaInfo.add("No information available");

				} else {
					dramaInfo.add(network);
					// System.out.println(network);
				}

				// get casts
				String htmlSource = doc.html();
				int castStart = htmlSource.lastIndexOf("Cast");
				int castEnd = htmlSource
						.lastIndexOf("name=\"Production_Credits\"");

				if ((castEnd == -1) || (castStart == -1)) {
					dramaInfo.add("No Information available");

				} else {
					String casts = htmlSource.substring(castStart, castEnd);

					String[] theCasts = casts.split("\n");
					List newCasts = new ArrayList();
					for (int i = 2; i < theCasts.length - 2; i++) {
						if (theCasts[i].equals("\n")) {
							continue;
						}
						String tmp = theCasts[i].replaceAll("\\<.*?\\>", "");
						tmp = tmp.replace(",", "");
						// tmp = tmp.replace("(???)","");
						newCasts.add(StringUtils.stripToNull(tmp));
					}

					casts = StringUtils.join(newCasts, ",");

					dramaInfo.add(casts);

				}
				return dramaInfo;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	/**
	 * Get ratings of drama
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
				if ((ratingsStart != -1) || (ratingsEnd != -1)) {
					String ratings = content.substring(ratingsStart + 8,
							ratingsEnd) + "votes)";
					// System.out.println(ratings);
					return ratings;
				} else {
					return "No information available";
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}

		}

	}
}
