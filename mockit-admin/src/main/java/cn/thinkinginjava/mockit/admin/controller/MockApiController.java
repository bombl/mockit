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

package cn.thinkinginjava.mockit.admin.controller;

import cn.thinkinginjava.mockit.admin.context.ResponseCallback;
import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.session.SessionHolder;
import cn.thinkinginjava.mockit.admin.utils.MessageUtil;
import cn.thinkinginjava.mockit.common.model.dto.CancelMockData;
import cn.thinkinginjava.mockit.common.model.dto.MockData;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import cn.thinkinginjava.mockit.common.model.dto.Message;
import cn.thinkinginjava.mockit.common.model.dto.MethodInfo;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MockApiController {

    private static final Logger logger = LoggerFactory.getLogger(MockApiController.class);

    /**
     * retrieves a list of method information based on the provided mock data.
     *
     * @param mockData The mock data containing the necessary information.
     * @return The result containing a list of method information.
     */
    @PostMapping("/methodList")
    public MockitResult<List<MethodInfo>> methodInfo(@RequestBody MockData mockData) {
        Message<String> sendMessage = new Message<>();
        sendMessage.setData(mockData.getClassName());
        sendMessage.setMessageType(MessageTypeEnum.GET_METHODS.getType());
        List<Session> sessions = getSessions(mockData.getAlias());
        Session session = sessions.get(0);
        ResponseCallback responseCallback = MessageUtil.sendMessage(session.getChannel(), sendMessage);
        String response = responseCallback.getResponse();
        Message<List<MethodInfo>> repMessage = GsonUtil.fromJsonToMessageList(response, MethodInfo.class);
        return MockitResult.successful(repMessage.getData());
    }

    /**
     * Controller method that performs mocking based on the provided mock data.
     *
     * @param mockData The mock data containing the necessary information.
     * @return The result containing the mocked response as a string.
     */
    @PostMapping("/mock")
    public MockitResult<String> mock(@RequestBody MockData mockData) {
        Message<MockData> sendMessage = new Message<>();
        sendMessage.setData(mockData);
        sendMessage.setMessageType(MessageTypeEnum.MOCK.getType());
        List<Session> sessions = getSessions(mockData.getAlias());
        sessions.forEach(session -> {
            try {
                ResponseCallback responseCallback = MessageUtil.sendMessage(session.getChannel(), sendMessage);
                // TODO UPDATE DB
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        return MockitResult.successful();
    }

    /**
     * Controller method that performs mocking cancellation based on the provided cancel mock data.
     *
     * @param cancelMockData The cancel mock data containing the necessary information.
     * @return The result containing the status of the mock cancellation as a string.
     */
    @PostMapping("/cancelMock")
    public MockitResult<String> cancelMock(@RequestBody CancelMockData cancelMockData) {
        Message<CancelMockData> sendMessage = new Message<>();
        sendMessage.setData(cancelMockData);
        sendMessage.setMessageType(MessageTypeEnum.CANCEL_MOCK.getType());
        List<Session> sessions = getSessions(cancelMockData.getAlias());
        sessions.forEach(session -> {
            try {
                ResponseCallback responseCallback = MessageUtil.sendMessage(session.getChannel(), sendMessage);
                // TODO UPDATE DB
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        return MockitResult.successful();
    }

    private List<Session> getSessions(String alias) {
        Map<String, List<Session>> sessionMap = SessionHolder.getSessionMap();
        if (MapUtils.isEmpty(sessionMap)
                || CollectionUtils.isEmpty(sessionMap.get(alias))) {
            throw new MockitException("Alias is not online.");
        }
        return sessionMap.get(alias);
    }
}
