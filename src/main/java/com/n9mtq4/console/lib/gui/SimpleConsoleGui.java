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
import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;

/**
 * Created by will on 8/13/15 at 2:24 AM.<br>
 * A default implementation of ConsoleGui
 */
public abstract class SimpleConsoleGui implements ConsoleGui {
	
	private BaseConsole parent;
	private Colour defaultTextColour;
	
	public SimpleConsoleGui(BaseConsole parent) {
		this.parent = parent;
	}
	
	/**
	 * Note: Override me!<br>
	 * Note: Add your gui stuff here when making a custom {@link ConsoleGui}<br>
	 * Called when {@link ConsoleGui} is added to {@link BaseConsole}.
	 */
	@Override
	public abstract void init();
	
	/**
	 * Note: Override me!<br>
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleGui} is removed.
	 */
	@Override
	public abstract void dispose();
	
	/**
	 * Note: Don't override me! Override {@link ConsoleGui#print} instead<br>
	 * Note: Don't use me!<br>
	 * A small helper method that {@link BaseConsole} calls. This method adds support for default text color.
	 *
	 * @param text   the text
	 * @param colour the colour
	 * @see ConsoleGui#print
	 */
	@Deprecated
	@Override
	public void print(String text, Colour colour) {
		if (colour == null) colour = this.defaultTextColour;
		printText(text, colour);
	}
	
	@Override
	public abstract void printImage(File file);
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link com.n9mtq4.console.lib.ConsoleListener} wants to print something.<br>
	 *
	 * @param text   The string that the user wants to print that should be handled
	 * @param colour The colour that the user wants to print the text in. If colour is null, it will be
	 *               automatically set to the defaultTextColour, however, if you haven't given
	 *               defaultTextColour a colour be prepared for colour to be null
	 * @see SimpleConsoleGui#setDefaultTextColour
	 * @see SimpleConsoleGui#getDefaultTextColour
	 */
	public abstract void printText(String text, Colour colour);
	
	/**
	 * Gets the parent {@link BaseConsole}.
	 *
	 * @return The  this
	 * is linked to.
	 * @see SimpleConsoleGui#setParent
	 */
	public BaseConsole getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent {@link BaseConsole}.
	 *
	 * @param parent Sets the parent to the new parent given.
	 * @see SimpleConsoleGui#getParent
	 */
	public void setParent(BaseConsole parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the default text colour to use when one is not specified<br>
	 * If you called setDefaultTextColour - no need to do anything. It's handled on a
	 * lower level.
	 *
	 * @return The colour that is the default colour to be used when one isn't specified
	 * @see SimpleConsoleGui#setDefaultTextColour
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
	 * @see SimpleConsoleGui#getDefaultTextColour
	 */
	public void setDefaultTextColour(Colour defaultTextColour) {
		this.defaultTextColour = defaultTextColour;
	}
	
}
