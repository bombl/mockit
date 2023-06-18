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

package cn.thinkinginjava.mockit.springboot.starter.support;

import cn.thinkinginjava.mockit.core.transformer.ResultMockClassFileTransformer;
import cn.thinkinginjava.mockit.core.transformer.manager.MockTransformerManager;
import cn.thinkinginjava.mockit.springboot.starter.model.MockReq;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The MockServlet class extends the HttpServlet class and serves as a mock servlet.
 * It handles incoming HTTP requests and provides a simulated response for testing or development purposes.
 * Custom logic can be implemented in this class to simulate specific behaviors or generate dynamic responses.
 */
public class MockServlet extends HttpServlet {

    /**
     * The service() method is an overridden method from the HttpServlet class.
     * It is responsible for processing incoming HTTP requests and generating corresponding responses.
     * This method is invoked by the servlet container and should not be directly called by application code.
     * The HttpServletRequest object represents the incoming request, and the HttpServletResponse object represents the response to be sent back.
     * Custom logic can be implemented in this method to handle the request and generate the appropriate response.
     * @param req req
     * @param resp resp
     * @throws IOException exception
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MockReq mockReq = getMockReq(req);
        try {
            MockTransformerManager.transformer(
                    new ResultMockClassFileTransformer(mockReq.getClassName(),
                            mockReq.getMethodName(), mockReq.getMockValue()),
                    Class.forName(mockReq.getClassName())
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The getMockReq() method is used to create a MockReq object based on the provided HttpServletRequest object.
     * It extracts the relevant information from the HttpServletRequest object and initializes a new MockReq instance with that information.
     * The method throws an IOException if there is an error while reading the request or extracting the information.
     *
     * @param request The HttpServletRequest object representing the incoming request.
     * @return A MockReq object representing the mock request.
     * @throws IOException if there is an error while reading the request or extracting the information.
     */
    public MockReq getMockReq(HttpServletRequest request) throws IOException {

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseStrBuilder.toString(), MockReq.class);
    }

}
