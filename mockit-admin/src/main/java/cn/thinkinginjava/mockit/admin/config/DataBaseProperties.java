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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DataSource configuration.
 */
@Component
@ConfigurationProperties(prefix = "mockit.database")
public class DataBaseProperties {

    private String dialect;

    private String initScript;

    private Boolean initEnable;

    /**
     * Gets the value of dialect.
     *
     * @return the value of dialect
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * Sets the dialect.
     *
     * @param dialect dialect
     */
    public void setDialect(final String dialect) {
        this.dialect = dialect;
    }

    /**
     * Gets the value of initScript.
     *
     * @return the value of initScript
     */
    public String getInitScript() {
        return initScript;
    }

    /**
     * Sets the initScript.
     *
     * @param initScript initScript
     */
    public void setInitScript(final String initScript) {
        this.initScript = initScript;
    }

    /**
     * Gets the value of initEnable.
     *
     * @return the value of initEnable
     */
    public Boolean getInitEnable() {
        return initEnable;
    }

    /**
     * Sets the initEnable.
     *
     * @param initEnable initEnable
     */
    public void setInitEnable(final Boolean initEnable) {
        this.initEnable = initEnable;
    }
}
