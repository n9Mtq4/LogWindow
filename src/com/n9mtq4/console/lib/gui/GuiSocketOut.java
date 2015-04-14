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

import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.RemovalActionEvent;
import com.n9mtq4.console.lib.managers.SocketManager;
import com.n9mtq4.console.lib.utils.Colour;

import java.io.File;

/**
 * Created by will on 2/24/15.
 */

/**
 * A {@link com.n9mtq4.console.lib.gui.ConsoleGui} that takes any output and
 * Sends it over a socket for a client somewhere to recieve
 */
public class GuiSocketOut extends ConsoleGui {
	
	/**
	 * The ip to connect to
	 */
	private String ip = "localhost";
	/**
	 * The port to send stuff to
	 */
	private int port = 4444;
	/**
	 * The {@link com.n9mtq4.console.lib.managers.SocketManager} to handle
	 */
	public SocketManager socketManager = new SocketManager();
	/**
	 * A {@link com.n9mtq4.console.lib.ConsoleListener} to help this gui
	 */
	private GuiSocketOut.SocketInputSender helperListener;
	
	public void init() {
		
		setDefaultTextColour(Colour.BLACK);
//		A helper listener for this gui
		helperListener = new GuiSocketOut.SocketInputSender(this);
		getParent().addListener(helperListener);
		
	}
	
	/**
	 * Disposes the gui<p>
	 * closes the socket connections
	 */
	@Override
	public void dispose() {
		getParent().removeListener(helperListener, RemovalActionEvent.WINDOW_CLOSE);
		socketManager.close();
	}
	
	/**
	 * Get all output and send it to ip:port
	 */
	@Override
	public void print(String text, Colour colour) {
		
		socketManager.clientConnect(ip, port);
		socketManager.clientPrint(text);
		socketManager.clientDisconnect();
		
	}
	
	/**
	 * Get a printed image and send the file location to ip:port
	 */
	@Override
	public void printImage(File file) {
		print(file.getAbsolutePath(), getDefaultTextColour());
	}
	
	/**
	 * Called when the ip or port changes<br>
	 * Does nothing right now
	 */
	public void refresh() {

//		dummy - for possible use in the future
		
	}
	
	/**
	 * Gets the ip
	 *
	 * @return the ip
	 * @see com.n9mtq4.console.lib.gui.GuiSocketOut#setIp
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * Gets the port
	 *
	 * @return the port
	 * @see com.n9mtq4.console.lib.gui.GuiSocketOut#setPort
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Gets the SocketManager
	 *
	 * @return the SocketManager
	 * @see com.n9mtq4.console.lib.managers.SocketManager
	 */
	public SocketManager getSocketManager() {
		return socketManager;
	}
	
	/**
	 * Sets the ip
	 *
	 * @param ip the new ip to set
	 * @see com.n9mtq4.console.lib.gui.GuiSocketOut#getIp
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * Sets the port
	 *
	 * @param port the new port to set
	 * @see com.n9mtq4.console.lib.gui.GuiSocketOut#getPort
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * A helper class for {@link com.n9mtq4.console.lib.gui.GuiSocketOut}
	 */
	public static class SocketInputSender extends ConsoleListener {
		
		private GuiSocketOut parent;
		
		public SocketInputSender(GuiSocketOut parent) {
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ConsoleActionEvent e) {
			
			if (e.getCommand().getLength() == 3) {
				if (e.getCommand().getText().toLowerCase().startsWith("socketsender port")) {
					try {
						int port = Integer.parseInt(e.getCommand().getArg(2));
						parent.setPort(port);
						parent.refresh();
					}catch (NumberFormatException e1) {
						e.getBaseConsole().print("Error: ", Colour.RED);
						e.getBaseConsole().println(e1.getMessage());
					}
				}else if (e.getCommand().getText().toLowerCase().startsWith("socketsender ip")) {
					String ip = e.getCommand().getArg(2);
					parent.setIp(ip);
					parent.refresh();
				}
			}
			
			parent.print(e.getCommand().getText(), Colour.BLACK);
			
		}
		
		@Override
		public void onDisable(DisableActionEvent e) {
			stopDisable(e);
		}
		
		@Override
		public void onRemoval(RemovalActionEvent e) {
			stopRemoval(e);
		}
		
	}
	
}
