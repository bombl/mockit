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
import cn.thinkinginjava.mockit.client.model.ReturnType;
import cn.thinkinginjava.mockit.client.utils.ClassUtil;
import cn.thinkinginjava.mockit.client.utils.TypeParser;
import cn.thinkinginjava.mockit.common.model.dto.Message;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import com.github.houbb.data.factory.core.api.context.DefaultDataContext;
import com.github.houbb.data.factory.core.util.DataUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Message handler for messages of type GENERATE_DATA.
 * Annotated with @MessageType(MessageTypeEnum.GENERATE_DATA).
 */
@MessageType(MessageTypeEnum.GENERATE_DATA)
public class GenerateMockMessageHandler implements MessageHandler {

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
        ReturnType returnType = TypeParser.parseReturnTypeToTree(className);
        assembleResultData(returnType);
        sendMockDataMessage(ctx, repMessage, returnType.getData());
    }

    /**
     * Assembles the result data based on the ReturnType object.
     *
     * @param returnType The ReturnType object representing the return type structure.
     * @return The assembled result data as per the given ReturnType.
     * @throws Exception If an error occurs during the assembly process.
     */
    private Object assembleResultData(ReturnType returnType) throws Exception {
        String className = returnType.getClassName();
        if (returnType.getChildren() == null) {
            Object resultData = getResultData(className, null, null);
            returnType.setData(resultData);
            return resultData;
        }
        if (returnType.isMap() && returnType.getChildren().getChildren() == null) {
            Object resultData = getResultData(className,
                    returnType.getKeyClassName(), returnType.getChildren().getClassName());
            returnType.setData(resultData);
            return resultData;
        }
        if (returnType.getChildren().getChildren() == null) {
            Object resultData = getResultData(className,
                    returnType.getChildren().getClassName(), null);
            returnType.setData(resultData);
            return resultData;
        }
        if (returnType.isMap()) {
            Class<?> keyClass = loadTargetClass(returnType.getKeyClassName());
            Object keyValue = DataUtil.build(keyClass);
            Map<Object, Object> map = new HashMap<>();
            map.put(keyValue, assembleResultData(returnType.getChildren()));
            returnType.setData(map);
            return map;
        }
        List<Object> list = new ArrayList<>();
        list.add(assembleResultData(returnType.getChildren()));
        returnType.setData(list);
        return list;
    }

    /**
     * Gets the result data based on the provided class names.
     *
     * @param className The name of the main class.
     * @param keyClassName The name of the key class in case of a Map.
     * @param valueClassName The name of the value class in case of a Map.
     * @return The result data as per the given class names.
     * @throws ClassNotFoundException If the specified class is not found during the process.
     */
    private Object getResultData(String className, String keyClassName, String valueClassName) throws ClassNotFoundException {
        if (ClassUtil.isObjectClass(className)) {
            className = "java.lang.String";
        }
        if (ClassUtil.isObjectClass(keyClassName)) {
            keyClassName = "java.lang.String";
        }
        if (ClassUtil.isObjectClass(valueClassName)) {
            valueClassName = "java.lang.String";
        }
        DefaultDataContext dataContext = new DefaultDataContext();
        if (StringUtils.isBlank(keyClassName)) {
            return DataUtil.build(dataContext, loadTargetClass(className));
        }
        List<Class> classList = new ArrayList<>();
        classList.add(loadTargetClass(keyClassName));
        if (StringUtils.isNotBlank(valueClassName)) {
            classList.add(loadTargetClass(valueClassName));
        }
        dataContext.setGenericList(classList);
        return DataUtil.build(dataContext, loadTargetClass(className));
    }

    /**
     * Sends the mock data message to the specified ChannelHandlerContext.
     *
     * @param ctx The ChannelHandlerContext to which the message will be sent.
     * @param repMessage The original Message containing the request details.
     * @param resultData The mock data to be sent in the response message.
     */
    private void sendMockDataMessage(ChannelHandlerContext ctx, Message<String> repMessage, Object resultData) {
        Message<String> resMessage = new Message<>();
        resMessage.setData(GsonUtil.toJson(resultData));
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