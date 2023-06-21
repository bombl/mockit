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

package cn.thinkinginjava.mockit.springboot.starter.config;

import cn.thinkinginjava.mockit.client.communication.MockitClient;
import cn.thinkinginjava.mockit.springboot.starter.support.MockServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * The type Mockit configuration.
 */
@Configuration
@ConditionalOnProperty(value = "mockit.plugin.enabled", havingValue = "true")
public class MockitConfiguration {

    /**
     * The mockServlet() method is a Spring Bean configuration method that registers a servlet.
     * It is annotated with @Bean, which indicates that this method produces a bean that can be managed by the Spring container.
     * The returned bean is a ServletRegistrationBean that allows for custom configuration and registration of a servlet.
     * This method is responsible for creating and configuring the mock servlet to be used in the application.
     *
     * @return the mock servlet to be used in the application
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> mockServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new MockServlet());
        servletRegistrationBean.addUrlMappings("/mock/*");
        return servletRegistrationBean;
    }

    /**
     * Creates a Bean named "mockitClient" of type MockitClient if there is no existing Bean of the same type.
     * It is conditionally created based on the absence of a Bean of type MockitClient.
     * The MockitPluginConfig object is injected as a dependency for configuring the MockitClient.
     *
     * @param mockitPluginConfig The MockitPluginConfig object used for configuring the MockitClient.
     * @return The MockitClient object.
     */
    @Bean
    @ConditionalOnMissingBean
    public MockitClient mockitClient(MockitPluginConfig mockitPluginConfig) {
        MockitClient mockitClient = new MockitClient();
        mockitClient.setAddresses(mockitPluginConfig.getAddresses());
        mockitClient.setAlias(mockitPluginConfig.getAlias());
        return mockitClient;
    }

    /**
     * Creates a Bean named "mockitPluginConfig" that is bound to the configuration properties with the prefix "mockit.plugin".
     * The properties under this prefix will be automatically mapped to the fields of the MockitPluginConfig object.
     * This allows accessing the configuration values from the configuration file.
     *
     * @return The MockitPluginConfig object populated with the configuration properties.
     */
    @Bean
    @ConfigurationProperties(prefix = "mockit.plugin")
    public MockitPluginConfig mockitPluginConfig() {
        return new MockitPluginConfig();
    }
}