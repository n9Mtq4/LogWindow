/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014-2015  Will (n9Mtq4) Bresnahan
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

import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Note: Use static import for best results.<br>
 * Class to simplify reflection calls.<br>
 */
@SuppressWarnings("unused")
public class ReflectionHelper {
	
	/**
	 * Gets int.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the int
	 */
	public static int getInt(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getInt(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets int.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the int
	 */
	public static int getInt(String fieldName, Object obj, Class clazz) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			return getInt(field, obj);
		}catch (NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets int.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setInt(int x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setInt(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets int.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setInt(int x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setInt(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets int.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the int
	 */
	public static int getInt(String fieldName, Object obj) {
		return getInt(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets int.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setInt(int x, String fieldName, Object obj) {
		setInt(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static int.
	 *
	 * @param field the field
	 * @return the static int
	 */
	public static int getStaticInt(Field field) {
		try {
			field.setAccessible(true);
			return field.getInt(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets static int.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static int
	 */
	public static int getStaticInt(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticInt(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticInt(int x, Field field) {
		try {
			field.setAccessible(true);
			field.setInt(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static int.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticInt(int x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticInt(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets byte.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the byte
	 */
	public static byte getByte(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getByte(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets byte.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the byte
	 */
	public static byte getByte(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getByte(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets byte.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setByte(byte x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setByte(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets byte.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setByte(byte x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setByte(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets byte.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the byte
	 */
	public static byte getByte(String fieldName, Object obj) {
		return getByte(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets byte.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setByte(byte x, String fieldName, Object obj) {
		setByte(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static byte.
	 *
	 * @param field the field
	 * @return the static byte
	 */
	public static byte getStaticByte(Field field) {
		try {
			field.setAccessible(true);
			return field.getByte(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets static byte.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static byte
	 */
	public static byte getStaticByte(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticByte(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets static byte.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticByte(byte x, Field field) {
		try {
			field.setAccessible(true);
			field.setByte(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static byte.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticByte(byte x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticByte(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets boolean.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the boolean
	 */
	public static boolean getBoolean(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getBoolean(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets boolean.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the boolean
	 */
	public static boolean getBoolean(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getBoolean(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Sets boolean.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setBoolean(boolean x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setBoolean(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets boolean.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setBoolean(boolean x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setBoolean(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets boolean.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the boolean
	 */
	public static boolean getBoolean(String fieldName, Object obj) {
		return getBoolean(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets boolean.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setBoolean(boolean x, String fieldName, Object obj) {
		setBoolean(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static boolean.
	 *
	 * @param field the field
	 * @return the static boolean
	 */
	public static boolean getStaticBoolean(Field field) {
		try {
			field.setAccessible(true);
			return field.getBoolean(null);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets static boolean.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static boolean
	 */
	public static boolean getStaticBoolean(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticBoolean(f);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Sets static boolean.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticBoolean(boolean x, Field field) {
		try {
			field.setAccessible(true);
			field.setBoolean(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static boolean.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticBoolean(boolean x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticBoolean(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets char.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the char
	 */
	public static char getChar(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getChar(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets char.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the char
	 */
	public static char getChar(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getChar(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets char.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setChar(char x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setChar(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets char.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setChar(char x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setChar(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets char.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the char
	 */
	public static char getChar(String fieldName, Object obj) {
		return getChar(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets char.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setChar(char x, String fieldName, Object obj) {
		setChar(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static char.
	 *
	 * @param field the field
	 * @return the static char
	 */
	public static char getStaticChar(Field field) {
		try {
			field.setAccessible(true);
			return field.getChar(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets static char.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static char
	 */
	public static char getStaticChar(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticChar(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets static char.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticChar(char x, Field field) {
		try {
			field.setAccessible(true);
			field.setChar(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static char.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticChar(char x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticChar(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets float.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the float
	 */
	public static float getFloat(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getFloat(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	/**
	 * Gets float.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the float
	 */
	public static float getFloat(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getFloat(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	/**
	 * Sets float.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setFloat(float x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setFloat(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets float.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setFloat(float x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setFloat(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets float.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the float
	 */
	public static float getFloat(String fieldName, Object obj) {
		return getFloat(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets float.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setFloat(float x, String fieldName, Object obj) {
		setFloat(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static float.
	 *
	 * @param field the field
	 * @return the static float
	 */
	public static float getStaticFloat(Field field) {
		try {
			field.setAccessible(true);
			return field.getFloat(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	/**
	 * Gets static float.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static float
	 */
	public static float getStaticFloat(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticFloat(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	/**
	 * Sets static float.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticFloat(float x, Field field) {
		try {
			field.setAccessible(true);
			field.setFloat(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static float.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticFloat(float x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticFloat(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets double.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the double
	 */
	public static double getDouble(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getDouble(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets double.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the double
	 */
	public static double getDouble(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getDouble(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets double.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setDouble(double x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setDouble(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets double.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setDouble(double x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setDouble(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets double.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the double
	 */
	public static double getDouble(String fieldName, Object obj) {
		return getDouble(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets double.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setDouble(double x, String fieldName, Object obj) {
		setDouble(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static double.
	 *
	 * @param field the field
	 * @return the static double
	 */
	public static double getStaticDouble(Field field) {
		try {
			field.setAccessible(true);
			return field.getDouble(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets static double.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static double
	 */
	public static double getStaticDouble(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticDouble(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets static double.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticDouble(double x, Field field) {
		try {
			field.setAccessible(true);
			field.setDouble(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static double.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticDouble(double x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticDouble(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets long.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the long
	 */
	public static long getLong(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.getLong(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets long.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the long
	 */
	public static long getLong(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getLong(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets long.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setLong(long x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.setLong(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets long.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setLong(long x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setLong(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets long.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the long
	 */
	public static long getLong(String fieldName, Object obj) {
		return getLong(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets long.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setLong(long x, String fieldName, Object obj) {
		setLong(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static long.
	 *
	 * @param field the field
	 * @return the static long
	 */
	public static long getStaticLong(Field field) {
		try {
			field.setAccessible(true);
			return field.getLong(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets static long.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static long
	 */
	public static long getStaticLong(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticLong(f);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets static long.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticLong(long x, Field field) {
		try {
			field.setAccessible(true);
			field.setLong(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static long.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticLong(long x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticLong(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets object.
	 *
	 * @param field the field
	 * @param obj the obj
	 * @return the object
	 */
	public static <E> E getObject(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return (E) field.get(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets object.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 * @return the object
	 */
	public static <E> E getObject(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getObject(f, obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sets object.
	 *
	 * @param x the x
	 * @param field the field
	 * @param obj the obj
	 */
	public static void setObject(Object x, Field field, Object obj) {
		try {
			field.setAccessible(true);
			field.set(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets object.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @param clazz     the class
	 */
	public static void setObject(Object x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setObject(x, f, obj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets object.
	 *
	 * @param fieldName the field name
	 * @param obj       the obj
	 * @return the object
	 */
	public static <E> E getObject(String fieldName, Object obj) {
		return getObject(fieldName, obj, obj.getClass());
	}
	
	/**
	 * Sets object.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param obj       the obj
	 */
	public static void setObject(Object x, String fieldName, Object obj) {
		setObject(x, fieldName, obj, obj.getClass());
	}
	
	/**
	 * Gets static object.
	 *
	 * @param field the field
	 * @return the static object
	 */
	public static <E> E getStaticObject(Field field) {
		try {
			field.setAccessible(true);
			return (E) field.get(null);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets static object.
	 *
	 * @param fieldName the field name
	 * @param clazz     the class
	 * @return the static object
	 */
	public static <E> E getStaticObject(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			return getStaticObject(f);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sets static object.
	 *
	 * @param x the x
	 * @param field the field
	 */
	public static void setStaticObject(Object x, Field field) {
		try {
			field.setAccessible(true);
			field.set(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets static object.
	 *
	 * @param x         the x
	 * @param fieldName the field name
	 * @param clazz     the class
	 */
	public static void setStaticObject(Object x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			setStaticObject(x, f);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Call constructor.
	 *
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the object
	 */
	public static <E> E callConstructor(Class clazz, Class[] classParams, Object[] params) {
		try {
			Constructor c = clazz.getDeclaredConstructor(classParams);
			c.setAccessible(true);
			return (E) c.newInstance(params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Call constructor.
	 *
	 * @param clazz  the class
	 * @param params the params
	 * @return the object
	 */
	public static <E> E callConstructor(Class clazz, Object... params) {
		return callConstructor(clazz, getClassParams(params), params);
	}
	
	/**
	 * Call object method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the object
	 */
	public static <E> E callObjectMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		Method m = null;
		try {
			m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (E) m.invoke(obj, params);
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Call object method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the object
	 */
	public static <E> E callObjectMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callObjectMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call object method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the object
	 */
	public static <E> E callObjectMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callObjectMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call object method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the object
	 */
	public static <E> E callObjectMethod(String methodName, Object obj, Object... params) {
		return callObjectMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static object method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the object
	 */
	public static <E> E callStaticObjectMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return callObjectMethod(methodName, null, clazz, classParams, params);
	}
	
	/**
	 * Call static object method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the object
	 */
	public static <E> E callStaticObjectMethod(String methodName, Class clazz, Object... params) {
		return callStaticObjectMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call void method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 */
	public static void callVoidMethod(String methodName, Object obj, Class clazz, Object[] params) {
		callVoidMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call void method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 */
	public static void callVoidMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call void method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 */
	public static void callVoidMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		callVoidMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call void method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 */
	public static void callVoidMethod(String methodName, Object obj, Object... params) {
		callObjectMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static void method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 */
	public static void callStaticVoidMethod(String methodName, Class clazz, Object... params) {
		callStaticVoidMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call static void method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 */
	public static void callStaticVoidMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call int method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the int
	 */
	public static int callIntMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Integer) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call int method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the int
	 */
	public static int callIntMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callIntMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call int method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the int
	 */
	public static int callIntMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callIntMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call int method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the int
	 */
	public static int callIntMethod(String methodName, Object obj, Object... params) {
		return callIntMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static int method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the int
	 */
	public static int callStaticIntMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Integer) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static int method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the int
	 */
	public static int callStaticIntMethod(String methodName, Class clazz, Object... params) {
		return callStaticIntMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call byte method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the byte
	 */
	public static byte callByteMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Byte) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call byte method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the byte
	 */
	public static byte callByteMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callByteMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call byte method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the byte
	 */
	public static byte callByteMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callByteMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call byte method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the byte
	 */
	public static byte callByteMethod(String methodName, Object obj, Object... params) {
		return callByteMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static byte method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the byte
	 */
	public static byte callStaticByteMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Byte) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static byte method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the byte
	 */
	public static byte callStaticByteMethod(String methodName, Class clazz, Object... params) {
		return callStaticByteMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call boolean method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the boolean
	 */
	public static boolean callBooleanMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Boolean) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call boolean method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the boolean
	 */
	public static boolean callBooleanMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callBooleanMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call boolean method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the boolean
	 */
	public static boolean callBooleanMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callBooleanMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call boolean method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the boolean
	 */
	public static boolean callBooleanMethod(String methodName, Object obj, Object... params) {
		return callBooleanMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static boolean method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the boolean
	 */
	public static boolean callStaticBooleanMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Boolean) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static boolean method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the boolean
	 */
	public static boolean callStaticBooleanMethod(String methodName, Class clazz, Object... params) {
		return callStaticBooleanMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call char method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the char
	 */
	public static char callCharMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Character) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call char method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the char
	 */
	public static char callCharMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callCharMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call char method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the char
	 */
	public static char callCharMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callCharMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call char method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the char
	 */
	public static char callCharMethod(String methodName, Object obj, Object... params) {
		return callCharMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static char method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the char
	 */
	public static char callStaticCharMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Character) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static char method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the char
	 */
	public static char callStaticCharMethod(String methodName, Class clazz, Object... params) {
		return callStaticCharMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call float method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the float
	 */
	public static float callFloatMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Float) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call float method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the float
	 */
	public static float callFloatMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callFloatMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call float method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the float
	 */
	public static float callFloatMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callFloatMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call float method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the float
	 */
	public static float callFloatMethod(String methodName, Object obj, Object... params) {
		return callFloatMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static float method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the float
	 */
	public static float callStaticFloatMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Float) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static float method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the float
	 */
	public static float callStaticFloatMethod(String methodName, Class clazz, Object... params) {
		return callStaticFloatMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call double method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the double
	 */
	public static double callDoubleMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Double) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call double method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the double
	 */
	public static double callDoubleMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callDoubleMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call double method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the double
	 */
	public static double callDoubleMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callDoubleMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call double method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the double
	 */
	public static double callDoubleMethod(String methodName, Object obj, Object... params) {
		return callDoubleMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static double method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the double
	 */
	public static double callStaticDoubleMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Double) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static double method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the double
	 */
	public static double callStaticDoubleMethod(String methodName, Class clazz, Object... params) {
		return callStaticDoubleMethod(methodName, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call long method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the long
	 */
	public static long callLongMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Long) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	/**
	 * Call long method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param clazz      the class
	 * @param params     the params
	 * @return the long
	 */
	public static long callLongMethod(String methodName, Object obj, Class clazz, Object... params) {
		return callLongMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	/**
	 * Call long method.
	 *
	 * @param methodName  the method name
	 * @param obj         the obj
	 * @param classParams the class params
	 * @param params      the params
	 * @return the long
	 */
	public static long callLongMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callLongMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	/**
	 * Call long method.
	 *
	 * @param methodName the method name
	 * @param obj        the obj
	 * @param params     the params
	 * @return the long
	 */
	public static long callLongMethod(String methodName, Object obj, Object... params) {
		return callLongMethod(methodName, obj, getClassParams(params), params);
	}
	
	/**
	 * Call static long method.
	 *
	 * @param methodName  the method name
	 * @param clazz       the class
	 * @param classParams the class params
	 * @param params      the params
	 * @return the long
	 */
	public static long callStaticLongMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Long) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	/**
	 * Call static long method.
	 *
	 * @param methodName the method name
	 * @param clazz      the class
	 * @param params     the params
	 * @return the long
	 */
	public static long callStaticLongMethod(String methodName, Class clazz, Object... params) {
		return callStaticLongMethod(methodName, clazz, getClassParams(params), params);
	}
	
//	start stuff not written by me
//	BrainStorm @ https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
	
	/**
	 * Attempts to list all the classes in the specified package as determined
	 * by the context class loader
	 *
	 * @param pckgname the package name to search
	 * @return a list of classes that exist within that package
	 * @throws ClassNotFoundException if something went wrong
	 */
	public static ArrayList<Class<?>> getClassesForPackage(String pckgname) throws ClassNotFoundException {
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		try {
			final ClassLoader cld = Thread.currentThread().getContextClassLoader();
			
			if (cld == null) throw new ClassNotFoundException("Can't get class loader.");
			
			Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
			URLConnection connection;
			
			for (URL url = null; resources.hasMoreElements() && ((url = resources.nextElement()) != null); ) {
				try {
					connection = url.openConnection();
					
					if (connection instanceof JarURLConnection) {
						
						checkJarFile((JarURLConnection) connection, pckgname, classes);
						
					}else if (connection instanceof FileURLConnection) {
						
						try {
							checkDirectory(new File(URLDecoder.decode(url.getPath(), "UTF-8")), pckgname, classes);
						}catch (UnsupportedEncodingException ex) {
							throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)", ex);
						}
						
					}else
						throw new ClassNotFoundException(pckgname + " (" + url.getPath() + ") does not appear to be a valid package");
				}catch (final IOException ioex) {
					throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname, ioex);
				}
			}
		}catch (final NullPointerException ex) {
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)", ex);
		}catch (final IOException ioex) {
			throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname, ioex);
		}
		
		return classes;
	}
	
	/**
	 * Private helper method.
	 *
	 * @param connection the connection to the jar
	 * @param pckgname   the package name to search for
	 * @param classes    the current ArrayList of all classes. This method will simply
	 *                   add new classes.
	 * @throws ClassNotFoundException if a file isn't loaded but still is in the jar file
	 * @throws IOException            if it can't correctly read from the jar file.
	 */
	private static void checkJarFile(JarURLConnection connection, String pckgname, ArrayList<Class<?>> classes) throws ClassNotFoundException, IOException {
		final JarFile jarFile = connection.getJarFile();
		final Enumeration<JarEntry> entries = jarFile.entries();
		String name;
		
		for (JarEntry jarEntry = null; entries.hasMoreElements() && ((jarEntry = entries.nextElement()) != null); ) {
			name = jarEntry.getName();
			
			if (name.contains(".class")) {
				name = name.substring(0, name.length() - 6).replace('/', '.');
				
				if (name.contains(pckgname)) {
					classes.add(Class.forName(name));
				}
			}
		}
	}
	
	/**
	 * Private helper method
	 *
	 * @param directory The directory to start with
	 * @param pckgname  The package name to search for. Will be needed for getting the
	 *                  Class object.
	 * @param classes   if a file isn't loaded but still is in the directory
	 * @throws ClassNotFoundException
	 */
	private static void checkDirectory(File directory, String pckgname, ArrayList<Class<?>> classes) throws ClassNotFoundException {
		File tmpDirectory;
		
		if (directory.exists() && directory.isDirectory()) {
			final String[] files = directory.list();
			
			for (final String file : files) {
				if (file.endsWith(".class")) {
					try {
						classes.add(Class.forName(pckgname + '.'
								+ file.substring(0, file.length() - 6)));
					}catch (NoClassDefFoundError e) {
//						Do nothing
					}
				}else if ((tmpDirectory = new File(directory, file))
						.isDirectory()) {
					checkDirectory(tmpDirectory, pckgname + "." + file, classes);
				}
			}
		}
	}
	
//	end stuff not written by me
	
	/**
	 * Find package names starting with.
	 *
	 * @param prefix the prefix
	 * @return the list
	 */
	public static List<String> findPackageNamesStartingWith(String prefix) {
		List<String> result = new ArrayList<String>();
		for (Package p : Package.getPackages()) {
			if (p.getName().startsWith(prefix)) {
				result.add(p.getName());
			}
		}
		return result;
	}
	
	/**
	 * Gets class by simple name.
	 *
	 * @param simpleName the simple name
	 * @return the class by simple name
	 */
	public static Class getClassBySimpleName(String simpleName) {
		Package[] packages = Package.getPackages();
		for (Package p : packages) {
			
			try {
				
				ArrayList<Class<?>> classes = getClassesForPackage(p.getName());
				for (Class c : classes) {
					
					if (c.getSimpleName().equals(simpleName)) {
						
						return c;
						
					}
					
				}
				
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	
	/**
	 * Gets class by full name.
	 *
	 * @param className the class name
	 * @return the class by full name
	 */
	public static Class getClassByFullName(String className) {
		try {
			return Class.forName(className);
		}catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Get class params.
	 *
	 * @param params the params
	 * @return the class [ ]
	 */
	public static Class[] getClassParams(Object[] params) {
		Class[] classParams = new Class[params.length];
		for (int i = 0; i < classParams.length; i++) {
			classParams[i] = params[i].getClass();
		}
		return classParams;
	}
	
}
