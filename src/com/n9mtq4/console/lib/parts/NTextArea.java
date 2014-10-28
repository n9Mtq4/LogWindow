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

package com.n9mtq4.console.lib.parts;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Created by Will on 10/20/14.
 */
public class NTextArea extends JTextPane {
	
	private boolean userEditable;
	private int maxLength;
	
	public NTextArea() {
		this.userEditable = false;
	}
	
	public NTextArea(StyledDocument styledDocument) {
		super(styledDocument);
		this.userEditable = false;
		this.maxLength = 3000;
	}
	
	public void append(String msg, Color c) {
		boolean edit = super.isEditable();
		super.setEditable(true);
//		thanks nIcE cOw @ stackoverflow
//		https://stackoverflow.com/questions/9650992/how-to-change-text-color-in-the-jtextarea
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		int len = this.getDocument().getLength();
		this.setCaretPosition(len);
		this.setCharacterAttributes(aset, false);
		this.replaceSelection(msg);
		super.setEditable(edit);
		
		if (len > maxLength) {
			this.setText(this.getText().substring(maxLength / 2));
		}
		
	}
	
	public boolean isUserEditable() {
		return userEditable;
	}
	
	public void setUserEditable(boolean userEditable) {
		this.userEditable = userEditable;
		if (!userEditable) {
			setEditable(false);
		}
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
}
