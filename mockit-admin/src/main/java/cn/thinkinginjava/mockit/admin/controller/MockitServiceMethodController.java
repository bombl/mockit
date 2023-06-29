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
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceClassVO;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceMethodVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * Represents a controller for managing MockitServiceMethod.
 */
@Controller
@RequestMapping("/method")
public class MockitServiceMethodController {

    @Resource
    private IMockitServiceMethodService iMockitServiceMethodService;

    /**
     * Saves or updates a MockitServiceMethod based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the information of the MockitServiceMethod to be saved or updated.
     * @return A MockitResult object indicating the result of the save or update operation.
     */
    @RequestMapping("/saveOrUpdate")
    public MockitResult<Void> saveOrUpdate(@Valid @RequestBody MockitServiceMethodDTO mockitServiceMethodDTO) {
        MockitMethodMockDataDTO mockDataDTO = mockitServiceMethodDTO.getMockDataDTO();
        if (mockDataDTO == null || StringUtils.isEmpty(mockDataDTO.getMockValue())) {
            throw new MockitException("Mock value can not empty.");
        }
        iMockitServiceMethodService.saveOrUpdateMethod(mockitServiceMethodDTO);
        return MockitResult.successful();
    }

    /**
     * Retrieves a paginated list of MockitServiceMethodVO objects based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return A MockitResult object encapsulating the paginated list of MockitServiceMethodVO objects.
     */
    @RequestMapping("/list")
    public MockitResult<IPage<MockitServiceMethodVO>> list(@RequestBody MockitServiceMethodDTO mockitServiceMethodDTO) {
        if (mockitServiceMethodDTO.getCurrentPage() == null) {
            throw new MockitException("currentPage can not empty.");
        }
        if (mockitServiceMethodDTO.getPageSize() == null) {
            throw new MockitException("pageSize can not empty.");
        }
        IPage<MockitServiceMethodVO> page = iMockitServiceMethodService.listByPage(mockitServiceMethodDTO);
        return MockitResult.successful(page);
    }

    /**
     * Updates the batchEnabled status for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     * @return A MockitResult object indicating the result of the operation.
     */
    @RequestMapping("/batchEnabled")
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
    public MockitResult<Void> delete(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceMethodService.batchDelete(batchCommonDTO);
        return MockitResult.successful();
    }
}
