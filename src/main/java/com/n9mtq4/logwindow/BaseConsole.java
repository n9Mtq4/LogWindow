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

package com.n9mtq4.logwindow;

import com.n9mtq4.logwindow.command.ConsoleCommand;
import com.n9mtq4.logwindow.dispose.ShutdownHook;
import com.n9mtq4.logwindow.events.*;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.managers.PluginManager;
import com.n9mtq4.logwindow.modules.ModuleJarLoader;
import com.n9mtq4.logwindow.modules.ModuleListener;
import com.n9mtq4.logwindow.ui.ConsoleUI;
import com.n9mtq4.logwindow.ui.UIContainer;
import com.n9mtq4.logwindow.ui.attributes.History;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by Will on 10/20/14.
 */

/**
 * The class that handles all User to Listener interactions.
 * 
 * @since v0.1
 * @author Will "n9Mtq4" Bresnahan
 */
@SuppressWarnings("unused")
public final class BaseConsole implements Serializable {
	
	private static final long serialVersionUID = 992050290203752760L;
	
	/**
	 * When {@link #pushObject(Object)} or {@link #sendPluginsObject(Object)}
	 * is called the message is set to the DEFAULT_OBJECT_PUSH_MESSAGE.
	 */
	private static final String DEFAULT_OBJECT_PUSH_MESSAGE = "";
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 * 
	 * @deprecated Not used, and not necessary, since I discovered {@link Runtime#addShutdownHook(Thread)}
	 */
	@Deprecated
	private static final ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 */
	private final ArrayList<ListenerContainer> listenerContainers;
	/**
	 * Keeps a record of the input.
	 * TODO: remove deprecated field?
	 * 
	 * @deprecated Not a necessary feature, may be removed before v5 release
	 */
	@Deprecated
	private final ArrayList<String> history;
	/**
	 * Has the local id for the {@link BaseConsole}.
	 */
	private final int id;
	/**
	 * Contains all {@link ConsoleUI}s attached.
	 *
	 * @see BaseConsole#addConsoleUi
	 * @see BaseConsole#removeUiContainer
	 */
	private final ArrayList<UIContainer> uiContainers;
	/**
	 * The thread that is called when the program exits
	 */
	private final ShutdownHook shutdownHook;
	/**
	 * Has this BaseConsole been disposed?
	 * */
	private boolean disposed;
	
	/**
	 * Constructor for {@link BaseConsole}.
	 */
	public BaseConsole() {
		this.listenerContainers = new ArrayList<ListenerContainer>();
		initMandatoryListeners();
		this.history = new ArrayList<String>();
		globalList.add(this);
		this.id = globalList.indexOf(this);
		this.uiContainers = new ArrayList<UIContainer>();
		this.shutdownHook = new ShutdownHook(this);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
	
	/**
	 * Instantiates a new {@link BaseConsole} while adding
	 * a gui. This is equivalent to:<br>
	 * 
	 * <code>
	 *     
	 *     BaseConsole baseConsole = new BaseConsole();<br>
	 *     baseConsole.addConsoleUi(consoleUi);
	 *     
	 * </code>
	 *
	 * @param consoleUI the console gui
	 */
	public BaseConsole(ConsoleUI consoleUI) {
		this();
		addConsoleUi(consoleUI);
	}
	
	/**
	 * Handles disposing down the {@link ListenerAttribute}s and {@link ConsoleUI}s.<br>
	 * Note: if overriding make sure to call super to close {@link ListenerAttribute} and {@link ConsoleUI}.<br>
	 *
	 * @see ConsoleUI#dispose
	 * @see com.n9mtq4.logwindow.listener.DisableListener#onDisable(DisableActionEvent)
	 * @see com.n9mtq4.logwindow.listener.RemovalListener#onRemoval(RemovalActionEvent)
	 */
	public final void dispose() {
		
		System.out.println("Disposing of BaseConsole with id of " + getId());
		
//		remove the listeners, while trying to protect against Concurrent Modification
		ArrayList<ListenerContainer> listenerContainers1 = getListenerContainers();
		for (ListenerContainer listenerContainer : listenerContainers1) {
			removeListenerContainer(listenerContainer, RemovalActionEvent.CONSOLE_DISPOSE);
		}
		
//		remove the ConsoleUIs, while trying to protect against Concurrent Modification
		ArrayList<UIContainer> uiContainers1 = getUIContainers();
		for (UIContainer uiContainer : uiContainers1) {
			removeUiContainer(uiContainer);
		}
		
//		removes shutdown hook because this BaseConsole has already been disposed
		try {
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
		}catch (IllegalStateException ignored) {} // this is ok, because this method should be called from the shutdown hook
		this.disposed = true;
		
	}
	
	/**
	 * Adds required {@link ListenerAttribute}s to the {@link BaseConsole}.<br>
	 * This is called in the constructor.
	 */
	private void initMandatoryListeners() {
		
		this.addListenerAttribute(new ModuleListener());
		this.addListenerAttribute(new ModuleJarLoader());
		
	}
	
	/**
	 * Prints text in colour to the {@link ConsoleUI}s
	 *
	 * @param object The object to print.
	 * @param colour The colour to print the text in.
	 */
	public final void print(Object object, Colour colour) {
		for (UIContainer gui : uiContainers) {
			gui.print(object, colour);
		}
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 *
	 * @param object The object to print.
	 */
	public final void print(Object object) {
		print(object, null);
	}
	
	/**
	 * Prints object in colour to the ConsoleGuis.
	 *
	 * @param object The object to print.
	 * @param colour The colour to print the object in.
	 */
	public final void println(Object object, Colour colour) {
		print(object, colour);
		println();
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 *
	 * @param object The object to print.
	 */
	public final void println(Object object) {
		println(object, null);
	}
	
	/**
	 * Prints a new line.
	 * */
	public final void println() {
		print("\n");
	}
	
	/**
	 * Send plugins object.
	 *
	 * @param object the object
	 */
	public final void sendPluginsObject(Object object) {
		sendPluginsObject(object, DEFAULT_OBJECT_PUSH_MESSAGE);
	}
	
	/**
	 * Send plugins object.
	 *
	 * @param object the object
	 * @param message the message
	 */
	public final void sendPluginsObject(Object object, String message) {
		history.add(object.getClass().getName() + " : " + message);
		pushObject(object, message);
	}
	
	/**
	 * Pushes an object to the {@link com.n9mtq4.logwindow.listener.ObjectListener}.
	 * Doesn't keep object in history. If you want history support use
	 * {@link #sendPluginsObject(Object)} or {@link #sendPluginsObject(Object, String)}.
	 * The message is set to "" as a default.
	 * 
	 * @see #sendPluginsObject(Object)
	 * @see #sendPluginsObject(Object, String)
	 * @see #pushObject(Object, String)
	 * @param object the object to push
	 */
	public final void pushObject(Object object) {
		pushObject(object, DEFAULT_OBJECT_PUSH_MESSAGE);
	}
	
	/**
	 * Push object.
	 *
	 * @param object the object
	 * @param message the message
	 */
	public final void pushObject(Object object, String message) {
		
//		has to clone iterator to prevent concurrent modification
		SentObjectEvent sentObjectEvent = new SentObjectEvent(this, object, message);
		ArrayList<ListenerContainer> listenerContainers1 = getListenerContainers();
		
		for (ListenerContainer listenerContainer : listenerContainers1) {
			try {
				
				if (listenerContainer.isEnabled() && (!sentObjectEvent.isCanceled() || listenerContainer.hasIgnoreDone())) {
					listenerContainer.pushObject(sentObjectEvent);
				}
				
			}catch (Exception e) {
//				wrap every listener in its own try so the program con continue if there is a crash.
//				catch anything that happens in a Listener and stop it from
//				bubbling up and hurting the rest of the program
				this.printStackTrace(e);
				e.printStackTrace();
				println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
				System.out.println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
			}
		}
		
	}
	
	/**
	 * Note: use me to send input when using a custom {@link ConsoleUI}.<br>
	 * Takes {@link String} and iterates through all {@link ListenerAttribute} on console.
	 *
	 * @param text String to send to
	 */
	public final void sendPluginsString(String text) {
		history.add(text);
		for (UIContainer g : uiContainers) {
			if (g.getGui() instanceof History) ((History) g.getGui()).historyUpdate();
		}
		push(text);
	}
	
	/**
	 * Low level version of {@link BaseConsole#sendPluginsString}.
	 *
	 * @param text String to send to the listeners
	 */
	public final void push(String text) {
		
//		has to clone iterator to prevent concurrent modification
		ConsoleCommand consoleCommand = new ConsoleCommand(text);
		ConsoleActionEvent consoleActionEvent = new ConsoleActionEvent(this, consoleCommand);
		ArrayList<ListenerContainer> listenerContainers1 = getListenerContainers();
		
		for (ListenerContainer listenerContainer : listenerContainers1) {
			try {
				
				if (listenerContainer.isEnabled() && (!consoleActionEvent.isCanceled() || listenerContainer.hasIgnoreDone())) {
					listenerContainer.pushString(consoleActionEvent);
				}
				
			}catch (Exception e) {
//				wrap every listener in its own try so the program con continue if there is a crash.
//				catch anything that happens in a Listener and stop it from
//				bubbling up and hurting the rest of the program
				this.printStackTrace(e);
				e.printStackTrace();
				println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
				System.out.println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
			}
		}
		
	}
	
	/**
	 * Prints the stack trace of a throwable to the {@link BaseConsole} output.
	 *
	 * @param throwable Prints the stackTrace.
	 * @see Throwable#printStackTrace
	 */
	public final void printStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		this.println(sw.toString(), Colour.RED);
	}
	
	/**
	 * Gets attribute from id.
	 *
	 * @param id the id
	 * @return the attribute from id
	 */
	public final ListenerAttribute getAttributeFromId(String id) {
		ListenerContainer listenerContainer = getContainerFromId(id);
		return listenerContainer == null ? null : listenerContainer.getAttribute();
	}
	
	/**
	 * Gets container from id.
	 *
	 * @param id the id
	 * @return the container from id
	 */
	public final ListenerContainer getContainerFromId(String id) {
		try {
			int index = Integer.parseInt(id);
			return getContainerFromIndex(index);
		}catch (NumberFormatException e) {
			return getContainerFromAttributeName(id);
		}
	}
	
	private ListenerContainer getContainerFromAttributeName(String name) {
		for (ListenerContainer listenerContainer : listenerContainers) {
			if (listenerContainer.getAttribute().getClass().getName().equals(name)) return listenerContainer;
		}
		return null;
	}
	
	private ListenerAttribute getAttributeFromIndex(int index) {
		return getContainerFromIndex(index).getAttribute();
	}
	
	private ListenerContainer getContainerFromIndex(int index) {
		return listenerContainers.get(index);
	}
	
	/**
	 * Gets container from attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 * @return the container from attribute
	 */
	public final ListenerContainer getContainerFromAttribute(ListenerAttribute listenerAttribute) {
		for (ListenerContainer listenerContainer : listenerContainers) {
			if (listenerContainer.getAttribute().equals(listenerAttribute)) return listenerContainer;
		}
		return null;
	}
	
	/**
	 * Add listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 */
	public final void addListenerAttribute(ListenerAttribute listenerAttribute) {
		addListenerAttributeRaw(listenerAttribute);
		enableListenerAttribute(listenerAttribute);
	}
	
	/**
	 * Add listener container.
	 *
	 * @param listenerContainer the listener container
	 */
	public final void addListenerContainer(ListenerContainer listenerContainer) {
		addListenerContainerRaw(listenerContainer);
		enableListenerContainer(listenerContainer);
	}
	
	/**
	 * Add listener attribute raw.
	 *
	 * @param listenerAttribute the listener attribute
	 */
	public final void addListenerAttributeRaw(ListenerAttribute listenerAttribute) {
		ListenerContainer listenerContainer = ListenerContainer.makeListenerEntry(listenerAttribute);
		addListenerContainerRaw(listenerContainer);
	}
	
	/**
	 * Add listener container raw.
	 *
	 * @param listenerContainer the listener container
	 */
	public final void addListenerContainerRaw(ListenerContainer listenerContainer) {
		if (!listenerContainers.contains(listenerContainer) || !listenerContainer.getLinkedBaseConsoles().contains(this)) {
			listenerContainers.add(listenerContainer);
			listenerContainer.addToConsole(this);
			listenerContainer.pushAdded(new AdditionActionEvent(this));
		}
	}
	
	/**
	 * Enable listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 */
	public final void enableListenerAttribute(ListenerAttribute listenerAttribute) {
		enableListenerContainer(getContainerFromAttribute(listenerAttribute));
	}
	
	/**
	 * Enable listener container.
	 *
	 * @param listenerContainer the listener container
	 */
	public final void enableListenerContainer(ListenerContainer listenerContainer) {
		if (!listenerContainer.isEnabled() || !listenerContainer.hasBeenEnabled()) {
			listenerContainer.setEnabled(true);
			listenerContainer.pushEnabled(new EnableActionEvent(this));
		}
	}
	
	/**
	 * Disable listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 */
	public final void disableListenerAttribute(ListenerAttribute listenerAttribute) {
		disableListenerAttribute(listenerAttribute, DisableActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Disable listener container.
	 *
	 * @param listenerContainer the listener container
	 */
	public final void disableListenerContainer(ListenerContainer listenerContainer) {
		disableListenerContainer(listenerContainer, DisableActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Remove listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 */
	public final void removeListenerAttribute(ListenerAttribute listenerAttribute) {
		removeListenerAttribute(listenerAttribute, RemovalActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Remove listener container.
	 *
	 * @param listenerContainer the listener container
	 */
	public final void removeListenerContainer(ListenerContainer listenerContainer) {
		removeListenerContainer(listenerContainer, RemovalActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Disable listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 * @param type the type
	 */
	public final void disableListenerAttribute(ListenerAttribute listenerAttribute, int type) {
		disableListenerContainer(getContainerFromAttribute(listenerAttribute), type);
	}
	
	/**
	 * Disable listener container.
	 *
	 * @param listenerContainer the listener container
	 * @param type the type
	 */
	public final void disableListenerContainer(ListenerContainer listenerContainer, int type) {
		if (listenerContainer.isEnabled()) {
			listenerContainer.setEnabled(false);
			listenerContainer.pushDisabled(new DisableActionEvent(this, type));
		}
	}
	
	/**
	 * Remove listener attribute.
	 *
	 * @param listenerAttribute the listener attribute
	 * @param type the type
	 */
	public final void removeListenerAttribute(ListenerAttribute listenerAttribute, int type) {
		removeListenerContainer(getContainerFromAttribute(listenerAttribute), type);
	}
	
	/**
	 * Remove listener container.
	 *
	 * @param listenerContainer the listener container
	 * @param type the type
	 */
	public final void removeListenerContainer(ListenerContainer listenerContainer, int type) {
		disableListenerContainer(listenerContainer, type);
		removeListenerContainerRaw(listenerContainer, type);
	}
	
	private final void removeListenerAttributeRaw(ListenerAttribute listenerAttribute, int type) {
		removeListenerContainerRaw(getContainerFromAttribute(listenerAttribute), type);
	}
	
	private final void removeListenerContainerRaw(ListenerContainer listenerContainer, int type) {
		if (listenerContainers.contains(listenerContainer) || listenerContainer.getLinkedBaseConsoles().contains(this)) {
			listenerContainers.remove(listenerContainer);
			listenerContainer.removeFromConsole(this);
			listenerContainer.pushRemoved(new RemovalActionEvent(this, type));
		}
	}
	
	/**
	 * Loads plugins to this console from the file path.
	 *
	 * @param filePath the file path of the folder to load this plugins from.
	 */
	public final void loadPlugins(String filePath) {
		
		PluginManager.loadPluginsToConsole(this, filePath);
		
	}
	
	/**
	 * Loads plugins to this console from "./plugins".<br>
	 *
	 * @see BaseConsole#loadPlugins(String)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, File)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, String)
	 */
	public final void loadPlugins() {
		
		PluginManager.loadPluginsToConsole(this, PluginManager.DEFAULT_PLUGIN_FOLDER);
		
	}
	
	/**
	 * Gets history.
	 *
	 * @return the history
	 */
	public final ArrayList<String> getHistory() {
		return history;
	}
	
	/**
	 * Gets the listener entries.
	 *
	 * @return A clone of the {@link ListenerContainer} array
	 */
	public final ArrayList<ListenerContainer> getListenerContainers() {
		return (ArrayList<ListenerContainer>) listenerContainers.clone();
	}
	
	/**
	 * Gets gui.
	 *
	 * @return A clone of the {@link ConsoleUI} array
	 */
	public final ArrayList<UIContainer> getUIContainers() {
		return (ArrayList<UIContainer>) uiContainers.clone();
	}
	
	/**
	 * Returns if the console has a gui attached to it.<br>
	 *
	 * @return If the
	 * has at least one gui attached to it
	 */
	public final boolean hasGuiAttached() {
		return uiContainers.size() > 0;
	}
	
	/**
	 * Adds a {@link ConsoleUI} to the BaseConsole.
	 *
	 * @param consoleUI the gui to add
	 */
	public final void addConsoleUi(ConsoleUI consoleUI) {
		uiContainers.add(new UIContainer(consoleUI));
		consoleUI.init();
	}
	
	/**
	 * Removes a {@link UIContainer} from the BaseConsole.
	 *
	 * @param UIContainer the gui to remove
	 */
	public final void removeUiContainer(UIContainer UIContainer) {
		uiContainers.remove(UIContainer);
		UIContainer.getGui().dispose();
	}
	
	/**
	 * Removes a {@link ConsoleUI} from the BaseConsole.
	 *
	 * @param consoleUI the gui to remove
	 */
	public final void removeConsoleUi(ConsoleUI consoleUI) {
//		this prevents a concurrency issue.
		int ir = 0;
		for (int i = 0; i < uiContainers.size(); i++) {
			if (uiContainers.get(i).getGui().equals(consoleUI)) {
				ir = i;
				break;
			}
		}
		uiContainers.remove(ir);
		consoleUI.dispose();
	}
	
	/**
	 * Gets the global id of this {@link BaseConsole}.
	 *
	 * @return the global ID for this
	 */
	public final int getId() {
		return id;
	}
	
	public final boolean isDisposed() {
		if (disposed) System.out.println("The BaseConsole has been disposed");
		return disposed;
	}
	
}
