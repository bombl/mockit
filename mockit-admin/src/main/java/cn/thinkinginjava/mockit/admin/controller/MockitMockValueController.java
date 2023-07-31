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

import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a controller for managing MockitServiceMethod.
 */
@Controller
@RequestMapping("/mock")
public class MockitMockValueController {

    @Resource
    private IMockitMethodMockDataService iMockitMethodMockDataService;

    @Resource
    private IMockitServiceMethodService iMockitServiceMethodService;

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    @Resource
    private IMockitServiceRegistryService iMockitServiceRegistryService;

    /**
     * Saves or updates a MockitServiceMethod based on the provided MockitServiceMethodDTO.
     *
     * @param mockitMethodMockDataDTO The DTO object containing the information of the MockitServiceMethod to be saved or updated.
     * @return A MockitResult object indicating the result of the save or update operation.
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public MockitResult<Void> saveOrUpdate(@Valid @RequestBody MockitMethodMockDataDTO mockitMethodMockDataDTO) {
        iMockitMethodMockDataService.saveOrUpdateMethod(mockitMethodMockDataDTO);
        MockitServiceMethod mockitServiceMethod = iMockitServiceMethodService.getById(mockitMethodMockDataDTO.getMethodId());
        MockitServiceClass serviceClass = iMockitServiceClassService.getById(mockitServiceMethod.getClassId());
        List<String> ids = new ArrayList<>();
        ids.add(serviceClass.getServiceId());
        BatchCommonDTO batchCommonDTO = new BatchCommonDTO();
        batchCommonDTO.setIds(ids);
        iMockitServiceRegistryService.mock(batchCommonDTO);
        return MockitResult.successful();
    }

    /**
     * Updates the batchEnabled status for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     * @return A MockitResult object indicating the result of the operation.
     */
    @RequestMapping("/batchEnabled")
    @ResponseBody
    public MockitResult<Void> enabled(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitMethodMockDataService.batchEnabled(batchCommonDTO);
        doMock(batchCommonDTO);
        return MockitResult.successful();
    }

    /**
     * Deletes a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be deleted.
     * @return A MockitResult object indicating the result of the deletion operation.
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    public MockitResult<Void> delete(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitMethodMockDataService.batchDelete(batchCommonDTO);
        doMock(batchCommonDTO);
        return MockitResult.successful();
    }

    /**
     * Private method to perform mock functionality using the provided BatchCommonDTO object.
     *
     * @param batchCommonDTO The BatchCommonDTO object obtained from the HTTP request body.
     *                       This object will be validated using the Spring validation framework
     *                       due to the @Valid annotation.
     */
    private void doMock(@RequestBody @Valid BatchCommonDTO batchCommonDTO) {
        List<MockitServiceMethod> methodList = iMockitServiceMethodService.listByIds(batchCommonDTO.getIds());
        List<String> classIds = methodList.stream().map(MockitServiceMethod::getClassId).collect(Collectors.toList());
        List<MockitServiceClass> serviceClassList = iMockitServiceClassService.listByIds(classIds);
        List<String> serviceIds = serviceClassList.stream().map(MockitServiceClass::getServiceId).collect(Collectors.toList());
        batchCommonDTO.setIds(serviceIds);
        iMockitServiceRegistryService.mock(batchCommonDTO);
    }
}
