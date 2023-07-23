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

    private List<MockitMethodMockData> queryMockData(BatchCommonDTO batchCommonDTO) {
        LambdaQueryWrapper<MockitMethodMockData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MockitMethodMockData::getMethodId, batchCommonDTO.getIds());
        queryWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        return list(queryWrapper);
    }
}
