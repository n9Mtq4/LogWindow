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

package com.n9mtq4.logwindow.modules;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.ConsoleActionEvent;
import com.n9mtq4.logwindow.listener.StringListener;

/**
 * Support for typing "exit" and the {@link BaseConsole} being closed.
 * 
 * <p>Created by will on 3/2/15.</p>
 * 
 * @since v4.0
 * @author Will "n9Mtq4" Bresnahan
 */
public final class ModuleExit implements StringListener {
	
	@Override
	public final void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
		
		if (e.getCommand().eqt("exit")) {
			baseConsole.dispose();
		}
		
	}
	
}
