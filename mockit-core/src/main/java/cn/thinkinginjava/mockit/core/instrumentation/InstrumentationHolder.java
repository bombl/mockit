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

package cn.thinkinginjava.mockit.core.instrumentation;

import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.instrument.Instrumentation;

/**
 * A holder class that provides a singleton instance of the Instrumentation.
 * The Instrumentation is a mechanism provided by the Java Virtual Machine (JVM) to allow
 * instrumentation of Java bytecode at runtime. This holder ensures that only one instance
 * of the Instrumentation is created and shared across the application.
 * Access to the Instrumentation instance can be obtained through the getInstance() method.
 */
public class InstrumentationHolder {

    /**
     * The singleton instance of the Instrumentation.
     * This field holds the reference to the shared Instrumentation object.
     * It is declared as private and final, indicating that it cannot be modified once initialized.
     */
    private final static Instrumentation instrumentation;

    /**
     * Static initialization block for the Instrumentation holder.
     * This block is responsible for installing the ByteBuddyAgent and assigning the returned Instrumentation
     * instance to the static field 'instrumentation'. It is executed once when the class is loaded,
     * ensuring that the Instrumentation is set up and available for use.
     */
    static {
        instrumentation = ByteBuddyAgent.install();
    }

    /**
     * Retrieves the singleton instance of the Instrumentation.
     * This method returns the shared instance of the Instrumentation object.
     * It provides access to the Instrumentation functionality for runtime bytecode instrumentation.
     *
     * @return The singleton instance of the Instrumentation.
     */
    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
