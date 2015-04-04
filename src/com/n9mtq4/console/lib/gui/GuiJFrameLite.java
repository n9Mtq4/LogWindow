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

import com.n9mtq4.console.lib.gui.attributes.HasFrame;
import com.n9mtq4.console.lib.gui.attributes.History;
import com.n9mtq4.console.lib.gui.attributes.Textable;
import com.n9mtq4.console.lib.utils.Colour;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by Will on 12/29/14.
 */
public class GuiJFrameLite extends ConsoleGui implements Textable, History, HasFrame {
	
	private JFrame frame;
	private JPanel noWrapPanel;
	private JTextArea area;
	private JTextField field;
	private JScrollPane scrollArea;
	private int historyIndex;
	
	@Override
	public void init() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultTextColour(Colour.BLACK);
		this.historyIndex = getParent().getHistory().size();
		frame = new JFrame("Console Lite");
		
		area = new JTextArea();
		area.setEditable(false);
		DefaultCaret caret = (DefaultCaret) area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		noWrapPanel = new JPanel(new BorderLayout());
		noWrapPanel.add(area);
		scrollArea = new JScrollPane(noWrapPanel);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		field = new JTextField();
		frame.add(scrollArea, BorderLayout.CENTER);
		frame.add(field, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setSize(360, 240);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(360, 240);
		frame.setLocationRelativeTo(null);
		
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
						field.setText(getParent().getHistory().get(historyIndex));
						field.setCaretPosition(field.getText().length());
					}
				}else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					if (historyIndex < getParent().getHistory().size() - 1) {
						historyIndex++;
						field.setText(getParent().getHistory().get(historyIndex));
						field.setCaretPosition(field.getText().length());
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}
		});
		
	}
	
	public void onFieldEnter(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		String text = source.getText();
		historyIndex = getParent().getHistory().size();
		if (!text.trim().equals("")) {
			source.setText("");
			getParent().sendPluginsString(text);
		}
	}
	
	@Override
	public void print(String text, Colour colour) {
		area.append(text);
	}
	
	@Override
	public void printImage(File file) {
	}
	
	@Override
	public void dispose() {
		this.frame.dispose();
	}
	
	@Override
	public String getText() {
		return area.getText();
	}
	
	@Override
	public void setText(String text) {
		area.setText(text);
	}
	
	@Override
	public void historyUpdate() {
		this.historyIndex = getParent().getHistory().size();
	}
	
	public JPanel getNoWrapPanel() {
		return noWrapPanel;
	}
	
	public void setNoWrapPanel(JPanel noWrapPanel) {
		this.noWrapPanel = noWrapPanel;
	}
	
	public JTextArea getArea() {
		return area;
	}
	
	public void setArea(JTextArea area) {
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
	
	@Override
	public JFrame getJFrame() {
		return this.frame;
	}
}
