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

package com.n9mtq4.console.lib.managers;

import com.n9mtq4.console.lib.BaseConsole;

import java.io.PrintStream;
import java.text.MessageFormat;

/**
 * Created by Will on 10/29/14.
 */
public class StdoutRedirect extends PrintStream {
	
	private BaseConsole baseConsole;
	private PrintStream backup;
	private boolean showLocation;
	private boolean on;
	
	public StdoutRedirect(BaseConsole baseConsole) {
		super(System.out);
		this.baseConsole = baseConsole;
	}
	
	public void turnOn(boolean showLocation) {
		
		this.on = true;
		this.showLocation = showLocation;
		this.backup = System.out;
		System.setOut(this);
		
	}
	
	public void turnOff() {
		
		this.on = false;
		System.setOut(backup);
		
	}
	
	@Override
	public void print(String s) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				baseConsole.print(getLocation() + s);
			}else {
				baseConsole.print(s);
			}
		}else {
			backup.print(s);
		}
	}
	
	@Override
	public void print(Object o) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				baseConsole.print(getLocation() + o.toString());
			}else {
				baseConsole.print(o.toString());
			}
		}else {
			backup.print(o);
		}
	}
	
	@Override
	public void println(Object x) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				baseConsole.println(getLocation() + x.toString());
			}else {
				baseConsole.println(x.toString());
			}
		}else {
			backup.println(x);
		}
	}
	
	@Override
	public void println(String x) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				baseConsole.println(getLocation() + x);
			}else {
				baseConsole.println(x);
			}
		}else {
			backup.println(x);
		}
	}
	
	private String getLocation() {
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		return MessageFormat.format("({0}:{1, number,#}): ", element.getFileName(), element.getLineNumber());
	}
	
	public BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	public PrintStream getBackup() {
		return backup;
	}
	
	public boolean isShowLocation() {
		return showLocation;
	}
	
	public boolean isOn() {
		return on;
	}
	
}
