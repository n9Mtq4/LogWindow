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
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
@SuppressWarnings("unused")
public class ObjectEvent implements GenericEvent, Serializable {
	
	private static final long serialVersionUID = 1595683797944824474L;
	
	/**
	 * When the {@link ObjectEvent} is created if no message is provided, then it
	 * uses this one.
	 */
	private static final String DEFAULT_OBJECT_PUSH_MESSAGE = "";
	/**
	 * The message to send with the text.
	 * */
	private static final String STRING_OBJECT_MESSAGE = "text";
	
	/**
	 * Creates a {@link ObjectEvent} with the object and the message.
	 * 
	 * @since v5.0
	 * @param baseConsole The {@link BaseConsole} that spawned this event
	 * @param object The object to create the event around
	 * @param message The message to go along with the object
	 * @return The {@link ObjectEvent} with the object and message
	 * */
	public static ObjectEvent createSentObjectEvent(final BaseConsole baseConsole, final Object object, final String message) {
		return new ObjectEvent(baseConsole, object, message == null ? DEFAULT_OBJECT_PUSH_MESSAGE : message);
	}
	
	/**
	 * Creates a {@link ObjectEvent} that contains text.
	 * 
	 * @since v5.0
	 * @param baseConsole The {@link BaseConsole} that spawned this event
	 * @param text The text that was sent
	 * @return The {@link ObjectEvent} with the text
	 * */
	public static ObjectEvent createTextEvent(final BaseConsole baseConsole, final String text) {
		return createSentObjectEvent(baseConsole, text, STRING_OBJECT_MESSAGE);
	}
	
	private final BaseConsole initiatingBaseConsole;
	
	private boolean canceled;
	private final Object obj;
	private final String message;
	private final String objectType;
	
	/**
	 * Instantiates a new Sent object event.
	 * 
	 * @since v4.1
	 * @param baseConsole The {@link BaseConsole}
	 * @param obj The object being sent to the {@link com.n9mtq4.logwindow.listener.ObjectListener}
	 * @param message The message going along with the object
	 */
	public ObjectEvent(BaseConsole baseConsole, Object obj, String message) {
		this.initiatingBaseConsole = baseConsole;
		this.obj = obj;
		this.message = message;
		//noinspection deprecation
		this.canceled = false;
		this.objectType = obj == null ? "null" : obj.getClass().getName();
	}
	
	/**
	 * Returns true if the inputed {@link ObjectEvent} was inputed by the user.
	 * Returns false otherwise.
	 *
	 * @since v5.0
	 * @return If this {@link ObjectEvent} was inputed by the user
	 * */
	public boolean isUserInputString() {
		return this.getMessage().equals(STRING_OBJECT_MESSAGE) && this.getObj() instanceof String;
	}
	
	/**
	 * Gets the {@link BaseConsole} that called this event.
	 * 
	 * @since v4.1
	 * @return The {@link BaseConsole} that called this event
	 */
	@Override
	public final BaseConsole getInitiatingBaseConsole() {
		return initiatingBaseConsole;
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
	 * Sets if this event has been canceled.
	 * 
	 * @see #isCanceled()
	 * @param canceled If the event should be canceled
	 * */
	private void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	/**
	 * Cancels this event.
	 * Any {@link com.n9mtq4.logwindow.listener.ListenerAttribute}s
	 * wont receive the event, unless their container has ignoreCanceled
	 * set to true.
	 * 
	 * @see #isCanceled()
	 * @since v5.0
	 * */
	public final void cancel() {
		setCanceled(true);
	}
	
	/**
	 * Gets the object being sent to the {@link com.n9mtq4.logwindow.listener.ObjectListener}.
	 * 
	 * @since v4.1
	 * @return The {@link Object}
	 */
	public final Object getObj() {
		return obj;
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
