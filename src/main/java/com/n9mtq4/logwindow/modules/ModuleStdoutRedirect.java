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

package com.n9mtq4.logwindow.modules;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.command.ConsoleCommand;
import com.n9mtq4.logwindow.events.ConsoleActionEvent;
import com.n9mtq4.logwindow.listener.StringListener;

/**
 * Created by Will on 10/30/14.
 */

/**
 * A module to enable or disable the redirection of StdOut to the console
 */
public final class ModuleStdoutRedirect implements StringListener {
	
	@Override
	public final void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
		
		ConsoleCommand c = e.getCommand();
		if (c.getArg(0).equalsIgnoreCase("stdout")) {
			
			if (c.getLength() == 2) {
				
				if (c.getArg(1).equalsIgnoreCase("test")) {
					System.out.println("Hello World! This was printed using System.out.println()");
				}
				
			}else if (c.getLength() == 3) {
				
				if (c.getArg(1).equalsIgnoreCase("redirect")) {
					
					if (c.getArg(2).equalsIgnoreCase("on")) {
						
						baseConsole.redirectStdoutOn();
						System.out.println("Hello World! This was printed using System.out.println()");
						
					}else if (c.getArg(2).equalsIgnoreCase("off")) {
						
						baseConsole.println("turned off stdout redirection");
						baseConsole.redirectStdoutOff();
						
					}
					
				}
				
			}else if (c.getLength() == 4) {
				
				if (c.getArg(1).equalsIgnoreCase("redirect")) {
					
					if (c.getArg(2).equalsIgnoreCase("on")) {
						
						baseConsole.redirectStdoutOn(Boolean.parseBoolean(c.getArg(3)));
						System.out.println("Hello World! This was printed using System.out.println()");
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
