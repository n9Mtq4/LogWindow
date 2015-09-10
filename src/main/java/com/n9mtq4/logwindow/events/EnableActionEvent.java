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

import java.io.Serializable;

/**
 * The event that is given to the
 * {@link com.n9mtq4.logwindow.listener.EnableListener}.
 *
 * <p>Created by Will on 10/23/14.</p>
 *
 * @see com.n9mtq4.logwindow.listener.EnableListener
 * @since v0.2
 * @author Will "n9Mtq4" Bresnahan
 */
public class EnableActionEvent implements Serializable {
	
	private static final long serialVersionUID = 6629615733570224391L;
	
	private final BaseConsole baseConsole;
	
	/**
	 * Instantiates a new Enable action event.
	 *
	 * @param baseConsole The {@link BaseConsole} that sent this {@link EnableActionEvent}
	 */
	public EnableActionEvent(BaseConsole baseConsole) {
		
		this.baseConsole = baseConsole;
		
	}
	
	/**
	 * Gets the {@link BaseConsole} that sent this {@link EnableActionEvent}.
	 *
	 * @return The {@link BaseConsole} that sent this {@link EnableActionEvent}
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
}
