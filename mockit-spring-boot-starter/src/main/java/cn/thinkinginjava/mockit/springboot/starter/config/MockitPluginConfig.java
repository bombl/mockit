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

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Represents the configuration for the Mockit plugin.
 */
@ConfigurationProperties(prefix = "mockit.plugin")
public class MockitPluginConfig {

    private Boolean enabled;
    private String addresses;
    private String alias;

    /**
     * Get the enabled.
     *
     * @return The enabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Set the enabled.
     *
     * @param enabled The enabled to set.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the addresses.
     *
     * @return The addresses.
     */
    public String getAddresses() {
        return addresses;
    }

    /**
     * Set the addresses.
     *
     * @param addresses The addresses to set.
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    /**
     * Get the alias.
     *
     * @return The alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Set the alias.
     *
     * @param alias The alias to set.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
}

