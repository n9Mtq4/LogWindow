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
 * Created by Will on 10/23/14.
 */
public class DisableActionEvent implements Serializable {
	
	private static final long serialVersionUID = 5554200220845774623L;
	
	/**
	 * The constant NOT_SPECIFIED.
	 */
	public static final int NOT_SPECIFIED = -1;
	/**
	 * The constant CONSOLE_DISPOSE.
	 */
	public static final int CONSOLE_DISPOSE = 0;
	/**
	 * The constant CODE_CLOSE.
	 */
	public static final int CODE_CLOSE = 1;
	/**
	 * The constant USER_CLOSE.
	 */
	public static final int USER_CLOSE = 2;
	/**
	 * The constant OTHER_CLOSE.
	 */
	public static int OTHER_CLOSE = 10;
	
	private final BaseConsole baseConsole;
	private final int type;
	
	/**
	 * Instantiates a new Disable action event.
	 *
	 * @param baseConsole the base console
	 */
	public DisableActionEvent(BaseConsole baseConsole) {
		this.baseConsole = baseConsole;
		this.type = NOT_SPECIFIED;
	}
	
	/**
	 * Instantiates a new Disable action event.
	 *
	 * @param baseConsole the base console
	 * @param type        the type
	 */
	public DisableActionEvent(BaseConsole baseConsole, int type) {
		
		this.baseConsole = baseConsole;
		this.type = type;
		
	}
	
	/**
	 * Gets base console.
	 *
	 * @return the base console
	 */
	public final BaseConsole getBaseConsole() {
		return baseConsole;
	}
	
	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public final int getType() {
		return type;
	}
	
}
