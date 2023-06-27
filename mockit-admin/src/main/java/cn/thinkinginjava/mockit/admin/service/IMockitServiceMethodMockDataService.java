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

import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceMethodMockDataDTO;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethodMockData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service interface for managing Mockit service method mock data.
 * This interface extends the base IService interface and specifies the operations related to MockitServiceMethodMockData entities.
 */
public interface IMockitServiceMethodMockDataService extends IService<MockitServiceMethodMockData> {

    void addMockData(List<MockitServiceMethodMockDataDTO> mockDataList);
}
