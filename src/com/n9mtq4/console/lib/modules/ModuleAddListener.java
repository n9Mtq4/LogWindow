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

import com.n9mtq4.console.lib.Console;
import com.n9mtq4.console.lib.ConsoleActionEvent;
import com.n9mtq4.console.lib.ConsoleListener;

import java.awt.*;

/**
 * Created by Will on 10/21/14.
 */
public class ModuleAddListener extends ConsoleListener {
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		if (e.getCommand().getArg(0).equalsIgnoreCase("addListener")) {
			
			if (e.getCommand().getLength() != 2) {
				e.getConsole().print("[ERROR]: ", Color.RED);
				e.getConsole().println("Argument error");
			}else {
				System.out.println(e.getCommand().getArg(1));
				try {
					final Class<?> clazz = Class.forName(e.getCommand().getArg(1));
					final Object clazz1 = clazz.newInstance();
					final Console e2 = e.getConsole();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							e2.print("[OUT]: ", Color.BLUE);
							e2.println("adding...");
							try {
								Thread.sleep(1000);
							}catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							e2.addConsoleListener((ConsoleListener) clazz1);
							e2.print("[OUT]: ", Color.BLUE);
							e2.println("Done adding: " + clazz1.getClass().getName());
						}
					}).start();
					
				}catch (Exception e1) {
					e.getConsole().print("[ERROR]: ", Color.RED);
					e.getConsole().println(e1.toString());
				}
				
			}
			
		}
		
	}
	
}
