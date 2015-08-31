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
	
	public static ListenerContainer makeListenerEntry(ListenerAttribute listener) {
		return new ListenerContainer(listener);
	}
	
	/**
	 * Prevents disabling this listener.<br>
	 * Call this method in the {@link ConsoleListener#onDisable}
	 * and give it the {@link DisableActionEvent}
	 *
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
	private boolean isAsyncString;
	private boolean isAsyncObject;
	
	private ListenerContainer(ListenerAttribute listener) {
		this.listener = listener;
		this.linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.ignoreDone = false;
		this.isAsyncString = false;
		this.isAsyncObject = false;
		annotations();
	}
	
	public final void pushObject(SentObjectEvent sentObjectEvent) {
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
	
	public final void pushString(ConsoleActionEvent consoleActionEvent) {
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
	
	private void pushSendObjectEvent(SentObjectEvent sentObjectEvent) {
		if (listener instanceof ObjectListener) {
			try {
				for (BaseConsole c : linkedBaseConsoles) {
					((ObjectListener) listener).objectReceived(sentObjectEvent, c);
				}
			}catch (ConcurrentModificationException e1) {
//			This is expected sometimes, and isn't a big deal
			}
		}
	}
	
	private void pushConsoleActionEvent(ConsoleActionEvent consoleActionEvent) {
		if (listener instanceof StringListener) {
			try {
				for (BaseConsole c : linkedBaseConsoles) {
					((StringListener) listener).actionPerformed(consoleActionEvent, c);
				}
			}catch (ConcurrentModificationException e1) {
//				This is expected sometimes, and isn't a big deal
			}
		}
	}
	
	public final void pushAdded(AdditionActionEvent additionActionEvent) {
		if (listener instanceof AdditionListener) {
			((AdditionListener) listener).onAddition(additionActionEvent);
		}
	}
	
	public final void pushEnabled(EnableActionEvent enableActionEvent) {
		if (listener instanceof EnableListener) {
			((EnableListener) listener).onEnable(enableActionEvent);
		}
	}
	
	public final void pushDisabled(DisableActionEvent disableActionEvent) {
		if (listener instanceof DisableListener) {
			((DisableListener) listener).onDisable(disableActionEvent);
		}
	}
	
	public final void pushRemoved(RemovalActionEvent removalActionEvent) {
		if (listener instanceof RemovalListener) {
			((RemovalListener) listener).onRemoval(removalActionEvent);
		}
	}
	
	private void annotations() {
//		actionPerformed annotation checks
		try {
			Method action = listener.getClass().getDeclaredMethod("actionPerformed", ConsoleActionEvent.class, BaseConsole.class);
			if (action.isAnnotationPresent(Async.class)) {
				Async annotation = action.getAnnotation(Async.class);
//				System.out.println(annotation.async());
				this.isAsyncString = annotation.async();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("[WARNING]: something isn't quit right with Async annotation (" + this.getClass().getName() + ")!");
		}
//		objectReceived annotation checks
		try {
			Method action = listener.getClass().getDeclaredMethod("objectReceived", SentObjectEvent.class, BaseConsole.class);
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
	 * @see BaseConsole#addListenerContainer(ListenerContainer) 
	 * @deprecated use {@link BaseConsole#addListenerContainer(ListenerContainer)} instead.
	 */
	@Deprecated
	public final void addToConsole(BaseConsole baseConsole) {
		
		if (!linkedBaseConsoles.contains(baseConsole) || !baseConsole.getListenerContainers().contains(this)) {
			linkedBaseConsoles.add(baseConsole);
			baseConsole.addListenerContainer(this);
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
	
	public final ListenerContainer setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public final boolean isIgnoreDone() {
		return ignoreDone;
	}
	
	public final ListenerContainer setIgnoreDone(boolean ignoreDone) {
		this.ignoreDone = ignoreDone;
		return this;
	}
	
	public final boolean isAsyncString() {
		return isAsyncString;
	}
	
	public final ListenerContainer setIsAsyncString(boolean isAsyncString) {
		this.isAsyncString = isAsyncString;
		return this;
	}
	
	public final boolean isAsyncObject() {
		return isAsyncObject;
	}
	
	public final ListenerContainer setIsAsyncObject(boolean isAsyncObject) {
		this.isAsyncObject = isAsyncObject;
		return this;
	}
	
	public final ListenerAttribute getAttribute() {
		return listener;
	}
	
	public final ArrayList<BaseConsole> getLinkedBaseConsoles() {
		return linkedBaseConsoles;
	}
	
	public final boolean isEnabled() {
		return enabled;
	}
	
	public final boolean hasIgnoreDone() {
		return ignoreDone;
	}
	
	public final boolean hasAsyncString() {
		return isAsyncString;
	}
	
	public final boolean hasAsyncObject() {
		return isAsyncObject;
	}
	
}
