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
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceClassDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceClassVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
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
 * Represents a controller for managing MockitServiceClass.
 */
@Controller
@RequestMapping("/class")
public class MockitServiceClassController {

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    /**
     * Adds a MockitServiceClass based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the information of the MockitServiceClass to be added.
     * @return A MockitResult object indicating the result of the add operation.
     */
    @RequestMapping("/add")
    public MockitResult<Void> addClass(@Valid @RequestBody MockitServiceClassDTO mockitServiceClassDTO) {
        iMockitServiceClassService.addClass(mockitServiceClassDTO);
        return MockitResult.successful();
    }

    /**
     * Retrieves a paginated list of MockitServiceClassVO objects based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return A MockitResult object encapsulating the paginated list of MockitServiceClassVO objects.
     */
    @RequestMapping("/list")
    public MockitResult<IPage<MockitServiceClassVO>> list(@RequestBody MockitServiceClassDTO mockitServiceClassDTO) {
        if (mockitServiceClassDTO.getCurrentPage() == null) {
            throw new MockitException("currentPage can not empty.");
        }
        if (mockitServiceClassDTO.getPageSize() == null) {
            throw new MockitException("pageSize can not empty.");
        }
        IPage<MockitServiceClassVO> page = iMockitServiceClassService.listByPage(mockitServiceClassDTO);
        return MockitResult.successful(page);
    }

    /**
     * Updates the MockitServiceRegistry based on the provided MockitServiceRegistryDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the updated information for the registry.
     * @return A MockitResult object indicating the result of the update operation.
     */
    @RequestMapping("/update")
    public MockitResult<Void> update(@RequestBody MockitServiceClassDTO mockitServiceClassDTO) {
        if (StringUtils.isEmpty(mockitServiceClassDTO.getId())) {
            throw new MockitException("service id can not empty.");
        }
        MockitServiceClass mockitServiceClass = new MockitServiceClass();
        BeanUtils.copyProperties(mockitServiceClassDTO, mockitServiceClass);
        mockitServiceClass.setUpdateAt(new Date());
        iMockitServiceClassService.updateById(mockitServiceClass);
        return MockitResult.successful();
    }

    /**
     * Updates the batchEnabled status for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     * @return A MockitResult object indicating the result of the operation.
     */
    @RequestMapping("/batchEnabled")
    public MockitResult<Void> enabled(@Valid @RequestBody BatchCommonDTO batchCommonDTO) {
        iMockitServiceClassService.batchEnabled(batchCommonDTO);
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
        iMockitServiceClassService.batchDelete(batchCommonDTO);
        return MockitResult.successful();
    }
}
