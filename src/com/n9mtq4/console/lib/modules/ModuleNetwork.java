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

import com.n9mtq4.console.lib.command.ConsoleCommand;
import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.*;
import com.n9mtq4.console.lib.managers.SocketManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Will on 11/16/14.
 */
public class ModuleNetwork extends ConsoleListener {
	
	private SocketManager manager;
	
	@Override
	public void onAddition(AdditionActionEvent e) {
		manager = new SocketManager();
	}
	
	@Override
	public void actionPreformed(ConsoleActionEvent e) {
		
		ConsoleCommand c = e.getCommand();
		if (c.getArg(0).equalsIgnoreCase("http")) {
			
			if (c.getLength() == 3) {
				if (c.getArg(1).equalsIgnoreCase("get")) {
					String url = c.getWordsStartingFrom(2);
					String result = httpGet(url);
					e.getBaseConsole().println(result);
				}else if (c.getArg(1).equalsIgnoreCase("post")) {
//					TODO: http post request
				}
			}
			
		}else if (c.getArg(0).equalsIgnoreCase("socket")) {
			
			if (c.getLength() == 3) {
				if (c.getArg(1).equalsIgnoreCase("connect")) {
					
					String ipport = c.getArg(2);
					if (ipport.contains(":")) {
						String ip;
						String portString;
						ip = ipport.substring(0, ipport.indexOf(":"));
						portString = ipport.substring(ipport.indexOf(":") + 1);
						try {
							int port = Integer.parseInt(portString);
							manager.clientConnect(ip, port);
						}catch (NumberFormatException e1) {
							e.getBaseConsole().println(e1.toString());
						}
					}
					
				}else if (c.getArg(1).equalsIgnoreCase("server")) {
					
					String portString = c.getArg(2);
					try {
						
						e.getBaseConsole().println("starting server on port " + portString);
						int port = Integer.parseInt(portString);
						e.getBaseConsole().println("Awaiting client connection to socket");
						manager.startServer(port);
						manager.startServerListenerToConsole(e.getBaseConsole());
						
					}catch (NumberFormatException e1) {
						e.getBaseConsole().println("port must be an int");
						e.getBaseConsole().println(e1.toString());
					}
				}
			}else if (c.getLength() >= 3) {
				if (c.getArg(1).equalsIgnoreCase("print")) {
					String msg = c.getWordsStartingFrom(2);
					manager.clientPrint(msg);
				}
			}
			
		}
		
	}
	
	public static String httpGet(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line + "\n";
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
