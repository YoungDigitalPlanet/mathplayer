package com.mathplayer.player;

import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.interaction.CustomField;
import com.mathplayer.player.model.interaction.Gap;
import com.mathplayer.player.model.layoutschematas.BarType;
import com.mathplayer.player.model.layoutschematas.FenceType;
import com.mathplayer.player.model.layoutschematas.MBar;
import com.mathplayer.player.model.layoutschematas.MFenced;
import com.mathplayer.player.model.layoutschematas.MFraction;
import com.mathplayer.player.model.layoutschematas.MRoot;
import com.mathplayer.player.model.layoutschematas.MRow;
import com.mathplayer.player.model.layoutschematas.MTable;
import com.mathplayer.player.model.layoutschematas.MUnderOver;
import com.mathplayer.player.model.layoutschematas.expansible.MUnderOverExpansible;
import com.mathplayer.player.model.layoutschematas.mmultiscripts.MMultiScripts;
import com.mathplayer.player.model.signs.expansible.ExpansibleSign;
import com.mathplayer.player.model.tokens.MEmpty;
import com.mathplayer.player.model.tokens.MIdentifier;
import com.mathplayer.player.model.tokens.MNumber;
import com.mathplayer.player.model.tokens.MOperator;
import com.mathplayer.player.model.tokens.MSpace;
import com.mathplayer.player.model.tokens.MStringLiteral;
import com.mathplayer.player.model.tokens.MText;
import com.mathplayer.player.parsers.ExpansibleOperatorParser;
import com.mathplayer.player.style.Context;
import com.mathplayer.player.utils.XmlUtils;

public abstract class MathMLParser {

	private static final ExpansibleOperatorParser EXPANSIBLE_OPERATOR_PARSER = new ExpansibleOperatorParser();
	
	public static Token parse(String source){
		source = source.replaceAll("&semi;", ";");
		Document dom = XMLParser.parse(source);
		Context styleContext = new Context();
		return parseElement(dom.getDocumentElement(), styleContext);
	}

	private static Token parseElement(Element element, Context context){

		String nodeName = element.getNodeName().toLowerCase();

		try {

			Context currContext = context.clone();
			currContext.parseElement(element);

			if (nodeName.equals("mrow")){
				int nodesCount = XmlUtils.getChildElementNodesCount(element);
				Vector<Token> tokens = new Vector<Token>();
				for (int n = 0 ; n < nodesCount ;  n ++){
					Element childElement = XmlUtils.getChildElementNodeAtIndex(n, element);
					Token parsedToken = parseElement( childElement , currContext );
					tokens.add( parsedToken);
				}
				return new MRow(tokens);
			} else if (nodeName.equals("mtable")){
				Vector<Vector<Token>> tokens = new Vector<Vector<Token>>();
				NodeList trNodes = element.getChildNodes();
				
				for (int row = 0 ; row < trNodes.getLength() ; row ++){
					if (trNodes.item(row).getNodeType() == Node.ELEMENT_NODE  &&  "mtr".equals(trNodes.item(row).getNodeName())){
						NodeList tdNodes = element.getChildNodes().item(row).getChildNodes();
						tokens.add(new Vector<Token>());
						for (int col = 0 ; col < tdNodes.getLength() ; col ++ ){
							if (tdNodes.item(col).getNodeType() == Node.ELEMENT_NODE  &&  "mtd".equals(tdNodes.item(col).getNodeName())){
								tokens.get(tokens.size()-1).add(parseElement( XmlUtils.getChildElementNodeAtIndex(0, (Element) tdNodes.item(col) ) , currContext ));
							}
						}
					}
				}
				MTable mTable = new MTable(tokens);
				mTable.setColumnAlign(XmlUtils.getAttribute(element, "columnalign"));
				return mTable;
			} else if (nodeName.equals("mfrac")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				return new MFraction(t1, t2);
			} else if (nodeName.equals("mmultiscripts")){
				return parseMultiscriptsElement(element, currContext);
			} else if (nodeName.equals("msubsup")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				Token t3 = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currContext);
				return new MMultiScripts(t1, t2, t3, null, null);
			} else if (nodeName.equals("mlsubsup")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				Token t3 = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currContext);
				return new MMultiScripts(t1, null, null, t2, t3);
			} else if (nodeName.equals("msub")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				return new MMultiScripts( t1, t2, null, null, null);
			} else if (nodeName.equals("msup")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				return new MMultiScripts( t1, null ,t2, null, null);
			} else if (nodeName.equals("munderover")){
				return parseMUnderOver(element, currContext);
			} else if (nodeName.equals("munder")){
				return parseMUnderToken(element, currContext);
			} else if (nodeName.equals("mover")){
				return parseMOverToken(element, currContext);
			} else if (nodeName.equals("mroot")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				Token t2 = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
				return new MRoot( t1, t2);
			} else if (nodeName.equals("msqrt")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				return new MRoot( t1, null);
			} else if (nodeName.equals("mfenced")){
				Token t1 = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
				return new MFenced( t1,
						FenceType.fromString(XmlUtils.getAttribute(element, "open")),
						FenceType.fromString(XmlUtils.getAttribute(element, "close")));
			} else if (nodeName.equals("mi")){
				String content = XmlUtils.getFirstTextNode(element).toString();
				content = XmlUtils.unescape(content);
				MIdentifier mi = new MIdentifier( content );
				mi.setStyleContext(currContext);
				return mi;
			} else if (nodeName.equals("mn")){
				String content = XmlUtils.getFirstTextNode(element).toString();
				content = XmlUtils.unescape(content);
				MNumber mn = new MNumber( content );
				mn.setStyleContext(currContext);
				return mn;
			} else if (nodeName.equals("mtext")){
				String content = XmlUtils.getFirstTextNode(element).toString();
				content = XmlUtils.unescape(content);
				MText mn = new MText( content );
				mn.setStyleContext(currContext);
				return mn;
			} else if (nodeName.equals("mo")){
				return parseMOperator(element, currContext);
			} else if (nodeName.equals("ms")){
				Node node = XmlUtils.getFirstTextNode(element);
				String value = "";
				if (node != null) {
					value = node.getNodeValue();
				}
				MStringLiteral ms = new MStringLiteral(value);
				ms.setStyleContext(currContext);
				return ms;
			} else if (nodeName.equals("mspace")){
				double width = 0;
				if (element.hasAttribute("width")){
					width = Double.parseDouble(element.getAttribute("width").replace("em", ""));
				}
				return new MSpace(width);
			} else if (nodeName.equals("gap")){
				if (element.hasAttribute("type")){
					String type;
					if (!"".equals(element.getAttribute("type") )){
						type = element.getAttribute("type");
					} else {
						type = "default";
					}
					String uid = null;
					if (element.hasAttribute("uid"))
						uid = element.getAttribute("uid");
					return new CustomField(GapIdentifier.createCombinedIdentifier(type, uid));
				}
				return new Gap();
			}
		} catch (Exception e) {
			System.out.println("Problem with the node: " + nodeName);
			e.printStackTrace();
		}
		return new MEmpty();
	}

	private static Token parseMOverToken(Element element, Context currContext) {
		Element baseElement = XmlUtils.getChildElementNodeAtIndex(0, element);
		Token overToken = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
		
		Token underOverToken = createCorrecrUnderOverToken(baseElement, null, overToken, currContext);
		return underOverToken;
	}

	private static Token parseMUnderToken(Element element, Context currContext) {
		Element baseElement = XmlUtils.getChildElementNodeAtIndex(0, element);
		Token underToken = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
		
		Token underOverToken = createCorrecrUnderOverToken(baseElement, underToken, null, currContext);
		return underOverToken;
	}

	private static Token parseMUnderOver(Element element, Context currContext) {
		Token underContent = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
		Token overContent = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currContext);
		Element baseElement = XmlUtils.getChildElementNodeAtIndex(0, element);
		
		Token underOverToken = createCorrecrUnderOverToken(baseElement, underContent, overContent, currContext);
		return underOverToken;
	}
	
	private static Token createCorrecrUnderOverToken(Element baseElement, Token underContent, Token overContent, Context currContext) {
		Token underOverToken = null;
		
		boolean isExpansibleOperator = EXPANSIBLE_OPERATOR_PARSER.isExpansibleOperator(baseElement);
		if(isExpansibleOperator){
			ExpansibleSign expansibleSign = EXPANSIBLE_OPERATOR_PARSER.parseExpansibleSign(baseElement);
			underOverToken = new MUnderOverExpansible(expansibleSign, underContent, overContent);
		}else{
			Token base = parseElement(baseElement , currContext);
			underOverToken = new MUnderOver( base, underContent, overContent);
		}
		return underOverToken;
	}

	private static Token parseMOperator(Element element, Context currContext) {
		String content = XmlUtils.getFirstTextNode(element).toString();
		
		MOperator mo = null;
		if(content != null){
			String trimedContent = content.trim();
			if ("OverBar".equals(trimedContent))
				mo = new MBar(BarType.SINGLE);
			else if ("DoubleOverBar".equals(trimedContent))
				mo = new MBar(BarType.DOUBLE);
			else if ("RightArrow".equals(trimedContent))
				mo = new MBar(BarType.ARROW);
			else if ("&gt;".equals(trimedContent))
				mo = new MOperator(">");
			else if ("&lt;".equals(trimedContent))
				mo = new MOperator("<");
		}
		if(mo == null){
			mo = new MOperator(content);
			mo.setStyleContext(currContext);
		}
		return mo;
	}

	private static Token parseMultiscriptsElement(Element element, Context currContext) {
		Token baseToken = parseElement(XmlUtils.getChildElementNodeAtIndex(0, element) , currContext);
		Token subToken = parseElement(XmlUtils.getChildElementNodeAtIndex(1, element) , currContext);
		Token supToken = parseElement(XmlUtils.getChildElementNodeAtIndex(2, element) , currContext);
		int n = 3;
		Element el;
		while ( (el = XmlUtils.getChildElementNodeAtIndex(n, element)) != null ) {
			if (el.getNodeName().equals("mprescripts"))
				break;
			n++;
		}
		Token leftSubToken = parseElement(XmlUtils.getChildElementNodeAtIndex(++n, element) , currContext);
		Token leftSupToken = parseElement(XmlUtils.getChildElementNodeAtIndex(++n, element) , currContext);
		
		return new MMultiScripts(baseToken, subToken, supToken, leftSubToken, leftSupToken);
	}

}
