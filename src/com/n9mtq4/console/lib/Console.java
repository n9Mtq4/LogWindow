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

package com.n9mtq4.console.lib;

import com.n9mtq4.console.lib.modules.*;
import com.n9mtq4.console.lib.parts.NTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Will on 10/20/14.
 */
public class Console {
	
	private ArrayList<ConsoleListener> listeners;
	private JFrame frame;
	private NTextArea area;
	private JTextField field;
	private JScrollPane scrollArea;
	private ArrayList<String> history;
	private int historyIndex;
	
	public Console() {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		history = new ArrayList<String>();
		gui();
	}
	
	public Console(ConsoleListener listener) {
		listeners = new ArrayList<ConsoleListener>();
		initMandatoryListeners();
		addListener(listener);
		history = new ArrayList<String>();
		gui();
	}
	
	private void gui() {
		
		frame = new JFrame("Console");
		
		area = new NTextArea();
		area.setUserEditable(false);
		field = new JTextField();
		scrollArea = new JScrollPane(area);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		frame.add(scrollArea, BorderLayout.CENTER);
		frame.add(field, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setSize(360, 240);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		field.requestFocus();
		field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onFieldEnter(actionEvent);
			}
		});
		field.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
					if (historyIndex > 0) {
						historyIndex--;
						field.setText(history.get(historyIndex));
						field.setCaretPosition(field.getText().length());
					}
				}else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					if (historyIndex < history.size() - 1) {
						historyIndex++;
						field.setText(history.get(historyIndex));
						field.setCaretPosition(field.getText().length());
					}
				}else if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
					tab();
				}
			}
			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}
		});
		
	}
	
	public void initMandatoryListeners() {
		
		this.addListener(new ModuleListener());
		this.addListener(new ModuleJarLoader());
		
	}
	
	public void addDefaultListeners() {
		this.addListener(new ModuleInput());
		this.addListener(new ModuleHistory());
	}
	
	private void onFieldEnter(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		String text = source.getText();
		if (!text.trim().equals("")) {
			source.setText("");
			history.add(text);
			historyIndex = history.size();
			push(text);
		}
	}
	
	private void push(String text) {
		
		try {
			for (ConsoleListener p : listeners) {
				p.push(text);
			}
		}catch (ConcurrentModificationException e) {
			
		}
		
	}
	
	private void tab() {
		
		try {
			for (ConsoleListener p : listeners) {
				p.tab();
			}
		}catch (ConcurrentModificationException e) {
		}
		
	}
	
	public void removeListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l);
				break;
			}
			
		}
		
	}
	
	public void removeListenersByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equals(name)) {
				removeListener(l);
			}
			
		}
		
	}
	
	public void removeAllListeners() {
		
		for (ConsoleListener l : listeners) {
			removeListener(l);
		}
		
	}
	
	public void addListener(ConsoleListener listener) {
		
		if (!listeners.contains(listener) || !listener.getLinkedConsoles().contains(this)) {
			listeners.add(listener);
			listener.onEnable(new EnableActionEvent(this));
			listener.addToConsole(this);
		}
		
	}
	
	public void removeListener(ConsoleListener listener) {
		
		if (listeners.contains(listener) || listener.getLinkedConsoles().contains(this)) {
			listeners.remove(listener);
			listener.removeFromConsole(this);
			listener.onDisable(new DisableActionEvent(this));
		}
		
	}
	
	public void loadPlugins() {
		
		ModulePluginManager.loadPluginsToConsole(this);
		
	}
	
	public void println(String text) {
		print(text + "\n");
	}
	
	public void println(String text, Color color) {
		print(text + "\n", color);
	}
	
	public void print(String text) {
		area.append(text, Color.BLACK);
	}
	
	public void print(String text, Color color) {
		area.append(text, color);
	}
	
	public ConsoleListener getListenerByName(String name) {
		
		for (ConsoleListener l : listeners) {
			
			if (l.getClass().getName().equalsIgnoreCase(name)) {
				
				return l;
				
			}
			
		}
		
		return null;
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public NTextArea getArea() {
		return area;
	}
	
	public void setArea(NTextArea area) {
		this.area = area;
	}
	
	public JTextField getField() {
		return field;
	}
	
	public void setField(JTextField field) {
		this.field = field;
	}
	
	public JScrollPane getScrollArea() {
		return scrollArea;
	}
	
	public void setScrollArea(JScrollPane scrollArea) {
		this.scrollArea = scrollArea;
	}
	
	public ArrayList<String> getHistory() {
		return history;
	}
	
	public void setHistory(ArrayList<String> history) {
		this.history = history;
	}
	
	public int getHistoryIndex() {
		return historyIndex;
	}
	
	public void setHistoryIndex(int historyIndex) {
		this.historyIndex = historyIndex;
	}

	public ArrayList<ConsoleListener> getListeners() {
		return listeners;
	}
	
}
