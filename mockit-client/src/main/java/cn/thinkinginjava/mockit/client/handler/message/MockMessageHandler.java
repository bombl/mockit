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

package cn.thinkinginjava.mockit.client.handler.message;

import cn.thinkinginjava.mockit.client.annotation.MessageType;
import cn.thinkinginjava.mockit.client.utils.ClassUtil;
import cn.thinkinginjava.mockit.common.model.dto.Message;
import cn.thinkinginjava.mockit.common.model.dto.MethodMockData;
import cn.thinkinginjava.mockit.common.model.dto.MockData;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import cn.thinkinginjava.mockit.core.model.MockInfo;
import cn.thinkinginjava.mockit.core.model.MockMethodInfo;
import cn.thinkinginjava.mockit.core.transformer.CancelMockClassFileTransformer;
import cn.thinkinginjava.mockit.core.transformer.ResultMockClassFileTransformer;
import cn.thinkinginjava.mockit.core.transformer.manager.MockTransformerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.SUCCESS;

/**
 * Implementation of the OptionStrategy interface for handling the mock option.
 */
@MessageType(MessageTypeEnum.MOCK)
public class MockMessageHandler implements MessageHandler {

    /**
     * Executes the option strategy with the given text.
     *
     * @param text The text containing the option information.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void handle(ChannelHandlerContext ctx, String text) throws Exception {
        Message<MockData> repMessage = GsonUtil.fromJsonToMessage(text, MockData.class);
        MockData mockData = repMessage.getData();
        if (mockData == null || StringUtils.isEmpty(mockData.getClassName())) {
            return;
        }
        Class<?> targetClass = loadTargetClass(mockData.getClassName());
        CancelMockClassFileTransformer cancelTransformer = new CancelMockClassFileTransformer(mockData.getClassName());
        MockTransformerManager.reduction(cancelTransformer, targetClass);

        ResultMockClassFileTransformer transformer = createTransformer(mockData);
        MockTransformerManager.transformer(transformer, targetClass);
        Message<String> resMessage = new Message<>();
        resMessage.setData(SUCCESS);
        resMessage.setRequestId(repMessage.getRequestId());
        String json = GsonUtil.toJson(resMessage);
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(json);
        ctx.writeAndFlush(responseFrame);
    }

    /**
     * Creates a ResultMockClassFileTransformer based on the provided MockData.
     *
     * @param mockData The MockData object containing the necessary information.
     * @return The created ResultMockClassFileTransformer.
     */
    private ResultMockClassFileTransformer createTransformer(MockData mockData) throws NotFoundException {
        MockInfo mockInfo = new MockInfo();
        mockInfo.setClassName(mockData.getClassName());
        List<MockMethodInfo> mockMethodInfoList = new ArrayList<>();
        List<MethodMockData> methodMockDataList = mockData.getMethodMockDataList();
        for (MethodMockData methodMockData : methodMockDataList) {
            MockMethodInfo mockMethodInfo = new MockMethodInfo();
            mockMethodInfo.setMethodName(methodMockData.getMethodName());
            mockMethodInfo.setMockValue(methodMockData.getMockValue());
            mockMethodInfo.setCtClasses(ClassUtil.getCtClasses(methodMockData.getParameters()));
            mockMethodInfoList.add(mockMethodInfo);
        }
        mockInfo.setMockMethodInfoList(mockMethodInfoList);
        return new ResultMockClassFileTransformer(mockInfo);
    }

    /**
     * Loads the target class based on the stored MockData.
     *
     * @param className The class name.
     * @return The loaded target class.
     * @throws ClassNotFoundException if the target class is not found.
     */
    private Class<?> loadTargetClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

}