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

package com.n9mtq4.console.lib.events;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.command.ConsoleCommand;

/**
 * Created by Will on 10/20/14.<br>
 * Event that is passed to {@link com.n9mtq4.console.lib.ConsoleListener}s by the {@link BaseConsole}
 */
public class ConsoleActionEvent {
	
	/**
	 * The {@link com.n9mtq4.console.lib.BaseConsole} that made this event
	 * */
	private BaseConsole baseConsole;
	/**
	 * The {@link com.n9mtq4.console.lib.command.ConsoleCommand} that stores the inputed data
	 * */
	private ConsoleCommand command;
	/**
	 * Has this event been canceled?
	 * */
	private boolean canceled;
	
	public ConsoleActionEvent(BaseConsole baseConsole, ConsoleCommand command) {
		this.baseConsole = baseConsole;
		this.command = command;
		this.canceled = false;
	}
	
	/**
	 * Gets the {@link com.n9mtq4.console.lib.BaseConsole} that called this event
	 * @return The {@link com.n9mtq4.console.lib.BaseConsole} that called this event
	 * */
	public BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Gets the {@link com.n9mtq4.console.lib.command.ConsoleCommand} that contains the inputed data
	 * @return The {@link com.n9mtq4.console.lib.command.ConsoleCommand} that contains the inputed data
	 * */
	public ConsoleCommand getCommand() {
		return command;
	}
	
	/**
	 * Returns true if a {@link com.n9mtq4.console.lib.ConsoleListener} has
	 * indicated that this event has been completed / shouldn't continue iterating through listeners
	 * */
	public boolean isCanceled() {
		return canceled;
	}
	
}
