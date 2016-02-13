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

package com.n9mtq4.logwindow.managers;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.JarLoader;
import com.n9mtq4.logwindow.utils.LWReflectionHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipFile;


/**
 * Created by Will on 10/26/14.<br>
 * Handles all the loading from plugin jar files into the {@link BaseConsole}.
 */
//TODO: finish javadocs
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
			
			if (f.getName().startsWith(".")) continue; // ignore hidden files
			listeners.addAll(loadPlugin(f, c));
			
		}
		
//		for (ListenerContainer listener : listeners) listener.pushEnabled(new EnableEvent(c));
		for (ListenerContainer listener : listeners) c.enableListenerContainer(listener);
		
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
	public static ArrayList<ListenerContainer> loadPlugin(File f, BaseConsole c) {
		
		ArrayList<ListenerContainer> listeners = new ArrayList<ListenerContainer>();
		
//		makes sure the file is a jar
		if (f.getAbsolutePath().trim().toLowerCase().endsWith(".jar")) {
			
			try {
				
//				add the jar file to the class loader, so we can reference it
				JarLoader.addFile(f);
				
//				read plugin.txt from the plugin jar and set the contents to infoText
				ZipFile zf = new ZipFile(f);
				InputStream is = zf.getInputStream(zf.getEntry("plugin.txt"));
				String infoText = streamToString(is);
				
//				for each line in the file
				String[] lines = infoText.split("\n");
				for (String line : lines) {
//					if the line isn't a comment
					if (!line.startsWith("#") && !line.trim().equals("")) {
						try {
//							try to add this listener to the base console using reflection
							ListenerAttribute listener = LWReflectionHelper.callConstructor(LWReflectionHelper.getClassByFullName(line.trim()));
							ListenerContainer le = ListenerContainer.makeListenerEntry(listener);
							c.addListenerContainerRaw(le);
							listeners.add(le);
						}catch (Exception e1) {
							System.err.println("Error in listener adding");
							e1.printStackTrace();
							c.println("Error in listener adding", Colour.RED);
							c.printStackTrace(e1);
						}
					}
				}
				
				zf.close(); //the input stream should close the zip file
				is.close();
				
				return listeners;
				
			}catch (IOException e) {
				e.printStackTrace();
				c.printStackTrace(e);
			}catch (NullPointerException e) {
				System.err.println("No plugin.txt in " + f.getName());
//				e.printStackTrace();
				c.println("No plugin.txt in " + f.getName(), Colour.RED);
//				c.printStackTrace(e);
			}catch (Exception e) {
				e.printStackTrace();
				c.printStackTrace(e);
			}
			
//		add a zip file as a resource
		}else if (f.getName().trim().toLowerCase().endsWith(".zip")) {
//			add the zip file to the class loader, so we can reference it
			try {
				JarLoader.addFile(f);
			}catch (IOException e) {
				System.err.println("Error adding " + f.getName());
				c.println("Error adding " + f.getName(), Colour.RED);
				c.printStackTrace(e);
				e.printStackTrace();
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
	private static String loadStringFromFile(String filePath) {
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
