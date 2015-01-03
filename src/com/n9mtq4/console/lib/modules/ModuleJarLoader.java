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

import com.n9mtq4.console.lib.events.*;
import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.utils.Colour;
import com.n9mtq4.console.lib.managers.PluginManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Will on 10/24/14.
 */
/**
 * A module to load jar files into the class path from a console
 * */
public class ModuleJarLoader extends ConsoleListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e) {
		
		if (e.getCommand().trim().toLowerCase().startsWith("jarloader ") && e.getCommand().getLength() >= 2) {
			
			String filePath = e.getCommand().getText().substring(e.getCommand().getText().indexOf("jarloader ") + "jarloader ".length());
			File jarFile = new File(filePath);
			if (!jarFile.exists()) {
				e.getBaseConsole().println("[ERROR]: " + jarFile.getPath() + " doesn't exist");
				return;
			}
			e.getBaseConsole().println("Adding jar file: " + jarFile, Colour.BLUE);
			try {
				PluginManager.addFile(jarFile);
			}catch (IOException e1) {
				e.getBaseConsole().println("[ERROR]: " + e1.toString(), Colour.RED);
			}
			e.getBaseConsole().println("done loading " + filePath, Colour.BLUE);
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getBaseConsole().enableListener(this);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't disable " + this.getClass().getName());
		}
		
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
		
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getBaseConsole().addListener(this);
			e.getBaseConsole().print("[ERROR]: ", Colour.RED);
			e.getBaseConsole().println("you can't remove " + this.getClass().getName());
		}
		
	}
	
}
