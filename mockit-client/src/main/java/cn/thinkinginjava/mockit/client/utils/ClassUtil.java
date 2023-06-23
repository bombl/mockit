package cn.thinkinginjava.mockit.client.utils;

import cn.thinkinginjava.mockit.core.utils.ClassPoolHolder;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

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
}
