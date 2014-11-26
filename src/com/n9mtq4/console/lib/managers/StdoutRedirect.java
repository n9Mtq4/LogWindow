package com.n9mtq4.console.lib.managers;

import com.n9mtq4.console.lib.BaseConsole;

import java.io.PrintStream;
import java.text.MessageFormat;

/**
 * Created by Will on 10/29/14.
 */
public class StdoutRedirect extends PrintStream {
	
	private BaseConsole c;
	private PrintStream backup;
	private boolean showLocation;
	private boolean on;
	
	public StdoutRedirect(BaseConsole c) {
		super(System.out);
		this.c = c;
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
				c.print(getLocation() + s);
			}else {
				c.print(s);
			}
		}else {
			if (showLocation) {
				backup.print(getLocation() + s);
			}else {
				backup.print(s);
			}
		}
	}
	
	@Override
	public void print(Object o) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				c.print(getLocation() + o.toString());
			}else {
				c.print(o.toString());
			}
		}else {
			if (showLocation) {
				backup.print(getLocation() + o.toString());
			}else {
				backup.print(o.toString());
			}
		}
	}
	
	@Override
	public void println(Object x) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				c.println(getLocation() + x.toString());
			}else {
				c.println(x.toString());
			}
		}else {
			if (showLocation) {
				backup.print(getLocation() + x.toString());
			}else {
				backup.print(x.toString());
			}
		}
	}
	
	@Override
	public void println(String x) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		if (!element.getMethodName().contains("print")) {
			if (showLocation) {
				c.println(getLocation() + x);
			}else {
				c.println(x);
			}
		}else {
			if (showLocation) {
				backup.print(getLocation() + x);
			}else {
				backup.print(x);
			}
		}
	}
	
	private String getLocation() {
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		return MessageFormat.format("({0}:{1, number,#}): ", element.getFileName(), element.getLineNumber());
	}
	
	public BaseConsole getC() {
		return c;
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
