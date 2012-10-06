package LuceneSearch;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class Indexer
{
    private IndexWriter writer;
   private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
    public Indexer(String indexdir) throws IOException {
        // create the index
    	File file = new File(indexdir);
    
        if(writer == null) {
        writer = new IndexWriter(NIOFSDirectory.open(
                file), new IndexWriterConfig(Version.LUCENE_40, analyzer));
        }
        
  
       
    }

    /** 
      * Indexing items
      */
    public void index(indexDrama indexItem) throws IOException {
        Document doc = new Document();

       
        doc.add(new Field(indexDrama.TITLE, indexItem.getTitle(), TextField.TYPE_STORED)); 
        doc.add(new Field(indexDrama.WEBURL, indexItem.getweburl(), StringField.TYPE_STORED));
        doc.add(new Field(indexDrama.FILENAME, indexItem.getFilename(), StringField.TYPE_STORED));
        
     
        // add the document to the index
        writer.addDocument(doc);
    }

    /**
      * Closing the index
      */
    public void close() throws IOException {
        writer.close();
    }
}
