/*
 * Copyright (c) 2009 Piotr Piastucki
 * 
 * This file is part of Patchca CAPTCHA library.
 * 
 *  Patchca is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Patchca is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Patchca. If not, see <http://www.gnu.org/licenses/>.
 */
package com.dinner.controller.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class CaptchaBestFitTextRenderer {

	private List<BufferedImageOp> filters;
	private int leftMargin;
	private int rightMargin;
	private int topMargin;
	private int bottomMargin;

	public CaptchaBestFitTextRenderer() {
		leftMargin = rightMargin = 5;
		topMargin = bottomMargin = 5;
	}

	public void draw(String text, BufferedImage canvas,  Color color) {
		Graphics2D g = (Graphics2D) canvas.getGraphics();
		CaptchaTextString ts = convertToCharacters(text, g, color);
		arrangeCharacters(canvas.getWidth(), canvas.getHeight(), ts);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for (CaptchaTextCharacter tc : ts.getCharacters()) {
			g.setColor(tc.getColor());
			g.drawString(tc.iterator(), (float) tc.getX(), (float) tc.getY());
		}
	}
	
	public Font getFont() {
		List<String> families = new ArrayList<String>();
		families.add("Verdana");
		families.add("Tahoma");
		boolean randomStyle = false;
		int minSize = 45;
		int maxSize = 45;
		Random r = new Random();
		String family = families.get(r.nextInt(families.size()));
		boolean bold = r.nextBoolean() && randomStyle;
		int size = minSize;
		if (maxSize - minSize > 0) {
			size += r.nextInt(maxSize - minSize);
		}
		return new Font(family, bold ? Font.BOLD : Font.PLAIN, size);
	}

	protected CaptchaTextString convertToCharacters(String text, Graphics2D g, Color color) {
		CaptchaTextString characters = new CaptchaTextString();
		FontRenderContext frc = g.getFontRenderContext();
		double lastx = 0;
		for (int i = 0; i < text.length(); i++) {
			Font font = getFont();
			char c = text.charAt(i);
			FontMetrics fm = g.getFontMetrics(font);
			Rectangle2D bounds = font.getStringBounds(String.valueOf(c), frc);
			CaptchaTextCharacter tc = new CaptchaTextCharacter();
			tc.setCharacter(c);
			tc.setFont(font);
			tc.setWidth(fm.charWidth(c));
			tc.setHeight(fm.getAscent() + fm.getDescent());
			tc.setAscent(fm.getAscent());
			tc.setDescent(fm.getDescent());
			tc.setX(lastx);
			tc.setY(0);
			tc.setFont(font);
			tc.setColor(color);
			lastx += bounds.getWidth();
			characters.addCharacter(tc);
		}
		return characters;
	}
	
	public BufferedImage applyFilters(BufferedImage source) {
		BufferedImage dest = source;
		for (BufferedImageOp filter : getFilters()) {
			dest = filter.filter(dest, null);
		}
		int x = (source.getWidth() - dest.getWidth()) / 2;
		int y = (source.getHeight() - dest.getHeight()) / 2;
		source = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		source.getGraphics().drawImage(dest, x, y, null);
		return source;
	}
	
	protected void arrangeCharacters(int width, int height, CaptchaTextString ts) {
		double widthRemaining = (width - ts.getWidth() - leftMargin - rightMargin) / ts.getCharacters().size();
		double x = leftMargin + widthRemaining / 2;
		height -= topMargin + bottomMargin;
		for (CaptchaTextCharacter tc : ts.getCharacters()) {
			double y = topMargin + (height + tc.getAscent() * 0.7) / 2;
			tc.setX(x);
			tc.setY(y);
			x += tc.getWidth() + widthRemaining;
		}
	}
	
	public List<BufferedImageOp> getFilters() {
		if (filters == null) {
			filters = new ArrayList<BufferedImageOp>();
			filters.add(new CaptchaRippleImageOp());
			filters.add(new CaptchaCurvesImageOp());
		}
		return filters;
	}

}
