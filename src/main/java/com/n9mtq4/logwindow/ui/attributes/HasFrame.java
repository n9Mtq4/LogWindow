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

package com.n9mtq4.logwindow.ui.attributes;

import javax.swing.JFrame;

/**
 * An interface that flags a {@link com.n9mtq4.logwindow.ui.ConsoleUI} that it contains a {@link JFrame}.
 * Allows code to get a {@link com.n9mtq4.logwindow.ui.ConsoleUI}'s {@link JFrame}.
 * 
 * <p>Created by will on 1/7/15.</p>
 * 
 * @see #getJFrame()
 * @since v4.0
 * @author Will "n9Mtq4" Bresnahan
 */
public interface HasFrame {
	
	/**
	 * If the {@link com.n9mtq4.logwindow.ui.ConsoleUI} implements HasFrame, get the {@link JFrame}
	 * 
	 * @since v4.0
	 * @return The {@link com.n9mtq4.logwindow.ui.ConsoleUI}'s {@link JFrame}
	 */
	JFrame getJFrame();
	
}
