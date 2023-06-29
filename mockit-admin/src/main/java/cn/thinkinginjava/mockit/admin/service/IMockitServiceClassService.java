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

package cn.thinkinginjava.mockit.admin.service;

import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceClassDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceClassVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service interface for managing Mockit service classes.
 * This interface extends the base IService interface and specifies the operations related to MockitServiceClass entities.
 */
public interface IMockitServiceClassService extends IService<MockitServiceClass> {

    /**
     * Adds a MockitServiceClass based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the information of the MockitServiceClass to be added.
     */
    void addClass(MockitServiceClassDTO mockitServiceClassDTO);

    /**
     * Retrieves a paginated list of MockitServiceClassVO objects based on the provided MockitServiceClassDTO.
     *
     * @param mockitServiceClassDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return An IPage object encapsulating the paginated list of MockitServiceClassVO objects.
     */
    IPage<MockitServiceClassVO> listByPage(MockitServiceClassDTO mockitServiceClassDTO);

    /**
     * Updates the enabled status in batch for the items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items and the desired enabled status.
     */
    void batchEnabled(BatchCommonDTO batchCommonDTO);

    /**
     * Deletes a batch of items specified in the BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be deleted.
     */
    void batchDelete(BatchCommonDTO batchCommonDTO);
}
