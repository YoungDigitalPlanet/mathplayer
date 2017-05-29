package com.mathplayer.player.utils;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public abstract class XmlUtils {

	private XmlUtils(){}

	public static Element getChildElementNodeAtIndex(int index, Element element){
		int counter = -1;
		for (int n = 0 ; n < element.getChildNodes().getLength() ; n ++){
			if (element.getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
				counter ++;
			}
			if (counter == index)
				return ((Element)element.getChildNodes().item(n));
		}
		return null;
	}
	
	public static Node getFirstTextNode(Element element){

		for (int n = 0 ; n < element.getChildNodes().getLength() ; n ++){
			if (element.getChildNodes().item(n).getNodeType() == Node.TEXT_NODE){
				return element.getChildNodes().item(n);
			}
		}
		return null;
	}

	public static boolean hasChildElementNodes(Element element){

		for (int n = 0 ; n < element.getChildNodes().getLength() ; n ++){
			if (element.getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
				return true;
			}
		}
		return false;
	}
	
	public static int getChildElementNodesCount(Element element){
		int counter = 0;
		for (int n = 0 ; n < element.getChildNodes().getLength() ; n ++){
			if (element.getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
				counter ++;;
			}
		}
		return counter;
	}
	
	public static String getAttribute(Element element, String attrName){
		if (element.hasAttribute(attrName))
			return element.getAttribute(attrName);
		
		return "";
	}

	public static String escape(String src){
	    StringBuffer b = new StringBuffer();
	    String[] x = src.split("(?=[;&<>\'\"])", -1);
	    for (int i = 0; i < x.length; i++) {
	      if (x[i].startsWith(";")) {
	        b.append("&semi;");
	        b.append(x[i].substring(1));
	      } else if (x[i].startsWith("&")) {
	        b.append("&amp;");
	        b.append(x[i].substring(1));
	      } else if (x[i].startsWith("\"")) {
	        b.append("&quot;");
	        b.append(x[i].substring(1));
	      } else if (x[i].startsWith("'")) {
	        b.append("&apos;");
	        b.append(x[i].substring(1));
	      } else if (x[i].startsWith("<")) {
	        b.append("&lt;");
	        b.append(x[i].substring(1));
	      } else if (x[i].startsWith(">")) {
	        b.append("&gt;");
	        b.append(x[i].substring(1));
	      } else {
	        b.append(x[i]);
	      }
	    }
	    return b.toString();
	}
	
	public static String unescape(String src){
		src = src.replaceAll("&apos;", "'");
		src = src.replaceAll("&quot;", "\"");
		src = src.replaceAll("&semi;", ";");
		src = src.replaceAll("&amp;", "&");
		src = src.replaceAll("&lt;", "<");
		src = src.replaceAll("&gt;", ">");
		return src;
	}
}
