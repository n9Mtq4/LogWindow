#LogWindow [![Build Status](https://travis-ci.org/n9Mtq4/LogWindow.svg?branch=master)](https://travis-ci.org/n9Mtq4/LogWindow)

------------------
##What is it?
This is a java library for debugging or handling sending events globally through the program or system.
You can easily create a subclass of BaseConsole, create a custom consoleUI/input interface for it, or make
your own modules that can handle input received by it.

- Version numbering guide:
 - A typical version will look like this 2.7.12
 - The first number increases every time there is a release that is not backwards compatible.
 - The second number increases every major release (large update or new feature).
 - The third number is the amount of commits between major releases.

##Making a plugin##
- Load LogWindowFramework-5.jar into your project as a library
- Make a class and implement ObjectListener, AdditionListener, EnableListener, DisableListener, or RemovalListener.

```java
import com.n9mtq4.logwindow.BaseConsole;
import com.n9mtq4.logwindow.events.ObjectEvent;
import com.n9mtq4.logwindow.listener.ObjectListener;
import com.n9mtq4.logwindow.ui.uis.GuiJFrame;
import com.n9mtq4.logwindow.utils.*;

public class EchoListener implements ObjectListener {
	
	public static void main(String[] args) {
		
		BaseConsole baseConsole = new BaseConsole(); // makes a new BaseConsole
		baseConsole.addConsoleUi(new GuiJFrame(baseConsole)); // adds a new logwindow gui
		baseConsole.addListenerAttribute(new EchoListener()); // adds our listener
		
	}
	
	@Override
	public void objectReceived(ObjectEvent objectEvent, BaseConsole baseConsole) {
		
		if (!objectEvent.isUserInputString()) return; // we only want text
		StringParser stringParser = new StringParser(objectEvent); // get a string parser
		
		baseConsole.println(stringParser.getText(), Colour.RED); // prints what was inputed in red
		
	}
	
}
```
