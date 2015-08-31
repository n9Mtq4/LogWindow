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
import com.n9mtq4.logwindow.listener.ObjectListener;

import java.io.Serializable;

/**
 * Created by will on 3/31/15.<br>
 * Event that is passed to {@link ObjectListener}s by the {@link BaseConsole}
 */
public final class SentObjectEvent implements Serializable {
	
	private static final long serialVersionUID = 1595683797944824474L;
	
	private final BaseConsole baseConsole;
	
	/**
	 * Has this event been canceled
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
	 * @param baseConsole the base console
	 * @param object      the object
	 * @param message     the message
	 */
	public SentObjectEvent(BaseConsole baseConsole, Object object, String message) {
		this.baseConsole = baseConsole;
		this.object = object;
		this.message = message;
		//noinspection deprecation
		this.canceled = false;
		this.objectType = object.getClass().getName();
	}
	
	/**
	 * Gets the {@link BaseConsole} that called this event.
	 *
	 * @return The  that called this event
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Returns true if a {@link ObjectListener} has
	 * indicated that this event has been completed / shouldn't continue iterating through listeners
	 *
	 * @return the boolean
	 */
	public final boolean isCanceled() {
		return canceled;
	}
	
	/**
	 * Gets the sent object
	 *
	 * @return the object
	 */
	public final Object getObject() {
		return object;
	}
	
	/**
	 * Gets message.
	 *
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}
	
	/**
	 * Gets object type.
	 *
	 * @return the object type
	 */
	public final String getObjectType() {
		return objectType;
	}
	
}
