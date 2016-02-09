package com.n9mtq4.logwindow.listener;

import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.annotation.ListensFor;
import com.n9mtq4.logwindow.events.GenericEvent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by will on 2/8/16 at 7:11 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class GenericListenerTest implements GenericListener, GenericEvent {
	
	@Test
	public void genericListenerTest() {
		BaseConsole baseConsole = new BaseConsole();
		GenericListenerTest listenerTest = new GenericListenerTest();
		baseConsole.addListenerAttribute(listenerTest);
		GenericEvent event = new GenericListenerTest();
		baseConsole.pushEvent(event);
		assertTrue(listenerTest.got == event);
	}
	
	public GenericListenerTest() {
		
	}
	
	private GenericListenerTest got;
	
	@ListensFor(GenericListenerTest.class)
	public void listenForMe(GenericListenerTest tester, BaseConsole baseConsole) {
		
		System.out.println("GOT!");
		this.got = tester;
		
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
