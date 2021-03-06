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

package com.n9mtq4.logwindow.listener;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.AdditionEvent;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;

/**
 * Created by will on 9/15/15 at 9:21 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class TestListenerImplementation implements ConsoleListener {
	
	public boolean hasBeenAdded = false;
	public boolean hasBeenEnabled = false;
	public boolean hasBeenDisabled = false;
	public boolean hasBeenRemoved = false;
	public boolean hasReceivedObject = false;
	
	@Override
	public void onAddition(AdditionEvent e) {
		this.hasBeenAdded = true;
	}
	
	@Override
	public void onDisable(DisableEvent e) {
		this.hasBeenDisabled = true;
	}
	
	@Override
	public void onEnable(EnableEvent e) {
		this.hasBeenEnabled = true;
	}
	
	@Override
	public void objectReceived(ObjectEvent e, BaseConsole baseConsole) {
		this.hasReceivedObject = true;
	}
	
	@Override
	public void onRemoval(RemovalEvent e) {
		this.hasBeenRemoved = true;
	}
	
}
