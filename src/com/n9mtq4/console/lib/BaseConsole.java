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
import com.n9mtq4.console.lib.gui.ConsoleGui;
import com.n9mtq4.console.lib.gui.attributes.History;
import com.n9mtq4.console.lib.listeners.ShutdownHook;
import com.n9mtq4.console.lib.managers.PluginManager;
import com.n9mtq4.console.lib.managers.StdoutRedirect;
import com.n9mtq4.console.lib.modules.ModuleJarLoader;
import com.n9mtq4.console.lib.modules.ModuleListener;
import com.n9mtq4.console.lib.utils.Colour;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
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
public class BaseConsole {
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 */
	public static ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 */
	private ArrayList<ConsoleListener> listeners;
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
	 * @see BaseConsole#removeGui
	 */
	private ArrayList<ConsoleGui> gui;
	/**
	 * The thread that is called when the program exits
	 */
	private ShutdownHook shutdownHook;
	
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
	public BaseConsole(ConsoleListener listener) {
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
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		initConsole();
	}
	
	/**
	 * Handles global {@link BaseConsole} initilizing<br>
	 * Is called in the constructor
	 */
	private void initConsole() {
		
		globalList.add(this);
		this.id = globalList.indexOf(this);
		gui = new ArrayList<ConsoleGui>();
		initGui();
		shutdownHook = new ShutdownHook(this);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
		
	}
	
	/**
	 * Note: Override me!<br>
	 * Note: Make a BaseConsole and addGui a gui instead of overriding BaseConsole
	 * Insert your {@link ConsoleGui} here with<br>
	 * this.addGui(new ThingThatExtendsConsoleGui());
	 */
	@Deprecated
	public void initGui() {
		
	}
	
	/**
	 * Handles closing down the {@link ConsoleListener}s and {@link ConsoleGui}s.<br>
	 * Note: if overriding make sure to call super to close {@link ConsoleListener} and {@link ConsoleGui}.<br>
	 *
	 * @see ConsoleGui#dispose
	 * @see ConsoleListener#onDisable
	 * @see ConsoleListener#onRemoval
	 */
	public void dispose() {
		
		System.out.println("Disposing of BaseConsole with id of " + globalList.indexOf(this));
		ArrayList<ConsoleListener> listeners;
		while ((listeners = this.listeners).size() > 0) {
			try {
				for (ConsoleListener l : listeners) {
					
					this.removeListener(l, RemovalActionEvent.WINDOW_CLOSE);
					
				}
			}catch (ConcurrentModificationException e) {
				
			}
		}
		ArrayList<ConsoleGui> guis;
		while ((guis = this.getGui()).size() > 0) {
			try {
				for (ConsoleGui g : guis) {
					
					this.removeGui(g);
					
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
	 * Adds required {@link ConsoleListener}s to the {@link BaseConsole}.<br>
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
			for (ConsoleListener l : listeners) {
				try {
					if (l.isEnabled() && (!event.isCanceled() || l.hasIgnoreDone())) {
						l.pushObject(event);
					}
				}catch (Exception e) {
//					catch anything that happens in a Listener and stop it from
//					bubbling up and hurting the rest of the program
					this.printStackTrace(e);
					e.printStackTrace();
				}
			}
		}catch (ConcurrentModificationException e1) {
//			this is expected, so this is just here to stop it from crashing
		}
	}
	
	/**
	 * Note: use me to send input when using a custom {@link ConsoleGui}.<br>
	 * Takes {@link String} and iterates through all {@link ConsoleListener} on console.
	 *
	 * @param text String to send to
	 *             .
	 */
	public void sendPluginsString(String text) {
		history.add(text);
		for (ConsoleGui g : gui) {
			if (g instanceof History) ((History) g).historyUpdate();
		}
		push(text);
	}
	
	/**
	 * Low level version of {@link BaseConsole#sendPluginsString}.
	 *
	 * @param text Sting to send to
	 *             .
	 */
	public void push(String text) {
		
		try {
			ConsoleCommand command = new ConsoleCommand(text);
			ConsoleActionEvent event = new ConsoleActionEvent(this, command);
			for (ConsoleListener p : listeners) {
				try {
					if (p.isEnabled() && (!event.isCanceled() || p.hasIgnoreDone())) {
						p.push(event);
					}
				}catch (Exception e) {
					this.printStackTrace(e);
					e.printStackTrace();
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
	 * Finds all {@link ConsoleListener}s with a given class name and returns them in the form of an array<br>
	 *
	 * @param name The name of the class to look for.
	 * @return an array of
	 * s with the given class name
	 */
	public ConsoleListener[] getListenersByName(String name) {
		
		ArrayList<ConsoleListener> list = new ArrayList<ConsoleListener>();
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equalsIgnoreCase(name)) {
				
				list.add(l);
				
			}
			
		}
		
		if (list.size() > 0) {
			return (ConsoleListener[]) list.toArray();
		}else {
			return null;
		}
		
	}
	
	/**
	 * Enables all {@link ConsoleListener}s that have been added to @{BaseConsole}
	 */
	public void enableAllListeners() {
		
		for (ConsoleListener l : listeners) {
			
			enableListener(l);
			
		}
		
	}
	
	/**
	 * Enables all {@link ConsoleListener} with a given name attached to {@link BaseConsole}.
	 *
	 * @param name Class name of the
	 *             to enable.
	 * @see BaseConsole#enableListenerByName
	 */
	public void enableListenersByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				enableListener(l);
			}
			
		}
		
	}
	
	/**
	 * Only enables the first {@link ConsoleListener} with a given name attached to {@link BaseConsole}.
	 *
	 * @param name Class name of the
	 *             to enable.
	 * @see BaseConsole#enableListenersByName
	 */
	public void enableListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				enableListener(l);
				break;
			}
			
		}
		
	}
	
	/**
	 * Enables the given {@link ConsoleListener} that is attached to {@link BaseConsole}.
	 *
	 * @param listener the
	 *                 object to enable
	 * @see BaseConsole#disableListener
	 * @see BaseConsole#enableListenerByName
	 */
	public void enableListener(ConsoleListener listener) {
		
		if (!listener.isEnabled()) {
			listener.setEnabled(true);
			listener.onEnable(new EnableActionEvent(this));
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
		
		for (ConsoleListener l : listeners) {
			
			disableListener(l, type);
			
		}
		
	}
	
	/**
	 * Disable listeners by name.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void disableListenersByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				disableListener(l, type);
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
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				disableListener(l, type);
				break;
			}
			
		}
		
	}
	
	/**
	 * Disable listener.
	 *
	 * @param listener the listener
	 */
	public void disableListener(ConsoleListener listener) {
		
		disableListener(listener, DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Disable listener.
	 *
	 * @param listener the listener
	 * @param type     the type
	 */
	public void disableListener(ConsoleListener listener, int type) {
		
		if (listener.isEnabled()) {
			listener.setEnabled(false);
			listener.onDisable(new DisableActionEvent(this, type));
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
	 *
	 * @param name the name
	 * @param type the type
	 */
	public void removeListenerByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l);
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
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l, type);
			}
			
		}
		
	}
	
	/**
	 * Remove all listeners.
	 *
	 * @param type the type
	 */
	public void removeAllListeners(int type) {
		
		int i = listeners.size();
		int c = 0;
		while (listeners.size() > 0 && i > c) {
			try {
				for (ConsoleListener l : listeners) {
					
					removeListener(l, type);
					
				}
			}catch (ConcurrentModificationException e) {
				
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
	 * Add listener by name.
	 *
	 * @param name the name
	 * @throws Exception the exception
	 */
	public void addListenerByName(String name) throws Exception {
		try {
			ConsoleListener l = ConsoleListener.getNewListenerByName(name);
			this.addListener(l);
		}catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Add listener.
	 *
	 * @param listener the listener
	 */
	@SuppressWarnings("deprecation")
	public void addListener(ConsoleListener listener) {
		
		if (!listeners.contains(listener) || !listener.getLinkedBaseConsoles().contains(this)) {
			listeners.add(listener);
			listener.onAddition(new AdditionActionEvent(this));
			listener.onEnable(new EnableActionEvent(this));
			listener.addToConsole(this);
		}
		
	}
	
	/**
	 * Remove listener.
	 *
	 * @param listener the listener
	 */
	public void removeListener(ConsoleListener listener) {
		
		removeListener(listener, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	/**
	 * Remove listener.
	 *
	 * @param listener the listener
	 * @param type     the type
	 */
	@SuppressWarnings("deprecation")
	public void removeListener(ConsoleListener listener, int type) {
		
		if (listeners.contains(listener) || listener.getLinkedBaseConsoles().contains(this)) {
			listeners.remove(listener);
			listener.removeFromConsole(this);
			listener.onDisable(new DisableActionEvent(this, type));
			listener.onRemoval(new RemovalActionEvent(this, type));
		}
		
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
		
		for (ConsoleGui g : gui) {
			g.printImage(file);
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
		
		for (ConsoleGui g : gui) {
			g.lowPrint(text, colour);
		}
		
	}
	
	/**
	 * Gets the the listener with a given name, or index.
	 *
	 * @param identifier A class name, or a number (in the form a
	 *                   ).
	 * @return A with the given name of index.
	 */
	@SuppressWarnings("deprecation")
	public ConsoleListener getListener(String identifier) {
		
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
	 * Gets a {@link ConsoleListener} with the index in the array.
	 *
	 * @param index The index of the desired
	 *              .
	 * @return The with the given class name.
	 * @see BaseConsole#getListener
	 * @deprecated Use {@link BaseConsole#getListener(String)} instead (index in
	 * array or name).
	 */
	@Deprecated
	public ConsoleListener getListenerByIndex(int index) {
		
		return listeners.get(index);
		
	}
	
	/**
	 * Gets a {@link ConsoleListener} with a given name.
	 *
	 * @param name The class name of the desired
	 *             .
	 * @return The with the given class name.
	 * @see BaseConsole#getListener
	 * @deprecated Use {@link BaseConsole#getListener(String)} instead (index in
	 * array or name).
	 */
	@Deprecated
	public ConsoleListener getListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equalsIgnoreCase(name)) {
				
				return l;
				
			}
			
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
	 * Gets listeners.
	 *
	 * @return the listeners
	 */
	public ArrayList<ConsoleListener> getListeners() {
		return (ArrayList<ConsoleListener>) listeners.clone();
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
	public ArrayList<ConsoleGui> getGui() {
		return (ArrayList<ConsoleGui>) gui.clone();
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
		gui.add(consoleGui);
		consoleGui.add(this);
	}
	
	/**
	 * Removes a {@link ConsoleGui} from the BaseConsole.
	 *
	 * @param consoleGui the gui to remove
	 */
	public void removeGui(ConsoleGui consoleGui) {
		gui.remove(consoleGui);
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
