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

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.RemovalActionEvent;
import com.n9mtq4.console.lib.utils.Colour;

import java.util.ArrayList;

/**
 * Created by Will on 10/21/14.
 */

/**
 * A module to add, remove, enable, and disable listeners on the console
 */
public class ModuleListener extends ConsoleListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e) {
		
		if (e.getCommand().getArg(0).equalsIgnoreCase("listener")) {
			
			if (e.getCommand().getLength() == 2) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("list")) {
					int i = 0;
					for (ConsoleListener l : e.getBaseConsole().getListeners()) {
						e.getBaseConsole().print("[" + i + "]: ");
						e.getBaseConsole().println(l.getClass().getName(), l.isEnabled() ? Colour.GREEN : Colour.RED);
						i++;
					}
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("adddefaults")) {
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("adding...");
					
					e.getBaseConsole().addDefaultListeners();
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("done adding default listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeduplicates")) {
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("removing duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ConsoleListener l : e.getBaseConsole().getListeners()) {
						String name = l.getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
						e.getBaseConsole().removeListenerByName(s, RemovalActionEvent.USER_CLOSE);
					}
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("done removing duplicate listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeall")) {
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("removing all listeners...");
					
					e.getBaseConsole().removeAllListeners(RemovalActionEvent.USER_CLOSE);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("done removing all listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableduplicates")) {
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("disabling duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ConsoleListener l : e.getBaseConsole().getListeners()) {
						String name = l.getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
						e.getBaseConsole().disableListenerByName(s, DisableActionEvent.USER_CLOSE);
					}
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("done disabling duplicate listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableall")) {
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("disabling all listeners...");
					
					e.getBaseConsole().disableAllListeners(DisableActionEvent.USER_CLOSE);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("done disabling all listeners");
					
				}
				
			}else if (e.getCommand().getLength() == 3) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("add")) {
					
					try {
						Class<?> clazz = Class.forName(e.getCommand().getArg(2));
						Object clazz1 = clazz.newInstance();
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println("adding...");
						
						e.getBaseConsole().addListener((ConsoleListener) clazz1);
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println("done adding: " + clazz.getName());
						
					}catch (Exception e1) {
						e.getBaseConsole().print("[ERROR]: ", Colour.RED);
						e.getBaseConsole().println(e1.toString());
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("remove")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("removing...");
					
					ConsoleListener l = e.getBaseConsole().getListener(name);
					if (l != null) {
						
						e.getBaseConsole().removeListener(l, RemovalActionEvent.USER_CLOSE);
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println("Done removing: " + l.getClass().getName());
						
					}else {
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println(name + " isn't a valid listener");
						
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("removing all instances...");
					
					e.getBaseConsole().removeListenersByName(name, RemovalActionEvent.USER_CLOSE);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("Done removing all instances: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("enable")) {
					
					try {
						
						String name = e.getCommand().getArg(2);
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println("enabling...");
						
						ConsoleListener l = e.getBaseConsole().getListener(name);
						if (l != null) {
							
							e.getBaseConsole().enableListener(l);
							
							e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
							e.getBaseConsole().println("done enabling: " + l.getClass().getName());
							
						}else {
							
							e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
							e.getBaseConsole().println(name + " isn't a valid listener");
							
						}
						
					}catch (Exception e1) {
						e.getBaseConsole().print("[ERROR]: ", Colour.RED);
						e.getBaseConsole().println(e1.toString());
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("enableallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("enabling all instances...");
					
					e.getBaseConsole().enableListenersByName(name);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("Done enabling all instances: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disable")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("disabling...");
					
					ConsoleListener l = e.getBaseConsole().getListener(name);
					if (l != null) {
						
						e.getBaseConsole().disableListener(l, DisableActionEvent.USER_CLOSE);
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println("Done disabling: " + l.getClass().getName());
						
					}else {
						
						e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
						e.getBaseConsole().println(name + " isn't a valid listener");
						
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableallof")) {
					
					String name = e.getCommand().getArg(2);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("disabling all instances...");
					
					e.getBaseConsole().disableListenersByName(name, DisableActionEvent.USER_CLOSE);
					
					e.getBaseConsole().print("[OUT]: ", Colour.BLUE);
					e.getBaseConsole().println("Done disabling all instances: " + name);
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("listconsoles")) {
					
					String name = e.getCommand().getArg(2);
					ConsoleListener l = e.getBaseConsole().getListener(name);
					for (BaseConsole c : l.getLinkedBaseConsoles()) {
						if (e.getBaseConsole().hasGuiAttached()) {
							e.getBaseConsole().println(c.getClass().getName() + ": " + e.getBaseConsole().getId() + ": " + c.getGui().get(0).getName());
						}else {
							e.getBaseConsole().print(c.getClass().getName() + ": " + e.getBaseConsole().getId());
						}
					}
					
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
