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
import com.n9mtq4.logwindow.utils.Colour;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by will on 9/24/15 at 5:39 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class UiTest {
	
	private static final String PRINT = "Hello World";
	
	@Test
	public void testPrint() {
		
		BaseConsole baseConsole = new BaseConsole();
		TestConsoleGui ui = new TestConsoleGui(baseConsole);
		
		assertEquals(false, ui.init);
		baseConsole.addConsoleUi(ui);
		assertEquals(true, ui.init);
		
		assertEquals("", ui.print);
		baseConsole.print(PRINT);
		assertEquals(PRINT, ui.print);
		
		assertEquals(false, ui.dispose);
		baseConsole.dispose();
		assertEquals(true, ui.dispose);
		
	}
	
	public static class TestConsoleGui extends SimpleConsoleUI {
		
		public boolean init = false;
		public boolean dispose = false;
		public String print = "";
		
		public TestConsoleGui(BaseConsole parent) {
			super(parent);
		}
		
		@Override
		public void init() {
			this.init = true;
		}
		
		@Override
		public void dispose() {
			this.dispose = true;
		}
		
		@Override
		public void printObject(Object object, Colour colour) {
			print += String.valueOf(object);
		}
		
	}
	
}
