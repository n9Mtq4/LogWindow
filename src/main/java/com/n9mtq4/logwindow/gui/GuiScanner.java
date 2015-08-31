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

package com.n9mtq4.logwindow.gui;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.utils.Colour;

import java.awt.*;
import java.io.Console;
import java.util.Scanner;

/**
 * Created by Will on 12/11/14.
 */
public final class GuiScanner extends SimpleConsoleGui {
	
	private Scanner scan;
	private Console console;
	private boolean shouldScan;
	private boolean ansi;
	
	public GuiScanner(BaseConsole parent) {
		super(parent);
	}
	
	@Override
	public final void init() {
		setDefaultTextColour(null);
		initInput();
		ansi = !(System.getProperty("os.name").toLowerCase().contains("window"));
	}
	
	@Override
	public final void dispose() {
		stopScan();
		if (scan != null) scan.close();
	}
	
	private void initInput() {
		if (System.console() == null) {
			initScanner();
		}else {
			initConsole();
		}
	}
	
	public final void initConsole() {
		
		this.console = System.console();
		this.shouldScan = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (GuiScanner.this.isShouldScan()) {
					System.out.print("> ");
					String s = GuiScanner.this.console.readLine();
					GuiScanner.this.getParent().sendPluginsString(s);
				}
			}
		}, "Console Input Listener").start();
		
	}
	
	/**
	 * Init scanner.
	 */
	public final void initScanner() {
		
		this.scan = new Scanner(System.in);
		this.shouldScan = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (GuiScanner.this.isShouldScan()) {
					System.out.print("> ");
					String s = scan.nextLine();
					GuiScanner.this.getParent().sendPluginsString(s);
				}
			}
		}, "Scanner Input Listener").start();
		
	}
	
	@Override
	public final void printText(String text, Colour colour) {
		
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
	public final void printImage(Image image) {
//		can't print images to command line
	}
	
	/**
	 * Stop scan.
	 */
	public final void stopScan() {
		shouldScan = false;
	}
	
	/**
	 * Gets scan.
	 *
	 * @return the scan
	 */
	public final Scanner getScan() {
		return scan;
	}
	
	/**
	 * Is should scan.
	 *
	 * @return the boolean
	 */
	public final boolean isShouldScan() {
		return shouldScan;
	}
	
}
