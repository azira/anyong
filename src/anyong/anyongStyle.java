package anyong;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class anyongStyle {
	
	public static void main(String[] args) {
		//resizeThumb("tmp/320px-Can_You_Hear_My_Heart.jpg");
	}

	public List resizeThumb(String imgSrc) {
		
		List thumbs = new ArrayList();
		File img = new File("/Users/Azira/Documents/Assignment/anyong/WebContent/" + imgSrc);
		SimpleImageInfo imageInfo = null;
		try {
			imageInfo = new SimpleImageInfo(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//System.out.println(imageInfo.getHeight());
		//System.out.println(imageInfo.getWidth());
		if (imageInfo.getHeight() > imageInfo.getWidth()) {
			thumbs.add("150px");
			thumbs.add("95px");
		} else if (imageInfo.getHeight() == imageInfo.getWidth()) {
			thumbs.add("141px");
			thumbs.add("141px");
		}
		else {
			thumbs.add("95px");
			thumbs.add("150px");
		}
	
		
		return thumbs;
	}

}
