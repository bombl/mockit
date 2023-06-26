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

package cn.thinkinginjava.mockit.admin.mybatis;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * the mybatis interceptor for update/insert/delete.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class OracleSQLPrepareInterceptor implements Interceptor {

    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        BoundSql boundSql = statementHandler.getBoundSql();
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        String replaceSql = boundSql.getSql().toLowerCase()
                .replace("`desc`", "\"desc\"")
                .replace("true", "'true'");
        replaceSql = replaceSql.replace("`", "");
        if (replaceSql.contains("resource")) {
            replaceSql = replaceSql.replace("into resource", "into \"resource\"")
                    .replace("from resource", "from \"resource\"")
                    .replace("update resource", "update \"resource\"");
        }
        if (replaceSql.contains("insert into") && replaceSql.split("\\(").length > 3) {
            replaceSql = replaceSql.replaceAll("\r|\n|\\s", "")
                    .replace("insertinto", "insert into ")
                    .replace("values", " SELECT * FROM (")
                    .replace("(?", " SELECT ?")
                    .replace("),", " FROM dual UNION ALL")
                    .replace("?)", " ? FROM dual)");
        }

        if (replaceSql.contains("select")) {
            if (replaceSql.contains("where")) {
                replaceSql = replaceSql.replace("limit 1", "and rownum = 1");
            } else {
                replaceSql = replaceSql.replace("limit 1", "where rownum = 1");
            }
        }
        field.set(boundSql, replaceSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(final Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(final Properties properties) {

    }
}
