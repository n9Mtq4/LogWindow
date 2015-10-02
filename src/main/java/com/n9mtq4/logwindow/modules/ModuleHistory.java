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
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.ui.UIContainer;
import com.n9mtq4.logwindow.ui.attributes.History;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.StringParser;

/**
 * A module for getting a list of the history and clearing the history.
 * 
 * <p>Created by Will on 10/20/14.</p>
 * 
 * @since v0.1
 * @author Will "n9Mtq4" Bresnahan
 */
public final class ModuleHistory implements ObjectListener {
	
	@Override
	public final void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
		
		if (!objectEvent.isUserInputString()) return;
		StringParser stringParser = new StringParser(objectEvent);
		
		if (stringParser.contains("history")) {
			
			if (stringParser.getLength() == 1) {
				if (stringParser.getArg(0).equalsIgnoreCase("history")) {
					for (String s : baseConsole.getHistory()) {
						baseConsole.println(s, Colour.MAGENTA);
					}
				}
			}else if (stringParser.getLength() == 2) {
				if (stringParser.getArg(1).equalsIgnoreCase("clear")) {
					while (baseConsole.getHistory().size() > 0) baseConsole.getHistory().remove(0);
					for (UIContainer g : baseConsole.getUIContainers()) {
						if (g.getConsoleUI() instanceof History) {
							((History) g.getConsoleUI()).historyUpdate();
						}
					}
					baseConsole.println("Cleared history", Colour.CYAN);
				}
			}
			
		}
		
	}
	
}
