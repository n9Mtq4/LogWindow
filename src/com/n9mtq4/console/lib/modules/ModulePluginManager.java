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

package com.n9mtq4.console.lib.modules;

import com.n9mtq4.console.lib.*;
import com.n9mtq4.console.lib.Console;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Will on 10/24/14.
 */
public class ModulePluginManager extends ConsoleListener {
	
	@Override
	public void onEnable(EnableActionEvent e) {
		
	}
	
	@Override
	public void actionTab(TabActionEvent e) {
		
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		if (e.getCommand().trim().equals("loadPlugins")) {
			e.getConsole().println("loading plugins");
			loadPluginsToConsole(e.getConsole());
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
	}
	
	public static void loadPluginsToConsole(Console c) {
		
		File folder = new File("plugins");
		if (!folder.exists()) {
			return;
		}
		File[] children = folder.listFiles();
		for (File f : children) {
			if (f.getAbsolutePath().trim().endsWith(".jar")) {
				
				String name = f.getName().substring(0, f.getName().lastIndexOf(".jar")).trim();
				
				if (new File("plugins/" + name + ".txt").exists()) {
					try {
						addFile(f);
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					String text = loadStringFromFile("plugins/" + name + ".txt");
					String[] tokens = text.split("\n");
					for (String t : tokens) {
						try {
							Class<?> clazz = Class.forName(t);
							ConsoleListener clazz1 = (ConsoleListener) clazz.newInstance();
							c.addListener(clazz1);
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
	
	private static final Class[] parameters = new Class[] {URL.class};
	
	private static void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}
	
	private static void addURL(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] {u});
		}catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
	
}
