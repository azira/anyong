package LuceneSearch;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class LuceneTest {

        public static void main(String[] args) throws IOException, ParseException {
        	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

            // Store the index in memory:
            Directory directory = new RAMDirectory();
            // To store an index on disk, use this instead:
            //Directory directory = FSDirectory.open("/tmp/testindex");
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            Document doc = new Document();
            String text = "This is the text to be indexed.";
            String text2 = "This was the text to be indexed";
            String text3 = "Was this the text to be indexed";
            doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
            doc.add(new Field("fieldname", text2, TextField.TYPE_STORED));
            doc.add(new Field("fieldname", text3, TextField.TYPE_STORED));
            iwriter.addDocument(doc);
            iwriter.close();
            
            // Now search the index:
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "fieldname", analyzer);
            Query query = parser.parse("was");
            ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
            //assertEquals(1, hits.length);
            // Iterate through the results:
            for (int i = 0; i < hits.length; i++) {
              Document hitDoc = isearcher.doc(hits[i].doc);
             System.out.println(hitDoc.get("fieldname"));
            }
            ireader.close();
            directory.close();
        }
}