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

package com.n9mtq4.console.lib.gui;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.utils.Colour;

import java.util.Random;

/**
 * Created by Will on 12/29/14.
 */
public class ConsoleGui {
	
	private BaseConsole parent;
	private String name;
	
	public ConsoleGui() {
		this.name = String.valueOf(new Random(10000).nextInt());
	}
	
	public void init() {
		
	}
	
	public void dispose() {
		
	}
	
	public void add(BaseConsole parent) {
		this.parent = parent;
		init();
	}
	
	/**
	 * Override me!
	 * */
	public void print(String text, Colour colour) {
		
	}
	
	/**
	 * Override me!
	 * */
	public void printImage(String filePath) {
		
	}
	
	public BaseConsole getParent() {
		return parent;
	}
	
	public void setParent(BaseConsole parent) {
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
