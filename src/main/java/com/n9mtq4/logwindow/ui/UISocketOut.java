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

package com.n9mtq4.logwindow.ui;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;
import com.n9mtq4.logwindow.events.SentObjectEvent;
import com.n9mtq4.logwindow.listener.DisableListener;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.listener.RemovalListener;
import com.n9mtq4.logwindow.managers.SocketManager;
import com.n9mtq4.logwindow.utils.Colour;
import com.n9mtq4.logwindow.utils.StringParser;

/**
 * A {@link ConsoleUI} that takes any output and
 * Sends it over a socket for a client somewhere to receive.
 * 
 * <p>Created by will on 2/24/15.</p>
 * 
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public class UISocketOut extends SimpleConsoleUI {
	
	/**
	 * The ip to connect to
	 */
	private String ip = "localhost";
	/**
	 * The port to send stuff to
	 */
	private int port = 4444;
	/**
	 * The {@link SocketManager} to handle
	 */
	public SocketManager socketManager = new SocketManager();
	/**
	 * A {@link ListenerAttribute} to help this gui
	 */
	private UISocketOut.SocketInputSender helperListener;
	
	public UISocketOut(BaseConsole parent) {
		super(parent);
	}
	
	public void init() {
		
		setDefaultTextColour(Colour.BLACK);
//		A helper listener for this gui
		helperListener = new UISocketOut.SocketInputSender(this);
		getParent().addListenerAttribute(helperListener);
		
	}
	
	/**
	 * Disposes the gui<p>
	 * closes the socket connections
	 */
	@Override
	public void dispose() {
		getParent().removeListenerAttribute(helperListener, RemovalEvent.CONSOLE_DISPOSE);
		socketManager.close();
	}
	
	/**
	 * Get all output and send it to ip:port
	 */
	@Override
	public void printObject(Object object, Colour colour) {
		
		socketManager.clientConnect(ip, port);
		socketManager.clientPrint(objectToString(object));
		socketManager.clientDisconnect();
		
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
	 * @see UISocketOut#setIp
	 */
	public final String getIp() {
		return ip;
	}
	
	/**
	 * Gets the port
	 *
	 * @return the port
	 * @see UISocketOut#setPort
	 */
	public final int getPort() {
		return port;
	}
	
	/**
	 * Gets the SocketManager
	 *
	 * @return the SocketManager
	 */
	public final SocketManager getSocketManager() {
		return socketManager;
	}
	
	/**
	 * Sets the ip
	 *
	 * @param ip the new ip to set
	 * @see UISocketOut#getIp
	 */
	public final void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * Sets the port
	 *
	 * @param port the new port to set
	 * @see UISocketOut#getPort
	 */
	public final void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * A helper class for {@link UISocketOut}
	 */
	public static final class SocketInputSender implements ObjectListener, DisableListener, RemovalListener {
		
		private final UISocketOut parent;
		
		/**
		 * Instantiates a new Socket input sender.
		 *
		 * @param parent the parent
		 */
		public SocketInputSender(UISocketOut parent) {
			this.parent = parent;
		}
		
		@Override
		public final void objectReceived(final SentObjectEvent e, final BaseConsole baseConsole) {
			
			if (!e.isUserInputString()) return;
			StringParser stringParser = new StringParser(e);
			
			if (stringParser.getLength() == 3) {
				if (stringParser.getText().toLowerCase().startsWith("socketsender port")) {
					try {
						int port = Integer.parseInt(stringParser.getArg(2));
						parent.setPort(port);
						parent.refresh();
					}catch (NumberFormatException e1) {
						baseConsole.print("Error: ", Colour.RED);
						baseConsole.println(e1.getMessage());
					}
				}else if (stringParser.getText().toLowerCase().startsWith("socketsender ip")) {
					String ip = stringParser.getArg(2);
					parent.setIp(ip);
					parent.refresh();
				}
			}
			
			parent.print(stringParser.getText(), Colour.BLACK);
			
		}
		
		@Override
		public void onDisable(DisableEvent e) {
//			stopDisable(e);
			ListenerContainer.stopDisable(this, e);
		}
		
		@Override
		public void onRemoval(RemovalEvent e) {
//			stopRemoval(e);
			ListenerContainer.stopRemoval(this, e);
		}
		
	}
	
}
