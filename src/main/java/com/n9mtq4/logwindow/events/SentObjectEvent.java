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
 * Event that is given to the
 * {@link com.n9mtq4.logwindow.listener.ObjectListener}
 *
 * <p>Created by will on 3/31/15.</p>
 *
 * @see com.n9mtq4.logwindow.listener.ObjectListener
 * @since v4.1
 * @author Will "n9Mtq4" Bresnahan
 */
public final class SentObjectEvent implements Serializable {
	
	private static final long serialVersionUID = 1595683797944824474L;
	
	private final BaseConsole baseConsole;
	
	/**
	 * Has this event been canceled
	 * 
	 * @since v4.1
	 * @deprecated Not official supported and can be dangerous; only use if you know what you are doing.
	 * */
	@Deprecated
	private boolean canceled;
	
	private final Object object;
	private final String message;
	private final String objectType;
	
	/**
	 * Instantiates a new Sent object event.
	 * 
	 * @since v4.1
	 * @param baseConsole The {@link BaseConsole}
	 * @param object The object being sent to the {@link com.n9mtq4.logwindow.listener.ObjectListener}
	 * @param message The message going along with the object
	 */
	public SentObjectEvent(BaseConsole baseConsole, Object object, String message) {
		this.baseConsole = baseConsole;
		this.object = object;
		this.message = message;
		//noinspection deprecation
		this.canceled = false;
		this.objectType = object == null ? "null" : object.getClass().getName();
	}
	
	/**
	 * Gets the {@link BaseConsole} that called this event.
	 * 
	 * @since v4.1
	 * @return The {@link BaseConsole} that called this event
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Returns true if a {@link com.n9mtq4.logwindow.listener.ObjectListener} has
	 * indicated that this event has been completed / shouldn't continue iterating through listeners
	 * 
	 * @since v4.1
	 * @return a boolean of whether this event has been canceled
	 */
	public final boolean isCanceled() {
		return canceled;
	}
	
	/**
	 * Gets the object being sent to the {@link com.n9mtq4.logwindow.listener.ObjectListener}.
	 * 
	 * @since v4.1
	 * @return The {@link Object}
	 */
	public final Object getObject() {
		return object;
	}
	
	/**
	 * Gets the message going along with the object
	 * 
	 * @since v4.1
	 * @return The message going along with the object
	 */
	public final String getMessage() {
		return message;
	}
	
	/**
	 * Gets object type in the form of a string.
	 * 
	 * @since v4.1
	 * @return object.getClass().getName(), or "null"
	 */
	public final String getObjectType() {
		return objectType;
	}
	
}
