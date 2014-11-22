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

package com.n9mtq4.console.lib;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Will on 11/20/14.
 */
public class TextConsole extends BaseConsole {
	
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	
	private Scanner scan;
	private boolean shouldScan;
	
	public TextConsole(String pluginDirectory) {
		super(pluginDirectory);
		initScanner();
	}
	
	public TextConsole() {
		super();
		initScanner();
	}
	
	public TextConsole(ConsoleListener listener) {
		super(listener);
		initScanner();
	}
	
	public void initScanner() {
		
		scan = new Scanner(System.in);
		shouldScan = true;
		final TextConsole thiz = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(thiz.isShouldScan()) {
					System.out.print("> ");
					String s = scan.nextLine();
					thiz.sendPluginsString(s);
				}
			}
		}, "Scanner Input Listener").start();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stopScan();
		scan.close();
	}
	
	@Override
	public void print(String text, Color color) {
		String sc = "";
		if (color.getRGB() == Color.RED.getRGB()) {
			sc = ANSI_RED;
		}else if (color.getRGB() == Color.YELLOW.getRGB()) {
			sc = ANSI_YELLOW;
		}else if (color.getRGB() == Color.GREEN.getRGB()) {
			sc = ANSI_GREEN;
		}else if (color.getRGB() == Color.BLUE.getRGB()) {
			sc = ANSI_BLUE;
		}else if (color.getRGB() == Color.CYAN.getRGB()) {
			sc = ANSI_CYAN;
		}else if (color.getRGB() == Color.BLACK.getRGB()) {
			sc = ANSI_BLACK;
		}
//		TODO: extend java.awt.Color to add this into another class
//		TODO: add purple and white
		System.out.print(sc + text + ANSI_RESET);
	}
	
	@Override
	public void printImage(String filePath) {
//		can't print images
	}
	
	public void stopScan() {
		shouldScan = false;
	}
	
	public Scanner getScan() {
		return scan;
	}
	
	public boolean isShouldScan() {
		return shouldScan;
	}
	
}
