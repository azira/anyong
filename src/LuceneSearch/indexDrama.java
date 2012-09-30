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
   
  
    public static final String TITLE = "title";
    public static final String WEBURL = "weburl";
    

    public indexDrama(String title, String weburl) {
      
        this.title = title;
        this.weburl = weburl;
       
    }


    public String getTitle() {
        return title;
    }
 
    public String getweburl() {
        return weburl;
    }

    @Override
    public String toString() {
        return title;
    }
}
