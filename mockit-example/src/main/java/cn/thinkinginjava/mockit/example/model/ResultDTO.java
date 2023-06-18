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

package cn.thinkinginjava.mockit.example.model;

import lombok.Data;

import java.io.Serializable;

/**
 * The ResultDTO class represents a data transfer object (DTO) for holding the result of an operation.
 * It implements the Serializable interface to enable serialization and deserialization of the object.
 * This class is used to encapsulate the result data and provide a standardized format for transferring data between components or services.
 */
@Data
public class ResultDTO implements Serializable {

    private String result;

    private String code;
}
