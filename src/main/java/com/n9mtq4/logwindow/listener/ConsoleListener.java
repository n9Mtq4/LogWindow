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

import java.io.Serializable;

/**
 * Interface for classes that wish to get everything.
 * This interface extends all other {@link ListenerAttribute}s.
 *
 * @see com.n9mtq4.logwindow.listener.AdditionListener
 * @see com.n9mtq4.logwindow.listener.EnableListener
 * @see com.n9mtq4.logwindow.listener.ObjectListener
 * @see com.n9mtq4.logwindow.listener.DisableListener
 * @see com.n9mtq4.logwindow.listener.RemovalListener
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public interface ConsoleListener extends AdditionListener, EnableListener, 
		ObjectListener, DisableListener, RemovalListener, Serializable {
	
}
