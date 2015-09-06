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
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.utils.Colour;

import java.util.Random;

/**
 * Created by will on 8/13/15 at 2:58 AM.
 */
public final class UIContainer {
	
	private static final Random random = new Random();
	
	private final ConsoleUI gui;
	private final String givenName;
	
	public UIContainer(ConsoleUI gui) {
		this.gui = gui;
		this.givenName = gui.getClass().getName() + ":" + String.valueOf(random.nextInt());
	}
	
	/**
	 * Note: Add your gui stuff here when making a custom {@link ConsoleUI}<br>
	 * Called when {@link ConsoleUI} is added to {@link BaseConsole}.
	 * */
	public final void init() {
		gui.init();
	}
	
	/**
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleUI} is removed.
	 * */
	public final void dispose() {
		gui.dispose();
	}
	
	/**
	 * Called when a {@link ListenerAttribute} wants to print something.<br>
	 *
	 * @param object The object that the user wants to print that should be handled
	 * @param colour The colour that the user wants to print the object in. If colour is null, it will be
	 *               automatically set to the defaultTextColour, however, if you haven't given
	 *               defaultTextColour a colour be prepared for colour to be null
	 */
	public final void print(Object object, Colour colour) {
		gui.print(object, colour);
	}
	
	public final ConsoleUI getGui() {
		return gui;
	}
	
	public final String getGivenName() {
		return givenName;
	}
	
}
