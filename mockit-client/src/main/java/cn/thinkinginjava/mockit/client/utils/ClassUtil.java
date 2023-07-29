package cn.thinkinginjava.mockit.client.utils;

import cn.thinkinginjava.mockit.core.utils.ClassPoolHolder;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Utility class for working with classes.
 */
public class ClassUtil {

    /**
     * Get an array of CtClasses corresponding to the given list of fully qualified class names.
     *
     * @param parameters The list of class names.
     * @return An array of CtClasses.
     * @throws NotFoundException If any of the classes cannot be found.
     */
    public static CtClass[] getCtClasses(List<String> parameters) throws NotFoundException {
        CtClass[] ctClasses = new CtClass[parameters.size()];
        ClassPool classPool = ClassPool.getDefault();
        for (int i = 0; i < parameters.size(); i++) {
            CtClass ctClazz = classPool.get(parameters.get(i));
            ctClasses[i] = ctClazz;
        }
        return ctClasses;
    }

    /**
     * Retrieves the Class object based on the given class name.
     *
     * @param className The fully qualified name of the class.
     * @return The Class object representing the specified class, or null if the class is not found.
     */
    public static Class<?> getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if the given class name represents the Object class.
     *
     * @param className The fully qualified name of the class to be checked.
     * @return true if the class name represents the Object class, false otherwise.
     */
    public static boolean isObjectClass(String className) {
        if (StringUtils.isBlank(className)) {
            return false;
        }
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.equals(Object.class);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
