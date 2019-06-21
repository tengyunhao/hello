package com.codem.hello.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import java.io.InputStream;

public class XMlUtils {
	
	//1。获取DOM 解析器的工厂实例。  
    static DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();  
    
    public static Document get(InputStream is) {
    		//2。获得具体的DOM解析器。  
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
}
