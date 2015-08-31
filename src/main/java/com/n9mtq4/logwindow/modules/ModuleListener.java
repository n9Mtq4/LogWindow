/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2015  Will (n9Mtq4) Bresnahan
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

package com.n9mtq4.logwindow.modules;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.ConsoleActionEvent;
import com.n9mtq4.logwindow.events.DisableActionEvent;
import com.n9mtq4.logwindow.events.RemovalActionEvent;
import com.n9mtq4.logwindow.listener.*;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.ReflectionHelper;

import java.util.ArrayList;

/**
 * Created by Will on 10/21/14.
 */

/**
 * A module to add, remove, enable, and disable listeners on the console
 */
public class ModuleListener implements StringListener, DisableListener, RemovalListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
		
//		TODO: this is a mess and unorganized, clean it up
		if (e.getCommand().getArg(0).equalsIgnoreCase("listener")) {
			
			if (e.getCommand().getLength() == 2) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("list")) {
					int i = 0;
					for (ListenerContainer l : baseConsole.getListenerContainers()) {
						baseConsole.print("[" + i + "]: ");
						baseConsole.println(l.getAttribute().getClass().getName(), l.isEnabled() ? Colour.GREEN : Colour.RED);
						i++;
					}
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("removeduplicates")) {
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("removing duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ListenerContainer l : baseConsole.getListenerContainers()) {
						String name = l.getAttribute().getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
//						baseConsole.removeListenerByName(s, RemovalActionEvent.USER_CLOSE);
						baseConsole.removeListenerContainer(baseConsole.getContainerFromId(s), RemovalActionEvent.USER_CLOSE);
					}
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("done removing duplicate listeners");
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disableduplicates")) {
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("disabling duplicates...");
					ArrayList<String> duplicateNames = new ArrayList<String>();
					ArrayList<String> namesAlready = new ArrayList<String>();
					for (ListenerContainer l : baseConsole.getListenerContainers()) {
						String name = l.getAttribute().getClass().getName();
						if (namesAlready.contains(name)) {
							duplicateNames.add(name);
						}
						namesAlready.add(name);
					}
					for (String s : duplicateNames) {
//						baseConsole.disableListenerByName(s, DisableActionEvent.USER_CLOSE);
						baseConsole.disableListenerContainer(baseConsole.getContainerFromId(s), DisableActionEvent.USER_CLOSE);
					}
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("done disabling duplicate listeners");
					
				}
				
			}else if (e.getCommand().getLength() == 3) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("add")) {
					
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("adding...");
					
					try {
						Class t = ReflectionHelper.getClass(e.getCommand().getArg(2));
						ListenerAttribute l1 = (ListenerAttribute) (t.newInstance());
//						baseConsole.addListener(l1);
						baseConsole.addListenerAttribute(l1);
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("done adding: " + l1.getClass().getName());
					}catch (Exception e2) {
						baseConsole.println("No Such listener");
						return;
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("remove")) {
					
					String name = e.getCommand().getArg(2);
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("removing...");
					
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					if (l != null) {
						
//						baseConsole.removeListenerEntry(l, RemovalActionEvent.USER_CLOSE);
						baseConsole.removeListenerContainer(l, RemovalActionEvent.USER_CLOSE);
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("Done removing: " + l.getClass().getName());
						
					}else {
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println(name + " isn't a valid listener");
						
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("enable")) {
					
					try {
						
						String name = e.getCommand().getArg(2);
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("enabling...");
						
//						ListenerContainer l = baseConsole.getListenerEntry(name);
						ListenerContainer l = baseConsole.getContainerFromId(name);
						if (l != null) {
							
//							baseConsole.enableListenerEntry(l);
							baseConsole.enableListenerContainer(l);
							
							baseConsole.print("[OUT]: ", Colour.BLUE);
							baseConsole.println("done enabling: " + l.getClass().getName());
							
						}else {
							
							baseConsole.print("[OUT]: ", Colour.BLUE);
							baseConsole.println(name + " isn't a valid listener");
							
						}
						
					}catch (Exception e1) {
						baseConsole.print("[ERROR]: ", Colour.RED);
						baseConsole.println(e1.toString());
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("disable")) {
					
					String name = e.getCommand().getArg(2);
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("disabling...");
					
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					if (l != null) {
						
//						baseConsole.disableListenerEntry(l, DisableActionEvent.USER_CLOSE);
						baseConsole.disableListenerContainer(l, DisableActionEvent.USER_CLOSE);
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("Done disabling: " + l.getClass().getName());
						
					}else {
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println(name + " isn't a valid listener");
						
					}
					
				}else if (e.getCommand().getArg(1).equalsIgnoreCase("listconsoles")) {
					
					String name = e.getCommand().getArg(2);
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					for (BaseConsole c : l.getLinkedBaseConsoles()) {
						if (baseConsole.hasGuiAttached()) {
							baseConsole.println(c.getClass().getName() + ": " + baseConsole.getId() + ": " + c.getGuiEntries().get(0).getGivenName());
						}else {
							baseConsole.print(c.getClass().getName() + ": " + baseConsole.getId());
						}
					}
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
//		stopDisable(e);
		ListenerContainer.stopDisable(this, e);
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
//		stopRemoval(e);
		ListenerContainer.stopRemoval(this, e);
	}
	
}
