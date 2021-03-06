/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2015  Will (n9Mtq4) Bresnahan
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.n9mtq4.logwindow.utils;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * A modified version of {@link Color} to support ANSI values.<br>
 * 
 * <p>Created by Will on 11/24/14.</p>
 * 
 * @since v3.1
 * @version v5.1
 * @author Will "n9Mtq4" Bresnahan
 */
public class Colour extends Color {
	
	private static final long serialVersionUID = 7273343206919797227L;
	
	/**
	 * The Colour white.
	 */
	public static final Colour WHITE = getColour(Color.WHITE);
	/**
	 * The Colour light gray.
	 */
	public static final Colour LIGHT_GRAY = getColour(Color.LIGHT_GRAY);
	/**
	 * The Colour gray.
	 */
	public static final Colour GRAY = getColour(Color.GRAY);
	/**
	 * The Colour red.
	 */
	public static final Colour RED = getColour(Color.RED);
	/**
	 * The Colour pink.
	 */
	public static final Colour PINK = getColour(Color.PINK);
	/**
	 * The Colour orange.
	 */
	public static final Colour ORANGE = getColour(Color.ORANGE);
	/**
	 * The Colour yellow.
	 */
	public static final Colour YELLOW = getColour(Color.YELLOW);
	/**
	 * The Colour green.
	 */
	public static final Colour GREEN = getColour(Color.GREEN);
	/**
	 * The Colour magenta.
	 */
	public static final Colour MAGENTA = getColour(Color.MAGENTA);
	/**
	 * The Colour blue.
	 */
	public static final Colour BLUE = getColour(Color.BLUE);
	/**
	 * The Colour cyan.
	 */
	public static final Colour CYAN = getColour(Color.CYAN);
	/**
	 * The Colour black.
	 */
	public static final Colour BLACK = getColour(Color.BLACK);
	/**
	 * The Colour purple.
	 */
	public static final Colour PURPLE = new Colour(128, 0, 128);
	
//	ANSI TO COLOUR SEPARATOR
	/**
	 * The ANSI code for resetting the color.
	 */
	private static final String ANSI_RESET = "\u001B[0m";
	/**
	 * The ANSI code for the color black.
	 */
	private static final String ANSI_BLACK = "\u001B[30m";
	/**
	 * The ANSI code for the color red.
	 */
	private static final String ANSI_RED = "\u001B[31m";
	/**
	 * The ANSI code for the color gree.
	 */
	private static final String ANSI_GREEN = "\u001B[32m";
	/**
	 * The ANSI code for the color yellow.
	 */
	private static final String ANSI_YELLOW = "\u001B[33m";
	/**
	 * The ANSI code for the color blue.
	 */
	private static final String ANSI_BLUE = "\u001B[34m";
	/**
	 * The ANSI code for the color purple.
	 */
	private static final String ANSI_PURPLE = "\u001B[35m";
	/**
	 * The ANSI code for the color cyan.
	 */
	private static final String ANSI_CYAN = "\u001B[36m";
	/**
	 * The ANSI code for the color white.
	 */
	private static final String ANSI_WHITE = "\u001B[37m";
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(int, int, int)
	 */
	public Colour(int i, int i1, int i2) {
		super(i, i1, i2);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(int, int, int, int)
	 */
	public Colour(int i, int i1, int i2, int i3) {
		super(i, i1, i2, i3);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(int)
	 */
	public Colour(int i) {
		super(i);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(int, boolean)
	 */
	public Colour(int i, boolean b) {
		super(i, b);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(float, float, float)
	 */
	public Colour(float v, float v1, float v2) {
		super(v, v1, v2);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(float, float, float, float)
	 */
	public Colour(float v, float v1, float v2, float v3) {
		super(v, v1, v2, v3);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since v3.1
	 * @see Color#Color(ColorSpace, float[], float)
	 */
	public Colour(ColorSpace colorSpace, float[] floats, float v) {
		super(colorSpace, floats, v);
	}
	
	/**
	 * Converts {@link Color} into {@link Colour}.
	 * 
	 * @since v3.1
	 * @param color Java's {@link Color}.
	 * @return The Colour version with ANSI support.
	 */
	public static Colour getColour(Color color) {
		return new Colour(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	/**
	 * Gives the color reset ANSI code.<br>
	 * 
	 * @since v3.1
	 * @return The ANSI code for resetting the colors.
	 */
	public static String getAnsiReset() {
		return ANSI_RESET;
	}
	
	/**
	 * Turns this {@link Colour} into an ANSI code.<br>
	 * Note: only works for some constants. (won't work with custom RGB).<br>
	 * 
	 * @since v3.1
	 * @return the ANSI string of the color for terminal colors.
	 */
	public final String getANSI() {
		return getANSI(this);
	}
	
	private static String getANSI(Colour colour) {
		if (colour.getRGB() == Colour.RED.getRGB()) {
			return ANSI_RED;
		}else if (colour.getRGB() == Colour.YELLOW.getRGB()) {
			return ANSI_YELLOW;
		}else if (colour.getRGB() == Colour.GREEN.getRGB()) {
			return ANSI_GREEN;
		}else if (colour.getRGB() == Colour.BLUE.getRGB()) {
			return ANSI_BLUE;
		}else if (colour.getRGB() == Colour.CYAN.getRGB()) {
			return ANSI_CYAN;
		}else if (colour.getRGB() == Colour.BLACK.getRGB()) {
			return ANSI_BLACK;
		}else if (colour.getRGB() == Colour.WHITE.getRGB()) {
			return ANSI_WHITE;
		}else if (colour.getRGB() == Colour.PURPLE.getRGB()) {
			return ANSI_PURPLE;
		}else if (colour.getRGB() == Colour.MAGENTA.getRGB()) {
			return ANSI_PURPLE;
		}
		return "";
	}
	
}
