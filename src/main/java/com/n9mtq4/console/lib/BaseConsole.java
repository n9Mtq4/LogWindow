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

package com.n9mtq4.console.lib;

import com.n9mtq4.console.lib.command.ConsoleCommand;
import com.n9mtq4.console.lib.events.*;
import com.n9mtq4.console.lib.gui.ConsoleGui;
import com.n9mtq4.console.lib.gui.GuiEntry;
import com.n9mtq4.console.lib.gui.attributes.History;
import com.n9mtq4.console.lib.dispose.ShutdownHook;
import com.n9mtq4.console.lib.listener.ListenerAttribute;
import com.n9mtq4.console.lib.listener.ListenerEntry;
import com.n9mtq4.console.lib.managers.PluginManager;
import com.n9mtq4.console.lib.managers.StdoutRedirect;
import com.n9mtq4.console.lib.modules.ModuleJarLoader;
import com.n9mtq4.console.lib.modules.ModuleListener;
import com.n9mtq4.console.lib.utils.Colour;
import com.n9mtq4.console.lib.utils.ObjectUtils;
import com.n9mtq4.console.lib.utils.ReflectionHelper;

import java.awt.*;
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
public class BaseConsole implements Serializable {
	
	/**
	 * Adds support for saving the BaseConsole.
	 * The number is randomly generated.
	 * */
	private static final long serialVersionUID = 5490740956289542664L;
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 */
	public static final ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 */
	private ArrayList<ListenerEntry> listenerEntries;
	/**
	 * Keeps a record of the input.
	 */
	private ArrayList<String> history;
	/**
	 * Has the local id for the {@link BaseConsole}.
	 */
	private int id;
	/**
	 * The object that handles setOut for redirection.
	 *
	 * @see com.n9mtq4.console.lib.managers.StdoutRedirect
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
	private ArrayList<GuiEntry> gui;
	/**
	 * The thread that is called when the program exits
	 */
	private ShutdownHook shutdownHook;
	
	/**
	 * Loads a BaseConsole from a previously
	 * saved file from <code>baseConsole.save(file)</code>.
	 *
	 * @param file the file
	 * @return the base console that has been loaded
	 */
	public static BaseConsole load(File file) {
		try {
			BaseConsole bc = ObjectUtils.readSerializable(file);
			ArrayList<GuiEntry> gs = bc.getGuiEntries();
			bc.gui = new ArrayList<GuiEntry>();
			for (GuiEntry g : gs) {
				ConsoleGui g1 = ReflectionHelper.callConstructor(g.getGui().getClass());
				bc.addGui(g1);
			}
			return bc;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 *
	 * @param pluginDirectory loads all plugins in this file path.
	 */
	public BaseConsole(String pluginDirectory) {
		init();
		this.loadPlugins(pluginDirectory);
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 */
	public BaseConsole() {
		init();
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 *
	 * @param listener adds the
	 *                 to the newly created
	 */
	public BaseConsole(ListenerAttribute listener) {
		init();
		addListener(listener);
	}
	
	/**
	 * Constructor for {@link BaseConsole}
	 *
	 * @param consoleGui adds the given
	 *                   to the newly created
	 */
	public BaseConsole(ConsoleGui consoleGui) {
		init();
		addGui(consoleGui);
	}
	
	private void init() {
		listenerEntries = new ArrayList<ListenerEntry>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		initConsole();
	}
	
	/**
	 * Handles global {@link BaseConsole} initializing<br>
	 * Is called in the constructor
	 */
	private void initConsole() {
		
		globalList.add(this);
		this.id = globalList.indexOf(this);
		gui = new ArrayList<GuiEntry>();
		initGui();
		shutdownHook = new ShutdownHook(this);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
		
	}
	
	/**
	 * Note: Override me!<br>
	 * TODO: remove in v5<br>
	 * Note: Make a BaseConsole and addGui a gui instead of overriding BaseConsole
	 * Insert your {@link ConsoleGui} here with<br>
	 * this.addGui(new ThingThatExtendsConsoleGui());
	 * @deprecated
	 */
	@Deprecated
	public void initGui() {
		
	}
	
	public boolean save(File file) {
		try {
			saveDirty(file);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public void saveDirty(File file) throws Exception {
		ObjectUtils.writeSerializable(this, file);
	}
	
	/**
	 * Handles closing down the {@link ListenerAttribute}s and {@link ConsoleGui}s.<br>
	 * Note: if overriding make sure to call super to close {@link ListenerAttribute} and {@link ConsoleGui}.<br>
	 *
	 * @see ConsoleGui#dispose
	 * @see com.n9mtq4.console.lib.listener.DisableListener#onDisable
	 * @see com.n9mtq4.console.lib.listener.RemovalListener#onRemoval
	 */
	public void dispose() {
		
		System.out.println("Disposing of BaseConsole with id of " + globalList.indexOf(this));
		ArrayList<ListenerEntry> listenerEntries1;
		while ((listenerEntries1 = this.listenerEntries).size() > 0) {
			try {
				for (ListenerEntry l : listenerEntries1) {
					
					this.removeListenerEntry(l, RemovalActionEvent.CONSOLE_DISPOSE);
					
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
		
		this.addListener(new ModuleListener());
		this.addListener(new ModuleJarLoader());
		
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
	public void redirectStdoutOff() {
		
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
	public void redirectStdoutOn() {
		
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
	public void redirectStdoutOn(boolean debug) {
		
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
	public void sendPluginsObject(Object object) {
		sendPluginsObject(object, "");
	}
	
	/**
	 * Send plugins object.
	 *
	 * @param object  the object
	 * @param message the message
	 */
	public void sendPluginsObject(Object object, String message) {
		history.add(object.getClass().getName() + " : " + message);
		pushObject(object, message);
	}
	
	/**
	 * Push object.
	 *
	 * @param object the object
	 */
	public void pushObject(Object object) {
		pushObject(object, "");
	}
	
	/**
	 * Push object.
	 *
	 * @param object  the object
	 * @param message the message
	 */
	public void pushObject(Object object, String message) {
		try {
			SentObjectEvent event = new SentObjectEvent(this, object, message);
//			SentObjectEvent</*object.getClass()?*/> event = new SentObjectEvent</*object.getClass()?*/>(this, object, message); // maybe add generics?
			for (ListenerEntry l : listenerEntries) {
				try {
					if (l.isEnabled() && (!event.isCanceled() || l.hasIgnoreDone())) {
						l.pushObject(event);
					}
				}catch (Exception e) {
//					catch anything that happens in a Listener and stop it from
//					bubbling up and hurting the rest of the program
					this.printStackTrace(e);
					e.printStackTrace();
					println("Listener " + l.getListener().getClass().getName() + " has an error!");
					System.out.println("Listener " + l.getListener().getClass().getName() + " has an error!");
				}
			}
		}catch (ConcurrentModificationException e1) {
//			this is expected, so this is just here to stop it from crashing
		}
	}
	
	/**
	 * Note: use me to send input when using a custom {@link ConsoleGui}.<br>
	 * Takes {@link String} and iterates through all {@link ListenerAttribute} on console.
	 *
	 * @param text String to send to
	 */
	public void sendPluginsString(String text) {
		history.add(text);
		for (GuiEntry g : gui) {
			if (g.getGui() instanceof History) ((History) g.getGui()).historyUpdate();
		}
		push(text);
	}
	
	/**
	 * Low level version of {@link BaseConsole#sendPluginsString}.
	 *
	 * @param text String to send to the listeners
	 */
	public void push(String text) {
		
		try {
			ConsoleCommand command = new ConsoleCommand(text);
			ConsoleActionEvent event = new ConsoleActionEvent(this, command);
			for (ListenerEntry listenerEntry : listenerEntries) {
				try {
					if (listenerEntry.isEnabled() && (!event.isCanceled() || listenerEntry.hasIgnoreDone())) {
						listenerEntry.pushString(event);
					}
				}catch (Exception e) {
					this.printStackTrace(e);
					e.printStackTrace();
					println("Listener " + listenerEntry.getListener().getClass().getName() + " has an error!");
					System.out.println("Listener " + listenerEntry.getListener().getClass().getName() + " has an error!");
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
	public void printStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		this.println(sw.toString(), Colour.RED);
	}
	
	/**
	 * Finds all {@link ListenerAttribute}s with a given class name and returns them in the form of an array.<br>
	 *
	 * @param name The name of the class to look for.
	 * @return an array of
	 * s with the given class name
	 */
	public ListenerAttribute[] getListenersByName(String name) {
		
		ListenerEntry[] listenerEntries1 = getListenerEntriesByName(name);
		ListenerAttribute[] listeners = new ListenerAttribute[listenerEntries1.length];
		
		for (int i = 0; i < listenerEntries1.length; i++) {
			listeners[i] = listenerEntries1[i].getListener();
		}
		
		return listeners;
		
	}
	
	
	/**
	 * Finds all {@link ListenerEntry}s with a listener of the given class name and returns them in the form of an array.
	 * 
	 * @param name The name of the listener class to look for.
	 * @return an array of ListenerEntries with a listener of the given class name.
	 * */
	public ListenerEntry[] getListenerEntriesByName(String name) {
		
		ArrayList<ListenerEntry> list = new ArrayList<ListenerEntry>();
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equalsIgnoreCase(name)) {
				
				list.add(l);
				
			}
			
		}
		
		return list.toArray(new ListenerEntry[list.size()]);
		
	}
	
	/**
	 * Enables all {@link ListenerEntry}s that have been added to @{BaseConsole}
	 */
	public void enableAllListeners() {
		
		for (ListenerEntry l : listenerEntries) {
			
			enableListenerEntry(l);
			
		}
		
	}
	
	/**
	 * Enables all {@link ListenerEntry} with a given name attached to {@link BaseConsole}.
	 *
	 * @param name Class name of the
	 *             to enable.
	 * @see BaseConsole#enableListenerByName
	 */
	public void enableListenersByName(String name) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				enableListenerEntry(l);
			}
			
		}
		
	}
	
	/**
	 * Only enables the first {@link ListenerAttribute} with a given name attached to {@link BaseConsole}.
	 *
	 * @param name Class name of the
	 *             to enable.
	 * @see BaseConsole#enableListenersByName
	 */
	public void enableListenerByName(String name) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				enableListenerEntry(l);
				break;
			}
			
		}
		
	}
	
	/**
	 * Enables the given {@link ListenerAttribute} that is attached to {@link BaseConsole}.
	 *
	 * @param listener the object to enable
	 * @see BaseConsole#disableListener
	 * @see BaseConsole#enableListenerByName
	 */
	public void enableListener(ListenerAttribute listener) {
		
		enableListenerEntry(getListenerEntryFromListener(listener));
		
	}
	
	
	/**
	 * Enables the given listener entry.
	 *
	 * @param listenerEntry the listener entry
	 */
	public void enableListenerEntry(ListenerEntry listenerEntry) {
		
		if (!listenerEntry.isEnabled()) {
			listenerEntry.setEnabled(true);
			listenerEntry.pushEnabled(new EnableActionEvent(this));
		}
		
	}
	
	/**
	 * Disable all listeners.
	 */
	public void disableAllListeners() {
		
		disableAllListeners(DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Disable all listeners.
	 *
	 * @param type the type
	 */
	public void disableAllListeners(int type) {
		
		for (ListenerEntry l : listenerEntries) {
			
			disableListenerEntry(l, type);
			
		}
		
	}
	
	/**
	 * Disable listeners by name.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void disableListenersByName(String name, int type) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				disableListenerEntry(l, type);
			}
			
		}
		
	}
	
	/**
	 * Disable listener by name.
	 *
	 * @param name the name
	 */
	public void disableListenerByName(String name) {
		
		disableListenerByName(name, DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Disable listener by name.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void disableListenerByName(String name, int type) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				disableListenerEntry(l, type);
				break;
			}
			
		}
		
	}
	
	/**
	 * Disable listener.
	 *
	 * @param listener the listener
	 */
	public void disableListener(ListenerAttribute listener) {
		
		disableListener(listener, DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Disable listener.
	 *
	 * @param listener the listener
	 * @param type     the type
	 */
	public void disableListener(ListenerAttribute listener, int type) {
		
		disableListenerEntry(getListenerEntryFromListener(listener), type);
		
	}
	
	/**
	 * Disables the given listener entry.
	 *
	 * @param listenerEntry the listener entry to disable
	 * @param type the type disable type
	 */
	public void disableListenerEntry(ListenerEntry listenerEntry, int type) {
		
		if (listenerEntry.isEnabled()) {
			listenerEntry.setEnabled(false);
			listenerEntry.pushDisabled(new DisableActionEvent(this, type));
		}
		
	}
	
	/**
	 * Remove listener by name.
	 *
	 * @param name the name
	 */
	public void removeListenerByName(String name) {
		
		removeListenerByName(name, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Remove listener by name.
	 * Only removes the first listener with the given name.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void removeListenerByName(String name, int type) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				removeListenerEntry(l);
				break;
			}
			
		}
		
	}
	
	/**
	 * Remove listeners by name.
	 *
	 * @param name the name
	 */
	public void removeListenersByName(String name) {
		
		removeListenersByName(name, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Remove listeners by name.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void removeListenersByName(String name, int type) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equals(name)) {
				removeListenerEntry(l, type);
			}
			
		}
		
	}
	
	/**
	 * Removes a listener attribute and its listener entry
	 *
	 * @param listenerAttribute the listener attribute to remove
	 * */
	public void removeListener(ListenerAttribute listenerAttribute) {
		removeListener(listenerAttribute, RemovalActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Removes a listener attribute and its listener entry
	 * 
	 * @param listenerAttribute the listener attribute to remove
	 * @param type the type
	 * */
	public void removeListener(ListenerAttribute listenerAttribute, int type) {
		removeListenerEntry(getListenerEntryFromListener(listenerAttribute), type);
	}
	
	/**
	 * Remove all listeners.
	 *
	 * @param type the type
	 */
	public void removeAllListeners(int type) {
		
		int i = listenerEntries.size();
		int c = 0;
		while (listenerEntries.size() > 0 && i > c) {
			try {
				for (ListenerEntry l : listenerEntries) {
					
					removeListenerEntry(l, type);
					
				}
			}catch (ConcurrentModificationException e) {
//				this is expected
			}
			c++;
		}
		
	}
	
	/**
	 * Remove all listeners.
	 */
	public void removeAllListeners() {
		
		removeAllListeners(RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Add listener.
	 *
	 * @param listener the listener
	 */
	@SuppressWarnings("deprecation")
	public void addListener(ListenerAttribute listener) {
		
		if (!containsListener(listener)) {
			ListenerEntry le = ListenerEntry.makeListenerEntry(listener);
			addListenerEntry(le);
		}
		
	}
	
	/**
	 * Adds a Listener Entry.
	 * 
	 * @param listenerEntry The Listener entry.
	 * */
	public void addListenerEntry(ListenerEntry listenerEntry) {
		
		if (!listenerEntries.contains(listenerEntry) || !listenerEntry.getLinkedBaseConsoles().contains(this)) {
			listenerEntry.pushAdded(new AdditionActionEvent(this));
			listenerEntries.add(listenerEntry);
			listenerEntry.pushEnabled(new EnableActionEvent(this));
			listenerEntry.addToConsole(this);
		}
		
	}
	
	/**
	 * Adds a listener without calling onEnable
	 * */
	@Deprecated
	public void addDisabledListener(ListenerAttribute listener) {
		
		if (!containsListener(listener)) {
			ListenerEntry le = ListenerEntry.makeListenerEntry(listener);
			addDisabledListenerEntry(le);
		}
		
	}
	
	/**
	 * Adds a listener without calling onEnable
	 * */
	@Deprecated
	public void addDisabledListenerEntry(ListenerEntry listenerEntry) {
		
		if (!listenerEntries.contains(listenerEntry) || !listenerEntry.getLinkedBaseConsoles().contains(this)) {
			listenerEntries.add(listenerEntry);
			listenerEntry.pushAdded(new AdditionActionEvent(this));
			listenerEntry.addToConsole(this);
		}
		
	}
	
	/**
	 * Removes the listener entry from the BaseConsole.
	 *
	 * @param listenerEntry the listener entry
	 */
	public void removeListenerEntry(ListenerEntry listenerEntry) {
		
		removeListenerEntry(listenerEntry, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Removes a listener entry.
	 *
	 * @param listenerEntry the listener entry
	 * @param type     the type
	 */
	@SuppressWarnings("deprecation")
	public void removeListenerEntry(ListenerEntry listenerEntry, int type) {
		
		if (listenerEntries.contains(listenerEntry) || listenerEntry.getLinkedBaseConsoles().contains(this)) {
			listenerEntries.remove(listenerEntry);
			listenerEntry.pushDisabled(new DisableActionEvent(this, type));
			listenerEntry.removeFromConsole(this);
			listenerEntry.pushRemoved(new RemovalActionEvent(this, type));
		}
		
	}
	
	private boolean containsListener(ListenerAttribute listenerAttribute) {
		for (ListenerEntry l : listenerEntries) {
			if (l.getListener().equals(listenerAttribute)) return true;
		}
		return false;
	}
	
	/**
	 * Loads plugins to this console from the file path.
	 *
	 * @param filePath the file path of the folder to load this plugins from.
	 */
	public void loadPlugins(String filePath) {
		
		PluginManager.loadPluginsToConsole(this, filePath);
		
	}
	
	/**
	 * Loads plugins to this console from "./plugins".<br>
	 *
	 * @see BaseConsole#loadPlugins(String)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, File)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, String)
	 */
	public void loadPlugins() {
		
		PluginManager.loadPluginsToConsole(this, PluginManager.DEFAULT_PLUGIN_FOLDER);
		
	}
	
	/**
	 * Prints an image to the gui.<br>
	 *
	 * @param file The file to print
	 * @see BaseConsole#printImage
	 */
	public void printlnImage(File file) {
		
		printImage(file);
		print("\n");
		
	}
	
	/**
	 * Prints an image to the gui.<br>
	 *
	 * @param filePath The file path of the image to print
	 * @see BaseConsole#printImage
	 */
	public void printlnImage(String filePath) {
		
		printImage(new File(filePath));
		print("\n");
		
	}
	
	/**
	 * Println void.
	 *
	 * @param x the x
	 */
	public void println(Object x) {
		println(x.toString());
	}
	
	/**
	 * Println void.
	 *
	 * @param x      the x
	 * @param colour the colour
	 */
	public void println(Object x, Colour colour) {
		println(x.toString(), colour);
	}
	
	/**
	 * Println void.
	 *
	 * @param text the text
	 */
	public void println(String text) {
		print(text + "\n");
	}
	
	/**
	 * Println void.
	 *
	 * @param text   the text
	 * @param colour the colour
	 */
	public void println(String text, Colour colour) {
		print(text + "\n", colour);
	}
	
	/**
	 * Prints the given string in the given color.
	 *
	 * @param text  String to print.
	 * @param color Colour to print string in.
	 * @see BaseConsole#println(String, Colour)
	 * @deprecated Use {@link BaseConsole#println(String, Colour)}
	 */
	@Deprecated
	public void println(String text, Color color) {
		print(text + "\n", color);
	}
	
	/**
	 * Prints an image to the gui.<br>
	 * No guarantee that it will actually print.
	 *
	 * @param filePath the File of the image to print
	 */
	public void printImage(String filePath) {
		
		printImage(new File(filePath));
		
	}
	
	/**
	 * Note: Lowest level<br>
	 * Prints an image to the gui<br>
	 * No guarantee that it will actually print.
	 *
	 * @param file the File of the image to print
	 */
	public void printImage(File file) {
		
		for (GuiEntry g : gui) {
			g.getGui().printImage(file);
		}
		
	}
	
	/**
	 * Prints an Object to the gui.
	 *
	 * @param x The object to print (Uses x.ToString)
	 */
	public void print(Object x) {
		print(x.toString());
	}
	
	/**
	 * Prints an Object to the gui.
	 *
	 * @param x      The object to print (Uses x.toString)
	 * @param colour the colour
	 */
	public void print(Object x, Colour colour) {
		print(x.toString(), colour);
	}
	
	/**
	 * Prints a string to the gui.
	 *
	 * @param text The string to print
	 */
	public void print(String text) {
		print(text, null);
	}
	
	/**
	 * Print void.
	 *
	 * @param text  the text
	 * @param color the color
	 * @deprecated Use {@link BaseConsole#print(String, Colour)}
	 */
	@Deprecated
	public void print(String text, Color color) {
		print(text, Colour.getColour(color));
	}
	
	/**
	 * Note: Lowest level<br>
	 * Prints a string with a colour to the gui.
	 *
	 * @param text   The string to print
	 * @param colour The colour to print the string in
	 */
	@SuppressWarnings("deprecation")
	public void print(String text, Colour colour) {
		
		for (GuiEntry g : gui) {
			g.getGui().print(text, colour);
		}
		
	}
	
	/**
	 * Gets the the listener with a given name, or index.
	 *
	 * @param identifier A class name, or a number (in the form an int or string).
	 * @return A with the given name of index.
	 */
	@SuppressWarnings("deprecation")
	public ListenerAttribute getListener(String identifier) {
		
		try {
			int i = Integer.parseInt(identifier);
			try {
				return getListenerByIndex(i);
			}catch (Exception e1) {
				return null;
			}
		}catch (NumberFormatException e) {
			return getListenerByName(identifier);
		}
		
	}
	
	/**
	 * Gets a {@link ListenerAttribute} with the index in the array.
	 *
	 * @param index The index of the desired listener attribute.
	 * @return The listener attribute with the given class name.
	 * @see BaseConsole#getListener
	 * @deprecated Use {@link BaseConsole#getListener(String)} instead (index in
	 * array or name).
	 */
	@Deprecated
	public ListenerAttribute getListenerByIndex(int index) {
		
		return listenerEntries.get(index).getListener();
		
	}
	
	/**
	 * Gets a {@link ListenerAttribute} with a given name.
	 *
	 * @param name The class name of the desired
	 *             .
	 * @return The with the given class name.
	 * @see BaseConsole#getListener
	 * @deprecated Use {@link BaseConsole#getListener(String)} instead (index in
	 * array or name).
	 */
	@Deprecated
	public ListenerAttribute getListenerByName(String name) {
		
		for (ListenerEntry l : listenerEntries) {
			
			if (l.getListener().getClass().getName().equalsIgnoreCase(name)) {
				
				return l.getListener();
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Gets a listener entry from its id (name or index)
	 * 
	 * @param id The id either the name or index in the list
	 * @return the wanted listener entry or null
	 * */
	public ListenerEntry getListenerEntry(String id) {
		return getListenerEntryFromListener(getListener(id));
	}
	
	/**
	 * Gets a listener entry from a listener
	 * 
	 * @param listenerAttribute the Listener attribute
	 * @return the Listener Entry that contains the listener attribute
	 * */
	public ListenerEntry getListenerEntryFromListener(ListenerAttribute listenerAttribute) {
		
		for (ListenerEntry listenerEntry : listenerEntries) {
			if (listenerEntry.getListener().equals(listenerAttribute)) return listenerEntry;
		}
		return null;
		
	}
	
	/**
	 * Gets history.
	 *
	 * @return the history
	 */
	public ArrayList<String> getHistory() {
		return (ArrayList<String>) history.clone();
	}
	
	/**
	 * Sets history.
	 *
	 * @param history the history
	 */
	public void setHistory(ArrayList<String> history) {
		this.history = history;
	}
	
	/**
	 * Gets the listener entries.
	 *
	 * @return the listener entries
	 */
	public ArrayList<ListenerEntry> getListenerEntries() {
		return (ArrayList<ListenerEntry>) listenerEntries.clone();
	}
	
	/**
	 * Gets stdout redirect.
	 *
	 * @return the stdout redirect
	 */
	public StdoutRedirect getStdoutRedirect() {
		return stdoutRedirect;
	}
	
	/**
	 * Gets gui.
	 *
	 * @return the gui
	 */
	public ArrayList<GuiEntry> getGuiEntries() {
		return (ArrayList<GuiEntry>) gui.clone();
	}
	
	/**
	 * Returns if the console has a gui attached to it.<br>
	 *
	 * @return If the
	 * has at least one gui attached to it
	 * @deprecated Since the new gui system in version 4 this is unnecessary
	 */
	@Deprecated
	public boolean hasGuiAttached() {
		return gui.size() > 0;
	}
	
	/**
	 * Adds a {@link ConsoleGui} to the BaseConsole.
	 *
	 * @param consoleGui the gui to add
	 */
	public void addGui(ConsoleGui consoleGui) {
		gui.add(new GuiEntry(consoleGui));
		consoleGui.init();
	}
	
	/**
	 * Removes a {@link GuiEntry} from the BaseConsole.
	 *
	 * @param guiEntry the gui to remove
	 */
	protected void removeGuiEntry(GuiEntry guiEntry) {
		gui.remove(guiEntry);
		guiEntry.getGui().dispose();
	}
	
	/**
	 * Removes a {@link ConsoleGui} from the BaseConsole.
	 *
	 * @param consoleGui the gui to remove
	 */
	public void removeGui(ConsoleGui consoleGui) {
//		this prevents a concurrency issue.
		int ir = 0;
		for (int i = 0; i < gui.size(); i++) {
			if (gui.get(i).getGui().equals(consoleGui)) {
				ir = i;
				break;
			}
		}
		gui.remove(ir);
		consoleGui.dispose();
	}
	
	/**
	 * Gets the global id of this {@link BaseConsole}.
	 *
	 * @return the global ID for this
	 */
	public int getId() {
		return id;
	}
	
}
