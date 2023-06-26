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

import cn.thinkinginjava.mockit.common.exception.MockitException;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * for execute schema sql file.
 */
@Component
@ConditionalOnExpression("'${mockit.database.dialect}' == 'h2' || '${mockit.database.dialect}' == 'mysql'")
public class DataSourceLoader implements InstantiationAwareBeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceLoader.class);

    private static final String PRE_FIX = "file:";

    @Resource
    private DataBaseProperties dataBaseProperties;

    @Override
    public Object postProcessAfterInitialization(@NonNull final Object bean, @NonNull final String beanName) throws BeansException {
        if ((bean instanceof DataSourceProperties) && Boolean.TRUE.equals(dataBaseProperties.getInitEnable())) {
            this.init((DataSourceProperties) bean);
        }
        return bean;
    }

    protected void init(final DataSourceProperties properties) {
        try {
            String jdbcUrl = StringUtils.replace(properties.getUrl(), "/mockit?", "?");
            Connection connection = DriverManager.getConnection(jdbcUrl, properties.getUsername(), properties.getPassword());
            this.execute(connection, dataBaseProperties.getInitScript());
        } catch (Exception e) {
            logger.error("Datasource init error.", e);
            throw new MockitException(e.getMessage());
        }
    }

    protected void execute(final Connection conn, final String script) throws Exception {
        ScriptRunner runner = new ScriptRunner(conn);
        try {
            runner.setLogWriter(null);
            runner.setAutoCommit(true);
            runner.setFullLineDelimiter(false);
            runner.setSendFullScript(false);
            runner.setStopOnError(false);
            Resources.setCharset(StandardCharsets.UTF_8);
            List<String> initScripts = Splitter.on(";").splitToList(script);
            for (String sqlScript : initScripts) {
                if (sqlScript.startsWith(PRE_FIX)) {
                    String sqlFile = sqlScript.substring(PRE_FIX.length());
                    try (Reader fileReader = getResourceAsReader(sqlFile)) {
                        logger.info("execute mockit schema sql: {}", sqlFile);
                        runner.runScript(fileReader);
                    }
                } else {
                    try (Reader fileReader = Resources.getResourceAsReader(sqlScript)) {
                        logger.info("execute mockit schema sql: {}", sqlScript);
                        runner.runScript(fileReader);
                    }
                }
            }
        } finally {
            runner.closeConnection();
        }
    }

    private static Reader getResourceAsReader(final String resource) throws IOException {
        return new InputStreamReader(Files.newInputStream(Paths.get(resource)), StandardCharsets.UTF_8);
    }
}
