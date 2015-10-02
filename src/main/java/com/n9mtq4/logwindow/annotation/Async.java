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

package com.n9mtq4.logwindow.annotation;

import com.n9mtq4.logwindow.events.AdditionEvent;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is added to methods in the ConsoleListener interface
 * ConsoleListener. Adding this annotation makes the method run in its own thread
 * 
 * @since v4.3
 * @author Will "n9Mtq4" Bresnahan
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Async {
	
	/**
	 * If the method should run in another thread.
	 * Can only be applied to {@link com.n9mtq4.logwindow.listener.AdditionListener#onAddition(AdditionEvent)},
	 * {@link com.n9mtq4.logwindow.listener.EnableListener#onEnable(EnableEvent)},
	 * {@link com.n9mtq4.logwindow.listener.ObjectListener#objectReceived(com.n9mtq4.logwindow.events.SentObjectEvent, com.n9mtq4.logwindow.BaseConsole)},
	 * {@link com.n9mtq4.logwindow.listener.DisableListener#onDisable(DisableEvent)}, and
	 * {@link com.n9mtq4.logwindow.listener.RemovalListener#onRemoval(RemovalEvent)}
	 * 
	 * @since v4.3
	 * @return A boolean. true if the method should be in its own thread, false if not.
	 * */
	boolean async() default true;
	
}
