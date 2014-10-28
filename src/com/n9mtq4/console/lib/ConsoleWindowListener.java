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

import com.n9mtq4.console.lib.events.DisableActionEvent;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Created by Will on 10/28/14.
 */
public class ConsoleWindowListener implements WindowListener {
	
	private Console console;
	
	public ConsoleWindowListener(Console c) {
		
		this.console = c;
		
	}
	
	@Override
	public void windowOpened(WindowEvent windowEvent) {
		
	}
	
	@Override
	public void windowClosing(WindowEvent windowEvent) {
		
		ArrayList<ConsoleListener> listeners = console.getListeners();
		for (ConsoleListener l : listeners) {
			
			l.onDisable(new DisableActionEvent(console, DisableActionEvent.WINDOW_CLOSE));
			
		}
		
	}
	
	@Override
	public void windowClosed(WindowEvent windowEvent) {
		
	}
	
	@Override
	public void windowIconified(WindowEvent windowEvent) {
		
	}
	
	@Override
	public void windowDeiconified(WindowEvent windowEvent) {
		
	}
	
	@Override
	public void windowActivated(WindowEvent windowEvent) {
		
	}
	
	@Override
	public void windowDeactivated(WindowEvent windowEvent) {
		
	}
	
}
