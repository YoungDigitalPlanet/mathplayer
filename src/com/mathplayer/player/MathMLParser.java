package com.mathplayer.player;

import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.interaction.Gap;
import com.mathplayer.player.model.layoutschematas.BarType;
import com.mathplayer.player.model.layoutschematas.FenceType;
import com.mathplayer.player.model.layoutschematas.MBar;
import com.mathplayer.player.model.layoutschematas.MFenced;
import com.mathplayer.player.model.layoutschematas.MFraction;
import com.mathplayer.player.model.layoutschematas.MRoot;
import com.mathplayer.player.model.layoutschematas.MRow;
import com.mathplayer.player.model.layoutschematas.MSubSup;
import com.mathplayer.player.model.layoutschematas.MTable;
import com.mathplayer.player.model.layoutschematas.MUnderOver;
import com.mathplayer.player.model.tokens.MEmpty;
import com.mathplayer.player.model.tokens.MIdentifier;
import com.mathplayer.player.model.tokens.MNumber;
import com.mathplayer.player.model.tokens.MOperator;
import com.mathplayer.player.model.tokens.MStringLiteral;
import com.mathplayer.player.style.StyleContext;
import com.mathplayer.player.utils.XmlUtils;

public abstract class MathMLParser {

	public static Token parse(String source){
		Document dom = XMLParser.parse(source);
		StyleContext styleContext = new StyleContext();
		return parseElement(dom.getDocumentElement(), styleContext);
	}

	private static Token parseElement(Element element, StyleContext styleContext){

		String nodeName = element.getNodeName().toLowerCase();

		try {

			StyleContext currStyleContext = styleContext.clone();
			currStyleContext.parseElement(element);

			if (nodeName.equals("mrow")){
				int nodesCount = XmlUtils.getChildElementNodesCount(element);
				Vector<Token> tokens = new Vector<Token>();
				for (int n = 0 ; n < nodesCount ;  n ++){
					tokens.add( parseElement( XmlUtils.getChildElementNodeAtIndex(n, element) , currStyleContext ));
				}
				return new MRow(tokens);
			} else if (nodeName.equals("mtable")){
				Vector<Vector<Token>> tokens = new Vector<Vector<Token>>();
				NodeList trNodes = element.getElementsByTagName("mtr");
				for (int row = 0 ; row < trNodes.getLength() ; row ++ ){
					if (trNodes.item(row).getNodeType() == Node.ELEMENT_NODE){
						NodeList tdNodes = ((Element)trNodes.item(row)).getElementsByTagName("mtd");
						tokens.add(new Vector<Token>());
						for (int col = 0 ; col < tdNodes.getLength() ; col ++ ){
							if (tdNodes.item(col).getNodeType() == Node.ELEMENT_NODE){
								tokens.get(row).add(parseElement( XmlUtils.getChildElementNodeAtIndex(0, (Element) tdNodes.item(col) ) , currStyleContext ));
							}
						}
					}

				}
				return new MTable(tokens);

			} else if (nodeName.equals("mfrac")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MFraction(t1, t2);
			} else if (nodeName.equals("msubsup")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				Token t3 = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currStyleContext);
				return new MSubSup(t1, t2, t3);
			} else if (nodeName.equals("msub")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MSubSup( t1, t2, null);
			} else if (nodeName.equals("msup")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MSubSup( t1, null ,t2);
			}  else if (nodeName.equals("munderover")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				Token t3 = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currStyleContext);
				return new MUnderOver( t1, t2, t3 );
			} else if (nodeName.equals("munder")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MUnderOver( t1, t2, null);
			} else if (nodeName.equals("mover")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MUnderOver( t1, null, t2);
			} else if (nodeName.equals("mroot")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currStyleContext);
				return new MRoot( t1, t2);
			} else if (nodeName.equals("msqrt")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				return new MRoot( t1, null);
			} else if (nodeName.equals("mfenced")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currStyleContext);
				return new MFenced( t1,
						FenceType.fromString(XmlUtils.getAttribute(element, "open")),
						FenceType.fromString(XmlUtils.getAttribute(element, "close")));
			} else if (nodeName.equals("mi")){
				MIdentifier mi = new MIdentifier( XmlUtils.getFirstTextNode(element).toString() );
				mi.setStyleContext(currStyleContext);
				return mi;
			} else if (nodeName.equals("mn")){
				MNumber mn = new MNumber( XmlUtils.getFirstTextNode(element).toString() );
				mn.setStyleContext(currStyleContext);
				return mn;
			} else if (nodeName.equals("mo")){
				String content = XmlUtils.getFirstTextNode(element).toString();
				if (content != null  &&  "OverBar".equals(content.trim()))
					return new MBar(BarType.SINGLE);
				else if (content != null  &&  "DoubleOverBar".equals(content.trim()))
					return new MBar(BarType.DOUBLE);
				else if (content != null  &&  "RightArrow".equals(content.trim()))
					return new MBar(BarType.ARROW);
				else if (content != null  &&  "&gt;".equals(content.trim()))
					return new MOperator(">");
				else if (content != null  &&  "&lt;".equals(content.trim()))
					return new MOperator("<");
				MOperator mo = new MOperator( content );
				mo.setStyleContext(currStyleContext);
				return mo;
			} else if (nodeName.equals("ms")){
				Node node = XmlUtils.getFirstTextNode(element);
				String value = "";
				if (node != null) {
					value = node.getNodeValue();
				}
				MStringLiteral ms = new MStringLiteral(value);
				ms.setStyleContext(currStyleContext);
				return ms;
			} else if (nodeName.equals("gap")){
				return new Gap();
			}
		} catch (Exception e) {
			System.out.println("Problem with the node: " + nodeName);
			e.printStackTrace();
		}
		return new MEmpty();
	}
}
