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

package com.n9mtq4.console.lib.modules;

import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.managers.PluginManager;

import java.io.File;

/**
 * Created by Will on 10/24/14.
 */

/**
 * A module to load plugins to the console
 */
public class ModulePluginManager extends ConsoleListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e) {
		
		if (e.getCommand().eqt("loadplugins")) {
			e.getBaseConsole().println("loading plugins...");
			PluginManager.loadPluginsToConsole(e.getBaseConsole(), PluginManager.DEFAULT_PLUGIN_FOLDER);
			e.getBaseConsole().println("done loading plugins");
			return;
		}
		
		if (e.getCommand().startsWith("loadplugin")) {
			String plPath = e.getCommand().getWordsStartingFrom(1);
			File f = new File(plPath);
			if (!f.exists()) {
				e.getBaseConsole().println(plPath + " does not exist");
				return;
			}
			PluginManager.loadPlugin(new File(plPath), e.getBaseConsole());
		}
		
	}
	
}
