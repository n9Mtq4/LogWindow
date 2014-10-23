/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
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

package com.n9mtq4.console.lib.modules;

import com.n9mtq4.console.lib.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Will on 10/20/14.
 */
public class ModuleHistory extends ConsoleListener {
	
	@Override
	public void onEnable(EnableActionEvent e) {
		
	}
	
	@Override
	public void actionTab(TabActionEvent e) {
		
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		if (e.getCommand().contains("history")) {
			
			if (e.getCommand().getLength() == 1) {
				if (e.getCommand().getArg(0).equalsIgnoreCase("history")) {
					for (String s : e.getConsole().getHistory()) {
						e.getConsole().println(s, Color.MAGENTA);
					}
				}
			}else if (e.getCommand().getLength() == 2) {
				if (e.getCommand().getArg(1).equalsIgnoreCase("clear")) {
					e.getConsole().setHistory(new ArrayList<String>());
					e.getConsole().setHistoryIndex(0);
					e.getConsole().println("Cleared history", Color.CYAN);
				}
			}
			
		}
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
	}
	
}
