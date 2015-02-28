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

import com.n9mtq4.console.lib.command.ConsoleCommand;
import com.n9mtq4.console.lib.events.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */
public abstract class ConsoleListener {
	
	/**
	 * A method that takes a string and returns a new instance of a ConsoleListener with the name
	 * @param name The full class name of the listener
	 * @return A new {@link com.n9mtq4.console.lib.ConsoleListener} with the same name
	 * @throws Exception when something doesn't work
	 * */
	public static ConsoleListener getNewListenerByName(String name) throws Exception{
		try {
			Class<?> clazz = Class.forName(name);
			Object clazz1 = clazz.newInstance();
			ConsoleListener l = (ConsoleListener) clazz1;
			return l;
		}catch (Exception e) {
			throw e;
		}
	}
	
	private ArrayList<BaseConsole> linkedBaseConsoles;
	private boolean enabled;
	private boolean ignoreDone;
	
	/**
	 * Makes a new {@link ConsoleListener} object.
	 */
	public ConsoleListener() {
		
		linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.ignoreDone = false;
		
	}
	
	public void onAddition(AdditionActionEvent e) {
	}
	
	public void onEnable(EnableActionEvent e) {
	}
	
	public abstract void actionPerformed(ConsoleActionEvent e);
	
	public void onDisable(DisableActionEvent e) {
	}
	
	public void onRemoval(RemovalActionEvent e) {
	}
	
	/**
	 * Sends this listener a String.
	 *
	 * @param text The string to send to actionPerformed
	 * @see ConsoleListener#push(ConsoleActionEvent)
	 * @deprecated use {@link ConsoleListener#push(ConsoleActionEvent)} instead.
	 */
	@Deprecated
	public void push(String text) {
		
		ConsoleCommand command = new ConsoleCommand(text);
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				this.actionPerformed(new ConsoleActionEvent(c, command));
			}
		}catch (ConcurrentModificationException e) {
		}
		
	}
	
	/**
	 * Sends this listener a ConsoleActionEvent.
	 *
	 * @param consoleActionEvent The event to send.
	 */
	public void push(ConsoleActionEvent consoleActionEvent) {
		
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				this.actionPerformed(consoleActionEvent);
			}
		}catch (ConcurrentModificationException e1) {
		}
		
	}
	
	/**
	 * Adds this listener onto a {@link BaseConsole}.
	 *
	 * @param baseConsole the {@link BaseConsole} to add this listener to.
	 * @see BaseConsole#addListener
	 * @deprecated use {@link BaseConsole#addListener} instead.
	 */
	@Deprecated
	public void addToConsole(BaseConsole baseConsole) {
		
		if (!linkedBaseConsoles.contains(baseConsole) || !baseConsole.getListeners().contains(this)) {
			linkedBaseConsoles.add(baseConsole);
			baseConsole.addListener(this);
		}
		
	}
	
	/**
	 * Removes this listener from a {@link BaseConsole}.
	 *
	 * @param baseConsole the {@link BaseConsole} to remove this listener from.
	 * @see BaseConsole#removeListener
	 * @deprecated use {@link BaseConsole#removeListener} instead.
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
	 * @return An array of {@link BaseConsole}.
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
	 * Tells you if the listener will ignore the isDone of {@link ConsoleActionEvent}.
	 *
	 * @return A boolean of if the listener will ignore the isDone
	 */
	public boolean hasIgnoreDone() {
		return ignoreDone;
	}
	
}
