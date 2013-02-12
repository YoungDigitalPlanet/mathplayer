package com.mathplayer.player.parsers;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.model.signs.expansible.ExpansibleRightArrow;
import com.mathplayer.player.model.signs.expansible.ExpansibleRightLeftHarpoons;
import com.mathplayer.player.model.signs.expansible.ExpansibleSign;
import com.mathplayer.player.utils.XmlUtils;

public class ExpansibleOperatorParser {

	private static final List<String> EXPANSIBLE_OPERATORS = Arrays.asList("RightArrow", "RightLeftHarpoons");
	
	public ExpansibleSign parseExpansibleSign(Element element){
		String content = XmlUtils.getFirstTextNode(element).toString();
		
		ExpansibleSign expansibleSign = null;
		if("RightArrow".equals(content)){
			expansibleSign = new ExpansibleRightArrow();
		}else if("RightLeftHarpoons".equals(content)){
			expansibleSign = new ExpansibleRightLeftHarpoons();
		}else{
			throw new RuntimeException(content+" is unknown expansible operator!");
		}
		
		return expansibleSign;
	}
	
	public boolean isExpansibleOperator(Element element){
		String nodeName = element.getNodeName().toLowerCase();
		
		boolean isExpansibleOperator = false;
		if("mo".equals(nodeName)){
			String content = XmlUtils.getFirstTextNode(element).toString();
			
			isExpansibleOperator = EXPANSIBLE_OPERATORS.contains(content);
		}
		return isExpansibleOperator;
	}
	
}
