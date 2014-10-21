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

package com.n9mtq4.console.demo;

import com.n9mtq4.console.lib.Console;
import com.n9mtq4.console.lib.ConsoleActionEvent;
import com.n9mtq4.console.lib.ConsoleListener;

import java.awt.*;

/**
 * Created by Will on 10/20/14.
 */
public class Main {
	
	public static void main(String[] args) {
		
		Console c = new Console();
		c.addDefaultListeners(); //adds history and echos the input you type
		
//		new MyListener().addToConsole(c); // this works
//		c.addConsoleListener(new MyListener()); // this works too!
		c.addConsoleListener(new MyListener());
		
//		you can also define listeners without making a new class
		new ConsoleListener() {
			@Override
			public void actionPreformed(ConsoleActionEvent e) {
				if (e.getCommand().contains("hi")) {
					e.getConsole().println("Hello!", Color.BLUE);
				}
			}
		}.addToConsole(c); // don't forget to add it
		
	}
	
}
