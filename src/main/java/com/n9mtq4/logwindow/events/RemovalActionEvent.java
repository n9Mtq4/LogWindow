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
 * Created by Will on 11/2/14.
 */
/**
 * The event that is given to the
 * {@link com.n9mtq4.logwindow.listener.RemovalListener}.
 * 
 * <p>Created by Will on 11/2/14.</p>
 * 
 * @see DisableActionEvent
 * @see com.n9mtq4.logwindow.listener.RemovalListener
 * @since v1.3
 * @author Will "n9Mtq4" Bresnahan
 */
public final class RemovalActionEvent extends DisableActionEvent
		implements Serializable {
	
	private static final long serialVersionUID = 7928707744295602780L;
	
	/**
	 * Instantiates a new Removal action event.
	 * 
	 * @param baseConsole The {@link BaseConsole} that sent this listener
	 */
	public RemovalActionEvent(BaseConsole baseConsole) {
		super(baseConsole);
	}
	
	/**
	 * Instantiates a new Removal action event.
	 *
	 * @see #getType()
	 * @since v1.1
	 * @param baseConsole The {@link BaseConsole}
	 * @param type The source of the disabling (type)
	 */
	public RemovalActionEvent(BaseConsole baseConsole, int type) {
		super(baseConsole, type);
	}
	
}
