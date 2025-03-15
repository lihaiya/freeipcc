/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sxx.jcc.core.view.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Utilities for java bean.
 *
 * @author wliu
 */
public class BeanUtils {

    private static final Logger logger = Logger.getLogger(BeanUtils.class);
    
    /**
     * the possible prefixes for read method
     */
    private static final String[] getterPrefixes = { "get", "is"};
    
    /**
     * Gets the type of a property of an object.
     * 
     * @param object The object that the property belongs to, cannot be null.
     * @param property The property to get type, can be nested. for example, 'foo.bar.baz'.
     * @return The type of the property. If the property doesn't exists in the object, returns null.
     */
    public static Class getPropertyType(Object object, String property) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null.");
        }
        return getPropertyType(object.getClass(), property);
    }

    /**
     * Gets the type of a property of a class.
     * 
     * @param clazz The class that the property belongs to, cannot be null.
     * @param property The property to get type, can be nested. for example, 'foo.bar.baz'.
     * @return The type of the property. If the property doesn't exists in the clazz, returns null.
     */
    public static Class getPropertyType(Class clazz, String property) {
        if (clazz == null) {
            throw new IllegalArgumentException("Clazz cannot be null.");
        }

        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null.");
        }

        int dotIndex = property.lastIndexOf('.');

        if (dotIndex == -1) {
            Method method = getReadMethod(clazz, property);
            return method == null ? null : method.getReturnType();
        } 
            
        String deepestProperty = property.substring(dotIndex + 1);
        String parentProperty = property.substring(0, dotIndex);
        return getPropertyType(getPropertyType(clazz, parentProperty), deepestProperty);
    }
    
    /**
     * Gets the read method for a property in a class.
     * 
     * for example: <code>
     * class Foo {
     *      public String getBar() { return "bar"; } 
     *      public Boolean isBaz() { return false; }
     * }
     * 
     * BeanUtils.getReadMethod(Foo.class, "bar"); // return Foo#getBar()
     * BeanUtils.getReadMethod(Foo.class, "baz"); // return Foo#isBaz()
     * BeanUtils.getReadMethod(Foo.class, "baa"); // return null
     * </code>
     * 
     * @param clazz The class to get read method.
     * @param property The property to get read method for, can NOT be nested.
     * @return The read method (getter) for the property, if there is no read method for
     *          the property, returns null.
     */
    public static Method getReadMethod(Class clazz, String property) {
        // Capitalize the property 
        StringBuffer buf = new StringBuffer();
        buf.append(property.substring(0, 1).toUpperCase());
        if (property.length() > 1) {
            buf.append(property.substring(1));
        }
        
        Method method = null;
        for (String prefix : getterPrefixes) {
            String methodName = prefix + buf.toString();
            try {
                method = clazz.getMethod(methodName);
                
                // Once get method successfully, jump out the loop. 
                break;
            } catch (NoSuchMethodException e) {
                // do nothing but logging
                logger.debug("No such read method '" + methodName + "()' in class '" +
                        clazz.getName() + "'.");
            } catch (SecurityException e) {
                // do nothing but logging
                logger.debug("Error occurs while getting read method '" + methodName + "()' in class '" +
                        clazz.getName() + "'.");
            }
        }
        
        return method;
    }
}