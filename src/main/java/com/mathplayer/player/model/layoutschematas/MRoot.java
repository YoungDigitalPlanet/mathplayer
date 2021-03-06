package com.mathplayer.player.model.layoutschematas;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.*;
import com.mathplayer.player.model.signs.RootSign;
import com.mathplayer.player.model.tokens.MNumber;
import java.util.Vector;

public class MRoot extends LayoutSchemata {

	protected RootSign rootSign;

	public MRoot(Token base, Token index) {
		tokens = new Vector<>();
		tokens.add(base);
		tokens.add(index);
		rootSign = new RootSign();
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		tokens.get(0).setFont(font);
		if (tokens.get(1) != null)
			tokens.get(1).setFont(font.cloneShrunk());
		rootSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;

		size = tokens.get(0).measure(socket);

		size.width += rootSign.getBetweenMargin() + rootSign.getOverhangRightMargin() + rootSign.getContentMargin();

		if (tokens.get(1) == null || rootSign.getOverhangLeftMargin() > tokens.get(1).measure(socket).width) {
			size.width += rootSign.getOverhangLeftMargin();
		} else {
			size.width += tokens.get(1).measure(socket).width;
		}

		if (tokens.get(1) == null || isBaseHigher(socket)) {
			size.height += rootSign.getOverMargin();
			size.middleLine += rootSign.getOverMargin();
		} else {
			size.height += tokens.get(1).measure(socket).height + rootSign.getOverhangLeftHeight() - tokens.get(0).measure(socket).height;
			size.middleLine += tokens.get(1).measure(socket).height + rootSign.getOverhangLeftHeight() - tokens.get(0).measure(socket).height;
		}

		return size;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		double indexWidth = rootSign.getOverhangLeftMargin();
		if (tokens.get(1) != null && indexWidth < tokens.get(1).measure(socket).width)
			indexWidth = tokens.get(1).measure(socket).width;

		Area baseArea = null;

		if (tokens.get(1) == null || isBaseHigher(socket)) {
			baseArea = new Area(exactArea.x + indexWidth + rootSign.getBetweenMargin() + rootSign.getContentMargin(),
								exactArea.y + rootSign.getOverMargin(),
								tokens.get(0).measure(socket));
			tokens.get(0).render(canvas, baseArea, socket);
		} else {
			baseArea = new Area(exactArea.x + indexWidth + rootSign.getBetweenMargin() + rootSign.getContentMargin(),
								exactArea.y + tokens.get(1).measure(socket).height + rootSign.getOverhangLeftHeight() - tokens.get(0).measure(socket).height,
								tokens.get(0).measure(socket));
			tokens.get(0).render(canvas, baseArea, socket);
		}

		if (tokens.get(1) != null) {
			Area indexArea = exactArea.clone();

			if (rootSign.getOverhangLeftMargin() > tokens.get(1).measure(socket).width) {
				indexArea.x += rootSign.getOverhangLeftMargin() - tokens.get(1).measure(socket).width;
			} else {
				indexArea.x += 0;
			}

			if (isBaseHigher(socket)) {
				indexArea.y += tokens.get(0).measure(socket).height + rootSign.getOverMargin() - rootSign.getOverhangLeftHeight() - tokens.get(1).measure(socket).height;
			} else {
				indexArea.y += 0;
			}

			indexArea.setSize(tokens.get(1).measure(socket));

			tokens.get(1).render(canvas, indexArea, socket);
		}

		rootSign.render(canvas, baseArea, socket);

	}

	@Override
	public String toString() {
		String s = tokens.get(0).toString();
		if (!(tokens.get(0) instanceof MNumber))
			s = "(" + s + ")";

		if (tokens.get(1) != null) {
			s += "$";
			if (tokens.get(0) instanceof ContentToken)
				s += tokens.get(1).toString();
			else
				s = "(" + tokens.get(1).toString() + ")";
		}

		return s;
	}

	@Override
	public String toMathML() {
		if (tokens.get(1) != null)
			return "<mroot>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + "</mroot>";
		return "<msqrt>" + tokens.get(0).toMathML() + "</msqrt>";
	}

	private boolean isBaseHigher(InteractionSocket socket) {
		return tokens.get(0).measure(socket).height + rootSign.getOverMargin() - rootSign.getOverhangLeftHeight() > tokens.get(1).measure(socket).height;
	}
}
