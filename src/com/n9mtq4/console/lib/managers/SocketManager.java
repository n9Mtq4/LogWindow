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

package com.n9mtq4.console.lib.managers;

import com.n9mtq4.console.lib.BaseConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Will on 11/17/14.
 */
public class SocketManager {
	
	private Socket s;
	private ServerSocket serverSocket;
	
	public SocketManager() {
		
	}
	
	public void close() {
		try {
			clientDisconnect();
			serverSocket.close();
		}catch (Exception e) {
			
		}
	}
	
	private static Socket socketConnect(String ip, int port) {
		
		try {
			Socket socket = new Socket();
			socket.setSoTimeout(2000);
			socket.connect((SocketAddress) new InetSocketAddress(ip, port));
			return socket;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static boolean socketPrint(Socket socket, String string) {
		
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.print(string);
			pw.flush();
			pw.close();
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void clientConnect(String ip, int port) {
		this.s = socketConnect(ip, port);
	}
	
	public void clientDisconnect() {
		try {
			s.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean clientPrint(String x) {
		return socketPrint(this.s, x);
	}
	
	public void startServerListenerToConsole(BaseConsole c, final boolean sendToPlugins) {
		final BaseConsole c1 = c;
		final SocketManager thiz = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						final Socket socket = thiz.serverSocket.accept();
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
									String inputLine;
									while ((inputLine = in.readLine()) != null) {
										if (sendToPlugins) {
											c1.sendPluginsString(inputLine);
										}else {
											c1.println(inputLine);
										}
									}
									Thread.sleep(2);
									socket.close();
								}catch (Exception e3) {
									e3.printStackTrace();
								}
							}
						}).start();
					}catch (Exception e) {
						e.printStackTrace();
						c1.printStackTrace(e);
						return;
					}
				}
				
			}
		}, "ServerSocketListener").start();
	}
	
	public void startServer(int port) {
		
		final int port1 = port;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(port1);
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}, "SeverSocket").start();
		
	}
	
}
