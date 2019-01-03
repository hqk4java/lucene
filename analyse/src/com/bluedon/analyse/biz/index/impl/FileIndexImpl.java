package com.bluedon.analyse.biz.index.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import com.bluedon.analyse.biz.index.FileIndex;
import com.bluedon.analyse.biz.sameword.SameAnalyzer;
import com.bluedon.analyse.biz.sameword.impl.SameWordImpl;
import com.bluedon.analyse.model.Doc;
import com.bluedon.analyse.util.IniConfig;
import com.bluedon.analyse.util.StringUtil;



/**
 * 
 * @author Fdong
 *
 */
public class FileIndexImpl implements FileIndex{
	private static Directory directory = null;
	private static List<File> fileList = new  ArrayList<File>();
	static {
		try {
			directory = FSDirectory.open(new File(IniConfig.get("lucene_indexDBPath", "indexDBPath")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Directory getDirectory() {
		return directory;
	}
	
	/**
	 * 开始初始化索引库
	 */
	public   void InitIndex(boolean  hasNew)  {
		IndexWriter writer = null;
		try {
			
			Analyzer mySameAnalyzer = new SameAnalyzer(new SameWordImpl());
			writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36,mySameAnalyzer));
			if(hasNew) {
				writer.deleteAll();
			}
			Document document = null;
			//将要需要创建索引的文件加载
			fileList  = getFileList();
			if(fileList!=null && fileList.size()!=0) {
				List<File> patchFileList = null;
				for (File f:fileList) {
					patchFileList = new ArrayList<File>();
					patchFileList.add(f);
					Doc doc = new Doc();
					doc.setId(UUID.randomUUID().toString());
					doc.setOperationPlatform("oa");
					doc.setPatchfile(patchFileList);
					doc.setDate(new Date().getTime());
					document =  doc2Document(doc);
					if(document!=null)
						writer.addDocument(document);
				}
			}
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 

		}
	}
	
	/**
	 * 批量删除一个索引库中一个元素
	 */
	public  Integer delIndex(ArrayList<Doc> doc)  {	
			Integer rs = 1; 
			if(doc!=null && doc.size()!=0) {
				for (Doc d : doc) {
					Integer r = delIndex(d);
					rs *= r;
				}
			}	
			return rs;
	}
	
	/**
	 * 删除一个索引库中一个元素
	 */
	public  Integer delIndex(Doc doc){
		IndexWriter writer = null;
		Analyzer mySameAnalyzer = new SameAnalyzer(new SameWordImpl());
				
		try {
			writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36,mySameAnalyzer));
			if(doc==null || doc.getId()==null){
				return 0;
			} 
			//此时删除的文档并不会完全删除，而是存储在一个回站中，可以恢复
			writer.deleteDocuments(new Term("id", doc.getId()));
			writer.forceMergeDeletes(); //清空回收站,应该加个定时器定时清空回收站
			writer.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	/**
	 *批量更新索引 
	 */
	public Integer updateIndex(ArrayList<Doc> Doc) {
		Integer rs =  1;
		if(Doc!=null && Doc.size()!=0) {
			for (Doc a : Doc) {
				Integer  r = updateIndex(a);
				rs *= r ;
			}
		}
		return rs;
	}
	/**
	 *更新索引 
	 */
	public  Integer updateIndex(Doc doc) {
		
		IndexWriter writer = null;
		Document document = null;
		Analyzer mySameAnalyzer = new SameAnalyzer(new SameWordImpl());
		try {  
			writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36,mySameAnalyzer));
			if(doc==null) {
				return 0;
			}
			document = doc2Document(doc);
			writer.updateDocument(new Term("id", doc.getId()), document);

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
				try {
					if(writer!=null) 
						writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
		}
		return 1;
	}
	
	
	/**
	 * 批量新增一个索引
	 */
	public  Integer addIndex(ArrayList<Doc> Doc) {
		if(Doc!=null && Doc.size()!=0) {
			for (Doc a : Doc) {
				//delIndex(getRecord(a));
				addIndex(a);
			}
		}
		return 1;
	}
	
	
	/**
	 *新增一个索引
	 */
	public  Integer addIndex(Doc  doc){
		IndexWriter writer = null;
		Document doccument = null;
		Analyzer mySameAnalyzer = new SameAnalyzer(new SameWordImpl());
		try {
			writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36,mySameAnalyzer));
			if(doc==null) 
				return 0;
			doccument = doc2Document(doc);
			writer.addDocument(doccument);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer!=null) 
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return 1; //成功反回 1
	}
	

	
	
	
	/**
	 * 将文件转化为doccument
	 */
	public  Document doc2Document(Doc doc){
		
	    StringBuilder fileArea = new StringBuilder();  //文本域
	    StringBuilder patchFileName = new StringBuilder();  //文件名称
	    String patchFileNameStr = new String();  //文件名称
	    BufferedReader br = null;
		Document document = new Document();
		Reader reader = null;
		Metadata  metadata = null;
		if(doc==null) 
			return null; 
		if(doc.getPatchfile()!=null) {
			List<File> fileList = doc.getPatchfile();
			for (File file : fileList) {
				metadata = new Metadata();
				try {
					reader = new Tika().parse(new FileInputStream(file),metadata);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				br = new BufferedReader(reader);
			    String line;
			    try {
					while((line=br.readLine())!=null) {
						//System.out.println(line);
						fileArea.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(br!=null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
			    patchFileName = patchFileName.append(file.getAbsolutePath()+"!!##&&%%"); 
			}
			if(patchFileName!=null) {
				patchFileNameStr = patchFileName.substring(0,(patchFileName.length()-8));
			}
		}
		
		document.add(new Field("id",StringUtil.trimNULL(doc.getId()),Field.Store.YES,Field.Index.NOT_ANALYZED ));
		document.add(new Field("content",StringUtil.trimNULL(doc.getContent()),Field.Store.YES,Field.Index.ANALYZED));
		document.add(new Field("title",StringUtil.trimNULL(doc.getTitle()),Field.Store.YES,Field.Index.ANALYZED));
		document.add(new Field("patchfile",StringUtil.trimNULL(patchFileNameStr),Field.Store.YES,Field.Index.ANALYZED));
		document.add(new Field("fileArea",StringUtil.trimNULL(fileArea.toString()),Field.Store.YES,Field.Index.ANALYZED ));
		document.add(new Field("fileType",StringUtil.trimNULL(doc.getFileType()),Field.Store.YES,Field.Index.NOT_ANALYZED));
		document.add(new Field("operationPlatform",StringUtil.trimNULL(doc.getOperationPlatform()),Field.Store.YES,Field.Index.NOT_ANALYZED));
		document.add(new NumericField("date",Field.Store.YES,true).setLongValue(doc.getDate()));
		document.add(new Field("navi",StringUtil.trimNULL(doc.getNavi()),Field.Store.YES,Field.Index.NOT_ANALYZED));
		document.add(new Field("url",StringUtil.trimNULL(doc.getUrl()),Field.Store.YES,Field.Index.NO));//这是不能被搜索的，它只是被搜索内容的附属物。如URL等
	    return document;
	}
	
	
	public  Doc getFileAttribute(File f) {
		Doc Doc = new Doc();
		
		
		
		return Doc;
	}
	 /**
	  * 通过递归得到某一路径下所有的目录及其文件
	  */
	 public static List<File> getFiles(String filePath,List<String> removePaths, List<String> removeFiles){
		 
		 File path = new File(filePath);
		//如果文件夹不存在则创建    
		if (!path.exists()  && !path.isDirectory())      
		{       
		    System.out.println("//不存在");  
		    path.mkdirs();
		} 
	    File root = new File(filePath);
	    File[] files = root.listFiles();
	    
	    for(File file:files){     
	     if(file.isDirectory()){		      
//	               递归调用,并过滤不需要的文件		        
	       	if(removePaths.contains(file.getAbsolutePath().replaceAll("\\\\", "/"))) {
	       		continue;
	       	}
	    	getFiles(file.getAbsolutePath(),removePaths,removeFiles);  
	     }else{
		     if(removeFiles.contains(file.getAbsolutePath().replaceAll("\\\\", "/"))) {
		       	continue;
		     }
		     File f = new File(file.getAbsolutePath().replaceAll("\\\\", "/"));
		     if(f.getAbsolutePath().endsWith("txt")) {
		    	 fileList.add(f); 
		     }
		     
	     }     
	    }
	    for (File fl : fileList) {
			System.out.println(fl.getAbsolutePath());
		}
	    return  fileList;
	 }
	/**
	 * 获取所有需要创建索引的文件
	 */
	public  List<File>  getFileList() {
		
	    List<String> filePaths = (ArrayList<String>)IniConfig.getSectionList("lucene_fileRootPaths");
	    List<String> removePaths = (ArrayList<String>)IniConfig.getSectionList("lucene_removePaths");
	    List<String> removeFiles = (ArrayList<String>)IniConfig.getSectionList("lucene_removeFilePaths");
		  
	    for (String p : filePaths) {
	    	fileList = getFiles(p,removePaths,removeFiles);
		}
	    return fileList;
	}
	
	public  ArrayList<Doc> getRecord(Doc a) {
		IndexReader reader = null;
		ArrayList<Doc> DocList = new ArrayList<Doc>();
		try {
			reader = IndexReader.open(directory);
		
		IndexSearcher searcher = new IndexSearcher(reader);
		TermQuery query = new TermQuery(new Term("id",a.getId()));
		TopDocs tds = searcher.search(query, 10);
		for (ScoreDoc sd :tds.scoreDocs) {
			Doc art =new Doc();
			Document doc = searcher.doc(sd.doc);
            art.setId(doc.get("id"));
            DocList.add(art);
		}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DocList;
		
	}
}
