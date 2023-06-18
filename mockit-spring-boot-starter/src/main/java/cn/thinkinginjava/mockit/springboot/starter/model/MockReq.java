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

package cn.thinkinginjava.mockit.springboot.starter.model;

/**
 * The MockReq class represents a mock request object.
 * It encapsulates the properties of a request, such as the parameters.
 */
public class MockReq {

    /**
     * The className field represents the name of a class.
     * It is used to store the name of a class for further processing or reference within the code.
     */
    private String className;

    /**
     * The methodName field represents the name of a method.
     * It is used to store the name of a method for further processing or reference within the code.
     */
    private String methodName;

    /**
     * The mockValue field represents the mock value for testing or simulation purposes.
     * It is used to store the value that will be used as a mock or simulated value during the execution of certain operations.
     */
    private String mockValue;

    public MockReq() {
    }

    public MockReq(String className, String methodName, String mockValue) {
        this.className = className;
        this.methodName = methodName;
        this.mockValue = mockValue;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMockValue() {
        return mockValue;
    }

    public void setMockValue(String mockValue) {
        this.mockValue = mockValue;
    }
}