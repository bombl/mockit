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

package cn.thinkinginjava.mockit.core.transformer.manager;

import cn.thinkinginjava.mockit.common.exception.MockitException;
import cn.thinkinginjava.mockit.core.instrumentation.InstrumentationHolder;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * MockTransformerManager is a class responsible for managing and applying class transformers for mocking purposes.
 * This class provides functionality to register, unregister, and apply class transformers that modify
 * the bytecode of target classes for mocking during runtime. It acts as a central manager for maintaining
 * the state of class transformers and facilitating their application to the target classes.
 * Usage:
 * Register the desired class transformers using the registerTransformer method.
 * Apply the registered transformers to the target classes using the applyTransformers method.
 * Unregister the transformers when they are no longer needed using the unregisterTransformer method.
 * Note: The transformations performed by the registered transformers may alter the behavior or properties
 * of the target classes to simulate specific scenarios or behavior during testing or mocking.
 */
public class MockTransformerManager {

    /**
     * Applies a ClassFileTransformer to the specified classes.
     * This public static method applies the given ClassFileTransformer to the provided classes.
     * It enables the transformation of the bytecode of the specified classes using the supplied transformer.
     *
     * @param classFileTransformer The ClassFileTransformer to be applied to the classes.
     * @param classes The classes to be transformed.
     */
    public static void transformer(ClassFileTransformer classFileTransformer, Class<?>... classes) {
        Instrumentation instrumentation = InstrumentationHolder.getInstrumentation();
        try {
            instrumentation.addTransformer(classFileTransformer, true);
            if (classes.length > 0) {
                instrumentation.retransformClasses(classes);
            }
        } catch (Exception e) {
            throw new MockitException("Transformation error occurred", e);
        } finally {
            instrumentation.removeTransformer(classFileTransformer);
        }
    }

    public static void reduction(ClassFileTransformer classFileTransformer, Class<?>... classes) {
        transformer(classFileTransformer, classes);
    }
}
