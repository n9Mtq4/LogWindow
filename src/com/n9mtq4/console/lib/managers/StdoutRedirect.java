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
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Created by Will on 10/29/14.<br>
 * A class that redirected System.out.prints and prints them to a
 * {@link BaseConsole}
 */
public class StdoutRedirect extends PrintStream 
		implements Serializable {
	
	private static final long serialVersionUID = 9180321456734075207L;
	
	private BaseConsole baseConsole;
	private PrintStream backup;
	private boolean showLocation;
	private boolean on;
	
	/**
	 * Instantiates a new Stdout redirect.
	 *
	 * @param baseConsole the base console
	 */
	public StdoutRedirect(BaseConsole baseConsole) {
		super(System.out);
		this.baseConsole = baseConsole;
	}
	
	/**
	 * Turns on the console redirection
	 *
	 * @see StdoutRedirect#turnOn(boolean)
	 */
	public void turnOn() {
		turnOn(false);
	}
	
	/**
	 * Turns on the console redirection
	 *
	 * @param showLocation show where the print was called from?
	 */
	public void turnOn(boolean showLocation) {
		
		this.on = true;
		this.showLocation = showLocation;
		this.backup = System.out;
		System.setOut(this);
		
	}
	
	/**
	 * Disables console redirection
	 */
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
	
	//	START Overriding methods from PrintStream START
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
//	END Overriding methods from PrintStream END
	
	/**
	 * Gets the location of where the print was called f rom
	 */
	private String getLocation() {
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		return MessageFormat.format("({0}:{1, number,#}): ", element.getFileName(), element.getLineNumber());
	}
	
	/**
	 * Getter for the {@link BaseConsole}
	 *
	 * @return the base console
	 */
	public BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Getter for the default System.out
	 *
	 * @return the backup
	 */
	public PrintStream getBackup() {
		return backup;
	}
	
	/**
	 * Is the redirection showing the location?
	 *
	 * @return the boolean
	 */
	public boolean isShowLocation() {
		return showLocation;
	}
	
	/**
	 * Is the redirection on?
	 *
	 * @return the boolean
	 */
	public boolean isOn() {
		return on;
	}
	
}
