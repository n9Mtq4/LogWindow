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

package com.n9mtq4.console.lib.listeners;

import com.n9mtq4.console.lib.BaseConsole;

/**
 * Created by Will on 12/30/14.
 */

/**
 * The {@link ShutdownHook#run} method in this class is called when the Runtime closes if<br/>
 * Runtime.getRuntime().addShutdownHook(new {@link ShutdownHook}());<br/>
 * is called.
 *
 * @see java.lang.Runtime
 * @see Runtime#addShutdownHook
 */
public class ShutdownHook extends Thread {
	
	private BaseConsole parent;
	
	public ShutdownHook(BaseConsole console) {
		this.parent = console;
	}
	
	@Override
	public void run() {
		
		parent.dispose();
		
	}
	
	public BaseConsole getParent() {
		return parent;
	}
	
	public void setParent(BaseConsole parent) {
		this.parent = parent;
	}
	
}
