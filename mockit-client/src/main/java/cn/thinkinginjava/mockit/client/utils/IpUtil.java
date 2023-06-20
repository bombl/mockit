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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Utility class for retrieving the local IP address.
 */
public class IpUtil {

    /**
     * Retrieves the local IP address.
     *
     * @return The local IP address as a string.
     */
    public static String getLocalIp() {
        InetAddress inetAddress = null;
        boolean isFind = false;
        Enumeration<NetworkInterface> networkInterfaceLists = null;
        try {
            networkInterfaceLists = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (Objects.requireNonNull(networkInterfaceLists).hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaceLists.nextElement();
            Enumeration<InetAddress> ips = networkInterface.getInetAddresses();
            while (ips.hasMoreElements()) {
                inetAddress = ips.nextElement();
                if (inetAddress instanceof Inet4Address && inetAddress.isSiteLocalAddress()
                        && !inetAddress.isLoopbackAddress()) {
                    isFind = true;
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        return inetAddress == null ? "" : inetAddress.getHostAddress();
    }

}