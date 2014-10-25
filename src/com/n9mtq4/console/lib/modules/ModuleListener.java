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
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.EnableActionEvent;
import com.n9mtq4.console.lib.events.TabActionEvent;

import java.awt.*;

/**
 * Created by Will on 10/21/14.
 */
public class ModuleListener extends ConsoleListener {
	
	@Override
	public void onEnable(EnableActionEvent e) {
		
	}
	
	@Override
	public void actionTab(TabActionEvent e) {
		
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		if (e.getCommand().getArg(0).equalsIgnoreCase("listener")) {
			
			if (e.getCommand().getLength() == 2) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("list")) {
					for (ConsoleListener l : e.getConsole().getListeners()) {
						e.getConsole().println(l.getClass().getName(), Color.MAGENTA);
					}
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("adddefaults")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("adding...");
					
					e.getConsole().addDefaultListeners();
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done adding default listeners");
					
				}
				
			}else if (e.getCommand().getLength() == 3) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("add")) {
					
					try {
						Class<?> clazz = Class.forName(e.getCommand().getArg(2));
						Object clazz1 = clazz.newInstance();
						
						e.getConsole().print("[OUT]: ", Color.BLUE);
						e.getConsole().println("adding...");
						
						e.getConsole().addListener((ConsoleListener) clazz1);
						
						e.getConsole().print("[OUT]: ", Color.BLUE);
						e.getConsole().println("done adding: " + clazz.getName());
						
					}catch (Exception e1) {
						e.getConsole().print("[ERROR]: ", Color.RED);
						e.getConsole().println(e1.toString());
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("remove")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("removing...");
					
					e.getConsole().removeListenerByName(name);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done removing: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeall")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("removing all instances...");
					
					e.getConsole().removeListenersByName(name);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done removing all instances: " + name);
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
		e.getConsole().addListener(this);
		e.getConsole().print("[ERROR]: ", Color.RED);
		e.getConsole().println("you can't remove " + this.getClass().getName());
		
	}
	
}
