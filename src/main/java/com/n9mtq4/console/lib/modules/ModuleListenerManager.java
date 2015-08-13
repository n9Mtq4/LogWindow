package com.n9mtq4.console.lib.modules;

import com.n9mtq4.console.lib.BaseConsole;
import com.n9mtq4.console.lib.ConsoleListener;
import com.n9mtq4.console.lib.events.AdditionActionEvent;
import com.n9mtq4.console.lib.events.ConsoleActionEvent;
import com.n9mtq4.console.lib.events.DisableActionEvent;
import com.n9mtq4.console.lib.events.RemovalActionEvent;
import com.n9mtq4.console.lib.utils.Colour;

import java.util.ArrayList;

/**
 * Created by will on 6/21/15 at 8:31 PM.<br>
 * A module to add, remove, enable, and disable listeners on the console.
 * TODO: WIP
 */
public class ModuleListenerManager extends ConsoleListener {
	
	@Override
	public void onAddition(AdditionActionEvent e) {
		BaseConsole b = e.getBaseConsole();
		b.addListener(new ListenerAdd());
		b.addListener(new ListenerOther());
//		conflicts with older ModuleListener, so (forcible) remove it
		b.removeListener(b.getListener("com.n9mtq4.console.lib.modules.ModuleListener"), RemovalActionEvent.WINDOW_CLOSE);
	}
	
	@Override
	public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
	}
	
	@Override
	public void onDisable(DisableActionEvent e) {
		stopDisable(e);
	}
	
	@Override
	public void onRemoval(RemovalActionEvent e) {
		stopRemoval(e);
	}
	
	private static class ListenerAdd extends ConsoleListener {
		
		@Override
		public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
			if (!e.getCommand().getArg(0).equalsIgnoreCase("listener")) return;
			
			if (e.getCommand().getLength() == 3 && e.getCommand().getArg(1).equalsIgnoreCase("add")) {
				try {
					
					ConsoleListener listener = ConsoleListener.getNewListenerByName(e.getCommand().getArg(2));
					if (listener != null) {
						baseConsole.addListener(listener);
						baseConsole.println("Added " + listener.getClass().getName(), Colour.BLUE);
					}
					
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		@Override
		public void onDisable(DisableActionEvent e) {
			stopDisable(e);
		}
		
		@Override
		public void onRemoval(RemovalActionEvent e) {
			stopRemoval(e);
		}
		
	}
	
	private static class ListenerOther extends ConsoleListener {
		
		@Override
		public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
			if (!e.getCommand().getArg(0).equalsIgnoreCase("listener")) return;
			if (e.getCommand().getLength() != 2) return;
			
			if (e.getCommand().getArg(1).equalsIgnoreCase("list")) {
				ArrayList<ConsoleListener> listeners = baseConsole.getListeners();
				for (int i = 0; i < listeners.size(); i++) {
					baseConsole.print("[" + i + "]: ");
					baseConsole.println(listeners.get(i).getClass().getName(), listeners.get(i).isEnabled() ? Colour.GREEN : Colour.RED);
				}
				return;
			}
			
			if (e.getCommand().getArg(1).equalsIgnoreCase("listallconsoles")) {
				for (BaseConsole c : BaseConsole.globalList) {
//					baseConsole.println(c.getClass().getName() + ": " + baseConsole.getId() + ": " + c.getGuiEntries().get(0).getName());
					baseConsole.println(c.getClass().getName() + ": " + baseConsole.getId() + ": " + c.getGuiEntries().get(0).getGivenName());
				}
			}
			
		}
		
		@Override
		public void onDisable(DisableActionEvent e) {
			stopDisable(e);
		}
		
		@Override
		public void onRemoval(RemovalActionEvent e) {
			stopRemoval(e);
		}
		
	}
	
}
