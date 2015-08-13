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

package com.n9mtq4.console.lib.gui;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.managers.SocketManager;
import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;

/**
 * Created by Will on 12/29/14.
 */
public class GuiSocket extends SimpleConsoleGui {
	
	/**
	 * The Port.
	 */
	public int port = 4444;
	private boolean sendToPluginsInsteadOfPrint = true;
	
	private SocketManager s = new SocketManager();
	
	public GuiSocket(BaseConsole parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		go();
	}
	
	/**
	 * Go void.
	 */
	public void go() {
		
		setDefaultTextColour(null);
		s.startServer(port);
		s.startServerListenerToConsole(getParent(), sendToPluginsInsteadOfPrint);
		
	}
	
	@Override
	public void printText(String text, Colour colour) {
		if (colour != null) {
			System.out.println(colour.getANSI() + text);
		}else {
			System.out.println(text);
		}
	}
	
	@Override
	public void dispose() {
		s.close();
	}
	
	@Override
	public void printImage(File file) {
		
	}
	
}
