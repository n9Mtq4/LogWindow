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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Will on 10/24/14.
 */
public class ModuleJarLoader extends ConsoleListener {
	
	@Override
	public void onEnable(EnableActionEvent e) {
		
	}
	
	@Override
	public void actionTab(TabActionEvent e) {
		
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		if (e.getCommand().trim().toLowerCase().startsWith("jarloader ") && e.getCommand().getLength() >= 2) {
			
			String filePath = e.getCommand().getText().substring(e.getCommand().getText().indexOf("jarloader ") + "jarloader ".length());
			File jarFile = new File(filePath);
			if (!jarFile.exists()) {
				e.getConsole().println("[ERROR]: " + jarFile.getPath() + " doesn;t exist");
				return;
			}
			e.getConsole().println("Adding jar file: " + jarFile, Color.BLUE);
			try {
				addFile(jarFile);
			}catch (IOException e1) {
				e.getConsole().println("[ERROR]: " + e1.toString(), Color.RED);
			}
			e.getConsole().println("done loading " + filePath, Color.BLUE);
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
		e.getConsole().addListener(this);
		e.getConsole().print("[ERROR]: ", Color.RED);
		e.getConsole().println("you can't remove " + this.getClass().getName());
		
	}
	
	private static final Class[] parameters = new Class[] {URL.class};
	
	private static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}
	
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
