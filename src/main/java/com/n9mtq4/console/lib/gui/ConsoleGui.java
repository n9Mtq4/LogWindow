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
import java.io.Serializable;

/**
 * Created by Will on 12/29/14.<br>
 * The interface that declares that the class is a gui for
 * a BaseConsole.
 */
public interface ConsoleGui extends Serializable {
	
	/**
	 * Note: Override me!<br>
	 * Note: Add your gui stuff here when making a custom {@link ConsoleGui}<br>
	 * Called when {@link ConsoleGui} is added to {@link BaseConsole}.
	 */
	void init();
	
	/**
	 * Note: Override me!<br>
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleGui} is removed.
	 */
	void dispose();
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link com.n9mtq4.console.lib.ConsoleListener} wants to print something.<br>
	 *
	 * @param text   The string that the user wants to print that should be handled
	 * @param colour The colour that the user wants to print the text in. If colour is null, it will be
	 *               automatically set to the defaultTextColour, however, if you haven't given
	 *               defaultTextColour a colour be prepared for colour to be null
	 */
	void print(String text, Colour colour);
	
	/**
	 * Note: Override me!<br>
	 * Called when a {@link com.n9mtq4.console.lib.ConsoleListener} wants to print an image.
	 *
	 * @param file the file
	 */
	void printImage(File file);
	
}
