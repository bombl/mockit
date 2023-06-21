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

/**
 * Represents the mock data entity, extending the {@link BaseEntity}.
 */
public class MockData extends BaseEntity {

    private String className;
    private String methodName;
    private String mockValue;

    /**
     * Get the class name.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name.
     *
     * @param className The class name to set.
     */
    public void setClassName(String className) {
        this.className = className;
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
}