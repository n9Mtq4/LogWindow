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

package com.n9mtq4.logwindow.listener;

import com.n9mtq4.logwindow.events.EnableActionEvent;

import java.io.Serializable;

/**
 * Interface for classes that wish to get notified when the listener is enabled.
 * 
 * @see #onEnable(EnableActionEvent)
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public interface EnableListener extends Serializable, ListenerAttribute {
	
	/**
	 * This method is called when the Listener is enabled.
	 * When loading from a plugin all the plugins will be loaded
	 * before this is called.
	 * 
	 * @see com.n9mtq4.logwindow.BaseConsole#enableListenerAttribute(ListenerAttribute)
	 * @see com.n9mtq4.logwindow.BaseConsole#enableListenerContainer(ListenerContainer)
	 * @since v5.0
	 * @param enableActionEvent The enable action event
	 * */
	void onEnable(EnableActionEvent enableActionEvent);
	
}
