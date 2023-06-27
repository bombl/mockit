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

package cn.thinkinginjava.mockit.admin.service.manager;

import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.admin.utils.SpringContextUtil;

/**
 * This class is responsible for managing the registration and status of services in the "Mockit" service registry.
 */
public class ServiceRegistryManager {

    /**
     * Method to offline all services
     */
    public static void offlineAll() {
        SpringContextUtil.getBean(IMockitServiceRegistryService.class).offlineAll();
    }

    /**
     * Method to offline a specific session
     * @param session session
     */
    public static void offline(Session session) {
        SpringContextUtil.getBean(IMockitServiceRegistryService.class).offline(session);
    }

    /**
     * Method to bring a specific session online
     * @param session session
     */
    public static void online(Session session) {
        SpringContextUtil.getBean(IMockitServiceRegistryService.class).online(session);
    }
}
