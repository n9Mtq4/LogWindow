#LogWindow [![Build Status](https://travis-ci.org/n9Mtq4/LogWindow.svg?branch=gradle)](https://travis-ci.org/n9Mtq4/LogWindow)#

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
- ConsoleListener is abstract, so there actionPerformed must be overridden.

```java
package me.plugin;

import com.n9mtq4.console.lib.ConsoleListener
import com.n9mtq4.console.lib.utils.Colour

public class MyPlugin implements ObjectListener {
	@Override
	public void objectReceived(final ObjectEvent objectEvent, final BaseConsole baseConsole) {
		
		if (!objectEvent.isUserInputString()) return;
		StringParser stringParser = new StringParser(objectEvent);
		
		baseConsole.println(stringParser.getText(), Colour.RED); //prints what was inputed in red
		
	}
}
```
