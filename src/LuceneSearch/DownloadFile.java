package LuceneSearch;

import java.net.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class DownloadFile
{
  public static void main(String[] args) throws IOException
  {
	  
      String folder = null;
      String weburl = "http://wiki.d-addicts.com/static/images/thumb/3/31/Can_You_Hear_My_Heart.jpg/320px-Can_You_Hear_My_Heart.jpg";
      
   
      int indexname = weburl.lastIndexOf("/");
      
      
      if (indexname == weburl.length()) {
          weburl = weburl.substring(1, indexname);
      }
      
      indexname = weburl.lastIndexOf("/");
      String name = weburl.substring(indexname +1, weburl.length());
      
      System.out.println(name);
	  //Open a URL Stream
      URL url = new URL(weburl);
      InputStream in = url.openStream();
      
      
      File f = new File("/Users/Azira/Documents/Assignment/anyoung/WebContent/tmp");
      if (!f.exists()) {
    	  f.mkdir();
    	  
      }
      
      OutputStream out = new BufferedOutputStream(new FileOutputStream(f + "/" + name));
      
      for (int b; (b = in.read()) != -1;) {
          out.write(b);
      }
      out.close();
      in.close();
  }

}