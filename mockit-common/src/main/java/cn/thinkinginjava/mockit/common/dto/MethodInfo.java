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

package cn.thinkinginjava.mockit.common.dto;

import java.io.Serializable;
import java.util.List;

import java.io.Serializable;
import java.util.List;

/**
 * Information about a method.
 */
public class MethodInfo implements Serializable {
    private static final long serialVersionUID = -7681499920336107506L;

    private String accessModifier;
    private String returnType;
    private String methodName;
    private List<String> parameters;
    private byte[] methodContent;

    /**
     * Default constructor.
     */
    public MethodInfo() {
    }

    /**
     * Get the access modifier.
     *
     * @return The access modifier.
     */
    public String getAccessModifier() {
        return accessModifier;
    }

    /**
     * Set the access modifier.
     *
     * @param accessModifier The access modifier to set.
     */
    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Get the return type.
     *
     * @return The return type.
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Set the return type.
     *
     * @param returnType The return type to set.
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * Get the method name.
     *
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set the method name.
     *
     * @param methodName The method name to set.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Get the list of parameters.
     *
     * @return The list of parameters.
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Set the list of parameters.
     *
     * @param parameters The list of parameters to set.
     */
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Get the method content.
     *
     * @return The method content.
     */
    public byte[] getMethodContent() {
        return methodContent;
    }

    /**
     * Set the method content.
     *
     * @param methodContent The method content to set.
     */
    public void setMethodContent(byte[] methodContent) {
        this.methodContent = methodContent;
    }
}

