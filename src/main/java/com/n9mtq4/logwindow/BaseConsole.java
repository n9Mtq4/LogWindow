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
import com.n9mtq4.logwindow.gui.ConsoleGui;
import com.n9mtq4.logwindow.gui.GuiEntry;
import com.n9mtq4.logwindow.gui.attributes.History;
import com.n9mtq4.logwindow.listener.DisableListener;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.listener.RemovalListener;
import com.n9mtq4.logwindow.managers.PluginManager;
import com.n9mtq4.logwindow.managers.StdoutRedirect;
import com.n9mtq4.logwindow.modules.ModuleJarLoader;
import com.n9mtq4.logwindow.modules.ModuleListener;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */

/**
 * The class that handles all User to Listener interactions.
 */
@SuppressWarnings("unused")
public final class BaseConsole implements Serializable {
	
	private static final long serialVersionUID = 992050290203752760L;
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 */
	public static final ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 */
	private final ArrayList<ListenerContainer> listenerContainers;
	/**
	 * Keeps a record of the input.
	 */
	private final ArrayList<String> history;
	/**
	 * Has the local id for the {@link BaseConsole}.
	 */
	private final int id;
	/**
	 * The object that handles setOut for redirection.
	 *
	 * @see StdoutRedirect
	 * @see BaseConsole#redirectStdoutOn
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 * @see BaseConsole#redirectStdoutOff
	 */
	private StdoutRedirect stdoutRedirect;
	/**
	 * Contains all {@link ConsoleGui}s attached.
	 *
	 * @see BaseConsole#addGui
	 * @see BaseConsole#removeGuiEntry
	 */
	private final ArrayList<GuiEntry> guis;
	/**
	 * The thread that is called when the program exits
	 */
	private final ShutdownHook shutdownHook;
	
	/**
	 * Constructor for {@link BaseConsole}.
	 */
	public BaseConsole() {
		this.listenerContainers = new ArrayList<ListenerContainer>();
		initMandatoryListeners();
		this.history = new ArrayList<String>();
		globalList.add(this);
		this.id = globalList.indexOf(this);
		this.guis = new ArrayList<GuiEntry>();
		this.shutdownHook = new ShutdownHook(this);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 *
	 * @param pluginDirectory loads all plugins in this file path.
	 */
	public BaseConsole(String pluginDirectory) {
		this();
		this.loadPlugins(pluginDirectory);
	}
	
	/**
	 * Instantiates a new Base console.
	 *
	 * @param consoleGui the console gui
	 */
	public BaseConsole(ConsoleGui consoleGui) {
		this();
		addGui(consoleGui);
	}
	
	/**
	 * Handles closing down the {@link ListenerAttribute}s and {@link ConsoleGui}s.<br>
	 * Note: if overriding make sure to call super to close {@link ListenerAttribute} and {@link ConsoleGui}.<br>
	 *
	 * @see ConsoleGui#dispose
	 * @see DisableListener#onDisable
	 * @see RemovalListener#onRemoval
	 */
	public final void dispose() {
		
		System.out.println("Disposing of BaseConsole with id of " + globalList.indexOf(this));
		ArrayList<ListenerContainer> listenerEntries1;
		while ((listenerEntries1 = this.listenerContainers).size() > 0) {
			try {
				for (ListenerContainer l : listenerEntries1) {
					
					this.removeListenerContainer(l, RemovalActionEvent.CONSOLE_DISPOSE);
					
				}
			}catch (ConcurrentModificationException e) {
				
			}
		}
		ArrayList<GuiEntry> guis;
		while ((guis = this.getGuiEntries()).size() > 0) {
			try {
				for (GuiEntry g : guis) {
					
					this.removeGuiEntry(g);
					
				}
			}catch (ConcurrentModificationException e) {
			}
		}
//		removes shutdown hook if this method wasn't called from the shutdown hook
		try {
			String className = Thread.currentThread().getStackTrace()[2].getClassName(); // gets the class name that called it
			if (!className.equals(ShutdownHook.class.getName())) {
				Runtime.getRuntime().removeShutdownHook(shutdownHook);
			}
		}catch (Exception e) {
		}
		
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
	 * Prints text in colour to the ConsoleGuis.
	 *
	 * @param object The object to print.
	 * @param colour The colour to print the text in.
	 */
	public final void print(Object object, Colour colour) {
		for (GuiEntry gui : guis) {
			gui.print(String.valueOf(object), colour);
		}
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 *
	 * @param object The object to print.
	 */
	public final void print(Object object) {
		print(String.valueOf(object), null);
	}
	
	/**
	 * Prints object in colour to the ConsoleGuis.
	 *
	 * @param object The object to print.
	 * @param colour The colour to print the object in.
	 */
	public final void println(Object object, Colour colour) {
		print(String.valueOf(object) + "\n", colour);
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 *
	 * @param object The object to print.
	 */
	public final void println(Object object) {
		print(String.valueOf(object) + "\n");
	}
	
	/**
	 * Turns off {@link System#out} redirection
	 * from
	 * {@link BaseConsole}.
	 * {@link BaseConsole#print}.
	 *
	 * @see BaseConsole#redirectStdoutOn()
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 */
	public final void redirectStdoutOff() {
		
		if (stdoutRedirect != null) stdoutRedirect.turnOff();
		
	}
	
	/**
	 * Turns on {@link System#out} redirection
	 * to
	 * {@link BaseConsole}.{@link BaseConsole#print}
	 *
	 * @see BaseConsole#redirectStdoutOff()
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 */
	public final void redirectStdoutOn() {
		
		redirectStdoutOn(false);
		
	}
	
	/**
	 * Turns on {@link System#out} redirection
	 * to
	 * {@link BaseConsole}.
	 * {@link BaseConsole#print}.
	 *
	 * @param debug Whether or not to print java file and line number of print statement
	 * @see BaseConsole#redirectStdoutOff()
	 * @see BaseConsole#redirectStdoutOn
	 */
	public final void redirectStdoutOn(boolean debug) {
		
		if (stdoutRedirect == null) {
			stdoutRedirect = new StdoutRedirect(this);
		}
		stdoutRedirect.turnOn(debug);
		
	}
	
	/**
	 * Send plugins object.
	 *
	 * @param object the object
	 */
	public final void sendPluginsObject(Object object) {
		sendPluginsObject(object, "");
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
	 * Push object.
	 *
	 * @param object the object
	 */
	public final void pushObject(Object object) {
		pushObject(object, "");
	}
	
	/**
	 * Push object.
	 *
	 * @param object the object
	 * @param message the message
	 */
	public final void pushObject(Object object, String message) {
		
		try {
			SentObjectEvent event = new SentObjectEvent(this, object, message);
			for (ListenerContainer listenerContainer : listenerContainers) {
				try {
					
					if (listenerContainer.isEnabled() && (!event.isCanceled() || listenerContainer.hasIgnoreDone())) {
						listenerContainer.pushObject(event);
					}
					
				}catch (Exception e) {
//					wrap every listener in its own try so the program con continue if there is a crash.
//					catch anything that happens in a Listener and stop it from
//					bubbling up and hurting the rest of the program
					this.printStackTrace(e);
					e.printStackTrace();
					println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
					System.out.println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
				}
			}
		}catch (ConcurrentModificationException e1) {
//			this is expected, so this is just here to stop a fatal crash
		}
		
	}
	
	/**
	 * Note: use me to send input when using a custom {@link ConsoleGui}.<br>
	 * Takes {@link String} and iterates through all {@link ListenerAttribute} on console.
	 *
	 * @param text String to send to
	 */
	public final void sendPluginsString(String text) {
		history.add(text);
		for (GuiEntry g : guis) {
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
		
		try {
			ConsoleCommand command = new ConsoleCommand(text);
			ConsoleActionEvent event = new ConsoleActionEvent(this, command);
			for (ListenerContainer listenerContainer : listenerContainers) {
				try {
					if (listenerContainer.isEnabled() && (!event.isCanceled() || listenerContainer.hasIgnoreDone())) {
						listenerContainer.pushString(event);
					}
				}catch (Exception e) {
					this.printStackTrace(e);
					e.printStackTrace();
					println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
					System.out.println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
				}
			}
		}catch (ConcurrentModificationException e1) {
//			its expected, so this is just here to stop it from crashing
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
		if (!listenerContainer.isEnabled()) {
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
	 * @return the listener entries
	 */
	public final ArrayList<ListenerContainer> getListenerContainers() {
		return (ArrayList<ListenerContainer>) listenerContainers.clone();
	}
	
	/**
	 * Gets stdout redirect.
	 *
	 * @return the stdout redirect
	 */
	public final StdoutRedirect getStdoutRedirect() {
		return stdoutRedirect;
	}
	
	/**
	 * Gets gui.
	 *
	 * @return the gui
	 */
	public final ArrayList<GuiEntry> getGuiEntries() {
		return (ArrayList<GuiEntry>) guis.clone();
	}
	
	/**
	 * Returns if the console has a gui attached to it.<br>
	 *
	 * @return If the
	 * has at least one gui attached to it
	 */
	public final boolean hasGuiAttached() {
		return guis.size() > 0;
	}
	
	/**
	 * Adds a {@link ConsoleGui} to the BaseConsole.
	 *
	 * @param consoleGui the gui to add
	 */
	public final void addGui(ConsoleGui consoleGui) {
		guis.add(new GuiEntry(consoleGui));
		consoleGui.init();
	}
	
	/**
	 * Removes a {@link GuiEntry} from the BaseConsole.
	 *
	 * @param guiEntry the gui to remove
	 */
	public final void removeGuiEntry(GuiEntry guiEntry) {
		guis.remove(guiEntry);
		guiEntry.getGui().dispose();
	}
	
	/**
	 * Removes a {@link ConsoleGui} from the BaseConsole.
	 *
	 * @param consoleGui the gui to remove
	 */
	public final void removeGui(ConsoleGui consoleGui) {
//		this prevents a concurrency issue.
		int ir = 0;
		for (int i = 0; i < guis.size(); i++) {
			if (guis.get(i).getGui().equals(consoleGui)) {
				ir = i;
				break;
			}
		}
		guis.remove(ir);
		consoleGui.dispose();
	}
	
	/**
	 * Gets the global id of this {@link BaseConsole}.
	 *
	 * @return the global ID for this
	 */
	public final int getId() {
		return id;
	}
	
}
