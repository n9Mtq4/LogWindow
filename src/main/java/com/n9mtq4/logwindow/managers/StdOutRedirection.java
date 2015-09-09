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

package com.n9mtq4.logwindow.managers;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.AdditionActionEvent;
import com.n9mtq4.logwindow.events.DisableActionEvent;
import com.n9mtq4.logwindow.events.EnableActionEvent;
import com.n9mtq4.logwindow.listener.AdditionListener;
import com.n9mtq4.logwindow.listener.DisableListener;
import com.n9mtq4.logwindow.listener.EnableListener;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Created by Will on 10/29/14.<br>
 * A class that redirected System.out.prints and prints them to a
 * {@link BaseConsole}
 */
public final class StdOutRedirection extends PrintStream 
		implements AdditionListener, EnableListener, DisableListener, Serializable {
	
	private static final long serialVersionUID = 9180321456734075207L;
	public static final boolean SHOW_LOCATION_DEFAULT_VALUE = false;
	
	/**
	 * Adds a new instance of StdOutRedirect to the given base console.
	 * Enables the show location if {@link StdOutRedirection#SHOW_LOCATION_DEFAULT_VALUE}
	 * is true. If you want to override the default value use
	 * {@link StdOutRedirection#addToBaseConsole(BaseConsole, boolean)}
	 * 
	 * @param baseConsole The BaseConsole to add the redirection to
	 * @see StdOutRedirection#addToBaseConsole(BaseConsole, boolean)
	 * */
	public static void addToBaseConsole(BaseConsole baseConsole) {
		addToBaseConsole(baseConsole, SHOW_LOCATION_DEFAULT_VALUE);
	}
	
	/**
	 * Adds a new instance of StdOutRedirect to the given base console.
	 * Enables the show location if the showLocation param
	 * is true. If you want to use the default value use
	 * {@link StdOutRedirection#addToBaseConsole(BaseConsole)}
	 *
	 * @param baseConsole The BaseConsole to add the redirection to
	 * @param showLocation Should the redirection show the line of code the call came from?
	 * @see StdOutRedirection#addToBaseConsole(BaseConsole)
	 * */
	public static void addToBaseConsole(BaseConsole baseConsole, boolean showLocation) {
		baseConsole.addListenerAttribute(new StdOutRedirection(showLocation));
	}
	
	private BaseConsole baseConsole;
	private PrintStream backup;
	private boolean showLocation;
	private boolean on;
	
	/**
	 * Makes a new StdOutRedirect instance
	 * */
	private StdOutRedirection() {
		super(System.out);
		this.showLocation = SHOW_LOCATION_DEFAULT_VALUE;
	}
	
	/**
	 * Makes a new StdOutRedirect instance with show location
	 * 
	 * @param showLocation Should it show the location?
	 * */
	private StdOutRedirection(boolean showLocation) {
		super(System.out);
		this.showLocation = showLocation;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public final void onAddition(AdditionActionEvent additionActionEvent) {
		this.baseConsole = additionActionEvent.getBaseConsole();
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public final void onEnable(EnableActionEvent enableActionEvent) {
		turnOn(showLocation);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public final void onDisable(DisableActionEvent disableActionEvent) {
		turnOff();
	}
	
	/**
	 * Turns on the console redirection
	 *
	 * @see StdOutRedirection#turnOn(boolean)
	 */
	public final void turnOn() {
		turnOn(false);
	}
	
	/**
	 * Turns on the console redirection
	 *
	 * @param showLocation show where the print was called from?
	 */
	public final void turnOn(boolean showLocation) {
		
		this.on = true;
		this.showLocation = showLocation;
		this.backup = System.out;
		System.setOut(this);
		
	}
	
	/**
	 * Disables console redirection
	 */
	public final void turnOff() {
		
		this.on = false;
		System.setOut(backup);
		
	}
	
//	START Overriding methods from PrintStream START
	@Override
	public final void print(String s) {
		if (baseConsole == null) backup.print(s);
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
	public final void print(Object o) {
		if (baseConsole == null) backup.print(o);
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
	public final void println(Object x) {
		if (baseConsole == null) backup.print(x);
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
	public final void println(String x) {
		if (baseConsole == null) backup.print(x);
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
	 * Gets the location of where the print was called from
	 * 
	 * @return the line number and file from where it was called 4 stack traces ago.
	 */
	private final String getLocation() {
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		return MessageFormat.format("({0}:{1, number,#}): ", element.getFileName(), element.getLineNumber());
	}
	
	/**
	 * Getter for the {@link BaseConsole}
	 *
	 * @return the base console
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Getter for the default System.out
	 *
	 * @return the backup
	 */
	public final PrintStream getBackup() {
		return backup;
	}
	
	/**
	 * Is the redirection showing the location?
	 *
	 * @return the boolean
	 */
	public final boolean isShowLocation() {
		return showLocation;
	}
	
	/**
	 * Is the redirection on?
	 *
	 * @return the boolean
	 */
	public final boolean isOn() {
		return on;
	}
	
}
