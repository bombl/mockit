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
import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service interface for managing Mockit service method mock data.
 * This interface extends the base IService interface and specifies the operations related to MockitServiceMethodMockData entities.
 */
public interface IMockitMethodMockDataService extends IService<MockitMethodMockData> {

    void saveOrUpdateMethod(MockitMethodMockDataDTO mockitMethodMockDataDTO);

    void batchEnabled(BatchCommonDTO batchCommonDTO);

    void batchDelete(BatchCommonDTO batchCommonDTO);
}
