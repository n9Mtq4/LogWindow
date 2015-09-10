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

import com.n9mtq4.logwindow.events.DisableActionEvent;

import java.io.Serializable;

/**
 * Created by will on 8/13/15 at 2:24 PM.
 */
public interface DisableListener extends Serializable, ListenerAttribute {
	
	/**
	 * This method is called when the listener is disabled.
	 * When the listener is disabled, it wont receive objects or strings.
	 *
	 * @see com.n9mtq4.logwindow.BaseConsole#disableListenerAttribute(ListenerAttribute)
	 * @see com.n9mtq4.logwindow.BaseConsole#disableListenerAttribute(ListenerAttribute, int)
	 * @see com.n9mtq4.logwindow.BaseConsole#disableListenerContainer(ListenerContainer)
	 * @see com.n9mtq4.logwindow.BaseConsole#disableListenerContainer(ListenerContainer, int)
	 * @since v5.0
	 * @param disableActionEvent The disable action event
	 * */
	void onDisable(DisableActionEvent disableActionEvent);
	
}
