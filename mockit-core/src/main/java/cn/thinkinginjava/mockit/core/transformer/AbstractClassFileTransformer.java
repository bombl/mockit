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

package cn.thinkinginjava.mockit.core.transformer;

import cn.thinkinginjava.mockit.core.exception.MockitException;
import cn.thinkinginjava.mockit.core.utils.ClassPoolHolder;
import javassist.CtClass;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Abstract base class for implementing a ClassFileTransformer.
 * This class provides a skeletal implementation of the ClassFileTransformer interface
 * and can be extended to create concrete class file transformer implementations.
 * It declares the class as abstract since it is intended to be subclassed and not instantiated directly.
 * Any subclass of this abstract class must implement the transform method defined in the ClassFileTransformer interface.
 * @see ClassFileTransformer
 */
public abstract class AbstractClassFileTransformer implements ClassFileTransformer {

    /**
     * The fully qualified class name.
     * This protected field holds the fully qualified class name of a Java class.
     * It is used to store and reference the complete name, including the package and class name,
     * of the class that is being manipulated or transformed.
     */
    protected String fullClassName;

    /**
     * Constructs an AbstractClassFileTransformer with the given fully qualified class name.
     * This constructor initializes an instance of the AbstractClassFileTransformer class
     * with the provided fully qualified class name. The fully qualified class name represents
     * the complete name, including the package and class name, of the class that will be transformed.
     *
     * @param fullClassName The fully qualified class name of the class to be transformed.
     */
    public AbstractClassFileTransformer(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    /**
     * The implementation of this method may transform the supplied class file and
     * return a new replacement class file.
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
     * @return                    The transformed bytecode as an array of bytes.
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            className = className.replace("/", ".");
            if (!fullClassName.equals(className)) {
                return classfileBuffer;
            }
            CtClass ctClass = ClassPoolHolder.getClassPool().get(className);
            if (ctClass.isFrozen()) {
                ctClass.defrost();
            }
            return doTransform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer, ctClass);
        } catch (Exception e) {
            throw new MockitException("Transformation error occurred", e);
        }
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
     * @return                    The transformed bytecode as an array of bytes.
     */
    protected abstract byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer, CtClass ctClass);

}
