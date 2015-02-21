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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import static com.n9mtq4.console.lib.utils.ReflectionHelper.*;

/**
 * Created by Will on 10/26/14.
 */
public class PluginManager {
	
	private static final Class[] parameters = new Class[] {URL.class};
	public static String DEFAULT_PLUGIN_FOLDER = "plugins/";
	
	public static void loadPluginsToConsole(BaseConsole c, String location) {
		
		if (!location.endsWith("/")) {
			location += "/";
		}
		
		File folder = new File(location);
		if (!folder.exists()) {
			return;
		}
		File[] children = folder.listFiles();
		assert children != null;
		for (File f : children) {
			
			loadPlugin(f, c, location);
			
		}
		
	}
	
	//TODO: test to see if works
	public static void loadPlugin(File f, BaseConsole c) {
		loadPlugin(f, c, f.getParent());
	}
	
	public static void loadPlugin(File f, BaseConsole c, String location) {
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
//							TODO: delete commented out code
							ConsoleListener l = (ConsoleListener) callConstructor(Class.forName(t));
							c.addListener(l);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
			}
			
		}
	}
	
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
	
	public static void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}
	
	public static void addURL(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
//		TODO: delete commented out code
/*		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] {u});
		}catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}*/
		callVoidMethod("addURL", sysloader, sysclass, new Class[]{URL.class}, new Object[]{u});
	}
	
}
