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

package com.n9mtq4.console.lib.events;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.command.TabCommand;

/**
 * Created by Will on 10/23/14.
 */
public class TabActionEvent {
	
	private BaseConsole baseConsole;
	private TabCommand tabCommand;
	private String currentText;
	
	public TabActionEvent(BaseConsole baseConsole, TabCommand tabCommand) {
		this.baseConsole = baseConsole;
		this.tabCommand = tabCommand;
		if (this.baseConsole.hasGuiAttached()) {
			this.currentText = baseConsole.getGui().getField().getText();
		}
		
	}
	
	public BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	public TabCommand getTabCommand() {
		return tabCommand;
	}
	
	public String getCurrentText() {
		return currentText;
	}
	
}
