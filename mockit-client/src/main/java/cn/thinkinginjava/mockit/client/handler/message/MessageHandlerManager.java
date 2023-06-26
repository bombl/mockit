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

package cn.thinkinginjava.mockit.client.handler.message;

import cn.thinkinginjava.mockit.client.annotation.MessageType;
import cn.thinkinginjava.mockit.common.model.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manages message handlers based on message types.
 */
public class MessageHandlerManager {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerManager.class);

    private static final Map<MessageTypeEnum, MessageHandler> handlerMap = new HashMap<>();

    static {
        scanAndRegisterHandlers();
    }

    /**
     * Scans and registers message handlers annotated with @MessageType.
     */
    private static void scanAndRegisterHandlers() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(MessageType.class));
        String basePackage = MessageHandlerManager.class.getPackage().getName();
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(basePackage);
        for (BeanDefinition candidateComponent : candidateComponents) {
            try {
                String className = candidateComponent.getBeanClassName();
                Class<?> annotatedClass = Class.forName(className);
                MessageType annotation = annotatedClass.getAnnotation(MessageType.class);
                MessageTypeEnum messageType = annotation.value();
                MessageHandler handler = (MessageHandler) annotatedClass.getDeclaredConstructor().newInstance();
                registerHandler(messageType, handler);
            } catch (Exception e) {
                logger.error("Failed to register message handler", e);
            }
        }
    }

    /**
     * Registers a message handler for the specified message type.
     *
     * @param messageTypeEnum The message type.
     * @param messageHandler  The message handler.
     */
    public static void registerHandler(MessageTypeEnum messageTypeEnum, MessageHandler messageHandler) {
        handlerMap.put(messageTypeEnum, messageHandler);
    }

    /**
     * Retrieves the message handler for the specified message type.
     *
     * @param messageTypeEnum The message type.
     * @return The message handler.
     */
    public static MessageHandler getHandler(MessageTypeEnum messageTypeEnum) {
        return handlerMap.get(messageTypeEnum);
    }
}