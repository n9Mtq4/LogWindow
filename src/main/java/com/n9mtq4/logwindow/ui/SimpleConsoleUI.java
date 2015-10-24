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
import com.n9mtq4.logwindow.modules.History;
import com.n9mtq4.logwindow.utils.Colour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by will on 8/13/15 at 2:24 AM.<br>
 * A default implementation of ConsoleGui.
 * 
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public abstract class SimpleConsoleUI implements ConsoleUI, History.HistorySupport {
	
	/**
	 * Turns an object into a string.<br>
	 * null = "null"<br>
	 * Object[] = [arg0, arg1...]<br>
	 * Collection = [arg0, arg1...]<br>
	 * Object = String.valueOf(object)<br>
	 * 
	 * @param object The Object to turn into a string
	 * @return The string value of that object
	 * */
	public static String objectToString(Object object) {
		if (object == null) return "null";
		if (object instanceof Object[]) return Arrays.toString((Object[]) object);
		if (object instanceof Collection) return Arrays.toString(((Collection) object).toArray());
		return String.valueOf(object);
	}
	
	private final BaseConsole parent;
	private Colour defaultTextColour;
	@Deprecated
	private ArrayList<String> history; // see com.n9mtq4.logwindow.modules.History's deprecation
	
	public SimpleConsoleUI(BaseConsole parent) {
		this.parent = parent;
		this.history = new ArrayList<String>();
	}
	
	/**
	 * Note: Override me!<br>
	 * Note: Add your gui stuff here when making a custom {@link ConsoleUI}<br>
	 * Called when {@link ConsoleUI} is added to {@link BaseConsole}.
	 */
	@Override
	public abstract void init();
	
	/**
	 * Note: Override me!<br>
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleUI} is removed.
	 */
	@Override
	public abstract void dispose();
	
	/**
	 * Note: Don't override me! Override {@link ConsoleUI#print} instead<br>
	 * Note: Don't use me!<br>
	 * A small helper method that {@link BaseConsole} calls. This method adds support for default text color.
	 *
	 * @param object the object to print
	 * @param colour the colour
	 * @see ConsoleUI#print
	 */
	@Deprecated
	@Override
	public final void print(Object object, Colour colour) {
		if (colour == null) colour = getDefaultTextColour();
		printObject(object, colour);
	}
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link BaseConsole} wants to print something.<br>
	 *
	 * @param object The object that the user wants to print that should be handled
	 * @param colour The colour that the user wants to print the object in. If colour is null, it will be
	 *               automatically set to the defaultTextColour, however, if you haven't given
	 *               defaultTextColour a colour be prepared for colour to be null
	 * @see SimpleConsoleUI#setDefaultTextColour
	 * @see SimpleConsoleUI#getDefaultTextColour
	 */
	public abstract void printObject(Object object, Colour colour);
	
	/**
	 * Default history support implementation
	 * */
	@Override
	public void historyUpdate(ArrayList<String> history) {
		this.history = history;
	}
	
	/**
	 * Gets the parent {@link BaseConsole}.
	 *
	 * @return The base console that the gui is linked to.
	 */
	public final BaseConsole getParent() {
		return parent;
	}
	
	/**
	 * Gets the default text colour to use when one is not specified<br>
	 * If you called setDefaultTextColour - no need to do anything. It's handled on a
	 * lower level.
	 *
	 * @return The colour that is the default colour to be used when one isn't specified
	 * @see SimpleConsoleUI#setDefaultTextColour
	 */
	public Colour getDefaultTextColour() {
		return defaultTextColour;
	}
	
	/**
	 * Sets the default text colour to use when one is not specified<br>
	 * The super class will set the colour in print to whatever is set here - no need to do anything. It's handled on a
	 * lower level.
	 *
	 * @param defaultTextColour The colour that is the default colour to be used when one isn't specified
	 * @see SimpleConsoleUI#getDefaultTextColour
	 */
	public final void setDefaultTextColour(Colour defaultTextColour) {
		this.defaultTextColour = defaultTextColour;
	}
	
	/**
	 * Gets the current history in the form of a String ArrayList
	 * 
	 * @return the history (ArrayList String)
	 * @see History
	 * @see com.n9mtq4.logwindow.modules.History.HistorySupport
	 * @deprecated see {@link History}
	 * */
	@Deprecated
	public final ArrayList<String> getHistory() {
		return history;
	}
	
}
