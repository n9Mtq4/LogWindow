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

package com.n9mtq4.logwindow.ui.components;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

/**
 * A custom JTextPane / JTextArea that supports colors
 * and images.
 * 
 * <p>Created by Will on 10/20/14.</p>
 * 
 * @since v0.1
 * @version v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
@SuppressWarnings("unused")
public class NTextArea extends JTextPane 
		implements Serializable {
	
	private static final long serialVersionUID = 7396962834170203316L;
	
	/**
	 * Can the user edit the text?
	 */
	private boolean userEditable;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see JTextPane#JTextPane()
	 * 
	 */
	public NTextArea() {
		super();
		this.userEditable = false;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see JTextPane#JTextPane(StyledDocument)
	 * @param styledDocument the styled document
	 */
	public NTextArea(StyledDocument styledDocument) {
		super(styledDocument);
		this.userEditable = false;
	}
	
	/**
	 * Appends a string onto the NTextFrame.
	 *
	 * @param msg the String to append
	 * @param c   the color that the string will be in
	 */
	public void append(String msg, Color c) {
		if (c == null) c = Color.BLACK;
		boolean edit = super.isEditable();
		super.setEditable(true);
//		http://stackoverflow.com/a/9652143/5196460
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		int len = this.getDocument().getLength();
		this.setCaretPosition(len);
		this.setCharacterAttributes(aset, false);
		this.replaceSelection(msg);
		super.setEditable(edit);
		
	}
	
	/**
	 * Sets the text of the {@link NTextArea}<br>
	 * 
	 * @since v0.1
	 * @param string The new String to set the text to
	 */
	@Override
	public void setText(String string) {
		boolean edit = super.isEditable();
		super.setEditable(true);
		super.setText(string);
		super.setEditable(edit);
	}
	
	/**
	 * Adds an image to the component.
	 * 
	 * @since v0.1
	 * @param image the picture to append.
	 */
	public void appendPicture(Image image) {
		
		int len = this.getDocument().getLength();
		this.setCaretPosition(len);
		this.insertIcon(new ImageIcon(image));
		
	}
	
	/**
	 * Can the user edit the text?
	 *
	 * @see NTextArea#setUserEditable
	 * @since v0.1
	 * @return true if the user edit it; false otherwise
	 */
	public final boolean isUserEditable() {
		return userEditable;
	}
	
	/**
	 * Setter method for userEditable.
	 *
	 * @see NTextArea#isUserEditable
	 * @since v0.1
	 * @param userEditable true or false of can the user edit it
	 */
	public final void setUserEditable(boolean userEditable) {
		this.userEditable = userEditable;
		if (!userEditable) {
			setEditable(false);
		}
	}
	
}
