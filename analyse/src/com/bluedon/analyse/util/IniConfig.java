package com.bluedon.analyse.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;


/**
 * @see http://ini4j.sourceforge.net
 * @author Fdong
 */
public  class IniConfig {

    private static final Ini ini = new Ini();
    private static final String INI_FILE = "lucene.properties";
    
    static {
        InputStream in = IniConfig.class.getClassLoader().getResourceAsStream(INI_FILE);
        if (in == null) {
            in = IniConfig.class.getResourceAsStream(INI_FILE);
        }
        try {
            ini.load(in);
        } catch (InvalidFileFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Ini getIni() {
        return ini;
    }
    
    public static Map<String, String> getSectionMap(String sectionName) {
        return (Map<String, String>) ini.get(sectionName);
    }
    
    public static Map<String, String> getSectionExpressionMap(String sectionName) {
        Map<String, String> map = new HashMap<String, String>();
        Section section = ini.get(sectionName);
        Set<String> keySet = section.keySet();
        for (String key : keySet) {
            map.put(key, section.fetch(key));
        }
        return map;
    }
    
    public static List<String> getSectionList(String sectionName) {
        Map<String, String> map = getSectionMap(sectionName);
        List<String> list = new ArrayList<String>();
        if (map != null) {
            list = new ArrayList<String>(map.values());
        }
        return list;
    }
    
    public static List<String> getSectionExpressionList(String sectionName) {
        Map<String, String> map = getSectionExpressionMap(sectionName);
        List<String> list = new ArrayList<String>();
        if (map != null) {
            list = new ArrayList<String>(map.values());
        }
        return list;
    }
    
    public static String get(String sectionName, String key) {
        return getIni().get(sectionName, key);
    }
    
    public static String fetch(String sectionName, String key) {
        return getIni().fetch(sectionName, key);
    }
    
    public void  readConfig() throws IOException {
    	/**��һ�ֶ�ȡ�ļ���ʽ*/
//    	String s_config="conf/config.properties";
    	String s_config = "lucene.properties";
    	InputStream in = ClassLoader.getSystemResourceAsStream(s_config);
    	if( in == null ){
    	 System.out.println("��"+ s_config +"ʧ�ܣ�");
    	}else
    	{
    	Properties properties = new Properties();
    	properties.load(in);
    	//�������Ϳ���ͨ��properties.getProperty(String obj)�����Խ���������Ϣ��ȡ��
    	} 
    	
    	/**�ڶ��ֶ�ȡ�ļ���ʽ*/
//    	File f = new File(this.getClass().getResource("/").getPath());
//    	f = new File(f.getPath() +"/conf/config.properties");
//    	System.out.println(this.getClass().getResource("/"));
    }
    
   
    
    public static void main(String[] args) throws IOException {
//    	System.out.println(getSectionList("lucene_fileRootPaths"));
//    	for (String string : getSectionList("lucene_fileRootPaths")) {
//			System.out.println(string);
//		}
    	
//        IniConfig i = new IniConfig();
//    	i.readConfig();

    }

    
}


