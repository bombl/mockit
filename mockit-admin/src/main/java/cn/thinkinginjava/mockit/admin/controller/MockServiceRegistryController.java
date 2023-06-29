package cn.thinkinginjava.mockit.admin.controller;

import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceRegistryDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceRegistryVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents a mock service registry controller.
 */
@Controller
@RequestMapping("/registry")
public class MockServiceRegistryController {

    @Resource
    private IMockitServiceRegistryService iMockitServiceRegistryService;

    /**
     * Retrieves a list of MockitServiceRegistryVO objects based on the provided MockitServiceRegistryDTO.
     *
     * @param mockitServiceRegistryDTO The DTO object containing the criteria for filtering the list.
     * @return A MockitResult object encapsulating the paginated list of MockitServiceRegistryVO objects.
     */
    @RequestMapping("/list")
    public MockitResult<IPage<MockitServiceRegistryVO>> list(@RequestBody MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        if (mockitServiceRegistryDTO.getCurrentPage() == null) {
            throw new MockitException("currentPage can not empty.");
        }
        if (mockitServiceRegistryDTO.getPageSize() == null) {
            throw new MockitException("pageSize can not empty.");
        }
        IPage<MockitServiceRegistryVO> page = iMockitServiceRegistryService.listByPage(mockitServiceRegistryDTO);
        return MockitResult.successful(page);
    }

    /**
     * Updates the enabled status for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     * @return A MockitResult object indicating the result of the operation.
     */
    @RequestMapping("/enabled")
    public MockitResult<Void> enabled(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceRegistryService.enabled(batchCommonDTO);
        return MockitResult.successful();
    }

    /**
     * Updates the MockitServiceRegistry based on the provided MockitServiceRegistryDTO.
     *
     * @param mockitServiceRegistryDTO The DTO object containing the updated information for the registry.
     * @return A MockitResult object indicating the result of the update operation.
     */
    @RequestMapping("/update")
    public MockitResult<Void> update(@RequestBody MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        if (StringUtils.isEmpty(mockitServiceRegistryDTO.getId())) {
            throw new MockitException("service id can not empty.");
        }
        MockitServiceRegistry mockitServiceRegistry = new MockitServiceRegistry();
        mockitServiceRegistry.setId(mockitServiceRegistryDTO.getId());
        mockitServiceRegistry.setRemarks(mockitServiceRegistryDTO.getRemarks());
        LambdaQueryWrapper<MockitServiceRegistry> registryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registryLambdaQueryWrapper.eq(MockitServiceRegistry::getId, mockitServiceRegistryDTO.getId());
        iMockitServiceRegistryService.update(mockitServiceRegistry, registryLambdaQueryWrapper);
        return MockitResult.successful();
    }

    /**
     * Initiates mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be mocked.
     * @return A MockitResult object indicating the result of the mocking operation.
     */
    @RequestMapping("/mock")
    public MockitResult<Void> mock(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceRegistryService.mock(batchCommonDTO);
        return MockitResult.successful();
    }

    /**
     * Cancels the mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to cancel the mocking for.
     * @return A MockitResult object indicating the result of the canceling operation.
     */
    @RequestMapping("/cancelMock")
    public MockitResult<Void> cancelMock(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceRegistryService.cancelMock(batchCommonDTO);
        return MockitResult.successful();
    }
}
