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

package com.n9mtq4.console.lib.managers;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.ConsoleListener;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipFile;

import static com.n9mtq4.console.lib.utils.JarLoader.addFile;
import static com.n9mtq4.console.lib.utils.ReflectionHelper.callConstructor;
import static com.n9mtq4.console.lib.utils.ReflectionHelper.getClassByFullName;


/**
 * Created by Will on 10/26/14.<br>
 * Handles all the loading from plugin jar files into the baseconsole.
 */
//TODO: finish javadocs
public class PluginManager {
	
	private static final Class[] parameters = new Class[]{URL.class};
	/**
	 * The constant DEFAULT_PLUGIN_FOLDER.
	 */
	public static final String DEFAULT_PLUGIN_FOLDER = "plugins/";
	
	/**
	 * Loads all plugins from a specific directory to the BaseConsole.<br>
	 *
	 * @param c        the c
	 * @param filePath the file path
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, File) Use loadPluginsToConsole(BaseConsole, File) instead
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
	 * @param c      the c
	 * @param folder the folder
	 */
	public static void loadPluginsToConsole(BaseConsole c, File folder) {
		
		if (!folder.exists()) {
			return;
		}
		File[] children = folder.listFiles();
		if (children == null) return;
		
		ArrayList<ConsoleListener> listeners = new ArrayList<ConsoleListener>();
		for (File f : children) {
			
			listeners.addAll(loadPlugin(f, c));
			
		}
		
		for (ConsoleListener listener : listeners) {
			c.enableListener(listener);
		}
		
	}
	
	/**
	 * Loads a plugin from File f into BaseConsole c.<br>
	 * This plugin being loaded must be in the new format with a plugin.txt
	 * in the root of the jar file.
	 *
	 * @param f the f
	 * @param c the c
	 */
	public static ArrayList<ConsoleListener> loadPlugin(File f, BaseConsole c) {
		
		ArrayList<ConsoleListener> listeners = new ArrayList<ConsoleListener>();
		
//		makes sure the file is a jar
		if (f.getAbsolutePath().trim().toLowerCase().endsWith(".jar")) {
			
			try {
				
//				read plugin.txt from the plugin jar and set the contents to infoText
				ZipFile zf = new ZipFile(f);
				InputStream is = zf.getInputStream(zf.getEntry("plugin.txt"));
				String infoText = streamToString(is);
				
//				add the jar file to the class loader, so we can reference it
				addFile(f);
				
//				for each line in the file
				String[] lines = infoText.split("\n");
				for (String line : lines) {
//					if the line isn't a comment
					if (!line.startsWith("#") && !line.trim().equals("")) {
						try {
//							try to add this listener to the base console using reflection
							ConsoleListener listener = callConstructor(getClassByFullName(line.trim()));
							c.addDisabledListener(listener);
							listeners.add(listener);
						}catch (Exception e1) {
							e1.printStackTrace();
							c.printStackTrace(e1);
						}
					}
				}
				
				return listeners;
				
			}catch (IOException e) {
				e.printStackTrace();
				c.printStackTrace(e);
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
	 * Loads plugin from File f, at location into BaseConsole c.<br>
	 * This plugin must be in the old format with a jar file, and another text
	 * file with the same name (case sensitive) containing all the classes in the
	 * jar file that extend ConsoleListener that wants to be added when the plugin
	 * is loaded.
	 *
	 * @param f        the file
	 * @param c        the BaseConsole
	 * @param location the location of the file
	 * @deprecated Use the new plugin format. TODO: THIS WILL BE REMOVED IN VERSION 5.x.x
	 */
	@Deprecated
	public static void loadPluginOLDFORMAT(File f, BaseConsole c, String location) {
		if (f.getAbsolutePath().trim().endsWith(".jar")) {
			
			String name = f.getName().substring(0, f.getName().lastIndexOf(".jar")).trim();
			if (new File(location + name + ".txt").exists()) {
				
				try {
					addFile(f);
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				String text = loadStringFromFile(location + name + ".txt");
				String[] tokens = text.split("\n");
				for (String t : tokens) {
					if (!t.startsWith("# ")) {
						try {
							ConsoleListener l = (ConsoleListener) callConstructor(Class.forName(t.trim()));
							c.addListener(l);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
			}
			
		}
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
