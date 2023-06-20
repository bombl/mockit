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

import cn.thinkinginjava.mockit.common.dto.CancelMockData;
import cn.thinkinginjava.mockit.common.dto.MockData;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import cn.thinkinginjava.mockit.core.transformer.ResultMockClassFileTransformer;
import cn.thinkinginjava.mockit.core.transformer.manager.MockTransformerManager;

/**
 * Implementation of the OptionStrategy interface for handling the cancel mock option.
 */
public class CancelMockOptionStrategy implements OptionStrategy {

    /**
     * Executes the option strategy with the given text.
     *
     * @param text The text containing the option information.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void execute(String text) throws Exception {
        CancelMockData cancelMockData = GsonUtil.fromJson(text, CancelMockData.class);
        MockTransformerManager.transformer(
                new ResultMockClassFileTransformer(cancelMockData.getClassName(),
                        cancelMockData.getMethodName(), cancelMockData.getMethodContent()),
                Class.forName(cancelMockData.getClassName()));
    }
}