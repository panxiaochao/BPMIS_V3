package org.pxcbpmisframework.core.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneParser {

	private static String STROE_INDEX = "E:\\index\\lucene\\"; // 存放索引文件的路径

	// private static String SEARCH_INDEX = "E:/index/lucene/";
	public static void main(String[] args) throws IOException {
		// 建立要索引的文件
		new LuceneParser().SearchLucene();
	}

	/**
	 * 
	 * @Title: CreateLucene
	 * @Description: TODO(创建索引)
	 */
	public void CreateLucene() {
		System.out.println("----------开始索引！");
		long currenttime = System.currentTimeMillis();
		IndexWriter indexWriter = null;
		try {
			Directory directory = FSDirectory.open(new File(STROE_INDEX));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,
					new StandardAnalyzer(Version.LUCENE_46));
			indexWriter = new IndexWriter(directory, config);
			// 创建索引模式：CREATE，覆盖模式； APPEND，追加模式
			if (isFile(STROE_INDEX)) {
				config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
			} else {
				config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			}

			FieldType fieldType = new FieldType();
			fieldType.setIndexed(false);// set 是否索引
			fieldType.setStored(true);// set 是否存储
			fieldType.setTokenized(true);// set 是否分类
			fieldType.setOmitNorms(false); // 

			Document doc = new Document();
			/*
			 * TYPE_NOT_STORED 索引，分词，不存储 TYPE_STORED 索引，分词，存储
			 */
			doc.add(new Field("content", "中国的首都在北京", TextField.TYPE_STORED));

			indexWriter.addDocument(doc);
			long endtime = System.currentTimeMillis();
			System.out.println("----------索引成功！耗时：" + (endtime - currenttime)
					+ "毫秒");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void SearchLucene() {
		IndexReader reader = null;
		String keywords = "中";
		try {
			reader = DirectoryReader.open(FSDirectory
					.open(new File(STROE_INDEX)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
			QueryParser parser = new QueryParser(Version.LUCENE_46, "content",
					new StandardAnalyzer(Version.LUCENE_46));
			// String[] fields;
			// QueryParser parser = new MultiFieldQueryParser(LUCENE_46, fields,
			// analyzer);
			Query query = parser.parse(keywords);
			System.out.println("Searching for: " + query.toString());

			TopDocs topDocs = searcher.search(query, null, 100);
			ScoreDoc[] hits = topDocs.scoreDocs;

			// 高亮显示关键字，如果内容中本来就有<span></span>，可能导致显示错乱
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
					"<span>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
					new QueryScorer(query));

			System.out.println("results.totalHits:" + topDocs.totalHits);
			System.out.println("查询结果数：" + hits.length);

			for (int n = 0; n < hits.length; n++) {
				System.out.println("Score doc " + n + " is "
						+ hits[n].toString());
				Document doc = searcher.doc(hits[n].doc);
				System.out.println("搜索的结果title：" + doc.get("content"));

				TokenStream tokenStream = analyzer.tokenStream("content",
						new StringReader(doc.get("content")));
				String str = highlighter.getBestFragment(tokenStream, doc
						.get("content"));

				System.out.println("Score doc " + n + " hightlight to: " + str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: isFile
	 * @Description: TODO(判断是否有索引文件存在)
	 */
	public boolean isFile(String filename) {
		File indexFile = new File(filename);
		if (!indexFile.exists()) { // 不存在有索引文件
			try {
				indexFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return false;
		} else {
			if (indexFile.list().length > 0)
				return true;
			return false;
		}

	}

}
