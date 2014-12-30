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

import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.RemovalActionEvent;
import com.n9mtq4.console.lib.managers.PluginRepository;
import com.n9mtq4.console.lib.utils.Colour;

/**
 * Created by Will on 12/29/14.
 */
public class ModuleRepository extends ConsoleListener {
	
	private PluginRepository pluginRepository;
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
//		TODO: install
		if (e.getCommand().getArg(0).equalsIgnoreCase("repo")) {
			int l = e.getCommand().getLength();
			if (l == 2) {
				if (e.getCommand().getArg(1).equalsIgnoreCase("update")) {
//					TODO: update
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("sync")) {
					pluginRepository.sync();
				}
			}
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
