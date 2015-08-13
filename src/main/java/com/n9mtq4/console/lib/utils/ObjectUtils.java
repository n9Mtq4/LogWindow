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

package com.n9mtq4.console.lib.utils;

import java.io.*;

/**
 * Created by will on 4/28/15 at 6:52 PM.
 */
public class ObjectUtils {
	
	/**
	 * Read a saved serializable object.
	 *
	 * @param <E>  the type parameter
	 * @param file the file
	 * @return the object that has been saved
	 * @throws Exception any errors that may have occurred.
	 */
	public static <E> E readSerializable(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		E o = (E) objectInputStream.readObject();
		objectInputStream.close();
		return o;
	}
	
	/**
	 * Read serializable object.
	 *
	 * @param file the file
	 * @return the object
	 * @throws Exception any errors that may have occurred.
	 */
	@Deprecated
	public static Object readSerializableObject(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Object o = objectInputStream.readObject();
		objectInputStream.close();
		return o;
	}
	
	/**
	 * Write a serializable to a file.
	 *
	 * @param object the object
	 * @param file the file
	 * @throws Exception any errors that may have occur.
	 */
	public static void writeSerializable(Object object, File file) throws Exception {
		
		if (!(object instanceof Serializable)) throw new IllegalArgumentException("The object must be Serializable");
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
//		fileOutputStream.close(); //TODO: does objectOutputStream close it? i think it does
		
	}
	
}
