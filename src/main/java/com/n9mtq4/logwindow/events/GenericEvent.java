/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2016  Will (n9Mtq4) Bresnahan
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

/**
 * Created by will on 2/8/16 at 6:16 PM.
 * 
 * The interface for a generic event.
 * 
 * @since v5.1
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public interface GenericEvent {
	
	/**
	 * Gets the {@link BaseConsole} that called this event.
	 *
	 * @since v5.1
	 * @return The {@link BaseConsole} that called this event
	 */
	BaseConsole getInitiatingBaseConsole();
	
	/**
	 * Returns true if a listener has
	 * indicated that this event has been completed / shouldn't continue iterating through listeners
	 *
	 * @since v5.1
	 * @return a boolean of whether this event has been canceled
	 */
	boolean isCanceled();
	
}
