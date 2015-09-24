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
import com.n9mtq4.logwindow.events.AdditionActionEvent;
import com.n9mtq4.logwindow.events.DisableActionEvent;
import com.n9mtq4.logwindow.events.EnableActionEvent;
import com.n9mtq4.logwindow.events.RemovalActionEvent;
import com.n9mtq4.logwindow.events.SentObjectEvent;

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
	
	@Override
	public void onAddition(AdditionActionEvent e) {
		this.hasBeenAdded = true;
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		this.hasBeenDisabled = true;
	}
	
	@Override
	public void onEnable(EnableActionEvent e) {
		this.hasBeenEnabled = true;
	}
	
	@Override
	public void objectReceived(SentObjectEvent e, BaseConsole baseConsole) {
		
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
		this.hasBeenRemoved = true;
	}
	
}
