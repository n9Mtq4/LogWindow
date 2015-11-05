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
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.JarLoader;
import com.n9mtq4.logwindow.utils.StringParser;

import java.io.File;
import java.io.IOException;

/**
 * A module to load jar files into the class path from a console.
 * 
 * <p>Created by Will on 10/24/14.</p>
 * 
 * @since v0.2
 * @author Will "n9Mtq4" Bresnahan
 */
public final class ModuleJarLoader implements ObjectListener {
	
	@Override
	public void objectReceived(ObjectEvent objectEvent, BaseConsole baseConsole) {
		
		if (!objectEvent.isUserInputString()) return;
		StringParser stringParser = new StringParser(objectEvent);
		
		if (stringParser.trim().toLowerCase().startsWith("jarloader ") && stringParser.getLength() >= 2) {
			
			String filePath = stringParser.getWordsStartingFrom(1);
			File jarFile = new File(filePath);
			if (!jarFile.exists()) {
				baseConsole.println("[ERROR]: " + jarFile.getPath() + " doesn't exist", Colour.RED);
				return;
			}
			baseConsole.println("Adding jar file: " + jarFile, Colour.BLUE);
			try {
				JarLoader.addFile(jarFile);
			}catch (IOException e1) {
				baseConsole.println("[ERROR]: " + e1.toString(), Colour.RED);
			}
			baseConsole.println("done loading " + filePath, Colour.BLUE);
			
		}
		
	}
}
