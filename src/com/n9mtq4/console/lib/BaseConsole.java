/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
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

import com.n9mtq4.console.lib.events.AdditionActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.EnableActionEvent;
import com.n9mtq4.console.lib.events.RemovalActionEvent;
import com.n9mtq4.console.lib.managers.PluginManager;
import com.n9mtq4.console.lib.managers.StdoutRedirect;
import com.n9mtq4.console.lib.modules.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */
public class BaseConsole {
	
	private ArrayList<ConsoleListener> listeners;
	private ArrayList<String> history;
	public int historyIndex;
	private StdoutRedirect stdoutRedirect;
	
	public BaseConsole(String pluginDirectory) {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		this.loadPlugins(pluginDirectory);
	}
	
	public BaseConsole() {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
	}
	
	public BaseConsole(ConsoleListener listener) {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		addListener(listener);
		history = new ArrayList<String>();
	}
	
	private void initMandatoryListeners() {
		
		this.addListener(new ModuleListener());
		this.addListener(new ModuleJarLoader());
		
	}
	
	public void addDefaultListeners() {
		this.addListener(new ModulePluginManager());
		this.addListener(new ModuleConsoleManager());
		this.addListener(new ModuleHistory());
		this.addListener(new ModuleStdoutRedirect());
	}
	
	public void onFieldEnter(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		String text = source.getText();
		if (!text.trim().equals("")) {
			source.setText("");
			history.add(text);
			historyIndex = history.size();
			push(text);
		}
	}
	
	public void redirectStdoutOff() {
		
		if (stdoutRedirect != null) stdoutRedirect.turnOff();
		
	}
	
	public void redirectStdoutOn() {
		
		redirectStdoutOn(false);
		
	}
	
	public void redirectStdoutOn(boolean debug) {
		
		if (stdoutRedirect == null) {
			stdoutRedirect = new StdoutRedirect(this);
		}
		stdoutRedirect.turnOn(debug);
		
	}
	
	private void push(String text) {
		
		try {
			for (ConsoleListener p : listeners) {
				try {
					if (p.isEnabled()) {
						p.push(text);
					}
				}catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					this.println(sw.toString(), Color.RED);
					e.printStackTrace();
				}
			}
		}catch (ConcurrentModificationException e1) {
		}
		
	}
	
	public void tab() {
		
		try {
			for (ConsoleListener p : listeners) {
				if (p.isEnabled()) {
					p.tab();
				}
			}
		}catch (ConcurrentModificationException e) {
		}
		
	}
	
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
	
	public void enableAllListeners() {
		
		for (ConsoleListener l : listeners) {
			
			enableListener(l);
			
		}
		
	}
	
	public void enableListenersByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				enableListener(l);
			}
			
		}
		
	}
	
	public void enableListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				enableListener(l);
				break;
			}
			
		}
		
	}
	
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
		
		int i = getListeners().size();
		int c = 0;
		while (getListeners().size() > 0 && i > c) {
			try {
				for (ConsoleListener l : getListeners()) {
					
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
	
	public void removeListener(ConsoleListener listener, int type) {
		
		if (listeners.contains(listener) || listener.getLinkedBaseConsoles().contains(this)) {
			listeners.remove(listener);
			listener.removeFromConsole(this);
			listener.onDisable(new DisableActionEvent(this, type));
			listener.onRemoval(new RemovalActionEvent(this, type));
		}
		
	}
	
	public void loadPlugins(String filePath) {
		
		PluginManager.loadPluginsToConsole(this, filePath);
		
	}
	
	public void loadPlugins() {
		
		PluginManager.loadPluginsToConsole(this, PluginManager.DEFAULT_PLUGIN_FOLDER);
		
	}
	
	public void printlnImage(File file) {
		
		printImage(file.getAbsolutePath());
		print("\n");
		
	}
	
	public void printlnImage(String filePath) {
		
		printImage(filePath);
		print("\n");
		
	}
	
	public void println(String text) {
		print(text + "\n");
	}
	
	public void println(String text, Color color) {
		print(text + "\n", color);
	}
	
	public void printImage(File file) {
		
		printImage(file.getPath());
		
	}
	
	public void printImage(String filePath) {
		
//		dummy
		
	}
	
	public void print(String text) {
		print(text, Color.BLACK);
	}
	
	public void print(String text, Color color) {
		System.out.println(text);
	}
	
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
	
	public ConsoleListener getListenerByIndex(int index) {
		
		return listeners.get(index);
		
	}
	
	public ConsoleListener getListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equalsIgnoreCase(name)) {
				
				return l;
				
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<String> getHistory() {
		return history;
	}
	
	public void setHistory(ArrayList<String> history) {
		this.history = history;
	}
	
	public int getHistoryIndex() {
		return historyIndex;
	}
	
	public void setHistoryIndex(int historyIndex) {
		this.historyIndex = historyIndex;
	}
	
	public ArrayList<ConsoleListener> getListeners() {
		return listeners;
	}
	
	public void setListeners(ArrayList<ConsoleListener> listeners) {
		this.listeners = listeners;
	}
	
	public StdoutRedirect getStdoutRedirect() {
		return stdoutRedirect;
	}
	
	public void setStdoutRedirect(StdoutRedirect stdoutRedirect) {
		this.stdoutRedirect = stdoutRedirect;
	}
	
}
