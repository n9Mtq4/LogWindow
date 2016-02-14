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
import com.n9mtq4.logwindow.annotation.ListensFor;
import com.n9mtq4.logwindow.events.AdditionEvent;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.GenericEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

/**
 * A class that wraps a {@link ListenerAttribute} for use on a
 * {@link BaseConsole}.
 * 
 * @version v5.1
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
	
	private HashMap<Class<?>, Method> listenerMethodLookup;
	private HashMap<Class<?>, Boolean> listenerMethodAlreadySeen;
	private HashMap<Method, Boolean> listenerMethodAsync;
	
	private boolean isAsyncAddition;
	private boolean isAsyncEnable;
	private boolean isAsyncObject;
	private boolean isAsyncDisable;
	private boolean isAsyncRemoval;
	
	private boolean init = false;
	
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
//		init();
	}
	
	private final void init() {
		if (init) return;
		init = true;
		this.listenerMethodLookup = findGenericListeners();
		this.listenerMethodAlreadySeen = new HashMap<Class<?>, Boolean>();
		asyncAnnotations();
	}
	
	public void pushGeneric(final GenericEvent event) {
//		listenerMethodLookup.get(event.getClass()).invoke(listener, event, base)
		if (!(listener instanceof GenericListener)) return;
		final Method target = findTarget(event);
		if (target == null) return;
//		boolean isAsync = listenerMethodAsync.get(event.getClass());
		boolean isAsync = genericFindShouldBeAsync(target);
		if (isAsync) new Thread(new Runnable() {
				@Override
				public void run() {
					pushGenericEvent(event, target);
				}
			}).start();
		else pushGenericEvent(event, target);
		
	}
	
	private final Method findTarget(GenericEvent event) {
//		simple look up
		Method target = listenerMethodLookup.get(event.getClass());
		if (target != null) return target;
		
//		already seen
		Boolean b = listenerMethodAlreadySeen.get(event.getClass());
		if (b != null) return null;
		
//		other processing now
		for (Class<?> clazz : listenerMethodLookup.keySet()) {
			if (clazz.isInstance(event)) {
				Method method = listenerMethodLookup.get(clazz);
				listenerMethodLookup.put(clazz, method);
				return method;
			}
		}
		
//		there is no support for this class
		listenerMethodAlreadySeen.put(event.getClass(), true);
		return null;
		
	}
	
	private final boolean genericFindShouldBeAsync(Method method) {
//		simple look up
		Boolean pisAsync = listenerMethodAsync.get(method);
		if (pisAsync != null) return pisAsync;
		
//		error
		System.err.println("[WARNING]: async annotation for " + method.getName() + " did not work");
		return false; // default to false
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
	
	private void pushGenericEvent(GenericEvent event, Method target) {
		try {
			for (BaseConsole c : linkedBaseConsoles) {
//				((ObjectListener) listener).objectReceived(objectEvent, c);
				target.invoke(listener, event, c);
			}
		}catch (ConcurrentModificationException e) {
//			this is expected sometimes, and isn't a big deal
		}catch (InvocationTargetException e) {
			System.err.println("Make sure the generic method looks something like: " + 
					target.getName() + "(" + event.getClass().getName() + " " + 
					event.getClass().getSimpleName().toLowerCase() + ", " + 
					BaseConsole.class.getName() + " baseConsole)");
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			System.err.println("Make sure the generic listener method " + target.getName() + " is not private and can be accessed");
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			System.err.println("Make sure the generic method looks something like: " + 
					target.getName() + "(" + event.getClass().getName() + " " + 
					event.getClass().getSimpleName().toLowerCase() + ", " + 
					BaseConsole.class.getName() + " baseConsole)");
			e.printStackTrace();
		}catch (Exception e) {
			if (target != null) System.err.println("Unknown error with generic listener method " + target.getName());
			else System.err.println("Unknown error with generic listener method! Target is null");
			e.printStackTrace();
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
	
	private HashMap<Class<?>, Method> findGenericListeners() {
		if (!(listener instanceof GenericListener)) return null;
		HashMap<Class<?>, Method> hm = new HashMap<Class<?>, Method>();
		Method[] methods = listener.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(ListensFor.class)) {
				ListensFor annotation = method.getAnnotation(ListensFor.class);
				method.setAccessible(true);
				Class<?> annotationValue = (annotation.value() == ListensFor.INHERIT.class) ? method.getParameterTypes()[0] : annotation.value();
				hm.put(annotationValue, method);
			}
		}
		return hm;
	}
	
	private void asyncAnnotations() {
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
//		the generic async annotations
		if (listener instanceof GenericListener) {
			this.listenerMethodAsync = new HashMap<Method, Boolean>(listenerMethodLookup.size());
			for (Class<?> clazz : listenerMethodLookup.keySet()) {
				Method method = listenerMethodLookup.get(clazz);
				if (method.isAnnotationPresent(Async.class)) {
					Async annotation = method.getAnnotation(Async.class);
					listenerMethodAsync.put(method, annotation.async());
				}else {
					listenerMethodAsync.put(method, false);
				}
			}
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
			init();
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
