/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2016  Will (n9Mtq4) Bresnahan
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

/**
 * Created by will on 2/13/16 at 12:42 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class DefaultGenericEvent implements GenericEvent {
	
	private BaseConsole initiatingBaseConsole;
	private boolean canceled = false;
	
	public DefaultGenericEvent(BaseConsole initiatingBaseConsole) {
		this.initiatingBaseConsole = initiatingBaseConsole;
	}
	
	@Override
	public BaseConsole getInitiatingBaseConsole() {
		return initiatingBaseConsole;
	}
	
	@Override
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
}
