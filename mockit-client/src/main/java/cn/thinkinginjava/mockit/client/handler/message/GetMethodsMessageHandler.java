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
import cn.thinkinginjava.mockit.common.model.dto.Message;
import cn.thinkinginjava.mockit.common.model.dto.MethodInfo;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import cn.thinkinginjava.mockit.common.utils.MethodUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Implementation of the OptionStrategy interface for handling the mock option.
 */
@MessageType(MessageTypeEnum.GET_METHODS)
public class GetMethodsMessageHandler implements MessageHandler {

    /**
     * Executes the option strategy with the given text.
     *
     * @param text The text containing the option information.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void handle(ChannelHandlerContext ctx, String text) throws Exception {
        Message<String> repMessage = GsonUtil.fromJsonToMessage(text, String.class);
        String className = repMessage.getData();
        if (StringUtils.isEmpty(className)) {
            return;
        }
        Class<?> targetClass = loadTargetClass(className);
        Method[] declaredMethods = targetClass.getDeclaredMethods();
        List<MethodInfo> methodInfoList = MethodUtil.convertMethods(declaredMethods);
        Message<List<MethodInfo>> resMessage = new Message<>();
        resMessage.setData(methodInfoList);
        resMessage.setRequestId(repMessage.getRequestId());
        String json = GsonUtil.toJson(resMessage);
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(json);
        ctx.writeAndFlush(responseFrame);
    }

    /**
     * Loads the target class based on the stored MockData.
     *
     * @param className The MockData object containing the necessary information.
     * @return The loaded target class.
     * @throws ClassNotFoundException if the target class is not found.
     */
    private Class<?> loadTargetClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

}