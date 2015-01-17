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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Will on 12/30/14.
 */

/**
 * Note: Use static import for best results.<br/>
 * Class to simplify reflection calls.<br/>
 */
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
	
	public static int getInt(String fieldName, Object obj) {
		return getInt(fieldName, obj, obj.getClass());
	}
	
	public static void setInt(int x, String fieldName, Object obj) {
		setInt(x, fieldName, obj, obj.getClass());
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
	
	public static byte getByte(String fieldName, Object obj) {
		return getByte(fieldName, obj, obj.getClass());
	}
	
	public static void setByte(byte x, String fieldName, Object obj) {
		setByte(x, fieldName, obj, obj.getClass());
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
	
	public static boolean getBoolean(String fieldName, Object obj) {
		return getBoolean(fieldName, obj, obj.getClass());
	}
	
	public static void setBoolean(boolean x, String fieldName, Object obj) {
		setBoolean(x, fieldName, obj, obj.getClass());
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
	
	public static char getChar(String fieldName, Object obj) {
		return getChar(fieldName, obj, obj.getClass());
	}
	
	public static void setChar(char x, String fieldName, Object obj) {
		setChar(x, fieldName, obj, obj.getClass());
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
	
	public static float getFloat(String fieldName, Object obj) {
		return getFloat(fieldName, obj, obj.getClass());
	}
	
	public static void setFloat(float x, String fieldName, Object obj) {
		setFloat(x, fieldName, obj, obj.getClass());
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
	
	public static double getDouble(String fieldName, Object obj) {
		return getDouble(fieldName, obj, obj.getClass());
	}
	
	public static void setDouble(double x, String fieldName, Object obj) {
		setDouble(x, fieldName, obj, obj.getClass());
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
	
	public static long getLong(String fieldName, Object obj) {
		return getLong(fieldName, obj, obj.getClass());
	}
	
	public static void setLong(long x, String fieldName, Object obj) {
		setLong(x, fieldName, obj, obj.getClass());
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
	
	public static Object getObject(String fieldName, Object obj) {
		return getObject(fieldName, obj, obj.getClass());
	}
	
	public static void setObject(Object x, String fieldName, Object obj) {
		setObject(x, fieldName, obj, obj.getClass());
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
	
	public static Object callConstructor(Class clazz, Class[] classParams, Object[] params) {
		try {
			Constructor c = clazz.getDeclaredConstructor(classParams);
			c.setAccessible(true);
			return c.newInstance(params);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object callConstructor(Class clazz, Object[] params) {
		return callConstructor(clazz, getClassParams(params), params);
	}
	
	public static Object callObjectMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		Method m = null;
		try {
			m = clazz.getDeclaredMethod(methodName, classParams);
			m.setAccessible(true);
			return m.invoke(obj, params);
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object callObjectMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callObjectMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static Object callObjectMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callObjectMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static Object callObjectMethod(String methodName, Object obj, Object[] params) {
		return callObjectMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static Object callStaticObjectMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return callObjectMethod(methodName, null, clazz, classParams, params);
	}
	
	public static Object callStaticObjectMethod(String methodName, Class clazz, Object[] params) {
		return callStaticObjectMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static void callVoidMethod(String methodName, Object obj, Class clazz, Object[] params) {
		callVoidMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static void callVoidMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static void callVoidMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		callVoidMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static void callVoidMethod(String methodName, Object obj, Object[] params) {
		callObjectMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static void callStaticVoidMethod(String methodName, Class clazz, Object[] params) {
		callStaticVoidMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static void callStaticVoidMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static int callIntMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Integer) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static int callIntMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callIntMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static int callIntMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callIntMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static int callIntMethod(String methodName, Object obj, Object[] params) {
		return callIntMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static int callStaticIntMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Integer) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static int callStaticIntMethod(String methodName, Class clazz, Object[] params) {
		return callStaticIntMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static byte callByteMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Byte) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static byte callByteMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callByteMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static byte callByteMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callByteMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static byte callByteMethod(String methodName, Object obj, Object[] params) {
		return callByteMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static byte callStaticByteMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Byte) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static byte callStaticByteMethod(String methodName, Class clazz, Object[] params) {
		return callStaticByteMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static boolean callBooleanMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Boolean) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static boolean callBooleanMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callBooleanMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static boolean callBooleanMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callBooleanMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static boolean callBooleanMethod(String methodName, Object obj, Object[] params) {
		return callBooleanMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static boolean callStaticBooleanMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Boolean) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static boolean callStaticBooleanMethod(String methodName, Class clazz, Object[] params) {
		return callStaticBooleanMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static char callCharMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Character) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static char callCharMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callCharMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static char callCharMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callCharMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static char callCharMethod(String methodName, Object obj, Object[] params) {
		return callCharMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static char callStaticCharMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Character) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static char callStaticCharMethod(String methodName, Class clazz, Object[] params) {
		return callStaticCharMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static float callFloatMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Float) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static float callFloatMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callFloatMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static float callFloatMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callFloatMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static float callFloatMethod(String methodName, Object obj, Object[] params) {
		return callFloatMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static float callStaticFloatMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Float) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static float callStaticFloatMethod(String methodName, Class clazz, Object[] params) {
		return callStaticFloatMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static double callDoubleMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Double) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static double callDoubleMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callDoubleMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static double callDoubleMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callDoubleMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static double callDoubleMethod(String methodName, Object obj, Object[] params) {
		return callDoubleMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static double callStaticDoubleMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Double) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static double callStaticDoubleMethod(String methodName, Class clazz, Object[] params) {
		return callStaticDoubleMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static long callLongMethod(String methodName, Object obj, Class clazz, Class[] classParams, Object[] params) {
		return (Long) callObjectMethod(methodName, obj, clazz, classParams, params);
	}
	
	public static long callLongMethod(String methodName, Object obj, Class clazz, Object[] params) {
		return callLongMethod(methodName, obj, clazz, getClassParams(params), params);
	}
	
	public static long callLongMethod(String methodName, Object obj, Class[] classParams, Object[] params) {
		return callLongMethod(methodName, obj, obj.getClass(), classParams, params);
	}
	
	public static long callLongMethod(String methodName, Object obj, Object[] params) {
		return callLongMethod(methodName, obj, getClassParams(params), params);
	}
	
	public static long callStaticLongMethod(String methodName, Class clazz, Class[] classParams, Object[] params) {
		return (Long) callStaticObjectMethod(methodName, clazz, classParams, params);
	}
	
	public static long callStaticLongMethod(String methodName, Class clazz, Object[] params) {
		return callStaticLongMethod(methodName, clazz, getClassParams(params), params);
	}
	
	public static Class getClass(String className) {
		try {
			return Class.forName(className);
		}catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public static Class[] getClassParams(Object[] params) {
		Class[] classParams = new Class[params.length];
		for (int i = 0; i < classParams.length; i++) {
			classParams[i] = params[i].getClass();
		}
		return classParams;
	}
	
}
