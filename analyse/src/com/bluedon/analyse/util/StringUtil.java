package com.bluedon.analyse.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.io.SAXReader;
/**
 * @classname: StringUtil
 * @desc : 字符处理工具类
 * @author Fdong
 */
public class StringUtil {
	public static String replaceBlank(String str) {
		String dest = "";

		if (str != null) {
			Pattern p = Pattern.compile("\\|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
    
	public static String trimNULL(String str) {
		if(str==null || str.equals(null)) 
			return "";
		return str;
	}

	/**
	 * 此方法把汉字转换为ASCII
	 */
	public static String gbkToAscii(String gbkStr){//字符串转换为ASCII码

		  char[]chars=gbkStr.toCharArray(); //把字符中转换为字符数组 
         StringBuffer sb=new  StringBuffer();
//         System.out.println("\n\n汉字 ASCII\n-------1---------------");
         for(int i=0;i<chars.length;i++)
         {
//       	  System.out.println(" "+chars[i]+" "+(int)chars[i]);
       	  sb.append("%"+(int)chars[i]);
         }
        
		    return sb.toString();
		 }
   
	/**
	 * 此方法把ASCII转换为汉字
	 */
	public static String asciiToGbk(String asciiStr){//ASCII转换为字符串

		  String[]chars=asciiStr.split("%");
		  StringBuffer sb=new StringBuffer();
		        for(int i=1;i<chars.length;i++){ 
		        	sb.append((char)Integer.parseInt(chars[i]));
//		            System.out.println((char)Integer.parseInt(chars[i]));
		        } 
		        return sb.toString();
		 }
	/** 
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！) 
     * 
     * @param res            原字符串 
     * @param filePath 文件路径 
     * @return 成功标记 
     */ 
    public static boolean string2File(String res, String filePath) { 
            boolean flag = true; 
            BufferedReader bufferedReader = null; 
            BufferedWriter bufferedWriter = null; 
            try { 
                    File distFile = new File(filePath); 
                    if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs(); 
                    bufferedReader = new BufferedReader(new StringReader(res)); 
                    bufferedWriter = new BufferedWriter(new FileWriter(distFile)); 
                    char buf[] = new char[1024];         //字符缓冲区 
                    int len; 
                    while ((len = bufferedReader.read(buf)) != -1) { 
                            bufferedWriter.write(buf, 0, len); 
                    } 
                    bufferedWriter.flush(); 
                    bufferedReader.close(); 
                    bufferedWriter.close(); 
            } catch (IOException e) { 
                    e.printStackTrace(); 
                    flag = false; 
                    return flag; 
            } finally { 
                    if (bufferedReader != null) { 
                            try { 
                                    bufferedReader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
                    } 
            } 
            return flag; 
    }


    /** 
     * 文本文件转换为指定编码的字符串 
     * 
     * @param file         文本文件 
     * @param encoding 编码类型 
     * @return 转换后的字符串 
     * @throws IOException 
     */ 
    public static String file2String(File file, String encoding) { 
            InputStreamReader reader = null; 
            StringWriter writer = new StringWriter(); 
            try { 
                    if (encoding == null || "".equals(encoding.trim())) { 
                            reader = new InputStreamReader(new FileInputStream(file), encoding); 
                    } else { 
                            reader = new InputStreamReader(new FileInputStream(file)); 
                    } 
                    //将输入流写入输出流 
                    char[] buffer = new char[1000]; 
                    int n = 0; 
                    while (-1 != (n = reader.read(buffer))) { 
                            writer.write(buffer, 0, n); 
                    } 
            } catch (Exception e) { 
                    e.printStackTrace(); 
                    return null; 
            } finally { 
                    if (reader != null) 
                            try { 
                                    reader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
            } 
            //返回转换结果 
            if (writer != null) 
                    return writer.toString(); 
            else return null; 
    }
    /**
	 * 去掉时间中的毫秒数
	 * @param date
	 * @return
	 */
	public static String replaceDate(Date date){
		DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newdate="";
		if(date!=null && !"".equals(date)){
			newdate=d.format(date);
		}
		return newdate;
	}


}
