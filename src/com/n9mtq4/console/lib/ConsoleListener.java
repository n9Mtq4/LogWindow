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

package com.n9mtq4.console.lib;

import com.n9mtq4.console.lib.command.ConsoleCommand;
import com.n9mtq4.console.lib.events.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */
public abstract class ConsoleListener {
	
	private ArrayList<BaseConsole> linkedBaseConsoles;
	private boolean enabled;
	private boolean ignoreDone;
	
	public ConsoleListener() {
		
		linkedBaseConsoles = new ArrayList<BaseConsole>();
		this.enabled = true;
		this.ignoreDone = false;
		
	}
	
	public void onAddition(AdditionActionEvent e) {
	}
	public void onEnable(EnableActionEvent e) {
	}
	public abstract void actionPerformed(ConsoleActionEvent e);
	public void onDisable(DisableActionEvent e) {
	}
	public void onRemoval(RemovalActionEvent e) {
	}
	
	@Deprecated
	public void push(String text) {
		
		ConsoleCommand command = new ConsoleCommand(text);
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				this.actionPerformed(new ConsoleActionEvent(c, command));
			}
		}catch (ConcurrentModificationException e) {
		}
		
	}
	
	public void push(ConsoleActionEvent e) {
		
		try {
			for (BaseConsole c : linkedBaseConsoles) {
				this.actionPerformed(e);
			}
		}catch (ConcurrentModificationException e1) {
		}
		
	}
	
	public void addToConsole(BaseConsole baseConsole) {
		
		if (!linkedBaseConsoles.contains(baseConsole) || !baseConsole.getListeners().contains(this)) {
			linkedBaseConsoles.add(baseConsole);
			baseConsole.addListener(this);
		}
		
	}
	
	public void removeFromConsole(BaseConsole baseConsole) {
		
		if (linkedBaseConsoles.contains(baseConsole) || baseConsole.getListeners().contains(this)) {
			linkedBaseConsoles.remove(baseConsole);
			baseConsole.removeListener(this);
		}
		
	}
	
	public void toggleEnabled() {
		
		this.enabled = !this.enabled;
		
	}
	
	public ArrayList<BaseConsole> getLinkedBaseConsoles() {
		return linkedBaseConsoles;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean hasIgnoreDone() {
		return ignoreDone;
	}
	
}
