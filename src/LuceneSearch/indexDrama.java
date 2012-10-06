package LuceneSearch;

/**
 * 
 * @author Hazirah
 * POJO Class to create index drama
 *
 */

public class indexDrama {


    private String title;
    private String weburl;
    private String filename;
   
  
    public static final String TITLE = "title";
    public static final String WEBURL = "weburl";
    public static final String FILENAME = "filename";
    

    public indexDrama(String title, String weburl, String filename) {
      
        this.title = title;
        this.weburl = weburl;
        this.filename = filename;
       
    }


    public String getTitle() {
        return title;
    }
 
    public String getweburl() {
        return weburl;
    }
    
    public String getFilename() {
    	return filename;
    }

    @Override
    public String toString() {
        return title;
    }
}
