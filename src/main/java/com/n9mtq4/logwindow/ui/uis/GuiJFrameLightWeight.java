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

package com.n9mtq4.logwindow.ui.uis;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.modules.ModuleExit;
import com.n9mtq4.logwindow.modules.ModuleHistory;
import com.n9mtq4.logwindow.modules.ModuleJarLoader;
import com.n9mtq4.logwindow.modules.ModuleListener;
import com.n9mtq4.logwindow.ui.SimpleConsoleUI;
import com.n9mtq4.logwindow.ui.attributes.HasFrame;
import com.n9mtq4.logwindow.ui.attributes.History;
import com.n9mtq4.logwindow.ui.attributes.Textable;
import com.n9mtq4.logwindow.utils.Colour;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Will on 12/29/14.
 * 
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public final class GuiJFrameLightWeight extends SimpleConsoleUI implements Textable, History, HasFrame {
	
	private JFrame frame;
	private JPanel noWrapPanel;
	private JTextArea area;
	private JTextField field;
	private JScrollPane scrollArea;
	private int historyIndex;
	
	public GuiJFrameLightWeight(BaseConsole parent) {
		super(parent);
	}
	
	@Override
	public final void init() {
		
		getParent().addListenerAttribute(new ModuleListener());
		getParent().addListenerAttribute(new ModuleJarLoader());
		getParent().addListenerAttribute(new ModuleExit());
		getParent().addListenerAttribute(new ModuleHistory());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultTextColour(Colour.BLACK);
		this.historyIndex = getParent().getHistory().size();
		frame = new JFrame("Console.min: " + getParent().getId());
		
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
	
	/**
	 * On field enter.
	 *
	 * @param e the e
	 */
	public final void onFieldEnter(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		String text = source.getText();
		historyIndex = getParent().getHistory().size();
		if (!text.trim().equals("")) {
			source.setText("");
			getParent().sendPluginsString(text);
		}
	}
	
	@Override
	public final void printObject(Object object, Colour colour) {
		DefaultCaret caret = (DefaultCaret) area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		area.append(objectToString(object));
	}
	
	@Override
	public final void dispose() {
		this.frame.dispose();
	}
	
	@Override
	public final String getText() {
		return area.getText();
	}
	
	@Override
	public final void setText(String text) {
		area.setText(text);
	}
	
	@Override
	public final void historyUpdate() {
		this.historyIndex = getParent().getHistory().size();
	}
	
	/**
	 * Gets no wrap panel.
	 *
	 * @return the no wrap panel
	 */
	public final JPanel getNoWrapPanel() {
		return noWrapPanel;
	}
	
	/**
	 * Sets no wrap panel.
	 *
	 * @param noWrapPanel the no wrap panel
	 */
	public final void setNoWrapPanel(JPanel noWrapPanel) {
		this.noWrapPanel = noWrapPanel;
	}
	
	/**
	 * Gets area.
	 *
	 * @return the area
	 */
	public final JTextArea getArea() {
		return area;
	}
	
	/**
	 * Sets area.
	 *
	 * @param area the area
	 */
	public final void setArea(JTextArea area) {
		this.area = area;
	}
	
	/**
	 * Gets field.
	 *
	 * @return the field
	 */
	public final JTextField getField() {
		return field;
	}
	
	/**
	 * Sets field.
	 *
	 * @param field the field
	 */
	public final void setField(JTextField field) {
		this.field = field;
	}
	
	/**
	 * Gets scroll area.
	 *
	 * @return the scroll area
	 */
	public final JScrollPane getScrollArea() {
		return scrollArea;
	}
	
	/**
	 * Sets scroll area.
	 *
	 * @param scrollArea the scroll area
	 */
	public final void setScrollArea(JScrollPane scrollArea) {
		this.scrollArea = scrollArea;
	}
	
	@Override
	public final JFrame getJFrame() {
		return this.frame;
	}
	
}
