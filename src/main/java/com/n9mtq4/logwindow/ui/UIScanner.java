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

package com.n9mtq4.logwindow.ui;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.Console;
import java.util.Scanner;

/**
 * Created by Will on 12/11/14.
 * 
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public final class UIScanner extends SimpleConsoleUI {
	
	private Scanner scan;
	private Console console;
	private boolean shouldScan;
	private boolean ansi;
	
	public UIScanner(BaseConsole parent) {
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
				while (UIScanner.this.isShouldScan()) {
					System.out.print("> ");
					String s = UIScanner.this.console.readLine();
					UIScanner.this.getParent().sendPluginsString(s);
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
				while (UIScanner.this.isShouldScan()) {
					System.out.print("> ");
					String s = scan.nextLine();
					UIScanner.this.getParent().sendPluginsString(s);
				}
			}
		}, "Scanner Input Listener").start();
		
	}
	
	@Override
	public final void printObject(Object object, Colour colour) {
		
		if (colour != null) {
			if (ansi) {
				System.out.print(colour.getANSI() + objectToString(object) + Colour.getAnsiReset());
			}else {
				System.out.print(objectToString(object));
			}
		}else {
			System.out.print(objectToString(object));
		}
		
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
