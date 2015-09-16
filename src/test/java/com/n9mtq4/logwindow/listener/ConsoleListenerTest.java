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

package com.n9mtq4.logwindow.listener;

import com.n9mtq4.logwindow.BaseConsole;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests to see if the correct methods are called
 * when adding or disabling listeners occur.
 * 
 * Created by will on 9/15/15 at 9:05 PM.
 * 
 * @author Will "n9Mtq4" Bresnahan
 */
public class ConsoleListenerTest {
	
	private BaseConsole baseConsole = new BaseConsole();
	private TestListenerImplementation add = new TestListenerImplementation();
	private TestListenerImplementation enable = new TestListenerImplementation();
	private TestListenerImplementation disable = new TestListenerImplementation();
	private TestListenerImplementation remove = new TestListenerImplementation();
	
	@Test
	public void testOnAddition() {
		assertEquals(add.hasBeenAdded, false);
		baseConsole.addListenerAttribute(add);
		assertEquals(add.hasBeenAdded, true);
	}
	
	@Test
	public void testOnEnable() {
		assertEquals(enable.hasBeenAdded, false);
		baseConsole.addListenerAttributeRaw(enable);
		assertEquals(enable.hasBeenAdded, true);
		
		assertEquals(enable.hasBeenEnabled, false);
		baseConsole.enableListenerAttribute(enable);
		assertEquals(enable.hasBeenEnabled, true);
	}
	
	@Test
	public void testOnDisable() {
		assertEquals(disable.hasBeenAdded, false);
		baseConsole.addListenerAttributeRaw(disable);
		assertEquals(disable.hasBeenAdded, true);
		
		baseConsole.addListenerAttribute(disable);
		assertEquals(disable.hasBeenDisabled, false);
		baseConsole.disableListenerAttribute(disable);
		assertEquals(disable.hasBeenDisabled, true);
	}
	
	@Test
	public void testOnRemoval() {
		assertEquals(remove.hasBeenAdded, false);
		baseConsole.addListenerAttributeRaw(remove);
		assertEquals(remove.hasBeenAdded, true);
		
		assertEquals(remove.hasBeenRemoved, false);
		assertEquals(remove.hasBeenDisabled, false);
		baseConsole.removeListenerAttribute(remove);
		assertEquals(remove.hasBeenRemoved, true);
		assertEquals(remove.hasBeenDisabled, true);
	}
	
}
