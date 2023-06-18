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

package cn.thinkinginjava.mockit.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The MockitExampleApplication class is the entry point of the Spring Boot application.
 * It is annotated with @SpringBootApplication, which indicates that this class is a Spring Boot application and
 * enables auto-configuration, component scanning, and other features provided by Spring Boot.
 */
@SpringBootApplication
public class MockitExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockitExampleApplication.class, args);
    }

}
