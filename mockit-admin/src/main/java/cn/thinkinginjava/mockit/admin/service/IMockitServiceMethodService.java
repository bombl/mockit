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
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceMethodDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceMethodVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service interface for managing Mockit service methods.
 * This interface extends the base IService interface and specifies the operations related to MockitServiceMethod entities.
 */
public interface IMockitServiceMethodService extends IService<MockitServiceMethod> {

    /**
     * Saves or updates the method mock data based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the method mock data to be saved or updated.
     */
    void saveOrUpdateMethod(MockitServiceMethodDTO mockitServiceMethodDTO);

    /**
     * Retrieves a paginated list of MockitServiceMethodVO objects based on the provided MockitServiceMethodDTO.
     *
     * @param mockitServiceMethodDTO The DTO object containing the criteria for filtering and paginating the list.
     * @return An IPage object encapsulating the paginated list of MockitServiceMethodVO objects.
     */
    IPage<MockitServiceMethodVO> listByPage(MockitServiceMethodDTO mockitServiceMethodDTO);

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
