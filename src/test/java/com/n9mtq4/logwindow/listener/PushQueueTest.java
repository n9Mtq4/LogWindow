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
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.utils.StringParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by will on 9/19/15 at 10:05 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 * @since v5.0
 */
public class PushQueueTest {
	
	@Test
	public void testPushQueue() {
		
		A a = new A();
		B b = new B();
		
		BaseConsole baseConsole = new BaseConsole();
		baseConsole.addListenerAttribute(a);
		baseConsole.addListenerAttribute(b);
		
		baseConsole.pushString("a");
		
		assertEquals("ab", a.out);
		assertEquals("ab", b.out);
		
	}
	
	public static class A implements ObjectListener {
		public String out = "";
		@Override
		public void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
			if (!objectEvent.isUserInputString()) return;
			StringParser stringParser = new StringParser(objectEvent);
			out += stringParser.getText();
			if (stringParser.eqt("a")) baseConsole.pushString("b");
		}
	}
	
	public static class B implements ObjectListener {
		public String out = "";
		@Override
		public void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
			if (!objectEvent.isUserInputString()) return;
			StringParser stringParser = new StringParser(objectEvent);
			out += stringParser.getText();
		}
	}
	
}
