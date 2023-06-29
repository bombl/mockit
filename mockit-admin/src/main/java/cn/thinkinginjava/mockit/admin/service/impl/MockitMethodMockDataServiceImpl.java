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
import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing Mockit service method mock data.
 * This class extends the base ServiceImpl class and implements the IMockitServiceMethodMockDataService interface.
 */
@Service
public class MockitMethodMockDataServiceImpl extends ServiceImpl<MockitServiceMethodMockDataMapper, MockitMethodMockData> implements IMockitMethodMockDataService {

    @Override
    public void addMockData(List<MockitMethodMockDataDTO> mockDataList) {
        LambdaQueryWrapper<MockitMethodMockData> mockDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mockDataLambdaQueryWrapper.eq(MockitMethodMockData::getMethodId, mockDataList.get(0).getMethodId());
        remove(mockDataLambdaQueryWrapper);
        List<MockitMethodMockData> methodMockDataList = mockDataList.stream().map(mockDataDTO -> {
            MockitMethodMockData mockData = new MockitMethodMockData();
            BeanUtils.copyProperties(mockDataDTO, mockData);
            mockData.setMockEnabled(MockConstants.YES);
            mockData.setCreateAt(new Date());
            mockData.setUpdateAt(new Date());
            return mockData;
        }).collect(Collectors.toList());
        saveBatch(methodMockDataList);
    }
}
