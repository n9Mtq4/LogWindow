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

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Will on 10/20/14.
 */
public class ConsoleParser {
	
	private ArrayList<Console> linkedConsoles;
	
	public ConsoleParser() {
		linkedConsoles = new ArrayList<Console>();
		initModules();
	}
	
	public void process(Console c, String text) {
		
		if (text.equals("hi")) {
			c.println("hi", Color.GREEN);
		}
		
	}
	
	public void push(String text) {
		
		for (Console c : linkedConsoles) {
			this.process(c, text);
		}
		
	}
	
	private void initModules() {
		
	}
	
	public void linkConsole(Console console) {
		
		if (!linkedConsoles.contains(console) || !console.getLinkedParsers().contains(this)) {
			linkedConsoles.add(console);
			console.linkParser(this);
		}
		
	}
	
	public void unlinkConsole(Console console) {
		
		if (linkedConsoles.contains(console) || console.getLinkedParsers().contains(this)) {
			linkedConsoles.remove(console);
			console.unlinkParser(this);
		}
		
	}

	public ArrayList<Console> getLinkedConsoles() {
		return linkedConsoles;
	}
}
