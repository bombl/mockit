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
 * Base entity class representing common attributes for entities.
 */
public class BaseEntity {

    private String optionType;

    /**
     * Get the option type.
     *
     * @return The option type.
     */
    public String getOptionType() {
        return optionType;
    }

    /**
     * Set the option type.
     *
     * @param optionType The option type to set.
     */
    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }
}
