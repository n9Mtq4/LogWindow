/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2016  Will (n9Mtq4) Bresnahan
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

package com.n9mtq4.logwindow.plugin;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.utils.JarLoader;
import com.n9mtq4.logwindow.utils.LWReflectionHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.ZipFile;

/**
 * Created by Will on 10/26/14.<br>
 * Handles all the loading from plugin jar files into the {@link BaseConsole}.
 * @since v4.0
 * @version v5.1
 */
public class PluginManager {
	
	/**
	 * The constant DEFAULT_PLUGIN_FOLDER.
	 * The default location for the plugins folder.<br>
	 * Currently, it is "plugins/"
	 */
	public static final String DEFAULT_PLUGIN_FOLDER = "plugins/";
	
	/**
	 * Loads all plugins from a specific directory to the BaseConsole.<br>
	 * 
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, File)
	 * @param c The {@link BaseConsole} to add the plugins to
	 * @param filePath The file path of the plugins directory
	 */
	public static void loadPluginsToConsole(BaseConsole c, String filePath) {
		if (!filePath.endsWith("/")) {
			filePath += "/";
		}
		
		File folder = new File(filePath);
		loadPluginsToConsole(c, folder);
	}
	
	/**
	 * Loads all plugins from a specific directory to the BaseConsole.<br>
	 *
	 * @param c The {@link BaseConsole} to add the plugins to
	 * @param folder The file of the plugins directory
	 */
	public static void loadPluginsToConsole(BaseConsole c, File folder) {
		
		if (!folder.exists()) {
			return;
		}
		File[] children = folder.listFiles();
		if (children == null) return;
		
		ArrayList<ListenerContainer> listeners = new ArrayList<ListenerContainer>();
		
		for (File f : children) {
			if (!isValidPlugin(f)) continue;
//			add the jar file to the class loader, so we can reference it
			try {
				JarLoader.addFile(f);
			}catch (IOException e) {
				e.printStackTrace();
				c.printStackTrace(e);
			}
		}
		
		for (File f : children) {
			if (!isValidPlugin(f)) continue;
			listeners.addAll(loadPluginListeners(f, c));
		}
		
		for (ListenerContainer listener : listeners) c.enableListenerContainer(listener);
		
	}
	
	/**
	 * Parses a plugin.txt file and turns it into a data model
	 * of what plugin classes should be loaded and when.
	 * 
	 * @param f the plugin to read the plugins to load from
	 * @return a list of {@link ListenerToLoad} that corresponds to the input file
	 * @throws IOException if there is an exception reading the file
	 * */
	public static ListenerToLoad[] parsePluginTxt(final File f) throws IOException {
		
		// load in files
		final ZipFile zf = new ZipFile(f);
		final InputStream is = zf.getInputStream(zf.getEntry("plugin.txt"));
		final String infoText = streamToString(is);
		
		// break into listener chunks
		final String[] listenerDeclarations = infoText.split(";|}"); // split by ; or }
		
		final ArrayList<ListenerToLoad> listenerToLoadsAl = new ArrayList<ListenerToLoad>();
		
		for (String listenerDeclaration : listenerDeclarations) {
			
			// listener chunks can be multiple lines
			final String[] lines = listenerDeclaration.split("\n");
			
			String name = null;
			final ArrayList<String> dependencies = new ArrayList<String>();
			final ArrayList<String> antidependencies = new ArrayList<String>();
			
			for (String line : lines) {
				
				final String trimmed = line.trim();
				
				if (trimmed.startsWith("#")) continue; // comment support
				if (trimmed.equals("")) continue; // ignore blank lines
				
				if (line.startsWith("\t") || line.startsWith(" ")) {
					// its a dependency
					if (trimmed.startsWith("!")) {
						// anti-dependency
						final String classId = trimmed.substring(1).trim();
						antidependencies.add(classId);
					}else {
						// dependency
						dependencies.add(trimmed);
					}
				}else {
					// must be the class id of the listener, a single one
					name = trimmed.endsWith("{") ? trimmed.substring(0, trimmed.length() - 1).trim() : trimmed; // grab the class id
				}
				
			}
			
			// make sure we found one to load
			if (name == null) {
				System.out.println("Error loading plugin from lines " + Arrays.toString(lines));
				break; // no name, start over
			}
			
			// copy from array lists to arrays
			String[] dependenciesArray = new String[dependencies.size()];
			dependencies.toArray(dependenciesArray);
			String[] antidependenciesArray = new String[antidependencies.size()];
			antidependencies.toArray(antidependenciesArray);
			
			// add it to our list
			final ListenerToLoad listenerToLoad = new ListenerToLoad(name, dependenciesArray, antidependenciesArray);
			listenerToLoadsAl.add(listenerToLoad);
			
		}
		
		// transform listener to load array list into return type
		final ListenerToLoad[] loadList = new ListenerToLoad[listenerToLoadsAl.size()];
		listenerToLoadsAl.toArray(loadList);
		
		return loadList;
		
	}
	
	/**
	 * Is the plugin a valid file?
	 * Makes sure it isn't hidden, doesn't start with a "." and 
	 * is a jar or zip file.
	 * 
	 * @param f the File to check
	 * @return if the plugin is valid
	 * */
	private static boolean isValidPlugin(File f) {
		return (!f.getName().startsWith(".") && !f.isHidden() && (f.getAbsolutePath().trim().toLowerCase().endsWith(".jar") || f.getName().trim().toLowerCase().endsWith(".zip")));
	}
	
	/**
	 * Loads a plugin from File f into BaseConsole c.<br>
	 * This plugin being loaded must be in the new format with a plugin.txt
	 * in the root of the jar file.
	 * 
	 * @param f The {@link File}
	 * @param c The {@link BaseConsole}
	 * @return An ArrayList of all the ListenerContainers that have been added.
	 */
	public static ArrayList<ListenerContainer> loadPluginListeners(File f, BaseConsole c) {
		
		ArrayList<ListenerContainer> listeners = new ArrayList<ListenerContainer>();
		
//		makes sure the file is a jar
		if (f.getAbsolutePath().trim().toLowerCase().endsWith(".jar")) {
			
			try {
				
				// read the plugin.txt info
				final ListenerToLoad[] listenersToLoad = parsePluginTxt(f);
				for (ListenerToLoad potentialListener : listenersToLoad) {
					
					// make sure we should load it
					if (!potentialListener.shouldLoad() && !potentialListener.isForceLoad()) continue;
					
					// try to add this listener to the base console using reflection
					ListenerAttribute listener = LWReflectionHelper.callConstructor(LWReflectionHelper.getClassByFullName(potentialListener.getClassIdentifier()));
					ListenerContainer le = ListenerContainer.makeListenerEntry(listener);
					c.addListenerContainerRaw(le);
					listeners.add(le);
					
				}
				
			}catch (IOException e) {
				// something has gone horribly wrong
				e.printStackTrace();
				c.printStackTrace(e);
			}catch (Exception e) {
				e.printStackTrace();
				c.printStackTrace(e);
				System.out.println("Error loading listener: " + f.getName());
				c.println("Error loading listener: " + f.getName());
			}
			
		}
		
		return listeners;
		
	}
	
	/**
	 * Turns a InputStream into a String.
	 * 
	 * @param is The InputStream to convert
	 * @return a string representation of the InputStream
	 */
	private static String streamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		String str = s.hasNext() ? s.next() : "";
		s.close();
		return str;
	}
	
	/**
	 * Loads the contents of a file into a string.
	 * 
	 * @param filePath The path of the file
	 * @return the String of the file
	 */
	private static String silentLoadStringFromFile(String filePath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			String everything = sb.toString();
			br.close();
			return everything;
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
