package LuceneSearch;

/**
 * 
 * @author Hazirah Hamdani
 * Title: Halyuuu
 * Description: Korea drama search using Lucene
 * 
 * Tutorial from 
 * http://fazlansabar.blogspot.com.au/2012/06/apache-lucene-tutorial-lucene-for-text.html
 * Credit: http://lucene.apache.org/core/
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

public class Lucene {

	// location where the index will be stored.
	private static final String INDEX_DIR = "/Users/Azira/Documents/Assignment/anyoung/index";
	private static final String dataFiles = "/Users/Azira/Documents/Assignment/anyoung/src/LuceneSearch/data";
	private static ArrayList<File> queue = new ArrayList<File>();

	public static void main(String[] args) throws ParseException, IOException {



		// Remember to comment if already have index
		 //indexList();

		// creating the Searcher to the same index location as the Indexer
    	Searcher searcher = new Searcher();

		String query = "love";
		
		// Check if it returns empty 

		// Spell check query
		spellCheck checker = new spellCheck();
		List spellCheck = checker.correctWords(query);
		if (spellCheck != null) {
			System.out.println("The Query was: " + query);
			System.out.println("Do you mean: ");
			Iterator spellCheckr = spellCheck.iterator();
	        while (spellCheckr.hasNext()) {  
	        	System.out.println(spellCheckr.next() + "");  
	 
	        }  

		} else {
			 
			  List<List<String>> dramaList = searcher.findByTitle(query);
			for (int i=0; i < dramaList.size(); i++) {
				List drama = dramaList.get(i);
				
					System.out.println(drama.get(0));
					System.out.println(drama.get(1));
					
			}
			

		}
		searcher.close();
	}

	public static void indexList() throws IOException {
		// deleting indexes
		File indexF = new File(INDEX_DIR);
		//check if directory exists
    	if(indexF.exists()){
           try{
 
               delete(indexF);
 
           }catch(IOException e){
               e.printStackTrace();
               System.exit(0);
           }
        }
		
		// Add Files need to be indexed
		addFiles(new File(
				dataFiles));
		Indexer indexer = new Indexer(INDEX_DIR);
		// Each file, index line by line
		for (File f : queue) {
			FileReader file = null;

			try {

				file = new FileReader(f);
				FileInputStream fstream = new FileInputStream(f);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				@SuppressWarnings("resource")
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
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File deleteFile = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(deleteFile);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
}
