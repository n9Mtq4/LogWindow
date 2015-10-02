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

package com.n9mtq4.logwindow.events;

import com.n9mtq4.logwindow.BaseConsole;

import java.io.Serializable;

/**
 * Event that is given to the
 * {@link com.n9mtq4.logwindow.listener.AdditionListener}.
 * 
 * <p>Created by Will on 10/23/14.</p>
 * 
 * @see com.n9mtq4.logwindow.listener.DisableListener
 * @since v0.2
 * @author Will "n9Mtq4" Bresnshan
 */
public class DisableEvent implements Serializable {
	
	private static final long serialVersionUID = 5554200220845774623L;
	
	/**
	 * The constant NOT_SPECIFIED.<br>
	 * The not specified reason for disabling
	 * 
	 * @since v1.1
	 */
	public static final int NOT_SPECIFIED = -1;
	/**
	 * The constant CONSOLE_DISPOSE.<br>
	 * The reason of the {@link BaseConsole} being disposed
	 * 
	 * @since v1.1
	 */
	public static final int CONSOLE_DISPOSE = 0;
	/**
	 * The constant CODE_CLOSE.<br>
	 * The reason of some code disabling the listener
	 * 
	 * @since v1.1
	 */
	public static final int CODE_CLOSE = 1;
	/**
	 * The constant USER_CLOSE.<br>
	 * The reason of the user disabling the listener
	 * 
	 * @see com.n9mtq4.logwindow.modules.ModuleListener
	 * 
	 * @since v1.1
	 */
	public static final int USER_CLOSE = 2;
	/**
	 * The constant OTHER_CLOSE.<br>
	 * Some other reason for disabling the listener
	 * 
	 * @since v4.0
	 */
	public static int OTHER_CLOSE = 10;
	
	private final BaseConsole baseConsole;
	private final int type;
	
	/**
	 * Instantiates a new Disable action event.
	 * 
	 * @since v0.2
	 * @param baseConsole The {@link BaseConsole} that sent this listener
	 */
	public DisableEvent(BaseConsole baseConsole) {
		this.baseConsole = baseConsole;
		this.type = NOT_SPECIFIED;
	}
	
	/**
	 * Instantiates a new Disable action event.
	 * 
	 * @see #getType()
	 * @since v1.1
	 * @param baseConsole The {@link BaseConsole}
	 * @param type The source of the disabling (type)
	 */
	public DisableEvent(BaseConsole baseConsole, int type) {
		
		this.baseConsole = baseConsole;
		this.type = type;
		
	}
	
	/**
	 * Gets the {@link BaseConsole}.
	 * 
	 * @since v0.2
	 * @return The {@link BaseConsole}
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Gets the source of the disabling (type).
	 * 
	 * @see #NOT_SPECIFIED
	 * @see #CONSOLE_DISPOSE
	 * @see #CODE_CLOSE
	 * @see #USER_CLOSE
	 * @see #OTHER_CLOSE
	 * @since v1.1
	 * @return The source of the disabling (type)
	 */
	public final int getType() {
		return type;
	}
	
}
