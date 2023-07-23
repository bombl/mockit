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

import cn.thinkinginjava.mockit.admin.mapper.MockitServiceClassMapper;
import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMapper;
import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMockDataMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceClassDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.enums.EnabledStatusEnum;
import cn.thinkinginjava.mockit.admin.model.enums.OnlineStatusEnum;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceClassVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing Mockit service classes.
 * This class extends the base ServiceImpl class and implements the IMockitServiceClassService interface.
 */
@Service
public class MockitServiceClassServiceImpl extends ServiceImpl<MockitServiceClassMapper, MockitServiceClass> implements IMockitServiceClassService {

    @Resource
    private MockitServiceMethodMapper mockitServiceMethodMapper;

    @Resource
    private MockitServiceMethodMockDataMapper mockitServiceMethodMockDataMapper;

    /**
     * Adds a MockitServiceClass based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the information of the MockitServiceClass to be added.
     */
    @Override
    public void addClass(MockitServiceClassDTO mockitServiceClassDTO) {
        MockitServiceClass mockitServiceClass = new MockitServiceClass();
        BeanUtils.copyProperties(mockitServiceClassDTO, mockitServiceClass);
        mockitServiceClass.setId(UUIDUtils.getUuid());
        mockitServiceClass.setMockEnabled(mockitServiceClassDTO.getMockEnabled());
        mockitServiceClass.setDeleted(MockConstants.NO);
        mockitServiceClass.setCreateAt(new Date());
        mockitServiceClass.setUpdateAt(new Date());
        mockitServiceClass.setId(UUIDUtils.getUuid());
        save(mockitServiceClass);
    }

    /**
     * Retrieves a paginated list of MockitServiceClassVO objects based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return An IPage object encapsulating the paginated list of MockitServiceClassVO objects.
     */
    @Override
    public IPage<MockitServiceClassVO> listByPage(MockitServiceClassDTO mockitServiceClassDTO) {
        LambdaQueryWrapper<MockitServiceClass> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(mockitServiceClassDTO.getServiceId())) {
            queryWrapper.eq(MockitServiceClass::getServiceId, mockitServiceClassDTO.getServiceId());
        }
        if (StringUtils.isNotEmpty(mockitServiceClassDTO.getClassName())) {
            queryWrapper.like(MockitServiceClass::getClassName, mockitServiceClassDTO.getClassName());
        }
        if (mockitServiceClassDTO.getMockEnabled() != null) {
            queryWrapper.eq(MockitServiceClass::getMockEnabled, mockitServiceClassDTO.getMockEnabled());
        }
        queryWrapper.eq(MockitServiceClass::getDeleted, MockConstants.NO);
        queryWrapper.orderByDesc(MockitServiceClass::getUpdateAt);
        return page(new Page<>(mockitServiceClassDTO.getCurrentPage(),
                mockitServiceClassDTO.getPageSize()), queryWrapper).convert(mockitServiceClass -> {
            MockitServiceClassVO mockitServiceClassVO = new MockitServiceClassVO();
            BeanUtils.copyProperties(mockitServiceClass, mockitServiceClassVO);
            mockitServiceClassVO.setMockEnabledMc(EnabledStatusEnum.getByValue(mockitServiceClass.getMockEnabled()));
            mockitServiceClassVO.setCreateTime(DateFormatUtils.format(mockitServiceClass.getCreateAt(), MockConstants.Y_M_D));
            return mockitServiceClassVO;
        });
    }

    /**
     * Updates the enabled status in batch for the items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     */
    @Override
    public void batchEnabled(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceClass> mockitServiceClassList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceClassList)) {
            return;
        }
        mockitServiceClassList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setMockEnabled(batchCommonDTO.getEnabledValue());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceClassList);
    }

    /**
     * Deletes a batch of items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be deleted.
     */
    @Override
    public void batchDelete(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceClass> mockitServiceClassList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceClassList)) {
            return;
        }
        mockitServiceClassList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setDeleted(batchCommonDTO.getEnabledValue());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceClassList);

        LambdaQueryWrapper<MockitServiceMethod> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MockitServiceMethod::getClassId,batchCommonDTO.getIds());
        queryWrapper.eq(MockitServiceMethod::getDeleted,MockConstants.NO);
        List<MockitServiceMethod> mockDataList = mockitServiceMethodMapper.selectList(queryWrapper);
        mockDataList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setDeleted(MockConstants.YES);
            mockitServiceRegistry.setUpdateAt(new Date());
            mockitServiceMethodMapper.updateById(mockitServiceRegistry);
        });

        LambdaQueryWrapper<MockitMethodMockData> dataWrapper = new LambdaQueryWrapper<>();
        dataWrapper.in(MockitMethodMockData::getMethodId, batchCommonDTO.getIds());
        dataWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        List<MockitMethodMockData> dataList = mockitServiceMethodMockDataMapper.selectList(dataWrapper);
        dataList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setDeleted(MockConstants.YES);
            mockitServiceRegistry.setUpdateAt(new Date());
            mockitServiceMethodMockDataMapper.updateById(mockitServiceRegistry);
        });
    }
}
