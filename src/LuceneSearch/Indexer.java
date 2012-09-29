package LuceneSearch;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class Indexer
{
    private IndexWriter writer;
   
    public Indexer(String indexdir) throws IOException {
        // create the index
    	File file = new File(indexdir);
        if(writer == null) {
        writer = new IndexWriter(FSDirectory.open(
                file), new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
        }
        writer.deleteAll();
       
    }

    /** 
      * Indexing items
      */
    public void index(indexDrama indexItem) throws IOException {

        // deleting the item, if already exists
        

        Document doc = new Document();

       
        doc.add(new Field(indexDrama.TITLE, indexItem.getTitle(), Field.Store.YES, Field.Index.ANALYZED)); 
        doc.add(new Field(indexDrama.WEBURL, indexItem.getweburl(), Field.Store.YES, Field.Index.ANALYZED));
      //doc.setBoost(1.5f);
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
