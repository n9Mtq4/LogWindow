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
import com.n9mtq4.console.lib.events.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Will on 10/21/14.
 */
public class ModuleListener extends ConsoleListener {
	
	@Override
	public void onAddition(AdditionActionEvent e) {
		
	}
	
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
						e.getConsole().println(l.getClass().getName(), l.isEnabled() ? Color.GREEN : Color.RED);
					}
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("adddefaults")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("adding...");
					
					e.getConsole().addDefaultListeners();
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done adding default listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeduplicates")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("removing duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ConsoleListener l : e.getConsole().getListeners()) {
						String name = l.getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
						e.getConsole().removeListenerByName(s, RemovalActionEvent.USER_CLOSE);
					}
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done removing duplicate listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeall")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("removing all listeners...");
					
					e.getConsole().removeAllListeners(RemovalActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done removing all listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableduplicates")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("disabling duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ConsoleListener l : e.getConsole().getListeners()) {
						String name = l.getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
						e.getConsole().disableListenerByName(s, DisableActionEvent.USER_CLOSE);
					}
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done disabling duplicate listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableall")) {
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("disabling all listeners...");
					
					e.getConsole().disableAllListeners(DisableActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("done disabling all listeners");
					
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
					
					e.getConsole().removeListenerByName(name, RemovalActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done removing: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("removing all instances...");
					
					e.getConsole().removeListenersByName(name, RemovalActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done removing all instances: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("enable")) {
					
					try {
						
						String name = e.getCommand().getArg(2);
						
						e.getConsole().print("[OUT]: ", Color.BLUE);
						e.getConsole().println("enabling...");
						
						e.getConsole().enableListenerByName(name);
						
						e.getConsole().print("[OUT]: ", Color.BLUE);
						e.getConsole().println("done enabling: " + name);
						
					}catch (Exception e1) {
						e.getConsole().print("[ERROR]: ", Color.RED);
						e.getConsole().println(e1.toString());
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("enableallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("enabling all instances...");
					
					e.getConsole().enableListenersByName(name);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done enabling all instances: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disable")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("disabling...");
					
					e.getConsole().disableListenerByName(name, DisableActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done disabling: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("disabling all instances...");
					
					e.getConsole().disableListenersByName(name, DisableActionEvent.USER_CLOSE);
					
					e.getConsole().print("[OUT]: ", Color.BLUE);
					e.getConsole().println("Done disabling all instances: " + name);
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getConsole().enableListener(this);
			e.getConsole().print("[ERROR]: ", Color.RED);
			e.getConsole().println("you can't disable " + this.getClass().getName());
		}
		
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
		
		if (e.getType() != DisableActionEvent.WINDOW_CLOSE) {
			e.getConsole().addListener(this);
			e.getConsole().print("[ERROR]: ", Color.RED);
			e.getConsole().println("you can't remove " + this.getClass().getName());
		}
		
	}
	
}
