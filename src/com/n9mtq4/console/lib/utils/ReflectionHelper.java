/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Will on 12/30/14.
 */
/**
 * Note: Use static import for best results.<br/>
 * Class to simplify reflection calls.<br/>
 * */
@SuppressWarnings("unused")
public class ReflectionHelper {
	
	public static int getInt(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getInt(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setInt(int x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setInt(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getStaticInt(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getInt(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticInt(int x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setInt(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte getByte(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getByte(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setByte(byte x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setByte(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte getStaticByte(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getByte(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticByte(byte x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setByte(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getBoolean(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getBoolean(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void setBoolean(boolean x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setBoolean(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getStaticBoolean(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getBoolean(null);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void setStaticBoolean(boolean x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setBoolean(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static char getChar(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getChar(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setChar(char x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setChar(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static char getStaticChar(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getChar(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticChar(char x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setChar(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float getFloat(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getFloat(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	public static void setFloat(float x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setFloat(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float getStaticFloat(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getFloat(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	public static void setStaticFloat(float x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setFloat(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double getDouble(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getDouble(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setDouble(double x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setDouble(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double getStaticDouble(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getDouble(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticDouble(double x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setDouble(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long getLong(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getLong(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setLong(long x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setLong(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long getStaticLong(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getLong(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticLong(long x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.setLong(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getObject(String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setObject(Object x, String fieldName, Object obj, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(obj, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getStaticObject(String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(null);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void setStaticObject(Object x, String fieldName, Class clazz) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(null, x);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object callConstructor(Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Constructor c = clazz.getDeclaredConstructor(classParams);
			c.setAccessible(true);
			return c.newInstance(params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object callObjectMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void callVoidMethod(String methodName, Object obj, Class clazz, Object[] params) {
		callObjectMethod(methodName, obj, clazz, params);
	}
	
	public static Integer callIntMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Integer) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Byte callByteMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Byte) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Boolean callBooleanMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Boolean) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Character callCharMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Character) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Float callFloatMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Float) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Double callDoubleMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Double) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Long callLongMethod(String methodName, Object obj, Class clazz, Object[] params) {
		try {
			Class[] classParams = new Class[params.length];
			for (int i = 0; i < classParams.length; i++) {
				classParams[i] = params[i].getClass();
			}
			Method m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return (Long) m.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
