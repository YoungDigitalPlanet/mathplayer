package com.mathplayer.player;

import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.interaction.Gap;
import com.mathplayer.player.model.layoutschematas.FenceType;
import com.mathplayer.player.model.layoutschematas.MFenced;
import com.mathplayer.player.model.layoutschematas.MFraction;
import com.mathplayer.player.model.layoutschematas.MRoot;
import com.mathplayer.player.model.layoutschematas.MRow;
import com.mathplayer.player.model.layoutschematas.MSubSup;
import com.mathplayer.player.model.layoutschematas.MTable;
import com.mathplayer.player.model.layoutschematas.MUnderOver;
import com.mathplayer.player.model.layoutschematas.MVector;
import com.mathplayer.player.model.tokens.MIdentifier;
import com.mathplayer.player.model.tokens.MNumber;
import com.mathplayer.player.model.tokens.MOperator;
import com.mathplayer.player.model.tokens.MStringLiteral;
import com.mathplayer.player.utils.XmlUtils;

public abstract class MathMLParser {

	public static Token parse(String source){
		Document dom = XMLParser.parse(source);
		return parseElement(dom.getDocumentElement());
	}
	
	private static Token parseElement(Element element){
		
		String nodeName = element.getNodeName().toLowerCase();
		
		if (nodeName.equals("mrow")){
			int nodesCount = XmlUtils.getChildElementNodesCount(element);
			Vector<Token> tokens = new Vector<Token>();
			for (int n = 0 ; n < nodesCount ;  n ++){
				tokens.add( parseElement( XmlUtils.getChildElementNodeAtIndex(n, element) ));
			}
			return new MRow(tokens);
		} else if (nodeName.equals("mtable")){
			Vector<Vector<Token>> tokens = new Vector<Vector<Token>>();
			NodeList trNodes = element.getElementsByTagName("mtr");
			for (int row = 0 ; row < trNodes.getLength() ; row ++ ){
				if (trNodes.item(row).getNodeType() == Node.ELEMENT_NODE){
					NodeList tdNodes = ((Element)trNodes.item(row)).getElementsByTagName("mtd");
					tokens.add(new Vector<Token>());
					for (int col = 0 ; col < trNodes.getLength() ; col ++ ){
						if (tdNodes.item(col).getNodeType() == Node.ELEMENT_NODE){
							tokens.get(row).add(parseElement( XmlUtils.getChildElementNodeAtIndex(0, (Element) tdNodes.item(col) ) ));
						}
					}
				}
				 
			}
			return new MTable(tokens);
			
		} else if (nodeName.equals("mfrac")){
			return new MFraction(parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)));
		} else if (nodeName.equals("msubsup")){
			return new MSubSup( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)),
					parseElement(XmlUtils.getChildElementNodeAtIndex(2, element)));
		} else if (nodeName.equals("msub")){
			return new MSubSup( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)),
					null);
		} else if (nodeName.equals("msup")){
			return new MSubSup( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					null,
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)));
		}  else if (nodeName.equals("munderover")){
			return new MUnderOver( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)),
					parseElement(XmlUtils.getChildElementNodeAtIndex(2, element)));
		} else if (nodeName.equals("munder")){
			return new MUnderOver( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)),
					null);
		} else if (nodeName.equals("mover")){
			return new MUnderOver( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					null,
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)));
		} else if (nodeName.equals("mroot")){
			return new MRoot( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					parseElement(XmlUtils.getChildElementNodeAtIndex(1, element)));
		} else if (nodeName.equals("msqtr")){
			return new MRoot( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), null);
		} else if (nodeName.equals("mfenced")){
			return new MFenced( parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)), 
					FenceType.fromString(XmlUtils.getAttribute(element, "open")),
					FenceType.fromString(XmlUtils.getAttribute(element, "close")));
		} else if (nodeName.equals("mi")){
			return new MIdentifier( XmlUtils.getFirstTextNode(element).toString() );
		} else if (nodeName.equals("mn")){
			return new MNumber( XmlUtils.getFirstTextNode(element).toString() );
		} else if (nodeName.equals("mo")){
			return new MOperator( XmlUtils.getFirstTextNode(element).toString() );
		} else if (nodeName.equals("ms")){
			return new MStringLiteral( XmlUtils.getFirstTextNode(element).toString() );
		} else if (nodeName.equals("mvector")){
			return new MVector(  parseElement(XmlUtils.getChildElementNodeAtIndex(0, element)) , "arrow".equals(XmlUtils.getAttribute(element, "decoration")));
		} else if (nodeName.equals("gap")){
			return new Gap();
		}
		
		return null;
	}
}
