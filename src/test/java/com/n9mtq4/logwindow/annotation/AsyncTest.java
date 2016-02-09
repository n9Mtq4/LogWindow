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

package com.n9mtq4.logwindow.annotation;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.AdditionEvent;
import com.n9mtq4.logwindow.events.DisableEvent;
import com.n9mtq4.logwindow.events.EnableEvent;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.events.RemovalEvent;
import com.n9mtq4.logwindow.listener.TestListenerImplementation;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link Async} annotation
 * WARNING: this test may fail on slower computers
 * to fix, just increase {@link #SLEEP_TIME}
 * 
 * Created by will on 10/24/15 at 12:09 PM.
 * 
 * @version v5.0
 * @since v5.0
 * @author Will "n9Mtq4" Bresnahan
 */
public class AsyncTest {
	
	private static final long SLEEP_TIME = 10l;
	
	@Test
	public void testNonAsync() {
		
		System.out.println("Running Non-Async Test...");
		
		BaseConsole baseConsole = new BaseConsole();
		
//		START non-async behavior
		NonAsyncListener nonAsyncListener = new NonAsyncListener();
		
		assertFalse(nonAsyncListener.hasBeenAdded);
		assertFalse(nonAsyncListener.hasBeenEnabled);
		baseConsole.addListenerAttribute(nonAsyncListener);
		assertTrue(nonAsyncListener.hasBeenAdded);
		assertTrue(nonAsyncListener.hasBeenEnabled);
		
		assertFalse(nonAsyncListener.hasReceivedObject);
		baseConsole.push("a string");
		assertTrue(nonAsyncListener.hasReceivedObject);
		
		assertFalse(nonAsyncListener.hasBeenDisabled);
		assertFalse(nonAsyncListener.hasBeenRemoved);
		baseConsole.removeListenerAttribute(nonAsyncListener);
		assertTrue(nonAsyncListener.hasBeenDisabled);
		assertTrue(nonAsyncListener.hasBeenRemoved);
//		END non-async behavior
		
		System.out.println("Done with Non-Async Test");
		baseConsole.dispose();
		
	}
	
	@Test
	public void testAsync() {
		
		System.out.println("Running Async Test...");
		
		BaseConsole baseConsole = new BaseConsole();
		
//		START async behavior
		AsyncListener asyncListener = new AsyncListener();
		
		assertFalse(asyncListener.hasBeenAdded);
		assertFalse(asyncListener.hasBeenEnabled);
		baseConsole.addListenerAttribute(asyncListener);
		assertFalse(asyncListener.hasBeenAdded);
		assertFalse(asyncListener.hasBeenEnabled);
		
		assertFalse(asyncListener.hasReceivedObject);
		baseConsole.push("a string");
		assertFalse(asyncListener.hasReceivedObject);
		
		assertFalse(asyncListener.hasBeenDisabled);
		assertFalse(asyncListener.hasBeenRemoved);
		baseConsole.removeListenerAttribute(asyncListener);
		assertFalse(asyncListener.hasBeenDisabled);
		assertFalse(asyncListener.hasBeenRemoved);
//		END async behavior
		
		System.out.println("Done with Async Test");
		baseConsole.dispose();
		
	}
	
	private class NonAsyncListener extends TestListenerImplementation {
		
		@Override
		public void onAddition(AdditionEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onAddition(e);
		}
		
		@Override
		public void onDisable(DisableEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onDisable(e);
		}
		
		@Override
		public void onEnable(EnableEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onEnable(e);
		}
		
		@Override
		public void objectReceived(ObjectEvent e, BaseConsole baseConsole) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.objectReceived(e, baseConsole);
		}
		
		@Override
		public void onRemoval(RemovalEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onRemoval(e);
		}
		
	}
	
	private class AsyncListener extends TestListenerImplementation {
		
		@Async
		@Override
		public void onAddition(AdditionEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onAddition(e);
		}
		
		@Async
		@Override
		public void onDisable(DisableEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onDisable(e);
		}
		
		@Async
		@Override
		public void onEnable(EnableEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onEnable(e);
		}
		
		@Async
		@Override
		public void objectReceived(ObjectEvent e, BaseConsole baseConsole) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.objectReceived(e, baseConsole);
		}
		
		@Async
		@Override
		public void onRemoval(RemovalEvent e) {
			try {
				Thread.sleep(SLEEP_TIME);
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.onRemoval(e);
		}
		
	}
	
}
