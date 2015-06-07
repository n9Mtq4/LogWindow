#LogWindow#

A library for modular/plugin based commands in a text window

- Version numbering guide:
 - A typical version will look like this 2.7.12
 - The first number increases every time there is a release that is not backwards compatible.
 - The second number increases every major release (large update or new feature).
 - The third number is the amount of commits between major releases.

##Making a plugin##
- Load LogWindowAPI.jar into your project as a library
- Make a class and extend ConsoleListener
- ConsoleListener is abstract, so there actionPerformed must be overridden.
```java
package me.plugin;
import com.n9mtq4.console.lib.ConsoleListener
import com.n9mtq4.console.lib.utils.Colour
public class MyPlugin extends ConsoleListener {
	@Override
	public void actionPerformed(ConsoleActionEvent e, BaseConsole baseConsole) {
		baseConsole.println(e.getCommand().getText(), Colour.RED); //prints what was inputed in red
	}
}
```
- Here's when each method is called
 - onAddition is called when your listener is added to the parent console
 - onEnable is called when your listener is enabled
 - actionTab is called when the user pushes tab in the input (for tab auto-complete) 
 - actionPreformed is called when the user pushes the enter key and submits the text in the input
 - onDisable is called when your listener is being disabled
 - onRemoval is called when your listener is being removed from the parent console
 - Note: When your listener is added to the console it also gets enabled
 - Note: When your listener is removed from the console it also gets disabled
 - Note: onAddition can be used as a constructor
