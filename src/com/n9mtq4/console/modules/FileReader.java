/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014-2015  Will (n9Mtq4) Bresnahan
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

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.utils.Colour;

import java.io.BufferedReader;
import java.io.File;

/**
 * Created by Will on 10/27/14.
 */
public class FileReader extends ConsoleListener {
	
	@Override
	public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
		
		if (!e.getCommand().startsWith("file ")) return;
		if (e.getCommand().getLength() == 3) {
			
			if (e.getCommand().getArg(0).equalsIgnoreCase("file")) {
				
				if (e.getCommand().getArg(1).equalsIgnoreCase("read")) {
					
					String filePath = e.getCommand().getWordsStartingFrom(2);
					File f = new File(filePath);
					
					try {
						BufferedReader br = new BufferedReader(new java.io.FileReader(f));
						StringBuilder sb = new StringBuilder();
						String line = br.readLine();
						while (line != null) {
							sb.append(line);
							sb.append("\n");
							line = br.readLine();
						}
						String everything = sb.toString();
						br.close();
						baseConsole.println(everything);
					}catch (Exception e1) {
						baseConsole.print("[ERROR]: ", Colour.RED);
						baseConsole.println(e1.toString());
					}
					
				}
				
			}
			
		}
		
	}
	
}
