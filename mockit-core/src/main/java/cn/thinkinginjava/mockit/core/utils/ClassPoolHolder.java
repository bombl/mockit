/**
 *
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.core.utils;

import javassist.ClassPool;
import javassist.LoaderClassPath;

/**
 * A holder class that provides a singleton instance of the ClassPool.
 * The ClassPool is responsible for managing a pool of Java class definitions.
 * This holder ensures that only one instance of the ClassPool is created and shared across the application.
 * Access to the ClassPool instance can be obtained through the getInstance() method.
 */
public class ClassPoolHolder {

    /**
     * The singleton instance of the ClassPool.
     * This field holds the reference to the shared ClassPool object.
     * It is declared as private and final, indicating that it cannot be modified once initialized.
     */
    private final static ClassPool classPool;

    /**
     * Static initialization block for the ClassPool holder.
     * This block initializes the ClassPool by obtaining the default instance
     * and adding the classpath of the current thread's context class loader.
     * It is executed once when the class is loaded, ensuring that the ClassPool
     * is set up with the necessary configuration.
     */
    static {
        classPool = ClassPool.getDefault();
        classPool.insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }

    /**
     * Retrieves the singleton instance of the ClassPool.
     * This method returns the shared instance of the ClassPool object,
     * providing access to the ClassPool functionality for managing a pool of Java class definitions.
     *
     * @return The singleton instance of the ClassPool.
     */
    public static ClassPool getClassPool() {
        return classPool;
    }
}
