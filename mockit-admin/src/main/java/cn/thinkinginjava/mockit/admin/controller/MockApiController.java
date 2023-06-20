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

import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.session.SessionHolder;
import cn.thinkinginjava.mockit.common.dto.CancelMockData;
import cn.thinkinginjava.mockit.common.dto.MockData;
import cn.thinkinginjava.mockit.common.enums.OptionTypeEnum;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("/api")
public class MockApiController {

    @GetMapping("/mock")
    public void mock(@RequestBody MockData mockData) {
        Map<String, List<Session>> sessionMap = SessionHolder.getSessionMap();
        mockData.setOptionType(OptionTypeEnum.MOCK.getType());
        String reqData = GsonUtil.toJson(mockData);
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(reqData);
        Session session = sessionMap.get("mockit-example").get(0);
        session.getChannel().writeAndFlush(responseFrame);
    }

    @GetMapping("/cancelMock")
    public void mock(@RequestBody CancelMockData cancelMockData) {
        Map<String, List<Session>> sessionMap = SessionHolder.getSessionMap();
        cancelMockData.setOptionType(OptionTypeEnum.CANCEL_MOCK.getType());
        String reqData = GsonUtil.toJson(cancelMockData);
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(reqData);
        Session session = sessionMap.get("mockit-example").get(0);
        session.getChannel().writeAndFlush(responseFrame);
    }
}
