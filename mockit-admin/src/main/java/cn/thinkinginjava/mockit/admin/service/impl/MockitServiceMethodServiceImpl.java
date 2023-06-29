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
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceMethodDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.utils.UUIDUtils;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing Mockit service methods.
 * This class extends the base ServiceImpl class and implements the IMockitServiceMethodService interface.
 */
@Service
public class MockitServiceMethodServiceImpl extends ServiceImpl<MockitServiceMethodMapper, MockitServiceMethod> implements IMockitServiceMethodService {

    @Resource
    private IMockitMethodMockDataService iMockitMethodMockDataService;

    @Override
    public void addMethod(List<MockitServiceMethodDTO> methodList) {
        LambdaQueryWrapper<MockitServiceMethod> mockDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mockDataLambdaQueryWrapper.eq(MockitServiceMethod::getClassId, methodList.get(0).getClassId());
        remove(mockDataLambdaQueryWrapper);
        List<MockitServiceMethod> serviceMethodList = methodList.stream().map(methodDTO -> {
            MockitServiceMethod mockData = new MockitServiceMethod();
            BeanUtils.copyProperties(methodDTO, mockData);
            mockData.setId(UUIDUtils.getUuid());
            mockData.setMockEnabled(MockConstants.YES);
            mockData.setCreateAt(new Date());
            mockData.setUpdateAt(new Date());
            return mockData;
        }).collect(Collectors.toList());
        saveBatch(serviceMethodList);
        List<MockitMethodMockDataDTO> mockDataList = serviceMethodList.stream().map(methodDTO -> {
            MockitMethodMockDataDTO mockData = new MockitMethodMockDataDTO();
            BeanUtils.copyProperties(methodDTO.getMockDataDTO(), mockData);
            mockData.setMethodId(methodDTO.getId());
            mockData.setMockEnabled(MockConstants.YES);
            return mockData;
        }).collect(Collectors.toList());
        iMockitMethodMockDataService.addMockData(mockDataList);
    }
}
