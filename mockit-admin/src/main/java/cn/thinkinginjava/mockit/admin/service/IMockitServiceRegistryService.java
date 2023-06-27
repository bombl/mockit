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

import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceRegistryDTO;
import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceRegistryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service interface for managing Mockit service registries.
 * This interface extends the base IService interface and specifies the operations related to MockitServiceRegistry entities.
 */
public interface IMockitServiceRegistryService extends IService<MockitServiceRegistry> {

    /**
     * Online method: Sets the given session as online
     *
     * @param session session
     */
    void online(Session session);

    /**
     * Offline method: Sets the given session as offline
     *
     * @param session session
     */
    void offline(Session session);

    /**
     * OfflineAll method: Sets the all sessions as offline
     *
     */
    void offlineAll();

    /**
     * AddService method：add new service
     * @param mockitServiceRegistryDTO service info
     */
    void addService(MockitServiceRegistryDTO mockitServiceRegistryDTO);

    /**
     * DeleteService method：delete service
     * @param mockitServiceRegistryDTO service info
     */
    void deleteService(MockitServiceRegistryDTO mockitServiceRegistryDTO);

    /**
     * UpdateService method：update service
     * @param mockitServiceRegistryDTO service info
     */
    void updateService(MockitServiceRegistryDTO mockitServiceRegistryDTO);

    /**
     * listByPage method：list service
     * @param mockitServiceRegistryDTO service info
     */
    IPage<MockitServiceRegistryVO> listByPage(MockitServiceRegistryDTO mockitServiceRegistryDTO);
}
