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

package cn.thinkinginjava.mockit.common.model.dto;

/**
 * Represents the cancel mock data entity.
 */
public class CancelMockData {

    private String alias;

    private String className;

    /**
     * Get the alias name.
     *
     * @return The alias name.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Set the alias name.
     *
     * @param alias The alias name to set.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

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

}