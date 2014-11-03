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

package com.n9mtq4.console.modules;

import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.*;

import java.io.*;

/**
 * Created by Will on 11/3/14.
 */
public class Log extends ConsoleListener {
	
	private String filePath = "plugins/log.txt";
	private File file;
	
	@Override
	public void onAddition(AdditionActionEvent e) {
		
		file = new File(filePath);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					System.out.println("Couldn't create file");
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onEnable(EnableActionEvent e) {
		
	}
	
	@Override
	public void actionTab(TabActionEvent e) {
		
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		append(e.getCommand().getText());
		
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
		
	}
	
	public void append(String s) {
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			out.println(s);
			out.close();
		} catch (IOException e) {
		}
		
	}
	
}
