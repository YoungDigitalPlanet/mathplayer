package com.mathplayer.player.model.layoutschematas;

import eu.ydp.gwtutil.client.StringUtils;
import gwt.g2d.client.graphics.Surface;

import java.util.List;
import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;

public class MTable extends LayoutSchemata {

	private String COLUMN_ALIGN_LEFT = "left";
	private String COLUMN_ALIGN_CENTER = "center";
	
	protected Vector<Vector<Token>> tokensArray;
	protected Vector<Double> cellWidths;
	protected Vector<Double> cellHeights;
	protected final double MARGIN_VERTICAL = 0.3d;
	protected final double MARGIN_HORIZONTAL = 0.5d;
	protected String columnAlign = COLUMN_ALIGN_LEFT;

	public MTable(Vector<Vector<Token>> tokensArray){
		this.tokensArray = tokensArray;
		tokens = new Vector<Token>();
		for (int row = 0 ; row < tokensArray.size() ; row ++){
			for (int col = 0 ; col < tokensArray.get(row).size() ; col ++){
				tokens.add(tokensArray.get(row).get(col));
			}
		}
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null) {
			return size.clone();
		}

		cellWidths = new Vector<Double>();
		cellHeights = new Vector<Double>();

		for (int row = 0 ; row < tokensArray.size() ; row ++){
			for (int col = 0 ; col < tokensArray.get(row).size() ; col ++){

				Size tokenSize = tokensArray.get(row).get(col).measure(socket);

				if (col < cellWidths.size()){
					if (tokenSize.width > cellWidths.get(col) ){
						cellWidths.set(col,  tokenSize.width );
					}
				} else {
					cellWidths.add(  tokenSize.width );
				}

				if (row < cellHeights.size()){
					if (tokenSize.height > cellHeights.get(row)){
						cellHeights.set(row,  tokenSize.height);
					}
				} else {
					cellHeights.add( tokenSize.height );
				}
			}
		}

		size = new Size();

		for (int row = 0 ; row < cellHeights.size() ; row ++){
			size.height += cellHeights.get(row);
		}
		size.height += font.size*MARGIN_VERTICAL*(cellHeights.size()-1);

		for (int col = 0 ; col < cellWidths.size() ; col ++){
			size.width += cellWidths.get(col);
		}
		size.width += font.size*MARGIN_HORIZONTAL*(cellWidths.size()-1);

		size.middleLine = size.height / 2;

		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		Area next = exactArea.clone();

		for (int row = 0 ; row < tokensArray.size() ; row ++){
			Area nextRow = next.clone();
			for (int col = 0 ; col < tokensArray.get(row).size() ; col ++){
				Area nextCol = nextRow.clone();
				Token currToken = tokensArray.get(row).get(col);
				nextCol.x += getMargin(cellWidths.get(col), currToken.measure(socket).width);
				nextCol.y += (cellHeights.get(row) - currToken.measure(socket).height)/2;
				nextCol.setSize(currToken.measure(socket).width, currToken.measure(socket).height, currToken.measure(socket).middleLine);
				currToken.render(canvas, nextCol, socket);
				nextRow.x += cellWidths.get(col) + font.size*MARGIN_HORIZONTAL;
			}
			next.y += cellHeights.get(row) + font.size*MARGIN_VERTICAL;
		}
	}
	
	
	protected double getMargin(double cellWidth, double tokenWidth) {
		if (columnAlign.equals(COLUMN_ALIGN_CENTER)) {
			return (cellWidth - tokenWidth) / 2;
		} else {
			return MARGIN_HORIZONTAL;
		}
	}
	
	public void setColumnAlign(String columnAlign) {
		if ( !columnAlign.equals(StringUtils.EMPTY_STRING) ) {
			this.columnAlign = columnAlign;
		}
	}

	@Override
	public String toString() {
		String str = "||";
		for (List<Token> currRow : tokensArray){
			for (Token currToken : currRow){
				str += currToken.toMathML() + " ";
			}
			str += "|";
		}
		str = str.substring(0, str.length()-1);
		str += "||";
		return str;
	}

	@Override
	public String toMathML() {
		String mml = "<mtable>";
		for (List<Token> currRow : tokensArray){
			mml += "<mtr>";
			for (Token currToken : currRow){
				mml += "<mtd>" + currToken.toMathML() + "</mtd>";
			}
			mml += "</mtr>";
		}
		mml += "</mtable>";
		return mml;
	}

}
