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

import java.io.Serializable;

/**
 * The {@link ShutdownHook#run} method in this class is called when the Runtime closes if<br>
 * Runtime.getRuntime().addShutdownHook(new {@link ShutdownHook}());<br>
 * is called.
 *
 * @see Runtime#addShutdownHook
 */
public class ShutdownHook extends Thread 
		implements Serializable {
	
	private static final long serialVersionUID = 8309347169410881059L;
	
	private BaseConsole parent;
	
	/**
	 * Instantiates a new Shutdown hook.
	 *
	 * @param console the console
	 */
	public ShutdownHook(BaseConsole console) {
		this.parent = console;
	}
	
	@Override
	public void run() {
		
		parent.dispose();
//		TODO: Not sure, but I think this helps close sockets and stuff
		Runtime.getRuntime().halt(0);
		
	}
	
}
