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

package cn.thinkinginjava.mockit.client.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for handling addresses and URIs.
 */
public class AddressUtil {

    /**
     * Resolves the addresses from a comma-separated string.
     *
     * @param addresses The addresses string to resolve.
     * @return A list of processed URLs.
     */
    public static List<String> resolveAddress(String addresses) {
        List<String> processedUrls = new ArrayList<>();
        String[] urlArray = addresses.split(",");
        for (String url : urlArray) {
            url = url.trim();
            if (url.startsWith("http://")) {
                url = url.substring(7);
            } else if (url.startsWith("https://")) {
                url = url.substring(8);
            }
            if (!processedUrls.contains(url)) {
                processedUrls.add(url);
            }
        }
        return processedUrls;
    }

    /**
     * Checks if the given address is a valid IP and port combination.
     *
     * @param address The address to validate.
     * @return {@code true} if the address is valid, {@code false} otherwise.
     */
    public static boolean isValidIpAndPort(String address) {
        if (StringUtils.isEmpty(address)) {
            return Boolean.FALSE;
        }
        String regex = "^(localhost|(\\d{1,3}\\.){3}\\d{1,3}:[1-9]\\d*)$";
        return address.matches(regex);
    }

    /**
     * Constructs a URI based on the provided address, port, and alias.
     *
     * @param address The address.
     * @param port    The port.
     * @param alias   The alias.
     * @return The constructed URI.
     * @throws URISyntaxException If the URI syntax is invalid.
     */
    public static URI getUri(String address, Integer port, String alias) throws URISyntaxException {
        return new URI("ws", null, address, port, "/mock",
                "ip=" + IpUtil.getLocalIp() + "&alias=" + alias, null);
    }
}