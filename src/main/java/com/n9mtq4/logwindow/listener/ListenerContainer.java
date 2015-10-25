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
import com.n9mtq4.logwindow.events.AdditionEvent;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * A class that wraps a {@link ListenerAttribute} for use on a
 * {@link BaseConsole}.
 * 
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan
 * @since v5.0
 */
public final class ListenerContainer implements Serializable {
	
	private static final long serialVersionUID = 3878570265471623572L;
	
	/**
	 * Makes a new listener entry.
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
	 * and give it the {@link DisableEvent}
	 *
	 * @param listener the listener
	 * @param e the DisableEvent
	 */
	public static void stopDisable(ListenerAttribute listener, DisableEvent e) {
		if (e.getType() != DisableEvent.CONSOLE_DISPOSE) {
			e.getBaseConsole().enableListenerAttribute(listener);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't disable " + listener.getClass().getName());
		}
	}
	
	/**
	 * Prevents removal of this listener.<br>
	 * Call this method in the {@link ConsoleListener#onRemoval}
	 * and give it the {@link RemovalEvent}
	 *
	 * @param listener the listener
	 * @param e the e
	 */
	public static void stopRemoval(ListenerAttribute listener, RemovalEvent e) {
		if (e.getType() != RemovalEvent.CONSOLE_DISPOSE) {
			e.getBaseConsole().addListenerAttributeRaw(listener);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't remove " + listener.getClass().getName());
		}
	}
	
	private final ListenerAttribute listener;
	private ArrayList<BaseConsole> linkedBaseConsoles;
	private boolean enabled;
	private boolean ignoreCanceled;
	private boolean hasBeenEnabled;
	
	private boolean isAsyncAddition;
	private boolean isAsyncEnable;
	private boolean isAsyncObject;
	private boolean isAsyncDisable;
	private boolean isAsyncRemoval;
	
	private ListenerContainer(ListenerAttribute listener) {
		this.listener = listener;
		this.linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.hasBeenEnabled = false;
		this.ignoreCanceled = false;
		this.isAsyncAddition = false;
		this.isAsyncObject = false;
		this.isAsyncEnable = false;
		this.isAsyncDisable = false;
		this.isAsyncRemoval = false;
		annotations();
	}
	
	/**
	 * Push object.
	 *
	 * @param objectEvent the sent object event
	 */
	public final void pushObject(ObjectEvent objectEvent) {
		if (listener instanceof ObjectListener) {
			if (isAsyncObject) {
				
				final ObjectEvent objectEvent1 = objectEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushObjectEvent(objectEvent1);
					}
				}).start();
				
			}else {
				pushObjectEvent(objectEvent);
			}
		}
	}
	
	/**
	 * Push added.
	 *
	 * @param additionEvent the addition action event
	 */
	public final void pushAdded(AdditionEvent additionEvent) {
		if (listener instanceof AdditionListener) {
			if (isAsyncAddition) {
				
				final AdditionEvent additionEvent1 = additionEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushAddedEvent(additionEvent1);
					}
				}).start();
				
			}else {
				pushAddedEvent(additionEvent);
			}
		}
	}
	
	/**
	 * Push enabled.
	 *
	 * @param enableEvent the enable action event
	 */
	public final void pushEnabled(EnableEvent enableEvent) {
		this.hasBeenEnabled = true;
		if (listener instanceof EnableListener) {
			if (isAsyncEnable) {
				
				final EnableEvent enableEvent1 = enableEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushEnabledEvent(enableEvent1);
					}
				}).start();
				
			}else {
				pushEnabledEvent(enableEvent);
			}
		}
	}
	
	/**
	 * Push disabled.
	 *
	 * @param disableEvent the disable action event
	 */
	public final void pushDisabled(DisableEvent disableEvent) {
		if (listener instanceof DisableListener) {
			if (isAsyncDisable) {
				
				final DisableEvent disableEvent1 = disableEvent;
				new Thread(new Runnable() {
					@Override
					public void run() {
						pushDisabledEvent(disableEvent1);
					}
				}).start();
				
			}else {
				pushDisabledEvent(disableEvent);
			}
		}
	}
	
	/**
	 * Push removed.
	 *
	 * @param removalActionEvent the removal action event
	 */
	public final void pushRemoved(RemovalEvent removalActionEvent) {
		if (listener instanceof RemovalListener) {
			if (isAsyncRemoval) {
				
				final RemovalEvent removalActionEvent1 = removalActionEvent;
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
	
	private void pushObjectEvent(ObjectEvent objectEvent) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				((ObjectListener) listener).objectReceived(objectEvent, c);
			}
		}catch (ConcurrentModificationException e) {
//			this is expected sometimes, and isn't a big deal
		}
	}
	
	private void pushAddedEvent(AdditionEvent additionEvent) {
		((AdditionListener) listener).onAddition(additionEvent);
	}
	
	private void pushEnabledEvent(EnableEvent enableEvent) {
		((EnableListener) listener).onEnable(enableEvent);
	}
	
	private void pushDisabledEvent(DisableEvent disableEvent) {
		((DisableListener) listener).onDisable(disableEvent);
	}
	
	private void pushRemovedEvent(RemovalEvent removalActionEvent) {
		((RemovalListener) listener).onRemoval(removalActionEvent);
	}
	
	
	private void annotations() {
		if (listener instanceof AdditionListener) {
			this.isAsyncAddition = shouldBeAsync("onAddition", AdditionEvent.class);
		}
		if (listener instanceof EnableListener) {
			this.isAsyncEnable = shouldBeAsync("onEnable", EnableEvent.class);
		}
		if (listener instanceof ObjectListener) {
			this.isAsyncObject = shouldBeAsync("objectReceived", ObjectEvent.class, BaseConsole.class);
		}
		if (listener instanceof DisableListener) {
			this.isAsyncDisable = shouldBeAsync("onDisable", DisableEvent.class);
		}
		if (listener instanceof RemovalListener) {
			this.isAsyncRemoval = shouldBeAsync("onRemoval", RemovalEvent.class);
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
	 * @deprecated use {@link BaseConsole#addListenerContainer(ListenerContainer)} instead.
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
	 * @deprecated use {@link BaseConsole#removeListenerContainer(ListenerContainer)} instead.
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
	public final boolean isIgnoreCanceled() {
		return ignoreCanceled;
	}
	
	/**
	 * Sets ignore done.
	 *
	 * @param ignoreCanceled the ignore done
	 * @return the ignore done
	 */
	public final ListenerContainer setIgnoreCanceled(boolean ignoreCanceled) {
		this.ignoreCanceled = ignoreCanceled;
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
		return ignoreCanceled;
	}
	
}
