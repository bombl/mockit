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

import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMockDataMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.utils.UUIDUtils;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the service for managing Mockit service method mock data.
 * This class extends the base ServiceImpl class and implements the IMockitServiceMethodMockDataService interface.
 */
@Service
public class MockitMethodMockDataServiceImpl extends ServiceImpl<MockitServiceMethodMockDataMapper, MockitMethodMockData> implements IMockitMethodMockDataService {

    /**
     * This method is used to save or update mock data for a specific method.
     * It takes a MockitMethodMockDataDTO as input, which contains the information
     * needed to create or update the mock data for the method.
     *
     * @param mockitMethodMockDataDTO The MockitMethodMockDataDTO containing the mock data details.
     */
    @Override
    public void saveOrUpdateMethod(MockitMethodMockDataDTO mockitMethodMockDataDTO) {
        MockitMethodMockData mockitMethodMockData = new MockitMethodMockData();
        mockitMethodMockData.setId(UUIDUtils.getUuid());
        mockitMethodMockData.setMethodId(mockitMethodMockDataDTO.getMethodId());
        mockitMethodMockData.setMockValue(mockitMethodMockDataDTO.getMockValue());
        mockitMethodMockData.setMockEnabled(mockitMethodMockDataDTO.getMockEnabled());
        mockitMethodMockData.setRemarks(mockitMethodMockDataDTO.getRemarks());
        mockitMethodMockData.setDeleted(MockConstants.NO);
        mockitMethodMockData.setCreateAt(new Date());
        mockitMethodMockData.setUpdateAt(new Date());
        LambdaQueryWrapper<MockitMethodMockData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MockitMethodMockData::getMethodId, mockitMethodMockDataDTO.getMethodId());
        queryWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        saveOrUpdate(mockitMethodMockData, queryWrapper);
    }

    /**
     * Enables or disables multiple items in batch.
     *
     * @param batchCommonDTO The BatchCommonDTO object containing the information of items to be enabled or disabled.
     */
    @Override
    public void batchEnabled(BatchCommonDTO batchCommonDTO) {
        List<MockitMethodMockData> mockDataList = queryMockData(batchCommonDTO);
        if (CollectionUtils.isEmpty(mockDataList)) {
            return;
        }
        mockDataList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setMockEnabled(batchCommonDTO.getEnabledValue());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockDataList);
    }

    /**
     * Deletes multiple items in batch.
     *
     * @param batchCommonDTO The BatchCommonDTO object containing the information of items to be deleted.
     */
    @Override
    public void batchDelete(BatchCommonDTO batchCommonDTO) {
        List<MockitMethodMockData> mockDataList = queryMockData(batchCommonDTO);
        if (CollectionUtils.isEmpty(mockDataList)) {
            return;
        }
        mockDataList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setDeleted(MockConstants.YES);
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockDataList);
    }

    /**
     * Queries mock data based on the given BatchCommonDTO criteria.
     *
     * @param batchCommonDTO The BatchCommonDTO object containing the criteria for querying mock data.
     * @return A list of MockitMethodMockData objects that match the criteria.
     */
    private List<MockitMethodMockData> queryMockData(BatchCommonDTO batchCommonDTO) {
        LambdaQueryWrapper<MockitMethodMockData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MockitMethodMockData::getMethodId, batchCommonDTO.getIds());
        queryWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        return list(queryWrapper);
    }
}
