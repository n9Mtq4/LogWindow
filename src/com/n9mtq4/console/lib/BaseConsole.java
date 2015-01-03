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
import com.n9mtq4.console.lib.listeners.ShutdownHook;
import com.n9mtq4.console.lib.managers.PluginManager;
import com.n9mtq4.console.lib.managers.StdoutRedirect;
import com.n9mtq4.console.lib.modules.*;
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
 * */
@SuppressWarnings("unused")
public class BaseConsole {
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 * */
	public static ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 * */
	private ArrayList<ConsoleListener> listeners;
	/**
	 * Keeps a record of the input.
	 * */
	private ArrayList<String> history;
	/**
	 * Has the local id for the {@link BaseConsole}.
	 * */
	private int id;
	/**
	 * The object that handles setOut for redirection.
	 * @see com.n9mtq4.console.lib.managers.StdoutRedirect
	 * @see BaseConsole#redirectStdoutOn
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 * @see BaseConsole#redirectStdoutOff
	 * */
	private StdoutRedirect stdoutRedirect;
	/**
	 * Contains all {@link ConsoleGui}s attached.
	 * @see BaseConsole#addGui
	 * @see BaseConsole#removeGui
	 * */
	private ArrayList<ConsoleGui> gui;
	
	/**
	 * The default text colour for print when no colour is specified.
	 * @see BaseConsole#setDefaultTextColour
	 * @see BaseConsole#getDefaultTextColour
	 * */
	private Colour defaultTextColour;
	
	/**
	 * Constructor for {@link BaseConsole}.
	 * @param pluginDirectory loads all plugins in this file path.
	 * */
	public BaseConsole(String pluginDirectory) {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		this.loadPlugins(pluginDirectory);
		initConsole();
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 * */
	public BaseConsole() {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		initConsole();
	}
	
	/**
	 * Constructor for {@link BaseConsole}.
	 * @param listener adds the {@link ConsoleListener} to the newly created {@link BaseConsole}
	 * */
	public BaseConsole(ConsoleListener listener) {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		addListener(listener);
		history = new ArrayList<String>();
		initConsole();
	}
	
	/**
	 * Handles global {@link BaseConsole} initilizing<br/>
	 * Is called in the constructor
	 * */
	private void initConsole() {
		
		globalList.add(this);
		this.id = globalList.indexOf(this);
		this.defaultTextColour = Colour.BLACK;
		gui = new ArrayList<ConsoleGui>();
		initGui();
		Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
		
	}
	
	/**
	 * Note: Override me!<br/>
	 * Insert your {@link ConsoleGui} here with<br/>
	 * this.addGui(new ThingThatExtendsConsoleGui());
	 * */
	public void initGui() {
		
	}
	
	/**
	 * Handles closing down the {@link ConsoleListener}s and {@link ConsoleGui}s.<br/>
	 * Note: if overriding make sure to call super to close {@link ConsoleListener} and {@link ConsoleGui}.<br/>
	 * @see ConsoleGui#dispose
	 * @see ConsoleListener#onDisable
	 * @see ConsoleListener#onRemoval
	 * */
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
		
	}
	
	/**
	 * Adds required {@link ConsoleListener}s to the {@link BaseConsole}.<br/>
	 * This is called in the constructor.
	 * */
	private void initMandatoryListeners() {
		
		this.addListener(new ModuleListener());
		this.addListener(new ModuleJarLoader());
		
	}
	
	/**
	 * Adds recommend {@link ConsoleListener}s to the {@link BaseConsole}.<br/>
	 * This is not called on init.
	 * */
	public void addDefaultListeners() {
		this.addListener(new ModulePluginManager());
		this.addListener(new ModuleConsoleManager());
		this.addListener(new ModuleHistory());
		this.addListener(new ModuleStdoutRedirect());
		this.addListener(new ModuleNetwork());
	}
	
	/**
	 * Turns off {@link System#out} redirection
	 * from
	 * {@link com.n9mtq4.console.lib.BaseConsole}.
	 * {@link com.n9mtq4.console.lib.BaseConsole#print}.
	 * @see BaseConsole#redirectStdoutOn()
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 * */
	public void redirectStdoutOff() {
		
		if (stdoutRedirect != null) stdoutRedirect.turnOff();
		
	}
	
	/**
	 * Turns on {@link System#out} redirection
	 * to
	 * {@link BaseConsole}.{@link BaseConsole#print}
	 * @see BaseConsole#redirectStdoutOff()
	 * @see BaseConsole#redirectStdoutOn(boolean)
	 * */
	public void redirectStdoutOn() {
		
		redirectStdoutOn(false);
		
	}
	
	/**
	 * Turns on {@link System#out} redirection
	 * to
	 * {@link com.n9mtq4.console.lib.BaseConsole}.
	 * {@link com.n9mtq4.console.lib.BaseConsole#print}.
	 * @param debug Whether or not to print java file and line number of print statement
	 * @see BaseConsole#redirectStdoutOff()
	 * @see BaseConsole#redirectStdoutOn
	 * */
	public void redirectStdoutOn(boolean debug) {
		
		if (stdoutRedirect == null) {
			stdoutRedirect = new StdoutRedirect(this);
		}
		stdoutRedirect.turnOn(debug);
		
	}
	
	/**
	 * Note: use me to send input when using a custom {@link ConsoleGui}.<br/>
	 * Takes {@link String} and iterates through all {@link ConsoleListener} on console.
	 * @param text String to send to {@link ConsoleListener#actionPerformed}.
	 * */
	public void sendPluginsString(String text) {
		history.add(text);
		push(text);
	}
	
	/**
	 * Low level version of {@link BaseConsole#sendPluginsString}.
	 * @param text Sting to send to {@link ConsoleListener#actionPerformed}.
	 * */
	private void push(String text) {
		
		try {
			ConsoleCommand command = new ConsoleCommand(text);
			ConsoleActionEvent event = new ConsoleActionEvent(this, command);
			for (ConsoleListener p : listeners) {
				try {
					if (p.isEnabled() && (!event.isDone() || p.hasIgnoreDone())) {
						p.push(event);
					}
				}catch (Exception e) {
					this.printStackTrace(e);
					e.printStackTrace();
				}
			}
		}catch (ConcurrentModificationException e1) {
		}
		
	}
	
	/**
	 * Prints the stack trace of a throwable to the {@link BaseConsole} output.
	 * @param throwable Prints the stackTrace.
	 * @see java.lang.Throwable#printStackTrace
	 * */
	public void printStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		this.println(sw.toString(), Colour.RED);
	}
	
	/**
	 * Finds all {@link ConsoleListener}s with a given class name and returns them in the form of an array<br/>
	 * @param name The name of the class to look for.
	 * @return an array of {@link ConsoleListener}s with the given class name
	 * */
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
	 * */
	public void enableAllListeners() {
		
		for (ConsoleListener l : listeners) {
			
			enableListener(l);
			
		}
		
	}
	
	/**
	 * Enables all {@link ConsoleListener} with a given name attached to {@link BaseConsole}.
	 * @param name Class name of the {@link ConsoleListener} to enable.
	 * @see BaseConsole#enableListenerByName
	 * */
	public void enableListenersByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				enableListener(l);
			}
			
		}
		
	}
	
	/**
	 * Only enables the first {@link ConsoleListener} with a given name attached to {@link BaseConsole}.
	 * @param name Class name of the {@link ConsoleListener} to enable.
	 * @see BaseConsole#enableListenersByName
	 * */
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
	 * @param listener the {@link ConsoleListener} object to enable
	 * @see BaseConsole#disableListener
	 * @see BaseConsole#enableListenerByName
	 * */
	public void enableListener(ConsoleListener listener) {
		
		if (!listener.isEnabled()) {
			listener.setEnabled(true);
			listener.onEnable(new EnableActionEvent(this));
		}
		
	}
	
	public void disableAllListeners() {
		
		disableAllListeners(DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	public void disableAllListeners(int type) {
		
		for (ConsoleListener l : listeners) {
			
			disableListener(l, type);
			
		}
		
	}
	
	public void disableListenersByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				disableListener(l, type);
			}
			
		}
		
	}
	
	public void disableListenerByName(String name) {
		
		disableListenerByName(name, DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	public void disableListenerByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				disableListener(l, type);
				break;
			}
			
		}
		
	}
	
	public void disableListener(ConsoleListener listener) {
		
		disableListener(listener, DisableActionEvent.NOT_SPECIFIED);
		
	}
	
	public void disableListener(ConsoleListener listener, int type) {
		
		if (listener.isEnabled()) {
			listener.setEnabled(false);
			listener.onDisable(new DisableActionEvent(this, type));
		}
		
	}
	
	public void removeListenerByName(String name) {
		
		removeListenerByName(name, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	public void removeListenerByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l);
				break;
			}
			
		}
		
	}
	
	public void removeListenersByName(String name) {
		
		removeListenersByName(name, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	public void removeListenersByName(String name, int type) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l, type);
			}
			
		}
		
	}
	
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
	
	public void removeAllListeners() {
		
		removeAllListeners(RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
	@SuppressWarnings("deprecation")
	public void addListener(ConsoleListener listener) {
		
		if (!listeners.contains(listener) || !listener.getLinkedBaseConsoles().contains(this)) {
			listeners.add(listener);
			listener.onAddition(new AdditionActionEvent(this));
			listener.onEnable(new EnableActionEvent(this));
			listener.addToConsole(this);
		}
		
	}
	
	public void removeListener(ConsoleListener listener) {
		
		removeListener(listener, RemovalActionEvent.NOT_SPECIFIED);
		
	}
	
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
	 * @param filePath the file path of the folder to load this plugins from.
	 * */
	public void loadPlugins(String filePath) {
		
		PluginManager.loadPluginsToConsole(this, filePath);
		
	}
	
	/**
	 * Loads plugins to this console from "./plugins".<br/>
	 * @see BaseConsole#loadPlugins(String)
	 * */
	public void loadPlugins() {
		
		PluginManager.loadPluginsToConsole(this, PluginManager.DEFAULT_PLUGIN_FOLDER);
		
	}
	
	/**
	 * Prints an image to the gui.<br/>
	 * @param file The file to print
	 * @see BaseConsole#printImage
	 * */
	public void printlnImage(File file) {
		
		printImage(file.getAbsolutePath());
		print("\n");
		
	}
	
	/**
	 * Prints an image to the gui.<br/>
	 * @param filePath The file path of the image to print
	 * @see BaseConsole#printImage
	 * */
	public void printlnImage(String filePath) {
		
		printImage(filePath);
		print("\n");
		
	}
	
	public void println(Object x) {
		println(x.toString());
	}
	
	public void println(Object x, Colour colour) {
		println(x.toString(), colour);
	}
	
	public void println(String text) {
		print(text + "\n");
	}
	
	public void println(String text, Colour colour) {
		print(text + "\n", colour);
	}
	
	/**
	 * Prints the given string in the given color.
	 * @deprecated Use {@link BaseConsole#println(String, Colour)}
	 * @param text String to print.
	 * @param color Colour to print string in.
	 * @see BaseConsole#println(String, Colour)
	 * */
	@Deprecated
	public void println(String text, Color color) {
		print(text + "\n", color);
	}
	
	public void printImage(File file) {
		
		printImage(file.getPath());
		
	}
	
	/**
	 * Note: Lowest level<br/>
	 * */
	public void printImage(String filePath) {
		
		for (ConsoleGui g : gui) {
			g.printImage(filePath);
		}
		
	}
	
	public void print(Object x) {
		print(x.toString());
	}
	
	public void print(Object x, Colour colour) {
		print(x.toString(), colour);
	}
	
	public void print(String text) {
		print(text, defaultTextColour);
	}
	
	/**
	 * @deprecated Use {@link BaseConsole#print(String, Colour)}
	 * */
	@Deprecated
	public void print(String text, Color color) {
		print(text, Colour.getColour(color));
	}
	
	/**
	 * Note: Lowest level<br/>
	 * */
	public void print(String text, Colour colour) {
		
		for (ConsoleGui g : gui) {
			g.print(text, colour);
		}
		
	}
	
	/**
	 * Gets the the listener with a given name, or index.
	 * @param identifier A class name, or a number (in the form a {@link String}).
	 * @return A {@link ConsoleListener} with the given name of index.
	 * */
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
	 * @deprecated Use {@link BaseConsole#getListener} instead (index in {@link ConsoleListener} array or name).
	 * @param index The index of the desired {@link ConsoleListener}.
	 * @return The {@link ConsoleListener} with the given class name.
	 * @see BaseConsole#getListener
	 * */
	@Deprecated
	public ConsoleListener getListenerByIndex(int index) {
		
		return listeners.get(index);
		
	}
	
	/**
	 * Gets a {@link ConsoleListener} with a given name.
	 * @deprecated Use {@link BaseConsole#getListener} instead (index in {@link ConsoleListener} array or name).
	 * @param name The class name of the desired {@link ConsoleListener}.
	 * @return The {@link ConsoleListener} with the given class name.
	 * @see BaseConsole#getListener
	 * */
	@Deprecated
	public ConsoleListener getListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equalsIgnoreCase(name)) {
				
				return l;
				
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<String> getHistory() {
		return (ArrayList<String>) history.clone();
	}
	
	public void setHistory(ArrayList<String> history) {
		this.history = history;
	}
	
	public ArrayList<ConsoleListener> getListeners() {
		return (ArrayList<ConsoleListener>) listeners.clone();
	}
	
	public StdoutRedirect getStdoutRedirect() {
		return stdoutRedirect;
	}
	
	public ArrayList<ConsoleGui> getGui() {
		return (ArrayList<ConsoleGui>) gui.clone();
	}
	
	public Colour getDefaultTextColour() {
		return defaultTextColour;
	}
	
	public void setDefaultTextColour(Colour defaultTextColour) {
		this.defaultTextColour = defaultTextColour;
	}
	
	@Deprecated
	public boolean hasGuiAttached() {
		return gui.size() > 0;
	}
	
	public void addGui(ConsoleGui consoleGui) {
		gui.add(consoleGui);
		consoleGui.add(this);
	}
	
	public void removeGui(ConsoleGui consoleGui) {
		gui.remove(consoleGui);
		consoleGui.dispose();
	}
	
	public int getId() {
		return id;
	}
	
}
