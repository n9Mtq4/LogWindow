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
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.LWReflectionHelper;
import com.n9mtq4.logwindow.utils.StringParser;

import java.util.ArrayList;

/**
 * A module to add, remove, enable, and disable listeners on the console.
 * 
 * <p>Created by Will on 10/21/14.</p>
 * 
 * @since v0.1
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan 
 */
public class ModuleListener implements ObjectListener {
	
	@Override
	public final void objectReceived(final ObjectEvent e, final BaseConsole baseConsole) {
		
		if (!e.isUserInputString()) return;
		StringParser stringParser = new StringParser(e);
		
//		TODO: this is a mess and unorganized, clean it up
		if (stringParser.getArg(0).equalsIgnoreCase("listener")) {
			
			if (stringParser.getLength() == 2) {
				
				if (stringParser.getArg(1).equalsIgnoreCase("list")) {
					int i = 0;
					for (ListenerContainer l : baseConsole.getListenerContainers()) {
						baseConsole.print("[" + i + "]: ");
						baseConsole.println(l.getAttribute().getClass().getName(), l.isEnabled() ? Colour.GREEN : Colour.RED);
						i++;
					}
				}else if (stringParser.getArg(1).equalsIgnoreCase("removeduplicates")) {
					
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
//						baseConsole.removeListenerByName(s, RemovalEvent.USER_CLOSE);
						baseConsole.removeListenerContainer(baseConsole.getContainerFromId(s), RemovalEvent.USER_CLOSE);
					}
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("done removing duplicate listeners");
					
				}else if (stringParser.getArg(1).equalsIgnoreCase("disableduplicates")) {
					
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
//						baseConsole.disableListenerByName(s, DisableEvent.USER_CLOSE);
						baseConsole.disableListenerContainer(baseConsole.getContainerFromId(s), DisableEvent.USER_CLOSE);
					}
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("done disabling duplicate listeners");
					
				}
				
			}else if (stringParser.getLength() == 3) {
				
				if (stringParser.getArg(1).equalsIgnoreCase("add")) {
					
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("adding...");
					
					try {
						Class t = LWReflectionHelper.getClass(stringParser.getArg(2));
						ListenerAttribute l1 = (ListenerAttribute) (t.newInstance());
//					baseConsole.addListener(l1);
						baseConsole.addListenerAttribute(l1);
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("done adding: " + l1.getClass().getName());
					}catch (InstantiationException e1) {
						e1.printStackTrace();
					}catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					
				}else if (stringParser.getArg(1).equalsIgnoreCase("remove")) {
					
					String name = stringParser.getArg(2);
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("removing...");
					
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					if (l != null) {
						
//						baseConsole.removeListenerEntry(l, RemovalEvent.USER_CLOSE);
						baseConsole.removeListenerContainer(l, RemovalEvent.USER_CLOSE);
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("Done removing: " + l.getClass().getName());
						
					}else {
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println(name + " isn't a valid listener");
						
					}
					
				}else if (stringParser.getArg(1).equalsIgnoreCase("enable")) {
					
					try {
						
						String name = stringParser.getArg(2);
						
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
					
				}else if (stringParser.getArg(1).equalsIgnoreCase("disable")) {
					
					String name = stringParser.getArg(2);
					
					baseConsole.print("[OUT]: ", Colour.BLUE);
					baseConsole.println("disabling...");
					
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					if (l != null) {
						
//						baseConsole.disableListenerEntry(l, DisableEvent.USER_CLOSE);
						baseConsole.disableListenerContainer(l, DisableEvent.USER_CLOSE);
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println("Done disabling: " + l.getClass().getName());
						
					}else {
						
						baseConsole.print("[OUT]: ", Colour.BLUE);
						baseConsole.println(name + " isn't a valid listener");
						
					}
					
				}else if (stringParser.getArg(1).equalsIgnoreCase("listconsoles")) {
					
					String name = stringParser.getArg(2);
//					ListenerContainer l = baseConsole.getListenerEntry(name);
					ListenerContainer l = baseConsole.getContainerFromId(name);
					for (BaseConsole c : l.getLinkedBaseConsoles()) {
						if (baseConsole.hasGuiAttached()) {
							baseConsole.println(c.getClass().getName() + ": " + baseConsole.getId() + ": " + c.getUIContainers().get(0).getGivenName());
						}else {
							baseConsole.print(c.getClass().getName() + ": " + baseConsole.getId());
						}
					}
					
				}
				
			}
			
		}
		
	}
	
}
