package com.n9mtq4.console.lib.gui;

import java.util.Random;

/**
 * Created by will on 8/13/15 at 2:58 AM.
 */
public class GuiEntry {
	
	private static final Random random = new Random();
	
	private ConsoleGui gui;
	private String givenName;
	
	public GuiEntry(ConsoleGui gui) {
		this.gui = gui;
		this.givenName = gui.getClass().getName() + ":" + String.valueOf(random.nextInt());
	}
	
	public ConsoleGui getGui() {
		return gui;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
}
