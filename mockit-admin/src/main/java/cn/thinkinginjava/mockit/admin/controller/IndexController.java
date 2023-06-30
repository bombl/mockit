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

import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private IMockitServiceRegistryService iMockitServiceRegistryService;

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    @Resource
    private IMockitServiceMethodService iMockitServiceMethodService;


    @Resource
    private IMockitMethodMockDataService iMockitMethodMockDataService;


    /**
     * Controller method for handling the index page
     * @param model model
     * @return modelAndView
     */
    @RequestMapping("/")
    public ModelAndView index(Model model) {
        return new ModelAndView("forward:/index");
    }

    /**
     * Controller method for handling the index page
     * @param model model
     * @return index
     */
    @RequestMapping("/index")
    public String index0(Model model) {
        setStatistics(model);
        return "index";
    }

    /**
     * Controller method for handling the service page
     * @param model model
     * @return service
     */
    @RequestMapping("/service")
    public String service(Model model) {
        return "service";
    }

    /**
     * Controller method for handling the class page
     * @param model model
     * @return class
     */
    @RequestMapping("/class")
    public String clazz(Model model) {
        return "class";
    }

    /**
     * Controller method for handling the method page
     * @param model model
     * @return method
     */
    @RequestMapping("/method")
    public String method(Model model) {
        return "method";
    }

    /**
     * Controller method for handling the data page
     * @param model model
     * @return data
     */
    @RequestMapping("/data")
    public String mockData(Model model) {
        return "data";
    }

    /**
     * Method to set statistics in the provided model
     * @param model model
     */
    private void setStatistics(Model model) {
        LambdaQueryWrapper<MockitServiceRegistry> registryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registryLambdaQueryWrapper.eq(MockitServiceRegistry::getOnline, MockConstants.YES);
        registryLambdaQueryWrapper.eq(MockitServiceRegistry::getDeleted, MockConstants.NO);
        int onlineAliasNum = iMockitServiceRegistryService.count(registryLambdaQueryWrapper);

        LambdaQueryWrapper<MockitServiceClass> classLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classLambdaQueryWrapper.eq(MockitServiceClass::getDeleted, MockConstants.NO);
        int classNum = iMockitServiceClassService.count(classLambdaQueryWrapper);

        LambdaQueryWrapper<MockitServiceMethod> methodLambdaQueryWrapper = new LambdaQueryWrapper<>();
        methodLambdaQueryWrapper.eq(MockitServiceMethod::getDeleted, MockConstants.NO);
        int methodNum = iMockitServiceMethodService.count(methodLambdaQueryWrapper);

        LambdaQueryWrapper<MockitMethodMockData> mockDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mockDataLambdaQueryWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        int mockDataNum = iMockitMethodMockDataService.count(mockDataLambdaQueryWrapper);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("onlineAliasNum", onlineAliasNum);
        resultMap.put("classNum", classNum);
        resultMap.put("methodNum", methodNum);
        resultMap.put("mockDataNum", mockDataNum);
        model.addAttribute("resultMap", resultMap);
    }
}
