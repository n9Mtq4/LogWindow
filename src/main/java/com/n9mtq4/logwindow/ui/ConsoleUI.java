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

import java.io.Serializable;

/**
 * Created by Will on 12/29/14.<br>
 * The interface that declares that the class is a gui for
 * a {@link BaseConsole}.
 * 
 * @since v5.0
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public interface ConsoleUI extends Serializable {
	
	/**
	 * Note: Add your gui stuff here when making a custom {@link ConsoleUI}<br>
	 * Called when {@link ConsoleUI} is added to {@link BaseConsole}.
	 * 
	 * @since v5.0
	 */
	void init();
	
	/**
	 * Note: Close streams or dispose {@link javax.swing.JFrame} here.<br>
	 * Called when {@link BaseConsole} is closing or your {@link ConsoleUI} is removed.
	 * 
	 * @since v5.0
	 */
	void dispose();
	
	/**
	 * Called when a {@link ListenerAttribute} wants to print something.<br>
	 * 
	 * @since v5.0
	 * @param object The object that the user wants to print that should be handled
	 * @param colour The colour that the user wants to print the object in. If colour is null, it will be
	 *               automatically set to the defaultTextColour, however, if you haven't given
	 *               defaultTextColour a colour be prepared for colour to be null
	 */
	void print(Object object, Colour colour);
	
}
