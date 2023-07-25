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

import cn.thinkinginjava.mockit.admin.context.ResponseCallback;
import cn.thinkinginjava.mockit.admin.mapper.MockitServiceRegistryMapper;
import cn.thinkinginjava.mockit.admin.model.dto.BatchCommonDTO;
import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.admin.model.dto.MockitServiceRegistryDTO;
import cn.thinkinginjava.mockit.admin.model.dto.Session;
import cn.thinkinginjava.mockit.admin.model.entity.MockitMethodMockData;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceClass;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceMethod;
import cn.thinkinginjava.mockit.admin.model.entity.MockitServiceRegistry;
import cn.thinkinginjava.mockit.admin.model.enums.EnabledStatusEnum;
import cn.thinkinginjava.mockit.admin.model.enums.OnlineStatusEnum;
import cn.thinkinginjava.mockit.admin.model.vo.MockitServiceRegistryVO;
import cn.thinkinginjava.mockit.admin.service.IMockitMethodMockDataService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceClassService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceMethodService;
import cn.thinkinginjava.mockit.admin.service.IMockitServiceRegistryService;
import cn.thinkinginjava.mockit.admin.session.SessionHolder;
import cn.thinkinginjava.mockit.admin.utils.MessageUtil;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import cn.thinkinginjava.mockit.common.model.dto.CancelMockData;
import cn.thinkinginjava.mockit.common.model.dto.Message;
import cn.thinkinginjava.mockit.common.model.dto.MethodMockData;
import cn.thinkinginjava.mockit.common.model.dto.MockData;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing Mockit service registries.
 * This class extends the base ServiceImpl class and implements the IMockitServiceRegistryService interface.
 */
@Service
public class MockitServiceRegistryServiceImpl extends ServiceImpl<MockitServiceRegistryMapper, MockitServiceRegistry> implements IMockitServiceRegistryService {

    private static final Logger logger = LoggerFactory.getLogger(MockitServiceRegistryServiceImpl.class);

    @Resource
    private IMockitServiceClassService iMockitServiceClassService;

    @Resource
    private IMockitServiceMethodService iMockitServiceMethodService;

    @Resource
    private IMockitMethodMockDataService iMockitMethodMockDataService;

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
        mockitServiceRegistryUpdate.setEnabled(MockConstants.NO);
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
        mockitServiceRegistryUpdate.setEnabled(MockConstants.NO);
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
        mockitServiceRegistry.setEnabled(MockConstants.NO);
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
        queryWrapper.eq(MockitServiceRegistry::getDeleted, MockConstants.NO);
        queryWrapper.orderByDesc(MockitServiceRegistry::getUpdateAt);
        return page(new Page<>(mockitServiceRegistryDTO.getCurrentPage(),
                mockitServiceRegistryDTO.getPageSize()), queryWrapper).convert(mockitServiceRegistry -> {
            MockitServiceRegistryVO mockitServiceRegistryVO = new MockitServiceRegistryVO();
            BeanUtils.copyProperties(mockitServiceRegistry, mockitServiceRegistryVO);
            mockitServiceRegistryVO.setEnabledMc(EnabledStatusEnum.getByValue(mockitServiceRegistry.getEnabled()));
            mockitServiceRegistryVO.setOnlineMc(OnlineStatusEnum.getByValue(mockitServiceRegistry.getOnline()));
            mockitServiceRegistryVO.setCreateTime(DateFormatUtils.format(mockitServiceRegistry.getCreateAt(), MockConstants.Y_M_D));
            return mockitServiceRegistryVO;
        });
    }

    /**
     * enabled the service.
     *
     * @param batchCommonDTO enabled info
     */
    @Override
    public void enabled(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceRegistry> mockitServiceRegistryList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceRegistryList)) {
            return;
        }
        mockitServiceRegistryList.forEach(mockitServiceRegistry -> {
            mockitServiceRegistry.setEnabled(batchCommonDTO.getEnabledValue());
            mockitServiceRegistry.setUpdateAt(new Date());
        });
        updateBatchById(mockitServiceRegistryList);
    }

    /**
     * Initiates mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be mocked.
     */
    @Override
    public void mock(BatchCommonDTO batchCommonDTO) {
        mockOrCancelMock(batchCommonDTO);
    }

    /**
     * Cancels the mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to cancel the mocking for.
     */
    @Override
    public void cancelMock(BatchCommonDTO batchCommonDTO) {
        mockOrCancelMock(batchCommonDTO);
    }

    /**
     * Retrieves the MockitServiceRegistry object associated with the provided session.
     *
     * @param session The session object for which to retrieve the associated service registry.
     * @return The MockitServiceRegistry object associated with the session.
     */
    @Override
    public MockitServiceRegistry getServiceRegistry(Session session) {
        LambdaQueryWrapper<MockitServiceRegistry> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MockitServiceRegistry::getAlias, session.getAlias());
        lambdaQueryWrapper.eq(MockitServiceRegistry::getIp, session.getIp());
        lambdaQueryWrapper.eq(MockitServiceRegistry::getDeleted, MockConstants.NO);
        return getOne(lambdaQueryWrapper);
    }

    /**
     * Initiates or cancels mocking for a batch of items based on the provided BatchCommonDTO.
     *
     * @param batchCommonDTO The DTO object containing the batch of items to be mocked or canceled.
     */
    private void mockOrCancelMock(BatchCommonDTO batchCommonDTO) {
        List<MockitServiceRegistry> mockitServiceRegistryList = listByIds(batchCommonDTO.getIds());
        if (CollectionUtils.isEmpty(mockitServiceRegistryList)) {
            return;
        }
        mockitServiceRegistryList = mockitServiceRegistryList.stream().filter(this::canMock).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mockitServiceRegistryList)) {
            return;
        }
        mockitServiceRegistryList.forEach(mockitServiceRegistry -> mockOrCancelMock(mockitServiceRegistry, batchCommonDTO.getEnabled()));
    }

    /**
     * Initiates or cancels mocking for the given MockitServiceRegistry based on the specified flag.
     *
     * @param mockitServiceRegistry The MockitServiceRegistry object for which to initiate or cancel mocking.
     * @param isMock                A boolean flag indicating whether to initiate mocking (true) or cancel mocking (false).
     */
    private void mockOrCancelMock(MockitServiceRegistry mockitServiceRegistry, Boolean isMock) {
        LambdaQueryWrapper<MockitServiceClass> serviceClassLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serviceClassLambdaQueryWrapper.eq(MockitServiceClass::getServiceId, mockitServiceRegistry.getId());
        serviceClassLambdaQueryWrapper.eq(MockitServiceClass::getMockEnabled, MockConstants.YES);
        serviceClassLambdaQueryWrapper.eq(MockitServiceClass::getDeleted, MockConstants.NO);
        List<MockitServiceClass> serviceClassList = iMockitServiceClassService.list(serviceClassLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(serviceClassList)) {
            return;
        }
        serviceClassList.forEach(mockitServiceClass -> mockOrCancelMock(mockitServiceRegistry, mockitServiceClass, isMock));
    }

    /**
     * Initiates or cancels mocking for the given MockitServiceClass within the provided MockitServiceRegistry based on the specified flag.
     *
     * @param mockitServiceRegistry The MockitServiceRegistry object that contains the MockitServiceClass to be mocked or canceled.
     * @param mockitServiceClass    The MockitServiceClass object for which to initiate or cancel mocking.
     * @param isMock                A boolean flag indicating whether to initiate mocking (true) or cancel mocking (false).
     */
    private void mockOrCancelMock(MockitServiceRegistry mockitServiceRegistry, MockitServiceClass mockitServiceClass, Boolean isMock) {
        if (isMock) {
            LambdaQueryWrapper<MockitServiceMethod> serviceMethodLambdaQueryWrapper = new LambdaQueryWrapper<>();
            serviceMethodLambdaQueryWrapper.eq(MockitServiceMethod::getClassId, mockitServiceClass.getId());
            serviceMethodLambdaQueryWrapper.eq(MockitServiceMethod::getMockEnabled, MockConstants.YES);
            serviceMethodLambdaQueryWrapper.eq(MockitServiceMethod::getDeleted, MockConstants.NO);
            List<MockitServiceMethod> serviceMethodList = iMockitServiceMethodService.list(serviceMethodLambdaQueryWrapper);
            if (CollectionUtils.isEmpty(serviceMethodList)) {
                return;
            }
            mock(mockitServiceRegistry, mockitServiceClass, serviceMethodList);
        } else {
            cancelMock(mockitServiceRegistry, mockitServiceClass);
        }
    }

    /**
     * Initiates mocking for the given MockitServiceClass and its associated list of MockitServiceMethod within the provided MockitServiceRegistry.
     *
     * @param mockitServiceRegistry The MockitServiceRegistry object that contains the MockitServiceClass to be mocked.
     * @param mockitServiceClass    The MockitServiceClass object for which to initiate mocking.
     * @param serviceMethodList     The list of MockitServiceMethod objects to be mocked.
     */
    private void mock(MockitServiceRegistry mockitServiceRegistry, MockitServiceClass mockitServiceClass, List<MockitServiceMethod> serviceMethodList) {
        List<MethodMockData> methodMockDataList = getMethodMockDataList(serviceMethodList);
        if (CollectionUtils.isEmpty(methodMockDataList)) {
            return;
        }
        Session session = getSession(mockitServiceRegistry.getAlias(), mockitServiceRegistry.getIp());
        if (session == null) {
            return;
        }
        MockData mockData = new MockData();
        mockData.setAlias(mockitServiceRegistry.getAlias());
        mockData.setClassName(mockitServiceClass.getClassName());
        mockData.setMethodMockDataList(methodMockDataList);
        Message<MockData> sendMessage = new Message<>();
        sendMessage.setData(mockData);
        sendMessage.setMessageType(MessageTypeEnum.MOCK.getType());
        ResponseCallback responseCallback = MessageUtil.sendMessage(session.getChannel(), sendMessage);
        CompletableFuture<String> completableFuture = responseCallback.getFuture().whenComplete((response, throwable) -> {
            if (MockitResult.isSuccess(response)) {
                MockitServiceRegistry serviceRegistry = new MockitServiceRegistry();
                serviceRegistry.setId(mockitServiceRegistry.getId());
                serviceRegistry.setEnabled(MockConstants.YES);
                serviceRegistry.setUpdateAt(new Date());
                updateById(serviceRegistry);
            }
        }).exceptionally(throwable -> {
            logger.error(throwable.getMessage());
            return null;
        });
        completableFuture.join();
    }

    /**
     * Retrieves a list of MethodMockData objects based on the provided list of MockitServiceMethod.
     *
     * @param serviceMethodList The list of MockitServiceMethod objects for which to retrieve the MethodMockData.
     * @return A list of MethodMockData objects corresponding to the provided MockitServiceMethod list.
     */
    private List<MethodMockData> getMethodMockDataList(List<MockitServiceMethod> serviceMethodList) {
        List<MethodMockData> methodMockDataList = new ArrayList<>();
        serviceMethodList.forEach(mockitServiceMethod -> {
            LambdaQueryWrapper<MockitMethodMockData> methodMockDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
            methodMockDataLambdaQueryWrapper.eq(MockitMethodMockData::getMethodId, mockitServiceMethod.getId());
            methodMockDataLambdaQueryWrapper.eq(MockitMethodMockData::getMockEnabled, MockConstants.YES);
            methodMockDataLambdaQueryWrapper.eq(MockitMethodMockData::getDeleted, MockConstants.NO);
            MockitMethodMockData serviceMethodMockData = iMockitMethodMockDataService.getOne(methodMockDataLambdaQueryWrapper);
            if (serviceMethodMockData == null) {
                return;
            }
            MethodMockData mockData = new MethodMockData();
            mockData.setMethodName(mockitServiceMethod.getMethodName());
            mockData.setParameters(Lists.newArrayList(mockitServiceMethod.getParameters().split("-")));
            mockData.setMockValue(serviceMethodMockData.getMockValue());
            methodMockDataList.add(mockData);
        });
        return methodMockDataList;
    }

    /**
     * Cancels mocking for the given MockitServiceClass within the provided MockitServiceRegistry.
     *
     * @param mockitServiceRegistry The MockitServiceRegistry object that contains the MockitServiceClass to cancel mocking.
     * @param mockitServiceClass    The MockitServiceClass object for which to cancel mocking.
     */
    private void cancelMock(MockitServiceRegistry mockitServiceRegistry, MockitServiceClass mockitServiceClass) {
        Session session = getSession(mockitServiceRegistry.getAlias(), mockitServiceRegistry.getIp());
        if (session == null) {
            return;
        }
        CancelMockData cancelMockData = new CancelMockData();
        cancelMockData.setAlias(session.getAlias());
        cancelMockData.setClassName(mockitServiceClass.getClassName());
        Message<CancelMockData> sendMessage = new Message<>();
        sendMessage.setData(cancelMockData);
        sendMessage.setMessageType(MessageTypeEnum.CANCEL_MOCK.getType());
        ResponseCallback responseCallback = MessageUtil.sendMessage(session.getChannel(), sendMessage);
        CompletableFuture<String> completableFuture = responseCallback.getFuture().whenComplete((response, throwable) -> {
            if (MockitResult.isSuccess(response)) {
                MockitServiceRegistry serviceRegistry = new MockitServiceRegistry();
                serviceRegistry.setId(mockitServiceRegistry.getId());
                serviceRegistry.setEnabled(MockConstants.NO);
                serviceRegistry.setUpdateAt(new Date());
                updateById(serviceRegistry);
            }
        }).exceptionally(throwable -> {
            logger.error(throwable.getMessage());
            return null;
        });
        completableFuture.join();
    }

    /**
     * Retrieves a session object based on the provided alias and IP address.
     *
     * @param alias The alias used to identify the session.
     * @param ip    The IP address associated with the session.
     * @return The Session object matching the provided alias and IP address.
     */
    private Session getSession(String alias, String ip) {
        Map<String, List<Session>> sessionMap = SessionHolder.getSessionMap();
        if (MapUtils.isEmpty(sessionMap)
                || CollectionUtils.isEmpty(sessionMap.get(alias))) {
            return null;
        }
        Optional<Session> sessionOptional = sessionMap.get(alias).stream()
                .filter(session -> ip.equals(session.getIp())).findFirst();
        return sessionOptional.orElse(null);
    }

    /**
     * Retrieves a QueryWrapper object for querying the MockitServiceRegistry based on the provided Session.
     *
     * @param session The session object used to create the query criteria.
     * @return The QueryWrapper object configured with the criteria for querying the MockitServiceRegistry.
     */
    private QueryWrapper<MockitServiceRegistry> getQueryWrapper(Session session) {
        QueryWrapper<MockitServiceRegistry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MockitServiceRegistry::getAlias, session.getAlias());
        queryWrapper.lambda().eq(MockitServiceRegistry::getIp, session.getIp());
        return queryWrapper;
    }

    /**
     * Determines whether mocking is allowed for the given MockitServiceRegistry.
     *
     * @param mockitServiceRegistry The MockitServiceRegistry object to check for mocking eligibility.
     * @return True if mocking is allowed for the given registry, false otherwise.
     */
    private boolean canMock(MockitServiceRegistry mockitServiceRegistry) {
        return MockConstants.YES.equals(mockitServiceRegistry.getOnline())
                && MockConstants.NO.equals(mockitServiceRegistry.getDeleted());
    }
}
