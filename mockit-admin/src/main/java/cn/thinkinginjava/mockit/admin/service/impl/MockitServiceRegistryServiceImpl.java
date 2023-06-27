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

package cn.thinkinginjava.mockit.admin.service.impl;

import cn.thinkinginjava.mockit.admin.mapper.MockitServiceRegistryMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchEnabledDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceRegistryDTO;
import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceRegistryVO;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing Mockit service registries.
 * This class extends the base ServiceImpl class and implements the IMockitServiceRegistryService interface.
 */
@Service
public class MockitServiceRegistryServiceImpl extends ServiceImpl<MockitServiceRegistryMapper, MockitServiceRegistry> implements IMockitServiceRegistryService {

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    /**
     * Online method: Sets the given session as online
     *
     * @param session session
     */
    @Override
    public void online(Session session) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = getQueryWrapper(session);
        MockitServiceRegistry mockitServiceRegistryUpdate = new MockitServiceRegistry();
        mockitServiceRegistryUpdate.setAlias(session.getAlias());
        mockitServiceRegistryUpdate.setIp(session.getIp());
        mockitServiceRegistryUpdate.setOnline(MockConstants.YES);
        mockitServiceRegistryUpdate.setCreateAt(new Date());
        mockitServiceRegistryUpdate.setUpdateAt(new Date());
        saveOrUpdate(mockitServiceRegistryUpdate, queryWrapper);
    }

    /**
     * Offline method: Sets the given session as offline
     *
     * @param session session
     */
    @Override
    public void offline(Session session) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = getQueryWrapper(session);
        MockitServiceRegistry mockitServiceRegistryUpdate = new MockitServiceRegistry();
        mockitServiceRegistryUpdate.setAlias(session.getAlias());
        mockitServiceRegistryUpdate.setIp(session.getIp());
        mockitServiceRegistryUpdate.setOnline(MockConstants.NO);
        mockitServiceRegistryUpdate.setUpdateAt(new Date());
        update(mockitServiceRegistryUpdate, queryWrapper);
    }

    /**
     * OfflineAll method: Sets the all sessions as offline
     */
    @Override
    public void offlineAll() {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getOnline, MockConstants.YES);
        queryWrapper.lambda().eq(MockitServiceRegistry::getDeleted, MockConstants.NO);
        MockitServiceRegistry mockitServiceRegistryUpdate = new MockitServiceRegistry();
        mockitServiceRegistryUpdate.setOnline(MockConstants.NO);
        mockitServiceRegistryUpdate.setUpdateAt(new Date());
        update(mockitServiceRegistryUpdate, queryWrapper);
    }

    /**
     * AddService method：add new service
     *
     * @param mockitServiceRegistryDTO service info
     */
    @Override
    public void addService(MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getAlias, mockitServiceRegistryDTO.getAlias());
        queryWrapper.lambda().eq(MockitServiceRegistry::getIp, mockitServiceRegistryDTO.getIp());
        MockitServiceRegistry mockitServiceRegistry = new MockitServiceRegistry();
        mockitServiceRegistry.setAlias(mockitServiceRegistryDTO.getAlias());
        mockitServiceRegistry.setIp(mockitServiceRegistryDTO.getIp());
        mockitServiceRegistry.setOnline(MockConstants.NO);
        mockitServiceRegistry.setEnabled(MockConstants.YES);
        mockitServiceRegistry.setDeleted(MockConstants.NO);
        mockitServiceRegistry.setCreateAt(new Date());
        mockitServiceRegistry.setUpdateAt(new Date());
        saveOrUpdate(mockitServiceRegistry, queryWrapper);
    }

    /**
     * DeleteService method：delete service
     *
     * @param mockitServiceRegistryDTO service info
     */
    @Override
    public void deleteService(MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getAlias, mockitServiceRegistryDTO.getAlias());
        queryWrapper.lambda().eq(MockitServiceRegistry::getIp, mockitServiceRegistryDTO.getIp());
        MockitServiceRegistry mockitServiceRegistry = new MockitServiceRegistry();
        mockitServiceRegistry.setOnline(MockConstants.NO);
        mockitServiceRegistry.setDeleted(MockConstants.YES);
        mockitServiceRegistry.setUpdateAt(new Date());
        update(mockitServiceRegistry, queryWrapper);
    }

    /**
     * UpdateService method：update service
     *
     * @param mockitServiceRegistryDTO service info
     */
    @Override
    public void updateService(MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getAlias, mockitServiceRegistryDTO.getAlias());
        queryWrapper.lambda().eq(MockitServiceRegistry::getIp, mockitServiceRegistryDTO.getIp());
        MockitServiceRegistry mockitServiceRegistry = new MockitServiceRegistry();
        BeanUtils.copyProperties(mockitServiceRegistryDTO, mockitServiceRegistry);
        mockitServiceRegistry.setUpdateAt(new Date());
        update(mockitServiceRegistry, queryWrapper);
    }

    /**
     * listByPage method：list service
     *
     * @param mockitServiceRegistryDTO service info
     */
    @Override
    public IPage<MockitServiceRegistryVO> listByPage(MockitServiceRegistryDTO mockitServiceRegistryDTO) {
        LambdaQueryWrapper<MockitServiceRegistry> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(mockitServiceRegistryDTO.getAlias())) {
            queryWrapper.like(MockitServiceRegistry::getAlias, mockitServiceRegistryDTO.getAlias());
        }
        if (StringUtils.isNotEmpty(mockitServiceRegistryDTO.getIp())) {
            queryWrapper.like(MockitServiceRegistry::getIp, mockitServiceRegistryDTO.getIp());
        }
        if (mockitServiceRegistryDTO.getEnabled() != null) {
            queryWrapper.eq(MockitServiceRegistry::getEnabled, mockitServiceRegistryDTO.getEnabled());
        }
        if (mockitServiceRegistryDTO.getOnline() != null) {
            queryWrapper.eq(MockitServiceRegistry::getOnline, mockitServiceRegistryDTO.getOnline());
        }
        queryWrapper.orderByDesc(MockitServiceRegistry::getUpdateAt);
        return page(new Page<>(mockitServiceRegistryDTO.getCurrentPage(),
                mockitServiceRegistryDTO.getPageSize()), queryWrapper).convert(mockitServiceRegistry -> {
            MockitServiceRegistryVO mockitServiceRegistryVO = new MockitServiceRegistryVO();
            BeanUtils.copyProperties(mockitServiceRegistry, mockitServiceRegistryVO);
            return mockitServiceRegistryVO;
        });
    }

    /**
     * enabled the service.
     *
     * @param batchEnabledDTO enabled info
     */
    @Override
    public void enabled(BatchEnabledDTO batchEnabledDTO) {
        List<MockitServiceRegistry> mockitServiceRegistryList = listByIds(batchEnabledDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceRegistryList)) {
            return;
        }
        mockitServiceRegistryList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setEnabled(batchEnabledDTO.getEnabled());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceRegistryList);
    }

    @Override
    public void mock(BatchEnabledDTO batchEnabledDTO) {
        List<MockitServiceRegistry> mockitServiceRegistryList = listByIds(batchEnabledDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceRegistryList)) {
            return;
        }
        mockitServiceRegistryList.forEach(mockitServiceRegistry -> {
            LambdaQueryWrapper<MockitServiceClass> serviceClassLambdaQueryWrapper = new LambdaQueryWrapper<>();
            serviceClassLambdaQueryWrapper.eq(MockitServiceClass::getServiceId,mockitServiceRegistry.getId());
            List<MockitServiceClass> serviceClassList = iMockitServiceClassService.list(serviceClassLambdaQueryWrapper);
            if (CollectionUtils.isEmpty(serviceClassList)) {
                return;
            }
            // TODO 今天就写到这吧
        });
    }

    @Override
    public void cancelMock(BatchEnabledDTO batchEnabledDTO) {

    }

    private QueryWrapper<MockitServiceRegistry> getQueryWrapper(Session session) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getAlias, session.getAlias());
        queryWrapper.lambda().eq(MockitServiceRegistry::getIp, session.getIp());
        return queryWrapper;
    }
}
