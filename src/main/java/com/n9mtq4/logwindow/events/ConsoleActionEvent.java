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

package com.n9mtq4.logwindow.events;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.command.ConsoleCommand;
import com.n9mtq4.logwindow.listener.StringListener;

import java.io.Serializable;

/**
 * Created by Will on 10/20/14.<br>
 * Event that is passed to {@link StringListener}s by the {@link BaseConsole}
 */
public final class ConsoleActionEvent implements Serializable {
	
	private static final long serialVersionUID = 1705101834722619856L;
	
	/**
	 * The {@link BaseConsole} that made this event
	 */
	private final BaseConsole initiatingBaseConsole;
	/**
	 * The {@link ConsoleCommand} that stores the inputed data
	 */
	private final ConsoleCommand command;
	/**
	 * Has this event been canceled?
	 */
	private boolean canceled;
	
	/**
	 * Instantiates a new Console action event.
	 *
	 * @param initiatingBaseConsole the base console
	 * @param command     the command
	 */
	public ConsoleActionEvent(BaseConsole initiatingBaseConsole, ConsoleCommand command) {
		this.initiatingBaseConsole = initiatingBaseConsole;
		this.command = command;
		this.canceled = false;
	}
	
	/**
	 * Gets the {@link BaseConsole} that called this event
	 *
	 * @return The  that called this event
	 */
	public final BaseConsole getInitiatingBaseConsole() {
		return initiatingBaseConsole;
	}
	
	/**
	 * Gets the {@link ConsoleCommand} that contains the inputed data
	 *
	 * @return The  that contains the inputed data
	 */
	public final ConsoleCommand getCommand() {
		return command;
	}
	
	/**
	 * Returns true if a {@link StringListener} has
	 * indicated that this event has been completed / shouldn't continue iterating through listeners
	 *
	 * @return the boolean
	 */
	public final boolean isCanceled() {
		return canceled;
	}
	
}