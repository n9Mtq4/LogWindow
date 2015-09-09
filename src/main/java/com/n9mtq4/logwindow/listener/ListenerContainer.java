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

package com.n9mtq4.logwindow.listener;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.annotation.Async;
import com.n9mtq4.logwindow.events.*;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by will on 8/13/15 at 2:17 PM.
 */
public final class ListenerContainer implements Serializable {
	
	private static final long serialVersionUID = 3878570265471623572L;
	
	/**
	 * Make listener entry.
	 *
	 * @param listener the listener
	 * @return the listener container
	 */
	public static ListenerContainer makeListenerEntry(ListenerAttribute listener) {
		return new ListenerContainer(listener);
	}
	
	/**
	 * Prevents disabling this listener.<br>
	 * Call this method in the {@link ConsoleListener#onDisable}
	 * and give it the {@link DisableActionEvent}
	 *
	 * @param listener the listener
	 * @param e the DisableActionEvent
	 */
	public static void stopDisable(ListenerAttribute listener, DisableActionEvent e) {
		if (e.getType() != DisableActionEvent.CONSOLE_DISPOSE) {
			e.getBaseConsole().enableListenerAttribute(listener);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't disable " + listener.getClass().getName());
		}
	}
	
	/**
	 * Prevents removal of this listener.<br>
	 * Call this method in the {@link ConsoleListener#onRemoval}
	 * and give it the {@link RemovalActionEvent}
	 *
	 * @param listener the listener
	 * @param e the e
	 */
	public static void stopRemoval(ListenerAttribute listener, RemovalActionEvent e) {
		if (e.getType() != RemovalActionEvent.CONSOLE_DISPOSE) {
			e.getBaseConsole().addListenerAttributeRaw(listener);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't remove " + listener.getClass().getName());
		}
	}
	
	private final ListenerAttribute listener;
	private ArrayList<BaseConsole> linkedBaseConsoles;
	private boolean enabled;
	private boolean ignoreDone;
	private boolean hasBeenEnabled;
	
	private boolean isAsyncAddition;
	private boolean isAsyncEnable;
	private boolean isAsyncString;
	private boolean isAsyncObject;
	private boolean isAsyncDisable;
	private boolean isAsyncRemoval;
	
	private ListenerContainer(ListenerAttribute listener) {
		this.listener = listener;
		this.linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.hasBeenEnabled = false;
		this.ignoreDone = false;
		this.isAsyncAddition = false;
		this.isAsyncString = false;
		this.isAsyncObject = false;
		this.isAsyncEnable = false;
		this.isAsyncDisable = false;
		this.isAsyncRemoval = false;
		annotations();
	}
	
	/**
	 * Push object.
	 *
	 * @param sentObjectEvent the sent object event
	 */
	public final void pushObject(SentObjectEvent sentObjectEvent) {
		if (listener instanceof ObjectListener) {
			if (isAsyncObject) {
				
				final SentObjectEvent sentObjectEvent1 = sentObjectEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushObjectEvent(sentObjectEvent1);
					}
				}).start();
				
			}else {
				pushObjectEvent(sentObjectEvent);
			}
		}
	}
	
	/**
	 * Push string.
	 *
	 * @param consoleActionEvent the console action event
	 */
	public final void pushString(ConsoleActionEvent consoleActionEvent) {
		if (listener instanceof StringListener) {
			if (isAsyncString) {
				
				final ConsoleActionEvent consoleActionEvent1 = consoleActionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushStringEvent(consoleActionEvent1);
					}
				}).start();
				
			}else {
				pushStringEvent(consoleActionEvent);
			}
		}
	}
	
	/**
	 * Push added.
	 *
	 * @param additionActionEvent the addition action event
	 */
	public final void pushAdded(AdditionActionEvent additionActionEvent) {
		if (listener instanceof AdditionListener) {
			if (isAsyncAddition) {
				
				final AdditionActionEvent additionActionEvent1 = additionActionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushAddedEvent(additionActionEvent1);
					}
				}).start();
				
			}else {
				pushAddedEvent(additionActionEvent);
			}
		}
	}
	
	/**
	 * Push enabled.
	 *
	 * @param enableActionEvent the enable action event
	 */
	public final void pushEnabled(EnableActionEvent enableActionEvent) {
		this.hasBeenEnabled = true;
		if (listener instanceof EnableListener) {
			if (isAsyncEnable) {
				
				final EnableActionEvent enableActionEvent1 = enableActionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushEnabledEvent(enableActionEvent1);
					}
				}).start();
				
			}else {
				pushEnabledEvent(enableActionEvent);
			}
		}
	}
	
	/**
	 * Push disabled.
	 *
	 * @param disableActionEvent the disable action event
	 */
	public final void pushDisabled(DisableActionEvent disableActionEvent) {
		if (listener instanceof DisableListener) {
			if (isAsyncDisable) {
				
				final DisableActionEvent disableActionEvent1 = disableActionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushDisabledEvent(disableActionEvent1);
					}
				}).start();
				
			}else {
				pushDisabledEvent(disableActionEvent);
			}
		}
	}
	
	/**
	 * Push removed.
	 *
	 * @param removalActionEvent the removal action event
	 */
	public final void pushRemoved(RemovalActionEvent removalActionEvent) {
		if (listener instanceof RemovalListener) {
			if (isAsyncRemoval) {
				
				final RemovalActionEvent removalActionEvent1 = removalActionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushRemovedEvent(removalActionEvent1);
					}
				}).start();
				
			}else {
				pushRemovedEvent(removalActionEvent);
			}
		}
	}
	
	private void pushObjectEvent(SentObjectEvent sentObjectEvent) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				((ObjectListener) listener).objectReceived(sentObjectEvent, c);
			}
		}catch (ConcurrentModificationException e) {
//			this is expected sometimes, and isn't a big deal
		}
	}
	
	private void pushStringEvent(ConsoleActionEvent consoleActionEvent) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				((StringListener) listener).actionPerformed(consoleActionEvent, c);
			}
		}catch (ConcurrentModificationException e) {
//			this is expected sometimes, and isn't a big deal
		}
	}
	
	private void pushAddedEvent(AdditionActionEvent additionActionEvent) {
		((AdditionListener) listener).onAddition(additionActionEvent);
	}
	
	private void pushEnabledEvent(EnableActionEvent enableActionEvent) {
		((EnableListener) listener).onEnable(enableActionEvent);
	}
	
	private void pushDisabledEvent(DisableActionEvent disableActionEvent) {
		((DisableListener) listener).onDisable(disableActionEvent);
	}
	
	private void pushRemovedEvent(RemovalActionEvent removalActionEvent) {
		((RemovalListener) listener).onRemoval(removalActionEvent);
	}
	
	
	private void annotations() {
		if (listener instanceof AdditionListener) {
			this.isAsyncAddition = shouldBeAsync("onAddition", AdditionActionEvent.class);
		}
		if (listener instanceof EnableListener) {
			this.isAsyncEnable = shouldBeAsync("onEnable", EnableActionEvent.class);
		}
		if (listener instanceof StringListener) {
			this.isAsyncString = shouldBeAsync("actionPerformed", ConsoleActionEvent.class, BaseConsole.class);
		}
		if (listener instanceof ObjectListener) {
			this.isAsyncObject = shouldBeAsync("objectReceived", SentObjectEvent.class, BaseConsole.class);
		}
		if (listener instanceof DisableListener) {
			this.isAsyncDisable = shouldBeAsync("onDisable", DisableActionEvent.class);
		}
		if (listener instanceof RemovalListener) {
			this.isAsyncRemoval = shouldBeAsync("onRemoval", RemovalActionEvent.class);
		}
	}
	
	private boolean shouldBeAsync(String methodName, Class... params) {
		try {
			Method action = listener.getClass().getDeclaredMethod(methodName, params);
			if (action.isAnnotationPresent(Async.class)) {
				Async annotation = action.getAnnotation(Async.class);
				return annotation.async();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("[WARNING]: something isn't quit right with " + methodName + " Async annotation (" + this.getClass().getName() + ")!");
		}
		return false;
	}
	
	/**
	 * Adds this listener onto a {@link BaseConsole}.
	 *
	 * @param baseConsole the
	 *                    to add this listener to.
	 * @see BaseConsole#addListenerContainer(ListenerContainer)
	 * @deprecated use  instead.
	 */
	@Deprecated
	public final void addToConsole(BaseConsole baseConsole) {
		
		if (!linkedBaseConsoles.contains(baseConsole) || !baseConsole.getListenerContainers().contains(this)) {
			linkedBaseConsoles.add(baseConsole);
			baseConsole.addListenerContainerRaw(this);
			annotations();
		}
		
	}
	
	/**
	 * Removes this listener from a {@link BaseConsole}.
	 *
	 * @param baseConsole the to remove this listener from.
	 * @see BaseConsole#removeListenerContainer(ListenerContainer)
	 * @deprecated use  instead.
	 */
	@Deprecated
	public final void removeFromConsole(BaseConsole baseConsole) {
		
		if (linkedBaseConsoles.contains(baseConsole) || baseConsole.getListenerContainers().contains(this)) {
			linkedBaseConsoles.remove(baseConsole);
			baseConsole.removeListenerContainer(this);
		}
		
	}
	
	/**
	 * Has been enabled.
	 *
	 * @return the boolean
	 */
	public final boolean hasBeenEnabled() {
		return hasBeenEnabled;
	}
	
	/**
	 * Sets enabled.
	 *
	 * @param enabled the enabled
	 * @return the enabled
	 */
	public final ListenerContainer setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	/**
	 * Is ignore done.
	 *
	 * @return the boolean
	 */
	public final boolean isIgnoreDone() {
		return ignoreDone;
	}
	
	/**
	 * Sets ignore done.
	 *
	 * @param ignoreDone the ignore done
	 * @return the ignore done
	 */
	public final ListenerContainer setIgnoreDone(boolean ignoreDone) {
		this.ignoreDone = ignoreDone;
		return this;
	}
	
	/**
	 * Is async string.
	 *
	 * @return the boolean
	 */
	public final boolean isAsyncString() {
		return isAsyncString;
	}
	
	/**
	 * Sets is async string.
	 *
	 * @param isAsyncString the is async string
	 * @return the is async string
	 */
	public final ListenerContainer setIsAsyncString(boolean isAsyncString) {
		this.isAsyncString = isAsyncString;
		return this;
	}
	
	/**
	 * Is async object.
	 *
	 * @return the boolean
	 */
	public final boolean isAsyncObject() {
		return isAsyncObject;
	}
	
	/**
	 * Sets is async object.
	 *
	 * @param isAsyncObject the is async object
	 * @return the is async object
	 */
	public final ListenerContainer setIsAsyncObject(boolean isAsyncObject) {
		this.isAsyncObject = isAsyncObject;
		return this;
	}
	
	/**
	 * Gets attribute.
	 *
	 * @return the attribute
	 */
	public final ListenerAttribute getAttribute() {
		return listener;
	}
	
	/**
	 * Gets linked base consoles.
	 *
	 * @return the linked base consoles
	 */
	public final ArrayList<BaseConsole> getLinkedBaseConsoles() {
		return linkedBaseConsoles;
	}
	
	/**
	 * Is enabled.
	 *
	 * @return the boolean
	 */
	public final boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Has ignore done.
	 *
	 * @return the boolean
	 */
	public final boolean hasIgnoreDone() {
		return ignoreDone;
	}
	
	/**
	 * Has async string.
	 *
	 * @return the boolean
	 */
	public final boolean hasAsyncString() {
		return isAsyncString;
	}
	
	/**
	 * Has async object.
	 *
	 * @return the boolean
	 */
	public final boolean hasAsyncObject() {
		return isAsyncObject;
	}
	
}
