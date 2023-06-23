/**
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.core.utils;

import javassist.ClassPool;

import java.lang.reflect.Method;

/**
 * Utility class for managing ClassPool.
 */
public class ClassPoolUtil {

    /**
     * Remove the cached class with the given class name from the ClassPool.
     *
     * @param className The name of the class to remove from the cache.
     * @throws Exception If an exception occurs during the removal process.
     */
    public static void removeCachedClass(String className) throws Exception {
        Class<?> classPoolClass = Class.forName("javassist.ClassPool");
        Method method = classPoolClass.getDeclaredMethod("removeCached", String.class);
        method.setAccessible(true);
        method.invoke(ClassPool.getDefault(), className);
    }
}

