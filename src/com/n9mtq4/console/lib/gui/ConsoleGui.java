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

package com.n9mtq4.console.lib.gui;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;
import java.util.Random;

/**
 * Created by Will on 12/29/14.
 */
public class ConsoleGui {
	
	/**
	 * Parent BaseConsole
	 */
	private BaseConsole parent;
	/**
	 * Identifier ID
	 */
	private String name;
	/**
	 * The default text colour for print when no colour is specified.
	 *
	 * @see ConsoleGui#setDefaultTextColour
	 * @see ConsoleGui#getDefaultTextColour
	 */
	private Colour defaultTextColour;
	
	/**
	 * Makes a new {@link ConsoleGui} object.
	 */
	public ConsoleGui() {
		this.name = this.getClass().getName() + ":" + String.valueOf(new Random(10000).nextInt());
	}
	
	/**
	 * Note: Override me!<br>
	 * Note: Add your gui stuff here when making a custom {@link ConsoleGui}<br>
	 * Called when {@link ConsoleGui} is added to {@link BaseConsole}.
	 */
	public void init() {
		
	}
	
	/**
	 * Note: Override me!<br>
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleGui} is removed.
	 */
	public void dispose() {
		
	}
	
	/**
	 * Note: DO NOT OVERRIDE!<br>
	 * Binds the object to a parent {@link BaseConsole}.
	 */
	public void add(BaseConsole parent) {
		this.parent = parent;
		init();
	}
	
	/**
	 * Note: Don't override me! Override {@link com.n9mtq4.console.lib.gui.ConsoleGui#print} instead<br>
	 * Note: Don't use me!
	 * A small helper method that {@link BaseConsole} calls. This method adds support for default text color.
	 * @see com.n9mtq4.console.lib.gui.ConsoleGui#print
	 * @deprecated Helper method only meant to be used by {@link BaseConsole}
	 * */
	@Deprecated
 	public void lowPrint(String text, Colour colour) {
		if (colour == null) colour = this.defaultTextColour;
		print(text, colour);
	}
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link com.n9mtq4.console.lib.ConsoleListener} wants to print something.
	 */
	public void print(String text, Colour colour) {
		
	}
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link com.n9mtq4.console.lib.ConsoleListener} wants to print an image.
	 */
	public void printImage(File file) {
		
	}
	
	/**
	 * Gets the parent {@link BaseConsole}.
	 *
	 * @return The {@link BaseConsole} this {@link ConsoleGui} is linked to.
	 */
	public BaseConsole getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent {@link BaseConsole}.
	 *
	 * @param parent Sets the parent to the new parent given.
	 */
	public void setParent(BaseConsole parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the Identifier Name of this {@link ConsoleGui}.
	 *
	 * @return The identifier id.
	 */
	public String getName() {
		return name;
	}
	
	public Colour getDefaultTextColour() {
		return defaultTextColour;
	}
	
	public void setDefaultTextColour(Colour defaultTextColour) {
		this.defaultTextColour = defaultTextColour;
	}
	
}
