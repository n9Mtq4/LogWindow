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

package com.n9mtq4.logwindow;

import com.n9mtq4.logwindow.dispose.ShutdownHook;
import com.n9mtq4.logwindow.events.AdditionActionEvent;
import com.n9mtq4.logwindow.events.DisableActionEvent;
import com.n9mtq4.logwindow.events.EnableActionEvent;
import com.n9mtq4.logwindow.events.RemovalActionEvent;
import com.n9mtq4.logwindow.events.SentObjectEvent;
import com.n9mtq4.logwindow.listener.ListenerAttribute;
import com.n9mtq4.logwindow.listener.ListenerContainer;
import com.n9mtq4.logwindow.managers.PluginManager;
import com.n9mtq4.logwindow.modules.ModuleJarLoader;
import com.n9mtq4.logwindow.modules.ModuleListener;
import com.n9mtq4.logwindow.ui.ConsoleUI;
import com.n9mtq4.logwindow.ui.UIContainer;
import com.n9mtq4.logwindow.utils.Colour;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * The class that handles all User to Listener interactions.
 * 
 * Created by Will on 10/20/14.
 * 
 * @since v0.1
 * @author Will "n9Mtq4" Bresnahan
 * @version v5.0
 */
@SuppressWarnings("unused")
public final class BaseConsole implements Serializable {
	
	private static final long serialVersionUID = 992050290203752760L;
	
	/**
	 * This is the message that is sent with a string that is sent
	 * from text.
	 * 
	 * @see #sendPluginsString(String)
	 * @see #pushString(String)
	 * */
	public static final String STRING_OBJECT_MESSAGE = "text";
	
	/**
	 * Keeps the ids of all {@link BaseConsole}s.
	 * TODO: remove deprecated field?
	 * 
	 * @deprecated Not used, and not necessary, since I discovered {@link Runtime#addShutdownHook(Thread)}
	 */
	@Deprecated
	private static final ArrayList<BaseConsole> globalList = new ArrayList<BaseConsole>();
	
	/**
	 * The array containing all the listeners attached to the {@link BaseConsole}.
	 */
	private final ArrayList<ListenerContainer> listenerContainers;
	/**
	 * Keeps a record of the input.
	 * TODO: remove deprecated field?
	 * 
	 * @deprecated Not a necessary feature, may be removed before v5 release
	 */
	@Deprecated
	private final ArrayList<String> history;
	/**
	 * Has the local id for the {@link BaseConsole}.
	 */
	private final int id;
	/**
	 * Contains all {@link ConsoleUI}s attached.
	 * 
	 * @see BaseConsole#addConsoleUi
	 * @see BaseConsole#removeUiContainer
	 */
	private final ArrayList<UIContainer> uiContainers;
	/**
	 * The thread that is called when the program exits
	 */
	private final ShutdownHook shutdownHook;
	/**
	 * Has this BaseConsole been disposed?
	 * */
	private boolean disposed;
	private int pushing;
	private final ArrayList<SentObjectEvent> pushQueue;
	
	/**
	 * Constructor for {@link BaseConsole}.
	 * It initializes everything that the {@link BaseConsole}
	 * needs.
	 * 
	 * @see #BaseConsole(ConsoleUI)
	 */
	public BaseConsole() {
		this.disposed = false;
		this.listenerContainers = new ArrayList<ListenerContainer>();
		initMandatoryListeners();
		this.history = new ArrayList<String>();
		this.id = globalList.size();
		globalList.add(this);
		this.uiContainers = new ArrayList<UIContainer>();
		this.shutdownHook = new ShutdownHook(this);
		this.pushing = 0;
		this.pushQueue = new ArrayList<SentObjectEvent>();
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
	
	/**
	 * Instantiates a new {@link BaseConsole} while adding
	 * a gui. This is equivalent to:<br>
	 * 
	 * <code>
	 *     BaseConsole baseConsole = new BaseConsole();<br>
	 *     baseConsole.addConsoleUi(consoleUi);
	 * </code>
	 * 
	 * @see #BaseConsole()
	 * @param consoleUI The {@link ConsoleUI} to add to the {@link BaseConsole}
	 */
	public BaseConsole(ConsoleUI consoleUI) {
		this();
		addConsoleUi(consoleUI);
	}
	
	/**
	 * Handles disposing down the {@link ListenerAttribute}s and {@link ConsoleUI}s.<br>
	 * 
	 * @see ConsoleUI#dispose
	 * @see com.n9mtq4.logwindow.listener.DisableListener#onDisable(DisableActionEvent)
	 * @see com.n9mtq4.logwindow.listener.RemovalListener#onRemoval(RemovalActionEvent)
	 * @deprecated This method should only be called from the {@link ShutdownHook} class.
	 */
	@Deprecated
	public final void dispose() {
		
		System.out.println("Disposing of BaseConsole with id of " + getId());
		
//		remove the listeners, while trying to protect against Concurrent Modification
		ArrayList<ListenerContainer> listenerContainers1 = getListenerContainers();
		for (ListenerContainer listenerContainer : listenerContainers1) {
			removeListenerContainer(listenerContainer, RemovalActionEvent.CONSOLE_DISPOSE);
		}
		
//		remove the ConsoleUIs, while trying to protect against Concurrent Modification
		ArrayList<UIContainer> uiContainers1 = getUIContainers();
		for (UIContainer uiContainer : uiContainers1) {
			removeUiContainer(uiContainer);
		}
		
//		removes shutdown hook because this BaseConsole has already been disposed
		try {
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
		}catch (IllegalStateException ignored) {} // this is ok, because this method should be called from the shutdown hook
		this.disposed = true;
		
	}
	
	/**
	 * Adds required {@link ListenerAttribute}s to the {@link BaseConsole}.<br>
	 * This is called in the constructor.
	 * 
	 * TODO: remove mandatory listeners?
	 * @deprecated mandatory listeners shouldn't be mandatory.
	 */
	@Deprecated
	private void initMandatoryListeners() {
		
		addListenerAttribute(new ModuleListener());
		addListenerAttribute(new ModuleJarLoader());
		
	}
	
	/**
	 * Prints text in colour to the {@link ConsoleUI}s
	 * 
	 * @param object The object to print.
	 * @param colour The colour to print the text in.
	 */
	public final void print(Object object, Colour colour) {
		if (isDisposed()) return;
		for (UIContainer gui : uiContainers) {
			gui.print(object, colour);
		}
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 * 
	 * @param object The object to print.
	 */
	public final void print(Object object) {
		print(object, null);
	}
	
	/**
	 * Prints object in colour to the ConsoleGuis.
	 * 
	 * @param object The object to print.
	 * @param colour The colour to print the object in.
	 */
	public final void println(Object object, Colour colour) {
		print(object, colour);
		println();
	}
	
	/**
	 * Prints object in the default colour to the ConsoleGuis
	 * 
	 * @param object The object to print.
	 */
	public final void println(Object object) {
		println(object, null);
	}
	
	/**
	 * Prints a new line.
	 * */
	public final void println() {
		print("\n");
	}
	
	
	/**
	 * Prints the stack trace of a throwable to the {@link BaseConsole} output.
	 * 
	 * @param throwable Prints the stackTrace.
	 * @see Throwable#printStackTrace
	 */
	public final void printStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		this.println(sw.toString(), Colour.RED);
	}
	
//	STRING PUSHING START
	/**
	 * Sends {@link ListenerAttribute} a string.
	 * Does the same thing as {@link #pushString(String)}
	 * but adds the text to the history.
	 * 
	 * @see #sendPluginsStringNow(String)
	 * @see #pushString(String)
	 * @see #pushStringNow(String)
	 * @since v5.0
	 * @param text The text to send to the {@link ListenerAttribute}s
	 * */
	public final void sendPluginsString(final String text) {
		history.add(text);
		pushString(text);
	}
	
	/**
	 * Sends {@link ListenerAttribute} a string.
	 * Does the same thing as {@link #pushString(String)}
	 * but adds the text to the history.
	 * <br>
	 * I recommend using {@link #sendPluginsString(String)} )} if sending
	 * the string now isn't ABSOLUTELY necessary.
	 * 
	 * @see #sendPluginsString(String)
	 * @see #pushString(String)
	 * @see #pushStringNow(String)
	 * @since v5.0
	 * @param text The text to send to the {@link ListenerAttribute}s
	 * */
	public final void sendPluginsStringNow(final String text) {
		history.add(text);
		pushStringNow(text);
	}
	
	/**
	 * Sends {@link ListenerAttribute}s a string.
	 * Doesn't add the text to the history like
	 * {@link #sendPluginsString(String)} does.
	 * 
	 * @see #sendPluginsString(String)
	 * @see #sendPluginsStringNow(String)
	 * @see #pushStringNow(String)
	 * @since v5.0
	 * @param text The text to send to the {@link ListenerAttribute}s
	 * */
	public final void pushString(final String text) {
		pushEvent(SentObjectEvent.createTextEvent(this, text));
	}
	
	/**
	 * Sends {@link ListenerAttribute}s a string.
	 * Doesn't add the text to the history like
	 * {@link #sendPluginsString(String)} does.
	 * <br>
	 * Does not wait for other pushes to finish.
	 * I recommend using {@link #pushString(String)} )} if sending
	 * the string now isn't ABSOLUTELY necessary.
	 * 
	 * @see #sendPluginsString(String)
	 * @see #sendPluginsStringNow(String)
	 * @see #pushString(String)
	 * @since v5.0
	 * @param text The text to send to the {@link ListenerAttribute}s
	 * */
	public final void pushStringNow(final String text) {
		pushEventNow(SentObjectEvent.createTextEvent(this, text));
	}
//	STRING PUSHING END
	
//	OBJECT PUSHING START
	/**
	 * Pushes a {@link SentObjectEvent} right now. Does not wait for other events
	 * to finish with the queue system.
	 * <br>
	 * I recommend using {@link #pushEvent(SentObjectEvent)} if pushing the event
	 * now isn't ABSOLUTELY necessary.
	 * 
	 * @see #pushEvent(SentObjectEvent)
	 * @see #pushNow(Object, String)
	 * @see #push(Object, String)
	 * @since v5.0
	 * @param sentObjectEvent The {@link SentObjectEvent} to push to the {@link ListenerAttribute}
	 * */
	public final void pushEventNow(final SentObjectEvent sentObjectEvent) {
		
		if (isDisposed()) return; // if disposed stop running
		startPushing(); // pushing started
		
//		push the event to the listeners
		ArrayList<ListenerContainer> listenerClones = getListenerContainers();
		for (ListenerContainer listenerContainer : listenerClones) {
			try {
				
//				make sure we can push to the listeners
				if (listenerContainer.isEnabled() && (!sentObjectEvent.isCanceled() || listenerContainer.hasIgnoreDone())) {
					listenerContainer.pushObject(sentObjectEvent);
				}
				
			}catch (Exception e) {
//				wrap every listener in its own try so the program con continue if there is a crash.
//				catch anything that happens in a Listener and stop it from
//				bubbling up and hurting the rest of the program
//				print some information
				this.printStackTrace(e);
				e.printStackTrace();
				println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
				System.out.println("Listener " + listenerContainer.getAttribute().getClass().getName() + " has an error!");
			}
		}
		
		stopPushing(); // pushing ended
		
	}
	
	/**
	 * Adds a {@link SentObjectEvent} to the pushing queue. Will then try
	 * to push the next queue.
	 * 
	 * @see #pushEventNow(SentObjectEvent)
	 * @see #push(Object, String)
	 * @see #pushNow(Object, String)
	 * @since v5.0
	 * @param sentObjectEvent The {@link SentObjectEvent} to push to the {@link ListenerAttribute}s
	 * */
	public final void pushEvent(final SentObjectEvent sentObjectEvent) {
		addToQueue(sentObjectEvent);
		requestNextPush();
//		maybe use the following code for better efficiency.
/*		if (pushing > 0) {
			addToQueue(sentObjectEvent);
		}else {
			pushEventNow(sentObjectEvent);
		}*/
	}
	
	/**
	 * Pushes an {@link Object} with a message right now. Does not wait for other events
	 * to finish with the queue system.
	 * <br>
	 * I recommend using {@link #push(Object, String)} if pushing the event
	 * now isn't ABSOLUTELY necessary.
	 * 
	 * @see #push(Object, String)
	 * @see #pushEvent(SentObjectEvent)
	 * @see #pushEventNow(SentObjectEvent)
	 * @since v5.0
	 * @param object The {@link Object} to send to the {@link ListenerAttribute}s
	 * @param message The message to send with the object
	 * */
	public final void pushNow(final Object object, final String message) {
		pushEventNow(SentObjectEvent.createSentObjectEvent(this, object, message));
	}
	
	/**
	 * Adds an {@link Object} with the message to the pushing queue. Will then try
	 * to push the next queue.
	 *
	 * @see #pushNow(Object, String)
	 * @see #pushEvent(SentObjectEvent)
	 * @see #pushEventNow(SentObjectEvent)
	 * @since v5.0
	 * @param object The {@link Object} to send to the {@link ListenerAttribute}s
	 * @param message The message to send with the object
	 * */
	public final void push(final Object object, final String message) {
		pushEvent(SentObjectEvent.createSentObjectEvent(this, object, message));
	}
	
	private void addToQueue(final SentObjectEvent sentObjectEvent) {
		pushQueue.add(sentObjectEvent);
	}
	
	private void requestNextPush() {
		if (pushing > 0) return; // already pushing, so stop
		if (pushQueue.size() <= 0) return; // nothing to push, so stop
		SentObjectEvent event = pushQueue.get(0); // retrieve the next in line
		pushQueue.remove(0); // remove it from the line
		pushEventNow(event); // push it
	}
	
	private void startPushing() {
		pushing++; // add a current pushing
	}
	
	private void stopPushing() {
		pushing--; // remove a current pushing
		if (pushing == 0) {
//			if there are no current pushing events;
			requestNextPush(); // try to add a push from queue;
		}
	}
	
//	OBJECT PUSHING END
	
	/**
	 * Gets attribute from id.
	 * 
	 * @param id the id
	 * @return the attribute from id
	 */
	public final ListenerAttribute getAttributeFromId(String id) {
		ListenerContainer listenerContainer = getContainerFromId(id);
		return listenerContainer == null ? null : listenerContainer.getAttribute();
	}
	
	/**
	 * Gets container from id.
	 * 
	 * @param id the id
	 * @return the container from id
	 */
	public final ListenerContainer getContainerFromId(String id) {
		try {
			int index = Integer.parseInt(id);
			return getContainerFromIndex(index);
		}catch (NumberFormatException e) {
			return getContainerFromAttributeName(id);
		}
	}
	
	private ListenerContainer getContainerFromAttributeName(String name) {
		for (ListenerContainer listenerContainer : listenerContainers) {
			if (listenerContainer.getAttribute().getClass().getName().equals(name)) return listenerContainer;
		}
		return null;
	}
	
	private ListenerAttribute getAttributeFromIndex(int index) {
		return getContainerFromIndex(index).getAttribute();
	}
	
	private ListenerContainer getContainerFromIndex(int index) {
		return listenerContainers.get(index);
	}
	
	/**
	 * Gets container from attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 * @return the container from attribute
	 */
	public final ListenerContainer getContainerFromAttribute(ListenerAttribute listenerAttribute) {
		for (ListenerContainer listenerContainer : listenerContainers) {
			if (listenerContainer.getAttribute().equals(listenerAttribute)) return listenerContainer;
		}
		return null;
	}
	
	/**
	 * Add listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 */
	public final void addListenerAttribute(ListenerAttribute listenerAttribute) {
		addListenerAttributeRaw(listenerAttribute);
		enableListenerAttribute(listenerAttribute);
	}
	
	/**
	 * Add listener container.
	 * 
	 * @param listenerContainer the listener container
	 */
	public final void addListenerContainer(ListenerContainer listenerContainer) {
		addListenerContainerRaw(listenerContainer);
		enableListenerContainer(listenerContainer);
	}
	
	/**
	 * Add listener attribute raw.
	 * 
	 * @param listenerAttribute the listener attribute
	 */
	public final void addListenerAttributeRaw(ListenerAttribute listenerAttribute) {
		ListenerContainer listenerContainer = ListenerContainer.makeListenerEntry(listenerAttribute);
		addListenerContainerRaw(listenerContainer);
	}
	
	/**
	 * Add listener container raw.
	 * 
	 * @param listenerContainer the listener container
	 */
	public final void addListenerContainerRaw(ListenerContainer listenerContainer) {
		if (isDisposed()) return;
		if (!listenerContainers.contains(listenerContainer) || !listenerContainer.getLinkedBaseConsoles().contains(this)) {
			listenerContainers.add(listenerContainer);
			listenerContainer.addToConsole(this);
			listenerContainer.pushAdded(new AdditionActionEvent(this));
		}
	}
	
	/**
	 * Enable listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 */
	public final void enableListenerAttribute(ListenerAttribute listenerAttribute) {
		enableListenerContainer(getContainerFromAttribute(listenerAttribute));
	}
	
	/**
	 * Enable listener container.
	 * 
	 * @param listenerContainer the listener container
	 */
	public final void enableListenerContainer(ListenerContainer listenerContainer) {
		if (isDisposed()) return;
		if (!listenerContainer.isEnabled() || !listenerContainer.hasBeenEnabled()) {
			listenerContainer.setEnabled(true);
			listenerContainer.pushEnabled(new EnableActionEvent(this));
		}
	}
	
	/**
	 * Disable listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 */
	public final void disableListenerAttribute(ListenerAttribute listenerAttribute) {
		disableListenerAttribute(listenerAttribute, DisableActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Disable listener container.
	 * 
	 * @param listenerContainer the listener container
	 */
	public final void disableListenerContainer(ListenerContainer listenerContainer) {
		disableListenerContainer(listenerContainer, DisableActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Remove listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 */
	public final void removeListenerAttribute(ListenerAttribute listenerAttribute) {
		removeListenerAttribute(listenerAttribute, RemovalActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Remove listener container.
	 * 
	 * @param listenerContainer the listener container
	 */
	public final void removeListenerContainer(ListenerContainer listenerContainer) {
		removeListenerContainer(listenerContainer, RemovalActionEvent.NOT_SPECIFIED);
	}
	
	/**
	 * Disable listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 * @param type the type
	 */
	public final void disableListenerAttribute(ListenerAttribute listenerAttribute, int type) {
		disableListenerContainer(getContainerFromAttribute(listenerAttribute), type);
	}
	
	/**
	 * Disable listener container.
	 * 
	 * @param listenerContainer the listener container
	 * @param type the type
	 */
	public final void disableListenerContainer(ListenerContainer listenerContainer, int type) {
		if (isDisposed()) return;
		if (listenerContainer.isEnabled()) {
			listenerContainer.setEnabled(false);
			listenerContainer.pushDisabled(new DisableActionEvent(this, type));
		}
	}
	
	/**
	 * Remove listener attribute.
	 * 
	 * @param listenerAttribute the listener attribute
	 * @param type the type
	 */
	public final void removeListenerAttribute(ListenerAttribute listenerAttribute, int type) {
		removeListenerContainer(getContainerFromAttribute(listenerAttribute), type);
	}
	
	/**
	 * Remove listener container.
	 * 
	 * @param listenerContainer the listener container
	 * @param type the type
	 */
	public final void removeListenerContainer(ListenerContainer listenerContainer, int type) {
		disableListenerContainer(listenerContainer, type);
		removeListenerContainerRaw(listenerContainer, type);
	}
	
	private final void removeListenerAttributeRaw(ListenerAttribute listenerAttribute, int type) {
		removeListenerContainerRaw(getContainerFromAttribute(listenerAttribute), type);
	}
	
	private final void removeListenerContainerRaw(ListenerContainer listenerContainer, int type) {
		if (isDisposed()) return;
		if (listenerContainers.contains(listenerContainer) || listenerContainer.getLinkedBaseConsoles().contains(this)) {
			listenerContainers.remove(listenerContainer);
			listenerContainer.removeFromConsole(this);
			listenerContainer.pushRemoved(new RemovalActionEvent(this, type));
		}
	}
	
	/**
	 * Loads plugins to this console from the file path.
	 * 
	 * @param filePath the file path of the folder to load this plugins from.
	 */
	public final void loadPlugins(String filePath) {
		
		if (isDisposed()) return;
		PluginManager.loadPluginsToConsole(this, filePath);
		
	}
	
	/**
	 * Loads plugins to this console from "./plugins".<br>
	 * 
	 * @see BaseConsole#loadPlugins(String)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, File)
	 * @see PluginManager#loadPluginsToConsole(BaseConsole, String)
	 */
	public final void loadPlugins() {
		
		if (isDisposed()) return;
		PluginManager.loadPluginsToConsole(this, PluginManager.DEFAULT_PLUGIN_FOLDER);
		
	}
	
	/**
	 * Gets history.
	 * 
	 * @return the history
	 */
	public final ArrayList<String> getHistory() {
		return history;
	}
	
	/**
	 * Gets the listener entries.
	 *
	 * @return A clone of the {@link ListenerContainer} array
	 */
	public final ArrayList<ListenerContainer> getListenerContainers() {
		return (ArrayList<ListenerContainer>) listenerContainers.clone();
	}
	
	/**
	 * Gets the list of {@link UIContainer}s.
	 * 
	 * <br>Note: the list is cloned before being returned.
	 * 
	 * @see #uiContainers
	 * @return A clone of the {@link ConsoleUI} array
	 */
	public final ArrayList<UIContainer> getUIContainers() {
		return (ArrayList<UIContainer>) uiContainers.clone();
	}
	
	/**
	 * Returns if the console has a gui attached to it.
	 * 
	 * @return If the {@link BaseConsole} has at least one gui attached to it
	 */
	public final boolean hasGuiAttached() {
		return uiContainers.size() > 0;
	}
	
	/**
	 * Adds a {@link ConsoleUI} to the {@link BaseConsole}.
	 *
	 * @param consoleUI the gui to add
	 */
	public final void addConsoleUi(ConsoleUI consoleUI) {
		if (isDisposed()) return;
		uiContainers.add(new UIContainer(consoleUI));
		consoleUI.init();
	}
	
	/**
	 * Removes a {@link UIContainer} from the {@link BaseConsole}.
	 * 
	 * @param UIContainer the gui to remove
	 */
	public final void removeUiContainer(UIContainer UIContainer) {
		uiContainers.remove(UIContainer);
		UIContainer.getGui().dispose();
	}
	
	/**
	 * Removes a {@link ConsoleUI} from the {@link BaseConsole}.
	 * 
	 * @param consoleUI The {@link ConsoleUI} to add.
	 */
	public final void removeConsoleUi(ConsoleUI consoleUI) {
//		this prevents a concurrency issue.
		int ir = 0;
		for (int i = 0; i < uiContainers.size(); i++) {
			if (uiContainers.get(i).getGui().equals(consoleUI)) {
				ir = i;
				break;
			}
		}
		uiContainers.remove(ir);
		consoleUI.dispose();
	}
	
	/**
	 * Gets the global id of this {@link BaseConsole}.
	 *
	 * @return the global ID for this {@link BaseConsole}
	 */
	public final int getId() {
		return id;
	}
	
	public final boolean isDisposed() {
		if (disposed) System.out.println("The BaseConsole has been disposed");
		return disposed;
	}
	
}
