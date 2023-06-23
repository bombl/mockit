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

package cn.thinkinginjava.mockit.core.model;

import javassist.CtClass;

/**
 * Represents mock data for a method.
 */
public class MockMethodInfo {

    private String methodName;
    private CtClass[] ctClasses;
    private String mockValue;

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
     * Get the mock value.
     *
     * @return The mock value.
     */
    public String getMockValue() {
        return mockValue;
    }

    /**
     * Set the mock value.
     *
     * @param mockValue The mock value to set.
     */
    public void setMockValue(String mockValue) {
        this.mockValue = mockValue;
    }

    /**
     * Gets the array of CtClass objects.
     *
     * @return The array of CtClass objects.
     */
    public CtClass[] getCtClasses() {
        return ctClasses;
    }

    /**
     * Sets the array of CtClass objects.
     *
     * @param ctClasses The array of CtClass objects to set.
     */
    public void setCtClasses(CtClass[] ctClasses) {
        this.ctClasses = ctClasses;
    }
}
