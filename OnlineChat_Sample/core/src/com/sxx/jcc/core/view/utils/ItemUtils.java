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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General utilities to process the Collecton of Beans or the Collection of
 * Maps. Most methods wrap or add value to the commons Beanutils.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class ItemUtils {

    private static final Logger logger = LoggerFactory.getLogger(ItemUtils.class);
    public static final String JMESA_ITEM = "jmesa-item";

    private ItemUtils() {
        // hide constructor
    }

    /**
     * Get the value from the Bean or Map by property.
     * 
     * @param item The Bean or Map.
     * @param property The Bean attribute or Map key.
     * @return The value from the Bean or Map.
     */
    public static Object getItemValue(Object item, String property) {
        Object itemValue = null;

        try {
            if (item instanceof Map) {
                itemValue = ((Map<?, ?>) item).get(property);
                if (itemValue != null) {
                    return itemValue;
                }

                // ports such as the tags will store the original bean
                Object bean = ((Map<?, ?>) item).get(JMESA_ITEM);

                if (bean == null) {
                    logger.debug("the map does not have property " + property);
                    return null;
                }

                itemValue = getItemValue(bean, property);
            } else {
                itemValue = PropertyUtils.getProperty(item, property);
            }
        } catch (Exception e) {
            logger.debug("item class " + item.getClass().getName() + " does not have property " + property);
        }

        return itemValue;
    }

    /**
     * Get the Class for the property.
     * 
     * @param items The Collection of Beans or Maps.
     * @param property The Bean attribute or Map key.
     * @return The Class for the property.
     */
    public static Class<?> getPropertyClassType(Collection<?> items, String property)
        throws Exception {

        Object item = items.iterator().next();

        if (item instanceof Map) {
            for (Object object : items) {
                Map<?, ?> map = (Map<?, ?>) object;
                Object val = map.get(property);

                if (val == null) {
                    continue;
                }

                return val.getClass();
            }
        }

        Class<?> type = null;
        try {
            type = PropertyUtils.getPropertyType(item, property);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Had problems getting property type by object, trying reflection...");
            }
            type = BeanUtils.getPropertyType(item, property);
        }
        return type;
    }
}