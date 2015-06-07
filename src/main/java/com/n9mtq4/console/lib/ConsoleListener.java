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

package com.n9mtq4.console.lib;

import com.n9mtq4.console.lib.annotation.Async;
import com.n9mtq4.console.lib.events.*;
import com.n9mtq4.console.lib.utils.Colour;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */
public abstract class ConsoleListener implements Serializable {
	
	private static final long serialVersionUID = 3170856814584823192L;
	
	/**
	 * A method that takes a string and returns a new instance of a ConsoleListener with the name
	 *
	 * @param name The full class name of the listener
	 * @return A new
	 * with the same name
	 * @throws Exception when something doesn't work
	 */
	public static ConsoleListener getNewListenerByName(String name) throws Exception {
		Class<?> clazz = Class.forName(name);
		Object clazz1 = clazz.newInstance();
		ConsoleListener l = (ConsoleListener) clazz1;
		return l;
	}
	
	private ArrayList<BaseConsole> linkedBaseConsoles;
	private boolean enabled;
	private boolean ignoreDone;
	private boolean isAsyncString;
	private boolean isAsyncObject;
	
	/**
	 * Makes a new {@link ConsoleListener} object.
	 */
	public ConsoleListener() {
		
		linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.ignoreDone = false;
		this.isAsyncString = false;
		this.isAsyncObject = false;
		
	}
	
	/**
	 * On addition.
	 *
	 * @param e the AdditionActionEvent
	 */
	public void onAddition(AdditionActionEvent e) {
	}
	
	/**
	 * On enable.
	 *
	 * @param e the EnableActionEvent
	 */
	public void onEnable(EnableActionEvent e) {
	}
	
	/**
	 * Action performed.
	 *
	 * @param e the ConsoleActionEvent
	 * @param baseConsole The baseConsole that is being used.
	 */
	public abstract void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole);
	
	/**
	 * Object received.
	 *
	 * @param e the SentObjectEvent
	 * @param baseConsole The baseConsole that is being used.
	 */
	public void objectReceived(SentObjectEvent e, BaseConsole baseConsole) {
	}
	
	/**
	 * On disable.
	 *
	 * @param e the DisableActionEvent
	 */
	public void onDisable(DisableActionEvent e) {
	}
	
	/**
	 * On removal.
	 *
	 * @param e the RemovalActionEvent
	 */
	public void onRemoval(RemovalActionEvent e) {
	}
	
	/**
	 * Prevents disabling this listener.<br>
	 * Call this method in the {@link ConsoleListener#onDisable}
	 * and give it the {@link DisableActionEvent}
	 *
	 * @param e the DisableActionEvent
	 */
	public void stopDisable(DisableActionEvent e) {
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getBaseConsole().enableListener(this);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't disable " + this.getClass().getName());
		}
	}
	
	/**
	 * Prevents removal of this listener.<br>
	 * Call this method in the {@link ConsoleListener#onRemoval}
	 * and give it the {@link RemovalActionEvent}
	 *
	 * @param e the e
	 */
	public void stopRemoval(RemovalActionEvent e) {
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getBaseConsole().addListener(this);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't remove " + this.getClass().getName());
		}
	}
	
	/**
	 * Sends this listener a SentObjectEvent.
	 *
	 * @param sentObjectEvent The event to send.
	 */
	public void pushObject(SentObjectEvent sentObjectEvent) {
		
//		TODO: very ugly
		if (isAsyncObject) {
			
			final SentObjectEvent sentObjectEvent1 = sentObjectEvent;
			new Thread(new Runnable() {
				@Override
				public void run() {
					pushSendObjectEvent(sentObjectEvent1);
				}
			}).start();
			
		}else {
			pushSendObjectEvent(sentObjectEvent);
		}
		
	}
	
	private void pushSendObjectEvent(SentObjectEvent sentObjectEvent) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				this.objectReceived(sentObjectEvent, c);
			}
		}catch (ConcurrentModificationException e1) {
//			This is expected sometimes, and isn't a big deal
		}
	}
	
	/**
	 * Sends this listener a ConsoleActionEvent.
	 *
	 * @param consoleActionEvent The event to send.
	 */
	public void push(ConsoleActionEvent consoleActionEvent) {
		
//		TODO: very ugly
		if (isAsyncString) {
			
			final ConsoleActionEvent consoleActionEvent1 = consoleActionEvent;
			new Thread(new Runnable() {
				@Override
				public void run() {
					pushConsoleActionEvent(consoleActionEvent1);
				}
			}).start();
			
		}else {
			pushConsoleActionEvent(consoleActionEvent);
		}
		
	}
	
	private void pushConsoleActionEvent(ConsoleActionEvent consoleActionEvent) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				ConsoleListener.this.actionPerformed(consoleActionEvent, c);
			}
		}catch (ConcurrentModificationException e1) {
		}
	}
	
	private void annotations() {
//		actionPerformed annotation checks
		try {
			Method action = this.getClass().getDeclaredMethod("actionPerformed", ConsoleActionEvent.class, BaseConsole.class);
			if (action.isAnnotationPresent(Async.class)) {
				Async annotation = action.getAnnotation(Async.class);
//				System.out.println(annotation.async());
				this.isAsyncString = annotation.async();
			}
		}catch (Exception e) {
//			e.printStackTrace();
			System.err.println("[WARNING]: something isn't quit right with Async annotation (" + this.getClass().getName() + ")!");
		}
//		objectReceived annotation checks
		try {
			Method action = this.getClass().getDeclaredMethod("objectReceived", SentObjectEvent.class, BaseConsole.class);
			if (action.isAnnotationPresent(Async.class)) {
				Async annotation = action.getAnnotation(Async.class);
				this.isAsyncObject = annotation.async();
			}
		}catch (Exception e) {
//			e.printStackTrace();
//			this is expected as objectReceived isn't abstract
		}
	}
	
	/**
	 * Adds this listener onto a {@link BaseConsole}.
	 *
	 * @param baseConsole the
	 *                    to add this listener to.
	 * @see BaseConsole#addListener
	 * @deprecated use {@link BaseConsole#addListener(ConsoleListener)} instead.
	 */
	@Deprecated
	public void addToConsole(BaseConsole baseConsole) {
		
		if (!linkedBaseConsoles.contains(baseConsole) || !baseConsole.getListeners().contains(this)) {
			linkedBaseConsoles.add(baseConsole);
			baseConsole.addListener(this);
			annotations();
		}
		
	}
	
	/**
	 * Removes this listener from a {@link BaseConsole}.
	 *
	 * @param baseConsole the
	 *                    to remove this listener from.
	 * @see BaseConsole#removeListener
	 * @deprecated use {@link BaseConsole#removeListener(ConsoleListener)} instead.
	 */
	@Deprecated
	public void removeFromConsole(BaseConsole baseConsole) {
		
		if (linkedBaseConsoles.contains(baseConsole) || baseConsole.getListeners().contains(this)) {
			linkedBaseConsoles.remove(baseConsole);
			baseConsole.removeListener(this);
		}
		
	}
	
	/**
	 * Toggles the enabled state of this listener.
	 *
	 * @see ConsoleListener#isEnabled
	 * @see ConsoleListener#setEnabled
	 */
	public void toggleEnabled() {
		
		this.enabled = !this.enabled;
		
	}
	
	/**
	 * Gives you a list of the {@link BaseConsole} this listener is listening on.
	 *
	 * @return An array of
	 * .
	 */
	public ArrayList<BaseConsole> getLinkedBaseConsoles() {
		return (ArrayList<BaseConsole>) linkedBaseConsoles.clone();
	}
	
	/**
	 * Tells you if this listener is enabled.
	 *
	 * @return The enabled boolean.
	 * @see ConsoleListener#setEnabled
	 * @see ConsoleListener#toggleEnabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Sets the enabled value of this listener.
	 *
	 * @param enabled The new value of enabled.
	 * @see ConsoleListener#isEnabled
	 * @see ConsoleListener#toggleEnabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Note: always false.<br>
	 * Tells you if the listener will ignore the isCanceled of {@link ConsoleActionEvent}.
	 *
	 * @return A boolean of if the listener will ignore isCanceled
	 */
	public boolean hasIgnoreDone() {
		return ignoreDone;
	}
	
	public boolean isAsyncString() {
		return isAsyncString;
	}
	
	public boolean isAsyncObject() {
		return isAsyncObject;
	}
	
}
