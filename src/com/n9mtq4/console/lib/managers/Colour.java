/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
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

package com.n9mtq4.console.lib.managers;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Created by Will on 11/24/14.
 */
public class Colour extends Color {
	
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	
	public static final Colour WHITE = getColour(Color.WHITE);
	public static final Colour LIGHT_GRAY = getColour(Color.LIGHT_GRAY);
	public static final Colour GRAY = getColour(Color.GRAY);
	public static final Colour RED = getColour(Color.RED);
	public static final Colour PINK = getColour(Color.PINK);
	public static final Colour ORANGE = getColour(Color.ORANGE);
	public static final Colour YELLOW = getColour(Color.YELLOW);
	public static final Colour GREEN = getColour(Color.GREEN);
	public static final Colour MAGENTA = getColour(Color.MAGENTA);
	public static final Colour BLUE = getColour(Color.BLUE);
	public static final Colour CYAN = getColour(Color.CYAN);
	public static final Colour BLACK = getColour(Color.BLACK);
	public static final Colour PURPLE = new Colour(128, 0, 128);
	
	public Colour(int i, int i1, int i2) {
		super(i, i1, i2);
	}
	
	public Colour(int i, int i1, int i2, int i3) {
		super(i, i1, i2, i3);
	}
	
	public Colour(int i) {
		super(i);
	}
	
	public Colour(int i, boolean b) {
		super(i, b);
	}
	
	public Colour(float v, float v1, float v2) {
		super(v, v1, v2);
	}
	
	public Colour(float v, float v1, float v2, float v3) {
		super(v, v1, v2, v3);
	}
	
	public Colour(ColorSpace colorSpace, float[] floats, float v) {
		super(colorSpace, floats, v);
	}
	
	public static Colour getColour(Color color) {
		return new Colour(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public String getANSI() {
		if (this.getRGB() == Color.RED.getRGB()) {
			return ANSI_RED;
		}else if (this.getRGB() == Colour.YELLOW.getRGB()) {
			return ANSI_YELLOW;
		}else if (this.getRGB() == Colour.GREEN.getRGB()) {
			return ANSI_GREEN;
		}else if (this.getRGB() == Colour.BLUE.getRGB()) {
			return ANSI_BLUE;
		}else if (this.getRGB() == Colour.CYAN.getRGB()) {
			return ANSI_CYAN;
		}else if (this.getRGB() == Colour.BLACK.getRGB()) {
			return ANSI_BLACK;
		}else if (this.getRGB() == Colour.WHITE.getRGB()) {
			return ANSI_WHITE;
		}else if (this.getRGB() == Colour.PURPLE.getRGB()) {
			return ANSI_PURPLE;
		}
		return "";
	}
	
	public static String getAnsiReset() {
		return ANSI_RESET;
	}
	
}
