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

package com.n9mtq4.console.lib.command;

import com.sun.istack.internal.NotNull;

/**
 * Created by Will on 10/20/14.
 */
@SuppressWarnings("unused")
public class ConsoleCommand {
	
	/**
	 * Stores the raw text that has been inputed.
	 */
	private String text;
	private String[] tokens;
	
	public ConsoleCommand(String text) {
		this.text = text;
		init();
	}
	
	/**
	 * Does pre-string calculations
	 */
	private void init() {
		
		tokens = text.split(" ");
		
	}
	
	/**
	 * The text of the command is split into words.
	 * getArg returns the word at the given index.
	 * <p/>
	 * "Hello Java World".getArg(1) returns "Java"<br/>
	 * "Hello Java World".getArg(4) throws {@link StringIndexOutOfBoundsException}
	 *
	 * @return The word at the given index.
	 * @throws java.lang.StringIndexOutOfBoundsException when the index is more than the number of words - 1 (first word is 0)
	 */
	public String getArg(int i) {
		
		return tokens[i];
		
	}
	
	/**
	 * Trims off the whitespace at the end of the text of the command.<br/>
	 *
	 * @return the trimmed string.
	 * @see String#trim
	 */
	public String trim() {
		
		return text.trim();
		
	}
	
	/**
	 * Does the same thing as {@link String}.trim().equalsIgnoreCase()<br/>
	 *
	 * @param s The comparing {@link String}
	 * @return The boolean of {@link String}.trim().equalsIgnoreCase(s)
	 * @see String#trim
	 * @see String#equalsIgnoreCase
	 */
	public boolean eqt(String s) {
		return text.trim().equalsIgnoreCase(s.toLowerCase());
	}
	
	/**
	 * Returns if the text of the command starts with the given {@link String}<br/>
	 * Same as getText().startsWith(s)
	 *
	 * @param s The comparing {@link String}
	 * @return boolean of {@link String}.startsWith(s);
	 * @see String#startsWith
	 */
	public boolean startsWith(String s) {
		
		return text.startsWith(s);
		
	}
	
	/**
	 * Gets a word starting from word index.<p/>
	 * "Hello Java World".getWordsStartingFrom(0) returns "Hello Java World"<br/>
	 * "Hello Java World".getWordsStartingFrom(1) returns "Java World"<br/>
	 * "Hello Java World".getWordsStartingFrom(4) returns null<br/>
	 *
	 * @param startIndex Index of the word to get the string there-on.
	 * @return The cropped {@link String}.
	 */
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
	
	/**
	 * Gets the string before a pattern.<br/>
	 * "Hello Java World".getBeforePattern("Java") returns "Hello "<br/>
	 *
	 * @param pattern The pattern to search for.
	 */
	public String getBeforePattern(String pattern) {
		int index = text.indexOf(pattern);
		if (index == -1) {
			return "";
		}
		try {
			return text.substring(0, index);
		}catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * Gets the string after a pattern.<br/>
	 * "Hello Java World".getBeforePattern("Java") returns " World"<br/>
	 *
	 * @param pattern The pattern to search for.
	 */
	public String getAfterPattern(String pattern) {
		int index = text.indexOf(pattern);
		if (index == -1) {
			return "";
		}
		try {
			return text.substring(index, text.length());
		}catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * Runs a contains on the text with ignoring the case.
	 *
	 * @param s The string to compare
	 * @return if the text inputed has the String given
	 * @see ConsoleCommand#contains
	 * @see String#contains
	 */
	public boolean containsIgnoreCase(@NotNull CharSequence s) {
		return text.toLowerCase().contains(String.valueOf(s).toLowerCase());
	}
	
	/**
	 * Runs a contains on the text.
	 *
	 * @param s The string to compare
	 * @return if the text inputed has the String given
	 * @see String#contains
	 */
	public boolean contains(@NotNull CharSequence s) {
		return text.contains(s);
	}
	
	/**
	 * Gives you the raw text that the command contains.<br/>
	 *
	 * @return the raw text.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gives you the number of words that the user inputed.
	 *
	 * @return tokens.length
	 */
	public int getLength() {
		
		return tokens.length;
		
	}
	
	/**
	 * Gives you the character count of the {@link String} the user inputed.
	 *
	 * @see String#length
	 */
	public int getTextLength() {
		return text.length();
	}
	
}
