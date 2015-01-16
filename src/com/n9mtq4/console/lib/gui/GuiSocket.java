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

package com.n9mtq4.console.lib.gui;

import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;
import java.net.ServerSocket;

/**
 * Created by Will on 12/29/14.
 */
public class GuiSocket extends ConsoleGui {

//	TODO: socket server that parent.sendPluginsString with data it receives\n then send back output
	
	private Thread serverT;
	private ServerSocket serverSocket;
	
	@Override
	public void init() {

//		open server & wait for connections
		
	}
	
	private void onDataRecieve(String data) {
		getParent().sendPluginsString(data);
	}
	
	@Override
	public void dispose() {

//		close connections
		
	}
	
	@Override
	public void print(String text, Colour colour) {
//		TODO: send back string to clients
	}
	
	@Override
	public void printImage(File file) {
//		TODO: image support (make a client?)
//		No image support (YET)
	}
}
