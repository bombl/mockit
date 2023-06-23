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

package cn.thinkinginjava.mockit.core.transformer;

import cn.thinkinginjava.mockit.core.exception.MockitException;
import cn.thinkinginjava.mockit.core.utils.ClassPoolUtil;
import javassist.CtClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.ProtectionDomain;

/**
 * ResultMockClassFileTransformer is a class that extends AbstractClassFileTransformer.
 * This class specializes in transforming class files to enable result mocking for testing purposes.
 * It inherits the functionality provided by the AbstractClassFileTransformer and implements the
 * necessary methods to perform the result mocking transformation.
 * ResultMockClassFileTransformer is responsible for modifying the bytecode of target classes to intercept
 * method calls and replace their results with predetermined values. This enables controlled testing scenarios
 * where specific results can be mocked and returned during runtime.
 * Usage:
 * Register an instance of ResultMockClassFileTransformer with the MockTransformerManager.
 * Apply the registered transformer to the target classes using the MockTransformerManager.
 * The transformed classes will now have result mocking capabilities.
 * Note: ResultMockClassFileTransformer is designed specifically for result mocking and may not be suitable
 * for other types of class transformations or modifications.
 */
public class CancelMockClassFileTransformer extends AbstractClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(CancelMockClassFileTransformer.class);

    /**
     * Constructs a new ResultMockClassFileTransformer with the specified full class name, method name, and mock value.
     * This constructor creates a new instance of ResultMockClassFileTransformer with the given full class name,
     * method name, and mock value. It is used to configure the transformer for intercepting method calls in the
     * specified class and replacing their results with the provided mock value during runtime.
     *
     * @param fullClassName The fully qualified name of the class to be transformed.
     */
    public CancelMockClassFileTransformer(String fullClassName) {
        super(fullClassName);
    }

    /**
     * Performs the actual transformation on the class bytecode.
     * This protected abstract method is responsible for performing the transformation
     * on the class bytecode. It is intended to be implemented by subclasses to provide
     * the custom transformation logic.
     *
     * @param loader              the defining loader of the class to be transformed,
     *                            may be <code>null</code> if the bootstrap loader
     * @param className           the name of the class in the internal form of fully
     *                            qualified class and interface names as defined in
     *                            <i>The Java Virtual Machine Specification</i>.
     *                            For example, <code>"java/util/List"</code>.
     * @param classBeingRedefined if this is triggered by a redefine or retransform,
     *                            the class being redefined or retransformed;
     *                            if this is a class load, <code>null</code>
     * @param protectionDomain    the protection domain of the class being defined or redefined
     * @param classfileBuffer     the input byte buffer in class file format - must not be modified
     * @param ctClass             The CtClass representation of the class being transformed.
     * @return The transformed bytecode as an array of bytes.
     */
    @Override
    protected byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer, CtClass ctClass) {
        try {
            ClassPoolUtil.removeCachedClass(className);
            return null;
        } catch (Exception e) {
            throw new MockitException("Transformation error occurred", e);
        }
    }

}
