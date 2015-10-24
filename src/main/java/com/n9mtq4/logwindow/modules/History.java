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
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.listener.EnableListener;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.ui.SimpleConsoleUI;
import com.n9mtq4.logwindow.ui.UIContainer;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.StringParser;

import java.util.ArrayList;

/**
 * A module for getting a list of the history and clearing the history.
 * 
 * <p>Created by Will on 10/20/14.</p>
 * 
 * @version v5.0
 * @since v0.1
 * @author Will "n9Mtq4" Bresnahan
 * @deprecated should't be necessary and storing and pushing history is an intensive task
 */
@Deprecated
public final class History implements EnableListener, ObjectListener {
	
	private final ArrayList<String> history;
	
	public History() {
		this.history = new ArrayList<String>();
	}
	
	/**
	 * Adds the HistoryCommands listener onto the {@link BaseConsole}.
	 * @since v5.0
	 * */
	@Override
	public void onEnable(final EnableEvent enableEvent) {
		enableEvent.getBaseConsole().addListenerAttribute(new HistoryCommands());
	}
	
	/**
	 * pushes to the listeners that implement {@link HistorySupport} that the history index has
	 * been updated
	 * */
	@Override
	public final void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
		
		history.add(SimpleConsoleUI.objectToString(objectEvent.getObject())); // converts to string and adds
		
//		tells all the listeners that want it
		for (UIContainer g : baseConsole.getUIContainers()) {
			if (g.getConsoleUI() instanceof HistorySupport) {
				((HistorySupport) g.getConsoleUI()).historyUpdate(history);
			}
		}
		
	}
	
	/**
	 * A module that allows for viewing the history
	 * 
	 * @version v5.0
	 * @since v5.0
	 * @deprecated see {@link History}
	 * */
	private final class HistoryCommands implements ObjectListener {
		
		@Override
		public void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
			
			if (!objectEvent.isUserInputString()) return;
			StringParser stringParser = new StringParser(objectEvent);
			
			if (stringParser.contains("history")) {
				
				if (stringParser.getLength() == 1) {
					if (stringParser.getArg(0).equalsIgnoreCase("history")) {
						for (String s : history) {
							baseConsole.println(s, Colour.MAGENTA);
						}
					}
				}else if (stringParser.getLength() == 2) {
					if (stringParser.getArg(1).equalsIgnoreCase("clear")) {
						while (history.size() > 0) history.remove(0);
						for (UIContainer g : baseConsole.getUIContainers()) {
							if (g.getConsoleUI() instanceof HistorySupport) {
								((HistorySupport) g.getConsoleUI()).historyUpdate(history);
							}
						}
						baseConsole.println("Cleared history", Colour.CYAN);
					}
				}
				
			}
			
		}
	}
	
	/**
	 * An interface that flags a {@link com.n9mtq4.logwindow.ui.ConsoleUI} that it supports
	 * the {@link com.n9mtq4.logwindow.BaseConsole}'s history.
	 *
	 * <p>Created by will on 1/1/15.</p>
	 *
	 * @see #historyUpdate
	 * @see History
	 * @version v5.0
	 * @since v4.0
	 * @author Will "n9Mtq4" Bresnahan
	 * @deprecated should't be necessary and storing and pushing history is an intensive task
	 */
	@Deprecated
	public interface HistorySupport {
		
		/**
		 * Called when {@link com.n9mtq4.logwindow.BaseConsole#push(Object)} is called
		 * and the {@link BaseConsole} has {@link History} added as a {@link ListenerAttribute}.
		 * 
		 * @since v4.0
		 * @deprecated see {@link com.n9mtq4.logwindow.modules.History.HistorySupport}
		 */
		@Deprecated
		void historyUpdate(ArrayList<String> history);
		
	}
	
	
}
