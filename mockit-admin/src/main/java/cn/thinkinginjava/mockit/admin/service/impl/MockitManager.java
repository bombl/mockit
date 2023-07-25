package cn.thinkinginjava.mockit.admin.service.impl;

import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMapper;
import cn.thinkinginjava.mockit.admin.mapper.MockitServiceMethodMockDataMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * This class represents the MockitManager component, which is responsible for managing mock data.
 * It is a Spring Component and can be autowired into other classes.
 */
@Component
public class MockitManager {

    @Resource
    private MockitServiceMethodMapper mockitServiceMethodMapper;

    @Resource
    private MockitServiceMethodMockDataMapper mockitServiceMethodMockDataMapper;

    /**
     * This method is used to batch delete methods based on the given BatchCommonDTO.
     * It allows for the deletion of multiple methods in one operation.
     *
     * @param batchCommonDTO The BatchCommonDTO containing the list of method IDs to be deleted.
     */
    public void batchDeleteMethod(BatchCommonDTO batchCommonDTO) {
        LambdaQueryWrapper<MockitServiceMethod> methodWrapper = new LambdaQueryWrapper<>();
        methodWrapper.in(MockitServiceMethod::getClassId, batchCommonDTO.getIds());
        methodWrapper.eq(MockitServiceMethod::getDeleted, MockConstants.NO);
        List<MockitServiceMethod> mockDataList = mockitServiceMethodMapper.selectList(methodWrapper);
        mockDataList.forEach(mockitServiceMethod -> {
            mockitServiceMethod.setDeleted(MockConstants.YES);
            mockitServiceMethod.setUpdateAt(new Date());
            mockitServiceMethodMapper.updateById(mockitServiceMethod);
        });
    }

    /**
     * This method is used to batch delete mock data based on the given BatchCommonDTO.
     * It allows for the deletion of multiple mock data entries in one operation.
     *
     * @param batchCommonDTO The BatchCommonDTO containing the list of mock data IDs to be deleted.
     */
    public void batchDeleteMockData(BatchCommonDTO batchCommonDTO) {
        LambdaQueryWrapper<MockitMethodMockData> dataWrapper = new LambdaQueryWrapper<>();
        dataWrapper.in(MockitMethodMockData::getMethodId, batchCommonDTO.getIds());
        dataWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
        List<MockitMethodMockData> dataList = mockitServiceMethodMockDataMapper.selectList(dataWrapper);
        dataList.forEach(mockitMethodMockData -> {
            mockitMethodMockData.setDeleted(MockConstants.YES);
            mockitMethodMockData.setUpdateAt(new Date());
            mockitServiceMethodMockDataMapper.updateById(mockitMethodMockData);
        });
    }
}
