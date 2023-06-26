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

package cn.thinkinginjava.mockit.admin.config;

import cn.thinkinginjava.mockit.admin.mybatis.OracleSQLPrepareInterceptor;
import cn.thinkinginjava.mockit.admin.mybatis.OracleSQLUpdateInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *  for MyBatis Configure management.
 */
@Configuration
public class MapperConfig {

    /**
     * The type OracleSQL.
     */
    @Configuration
    @ConditionalOnProperty(name = "mockit.database.dialect", havingValue = "oracle")
    static class OracleSQLConfig {

        /**
         * Add the plugin to the MyBatis plugin interceptor chain.
         *
         * @return {@linkplain OracleSQLPrepareInterceptor}
         */
        @Bean
        @ConditionalOnMissingBean(OracleSQLPrepareInterceptor.class)
        public OracleSQLPrepareInterceptor oracleSqlPrepareInterceptor() {
            return new OracleSQLPrepareInterceptor();
        }

        /**
         * Add the plugin to the MyBatis plugin interceptor chain.
         *
         * @return {@linkplain OracleSQLUpdateInterceptor}
         */
        @Bean
        @ConditionalOnMissingBean(OracleSQLUpdateInterceptor.class)
        public OracleSQLUpdateInterceptor oracleSqlUpdateInterceptor() {
            return new OracleSQLUpdateInterceptor();
        }
    }
}
