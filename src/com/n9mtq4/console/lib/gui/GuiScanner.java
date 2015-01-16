/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014-2015  Will (n9Mtq4) Bresnahan
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

package com.n9mtq4.console.lib.gui;

import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Will on 12/11/14.
 */
public class GuiScanner extends ConsoleGui {
	
	private Scanner scan;
	private boolean shouldScan;
	private boolean ansi;
	
	@Override
	public void init() {
		getParent().setDefaultTextColour(null);
		initScanner();
		ansi = !(System.getProperty("os.name").toLowerCase().contains("window"));
	}
	
	@Override
	public void dispose() {
		stopScan();
		scan.close();
	}
	
	public void initScanner() {
		
		scan = new Scanner(System.in);
		shouldScan = true;
		final GuiScanner thiz = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (thiz.isShouldScan()) {
					System.out.print("> ");
					String s = scan.nextLine();
					thiz.getParent().sendPluginsString(s);
				}
			}
		}, "Scanner Input Listener").start();
		
	}
	
	@Override
	public void print(String text, Colour colour) {
		if (colour != null) {
			if (ansi) {
				System.out.print(colour.getANSI() + text + Colour.getAnsiReset());
			}else {
				System.out.print(text);
			}
		}else {
			System.out.print(text);
		}
		
	}
	
	@Override
	public void printImage(File file) {
//		can't print images to command line
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
