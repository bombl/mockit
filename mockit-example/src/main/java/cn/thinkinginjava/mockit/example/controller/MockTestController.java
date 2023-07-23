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

package cn.thinkinginjava.mockit.example.controller;

import cn.thinkinginjava.mockit.example.model.ResultDTO;
import cn.thinkinginjava.mockit.example.service.MockTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The MockTestController class is a controller class that handles HTTP requests related to mock testing.
 * It contains methods annotated with @RequestMapping or other relevant annotations to map specific URLs and handle corresponding requests.
 * This controller class is responsible for processing mock testing requests and returning appropriate responses.
 * It may interact with other components or services to perform the necessary operations for mock testing.
 */
@RestController
@RequestMapping()
public class MockTestController {

    @Autowired
    private MockTestService mockTestService;

    /**
     * The hello() method is a controller method that handles the HTTP request for the "hello" endpoint.
     * It accepts a String parameter representing the name and returns a ResultDTO object as the response.
     * This method is responsible for processing the request and generating the appropriate response.
     * It may perform business logic, call other services or components, and construct the ResultDTO object with the necessary data.
     *
     * @param name The name parameter passed in the request.
     * @return A ResultDTO object containing the result of the "hello" operation.
     */
    @GetMapping("hello")
    public ResultDTO hello(String name) {
        return mockTestService.say(name);
    }
    @GetMapping("hello2")
    public Integer hello2(String name) {
        return mockTestService.say2(name);
    }
    @GetMapping("hello3")
    public List<ResultDTO> hello3(String name) {
        return mockTestService.say3(name);
    }
    @GetMapping("hello4")
    public String hello4(String name) {
        return mockTestService.say4(name);
    }
    @GetMapping("hello5")
    public Map<String,Object> hello5(String name) {
        return mockTestService.say5(name);
    }
}
