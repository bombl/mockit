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

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * DataSource Configuration.
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * Register datasourceProperties for LocalDataSourceLoader.
     *
     * @param dialect    database dialect
     * @param initScript database init script
     * @param initEnable database init enable
     * @return {@linkplain DataBaseProperties}
     */
    @Bean
    @ConditionalOnMissingBean(value = DataBaseProperties.class)
    public DataBaseProperties dataBaseProperties(@Value("${mockit.database.dialect:h2}") final String dialect,
                                                 @Value("${mockit.database.init_script:script/h2/schema.sql}") final String initScript,
                                                 @Value("${mockit.database.init_enable:true}") final Boolean initEnable) {
        DataBaseProperties dataSourceProperties = new DataBaseProperties();
        dataSourceProperties.setDialect(dialect);
        dataSourceProperties.setInitScript(initScript);
        dataSourceProperties.setInitEnable(initEnable);
        return dataSourceProperties;
    }

    /**
     * Database dialect configuration.
     *
     * @return {@linkplain DatabaseIdProvider}
     */
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("H2", "h2");
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
