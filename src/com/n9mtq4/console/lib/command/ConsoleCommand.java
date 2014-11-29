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

package com.n9mtq4.console.lib.command;

import com.sun.istack.internal.NotNull;

/**
 * Created by Will on 10/20/14.
 */
public class ConsoleCommand {
	
	private String text;
	private String[] tokens;
	
	public ConsoleCommand(String text) {
		this.text = text;
		init();
	}
	
	private void init() {
		
		tokens = text.split(" ");
		
	}
	
	public String getArg(int  i) {
		
		return tokens[i];
		
	}
	
	public String trim() {
		
		return text.trim();
		
	}
	
	public boolean startsWith(String s) {
		
		return text.startsWith(s);
		
	}
	
	public String getWordsStartingFrom(int startIndex) {
		String before = getText();
		String cache = before;
		for (int word = 0; word < startIndex; word++) {
			try {
				int iofs = cache.indexOf(" ");
				String sub = cache.substring(iofs + 1);
				cache = sub;
			}catch (StringIndexOutOfBoundsException e) {
				return null;
			}
		}
		return cache;
	}
	
	public boolean contains(@NotNull CharSequence c) {
		return text.contains(c);
	}
	
	public String getText() {
		return text;
	}
	
	public int getLength() {
		
		return tokens.length;
		
	}
	
}
