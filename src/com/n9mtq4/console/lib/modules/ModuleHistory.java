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

package com.n9mtq4.console.lib.modules;

import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.gui.ConsoleGui;
import com.n9mtq4.console.lib.gui.interfaces.History;
import com.n9mtq4.console.lib.utils.Colour;

import java.util.ArrayList;

/**
 * Created by Will on 10/20/14.
 */

/**
 * A module for getting a list of the history and clearing the history
 */
public class ModuleHistory extends ConsoleListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e) {
		
		if (e.getCommand().contains("history")) {
			
			if (e.getCommand().getLength() == 1) {
				if (e.getCommand().getArg(0).equalsIgnoreCase("history")) {
					for (String s : e.getBaseConsole().getHistory()) {
						e.getBaseConsole().println(s, Colour.MAGENTA);
					}
				}
			}else if (e.getCommand().getLength() == 2) {
				if (e.getCommand().getArg(1).equalsIgnoreCase("clear")) {
					e.getBaseConsole().setHistory(new ArrayList<String>());
					for (ConsoleGui g : e.getBaseConsole().getGui()) {
						if (g instanceof History) {
							((History) g).historyUpdate();
						}
					}
					e.getBaseConsole().println("Cleared history", Colour.CYAN);
				}
			}
			
		}
		
	}
	
}
