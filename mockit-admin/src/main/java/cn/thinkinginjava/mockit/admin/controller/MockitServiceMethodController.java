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

import cn.thinkinginjava.mockit.admin.model.dto.*;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.enums.EnabledStatusEnum;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceMethodVO;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a controller for managing MockitServiceMethod.
 */
@Controller
@RequestMapping("/method")
public class MockitServiceMethodController {

    @Resource
    private IMockitServiceMethodService iMockitServiceMethodService;

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    @Resource
    private IMockitServiceRegistryService iMockitServiceRegistryService;

    @Resource
    private IMockitMethodMockDataService iMockitMethodMockDataService;

    /**
     * Saves or updates a MockitServiceMethod based on the provided MockitServiceMethodDTO.
     *
     * @param mockitMethodDTO The DTO object containing the information of the MockitServiceMethod to be saved or updated.
     * @return A MockitResult object indicating the result of the save or update operation.
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public MockitResult<Void> saveOrUpdate(@Valid @RequestBody MockitMethodDTO mockitMethodDTO) {
        List<MockitServiceMethodDTO> mockitMethodList = mockitMethodDTO.getMockitMethodList();
        for (MockitServiceMethodDTO mockitServiceMethodDTO : mockitMethodList) {
            iMockitServiceMethodService.saveOrUpdateMethod(mockitServiceMethodDTO);
        }
        return MockitResult.successful();
    }

    /**
     * Retrieves a paginated list of MockitServiceMethodVO objects based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return A MockitResult object encapsulating the paginated list of MockitServiceMethodVO objects.
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(@RequestBody MockitServiceMethodDTO mockitServiceMethodDTO) {
        if (mockitServiceMethodDTO.getCurrentPage() == null) {
            throw new MockitException("currentPage can not empty.");
        }
        if (mockitServiceMethodDTO.getPageSize() == null) {
            throw new MockitException("pageSize can not empty.");
        }
        LambdaQueryWrapper<MockitServiceClass> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(mockitServiceMethodDTO.getServiceId())) {
            queryWrapper.eq(MockitServiceClass::getServiceId, mockitServiceMethodDTO.getServiceId());
        }
        queryWrapper.eq(MockitServiceClass::getDeleted, MockConstants.NO);
        List<MockitServiceClass> mockitServiceClassList = iMockitServiceClassService.list(queryWrapper);
        if (CollectionUtils.isEmpty(mockitServiceClassList)) {
            return new HashMap<>();
        }
        List<String> classIdList = mockitServiceClassList.stream().filter(mockitServiceClass -> {
            if (StringUtils.isNotBlank(mockitServiceMethodDTO.getClassName())) {
                return mockitServiceClass.getClassName().contains(mockitServiceMethodDTO.getClassName());
            }
            return Boolean.TRUE;
        }).map(MockitServiceClass::getId).collect(Collectors.toList());
        mockitServiceMethodDTO.setClassIdList(classIdList);
        if (CollectionUtils.isEmpty(classIdList)) {
            return new HashMap<>();
        }
        IPage<MockitServiceMethodVO> page = iMockitServiceMethodService.listByPage(mockitServiceMethodDTO);
        page.convert(mockitServiceMethod -> {
            MockitServiceMethodVO mockitServiceMethodVO = new MockitServiceMethodVO();
            BeanUtils.copyProperties(mockitServiceMethod, mockitServiceMethodVO);
            if (mockitServiceMethodDTO.getIsMockData() != null && mockitServiceMethodDTO.getIsMockData()) {
                LambdaQueryWrapper<MockitMethodMockData> dataQueryWrapper = new LambdaQueryWrapper<>();
                dataQueryWrapper.eq(MockitMethodMockData::getMethodId,mockitServiceMethod.getId());
                dataQueryWrapper.eq(MockitMethodMockData::getDeleted,MockConstants.NO);
                if (mockitServiceMethodDTO.getMockEnabled() != null) {
                    dataQueryWrapper.eq(MockitMethodMockData::getMockEnabled, mockitServiceMethodDTO.getMockEnabled());
                }
                dataQueryWrapper.last("limit 1");
                MockitMethodMockData mockData = iMockitMethodMockDataService.getOne(dataQueryWrapper);
                if (mockData != null) {
                    mockitServiceMethodVO.setMockEnabledMc(EnabledStatusEnum.getByValue(mockData.getMockEnabled()));
                    mockitServiceMethodVO.setCreateTime(DateFormatUtils.format(mockData.getCreateAt(), MockConstants.Y_M_D));
                    mockitServiceMethodVO.setRemarks(mockData.getRemarks());
                    mockitServiceMethodVO.setMockValue(mockData.getMockValue());
                } else {
                    mockitServiceMethodVO.setMockEnabledMc("");
                    mockitServiceMethodVO.setRemarks("");
                    mockitServiceMethodVO.setCreateTime("");
                }
            }
            MockitServiceClass mockitServiceClass = iMockitServiceClassService.getById(mockitServiceMethod.getClassId());
            if (mockitServiceClass != null) {
                mockitServiceMethodVO.setClassName(mockitServiceClass.getClassName());
                MockitServiceRegistry mockitService = iMockitServiceRegistryService.getById(mockitServiceClass.getServiceId());
                mockitServiceMethodVO.setAlias(mockitService.getAlias());
            }
            return mockitServiceMethodVO;
        });
        Map<String, Object> map = new HashMap<>();
        map.put("recordsTotal", page.getRecords().size());
        map.put("recordsFiltered", page.getRecords().size());
        map.put("data", page.getRecords());
        return map;
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
        iMockitServiceMethodService.batchEnabled(batchCommonDTO);
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
        iMockitServiceMethodService.batchDelete(batchCommonDTO);
        return MockitResult.successful();
    }

    @RequestMapping("/listMethod")
    @ResponseBody
    public MockitResult<List<MockitServiceMethodVO>> listMethod(@RequestBody MockitServiceMethodDTO mockitServiceMethodDTO) {
        LambdaQueryWrapper<MockitServiceMethod> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MockitServiceMethod::getClassId,mockitServiceMethodDTO.getClassId());
        queryWrapper.eq(MockitServiceMethod::getDeleted,MockConstants.NO);
        List<MockitServiceMethod> mockitServiceMethodList = iMockitServiceMethodService.list(queryWrapper);
        List<MockitServiceMethodVO> dataList = new ArrayList<>();
        mockitServiceMethodList.forEach(mockitServiceMethod -> {
            MockitServiceMethodVO mockitServiceMethodVO = new MockitServiceMethodVO();
            BeanUtils.copyProperties(mockitServiceMethod,mockitServiceMethodVO);
            mockitServiceMethodVO.setParameterList(Arrays.asList(mockitServiceMethod.getParameters().split("-")));
            dataList.add(mockitServiceMethodVO);
        });
        return MockitResult.successful(dataList);
    }
}
