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

package cn.thinkinginjava.mockit.admin.service.impl;

import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceMethodDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.enums.EnabledStatusEnum;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceMethodVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.utils.UUIDUtils;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the service for managing Mockit service methods.
 * This class extends the base ServiceImpl class and implements the IMockitServiceMethodService interface.
 */
@Service
public class MockitServiceMethodServiceImpl extends ServiceImpl<MockitServiceMethodMapper, MockitServiceMethod> implements IMockitServiceMethodService {

    @Resource
    private MockitManager mockitManager;

    /**
     * Saves or updates the method mock data based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the method mock data to be saved or updated.
     */
    @Override
    public MockitServiceMethod saveOrUpdateMethod(MockitServiceMethodDTO mockitServiceMethodDTO) {
        LambdaQueryWrapper<MockitServiceMethod> mockDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getDeleted, MockConstants.NO);
        if (StringUtils.isNotBlank(mockitServiceMethodDTO.getId())) {
            mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getId, mockitServiceMethodDTO.getId());
        } else {
            mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getClassId, mockitServiceMethodDTO.getClassId());
            mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getMethodName, mockitServiceMethodDTO.getMethodName());
            mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getParameters, mockitServiceMethodDTO.getParameters());
        }

        MockitServiceMethod mockitServiceMethod = new MockitServiceMethod();
        BeanUtils.copyProperties(mockitServiceMethodDTO, mockitServiceMethod);
        if (StringUtils.isBlank(mockitServiceMethodDTO.getId())) {
            mockitServiceMethod.setId(UUIDUtils.getUuid());
        }
        mockitServiceMethod.setCreateAt(new Date());
        mockitServiceMethod.setUpdateAt(new Date());
        saveOrUpdate(mockitServiceMethod, mockDataLambdaQueryWrapper);
        return mockitServiceMethod;
    }

    /**
     * Retrieves a paginated list of MockitServiceMethodVO objects based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return An IPage object encapsulating the paginated list of MockitServiceMethodVO objects.
     */
    @Override
    public IPage<MockitServiceMethodVO> listByPage(MockitServiceMethodDTO mockitServiceMethodDTO) {
        LambdaQueryWrapper<MockitServiceMethod> queryWrapper = new LambdaQueryWrapper<>();
        if (mockitServiceMethodDTO.getIsMockData() == null && mockitServiceMethodDTO.getMockEnabled() != null) {
            queryWrapper.eq(MockitServiceMethod::getMockEnabled, mockitServiceMethodDTO.getMockEnabled());
        }
        if (CollectionUtils.isNotEmpty(mockitServiceMethodDTO.getClassIdList())) {
            queryWrapper.in(MockitServiceMethod::getClassId, mockitServiceMethodDTO.getClassIdList());
        }
        if (StringUtils.isNotBlank(mockitServiceMethodDTO.getMethodName())) {
            queryWrapper.like(MockitServiceMethod::getMethodName, mockitServiceMethodDTO.getMethodName());
        }
        queryWrapper.eq(MockitServiceMethod::getDeleted, MockConstants.NO);
        queryWrapper.orderByDesc(MockitServiceMethod::getUpdateAt);

        return page(new Page<>(mockitServiceMethodDTO.getCurrentPage(),
                mockitServiceMethodDTO.getPageSize()), queryWrapper).convert(mockitServiceRegistry -> {
            MockitServiceMethodVO mockitServiceMethodVO = new MockitServiceMethodVO();
            BeanUtils.copyProperties(mockitServiceRegistry, mockitServiceMethodVO);
            mockitServiceMethodVO.setMockEnabledMc(EnabledStatusEnum.getByValue(mockitServiceMethodVO.getMockEnabled()));
            mockitServiceMethodVO.setCreateTime(DateFormatUtils.format(mockitServiceMethodVO.getCreateAt(), MockConstants.Y_M_D));
            mockitServiceMethodVO.setMethodInfo(mockitServiceMethodVO.getAccessModifer() + " "
                    + mockitServiceMethodVO.getReturnType() + " " + mockitServiceMethodVO.getMethodName() + "("
                    + String.join(",", mockitServiceMethodVO.getParameters().split("-")) + ")");
            return mockitServiceMethodVO;
        });
    }

    /**
     * Updates the enabled status in batch for the items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     */
    @Override
    public void batchEnabled(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceMethod> mockitServiceMethodList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceMethodList)) {
            return;
        }
        mockitServiceMethodList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setMockEnabled(batchCommonDTO.getEnabledValue());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceMethodList);
    }

    /**
     * Deletes a batch of items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be deleted.
     */
    @Override
    public void batchDelete(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceMethod> mockitServiceMethodList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceMethodList)) {
            return;
        }
        mockitServiceMethodList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setDeleted(MockConstants.YES);
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceMethodList);
        mockitManager.batchDeleteMockData(batchCommonDTO);
    }
}
