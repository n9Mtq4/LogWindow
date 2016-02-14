/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2016  Will (n9Mtq4) Bresnahan
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
import com.n9mtq4.logwindow.annotation.ListensFor;
import com.n9mtq4.logwindow.events.GenericEvent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by will on 2/13/16 at 12:18 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class AdvancedGenericListenerTest implements GenericListener, GenericEvent {
	
	@Test
	public void genericListenerTest() {
		BaseConsole baseConsole = new BaseConsole();
		AdvancedGenericListenerTest listenerTest = new AdvancedGenericListenerTest();
		baseConsole.addListenerAttribute(listenerTest);
		GenericEvent event = new GenericListenerTest();
		baseConsole.pushEvent(event);
//		System.out.println(Thread.currentThread().getName());
		assertTrue(listenerTest.got == event);
	}
	
	public AdvancedGenericListenerTest() {
		
	}
	
	private GenericListenerTest got;
	
	@ListensFor
	public void listenForMe(GenericEvent tester, BaseConsole baseConsole) {
		
		System.out.println("GOT!");
//		System.out.println(Thread.currentThread().getName());
		this.got = (GenericListenerTest) tester;
		
	}
	
	public String getMessage() {
		return "Hi";
	}
	
	@Override
	public BaseConsole getInitiatingBaseConsole() {
		return null;
	}
	
	@Override
	public boolean isCanceled() {
		return false;
	}
	
}
