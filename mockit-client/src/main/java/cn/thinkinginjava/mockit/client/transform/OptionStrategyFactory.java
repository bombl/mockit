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

package cn.thinkinginjava.mockit.client.transform;

import cn.thinkinginjava.mockit.common.enums.OptionTypeEnum;

/**
 * Factory class for creating option strategies.
 */
public class OptionStrategyFactory {

    /**
     * Creates an instance of the option strategy based on the provided option type enum.
     *
     * @param optionTypeEnum The option type enum indicating the type of the strategy to create.
     * @return An instance of the corresponding option strategy.
     */
    public static OptionStrategy createOptionStrategy(OptionTypeEnum optionTypeEnum) {
        switch (optionTypeEnum) {
            case MOCK:
                return new MockOptionStrategy();
            case CANCEL_MOCK:
                return new CancelMockOptionStrategy();
            default:
                throw new IllegalArgumentException("Unsupported option type: " + optionTypeEnum);
        }
    }
}