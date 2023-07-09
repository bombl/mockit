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
import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceRegistryDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceRegistryVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public Map<String,Object> list(@RequestBody MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        if (mockitServiceRegistryDTO.getCurrentPage() == null) {
            throw new MockitException("currentPage can not empty.");
        }
        if (mockitServiceRegistryDTO.getPageSize() == null) {
            throw new MockitException("pageSize can not empty.");
        }
        IPage<MockitServiceRegistryVO> page = iMockitServiceRegistryService.listByPage(mockitServiceRegistryDTO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recordsTotal", page.getRecords().size());
        map.put("recordsFiltered", page.getRecords().size());
        map.put("data", page.getRecords());
        return map;
    }

    /**
     * Updates the enabled status for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     * @return A MockitResult object indicating the result of the operation.
     */
    @RequestMapping("/enabled")
    @ResponseBody
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
    @ResponseBody
    public MockitResult<Void> update(@RequestBody MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        if (StringUtils.isEmpty(mockitServiceRegistryDTO.getId())) {
            throw new MockitException("service id can not empty.");
        }
        MockitServiceRegistry mockitServiceRegistry = new MockitServiceRegistry();
        BeanUtils.copyProperties(mockitServiceRegistryDTO, mockitServiceRegistry);
        mockitServiceRegistry.setUpdateAt(new Date());
        iMockitServiceRegistryService.updateById(mockitServiceRegistry);
        return MockitResult.successful();
    }

    /**
     * Initiates mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be mocked.
     * @return A MockitResult object indicating the result of the mocking operation.
     */
    @RequestMapping("/mock")
    @ResponseBody
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
    @ResponseBody
    public MockitResult<Void> cancelMock(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceRegistryService.cancelMock(batchCommonDTO);
        return MockitResult.successful();
    }
}
