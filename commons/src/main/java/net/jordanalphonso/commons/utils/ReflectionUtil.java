package net.jordanalphonso.commons.utils;

import net.jordanalphonso.commons.exception.WatchFaceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jordan.alphonso on 11/28/2016.
 */

public class ReflectionUtil {

    private ReflectionUtil() {
        //accessed statically
    }

    public static <T> void invokeMethod(Object bean, Class<T> clazz, String methodName) throws WatchFaceException {

        if (bean == null || clazz == null || StringUtils.isEmpty(methodName)) {
            return;
        }

        if (hasMethod(clazz, methodName)) {
            Method method = getMethod(clazz, methodName);
            try {
                method.invoke(bean, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new WatchFaceException(e.getMessage());
            }
        }
    }

    private static <T> Method getMethod(Class<T> clazz, String methodName) throws WatchFaceException {
        if (hasMethod(clazz, methodName)) {
            try {
                return clazz.getMethod(methodName, null);
            } catch (NoSuchMethodException e) {
                throw new WatchFaceException("Class: "+clazz.getSimpleName()
                        +" does not have method: "+methodName);
            }
        }
        throw new WatchFaceException("Class: "+clazz.getSimpleName()
                +" does not have method: "+methodName);
    }

    private static <T> boolean hasMethod(Class<T> clazz, String methodName) {
        boolean valid = false;

        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                valid = true;
            }
        }

        return valid;
    }
}
