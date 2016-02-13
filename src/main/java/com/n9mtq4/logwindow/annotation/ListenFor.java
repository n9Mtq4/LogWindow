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

package com.n9mtq4.logwindow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by will on 2/8/16 at 6:26 PM.
 * 
 * The annotation that tells the BaseConsole that the method
 * should be called when the specified event is sent.
 * 
 * @since v5.1
 * @version v5.1
 * @author Will "n9Mtq4" Bresnahan
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ListenFor {
	
	/**
	 * The class of the event that this method is receiving.
	 * 
	 * @return The class of the event to be listening for
	 * */
	Class<?> value();
	
}
