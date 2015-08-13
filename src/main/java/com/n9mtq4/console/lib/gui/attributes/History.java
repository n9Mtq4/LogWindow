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

package com.n9mtq4.console.lib.gui.attributes;

/**
 * Created by Will on 1/1/15.
 */

/**
 * If a {@link com.n9mtq4.console.lib.gui.ConsoleGui} has native support for history,
 * it might need a call when a {@link com.n9mtq4.console.lib.gui.ConsoleGui} calls
 * {@link com.n9mtq4.console.lib.BaseConsole#sendPluginsString}
 */
public interface History {
	
	/**
	 * Called when {@link com.n9mtq4.console.lib.BaseConsole#sendPluginsString} is called
	 */
	void historyUpdate();
	
}
