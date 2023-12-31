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

package cn.thinkinginjava.mockit.admin.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Represents a Mockit service class.
 * This class implements the Serializable interface to support serialization.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MockitServiceClassDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Service id
     */
    @NotBlank
    private String serviceId;

    /**
     * Name of the service class
     */
    @NotBlank
    private String className;

    /**
     * Indicates whether the service class is mocked or not（0.disabled, 1.enabled）
     */
    private Integer mockEnabled;

    /**
     * Marks if the service class is deleted（0.not deleted, 1.deleted）
     */
    private Integer deleted;

    /**
     * Additional information or notes
     */
    private String remarks;

    /**
     * Date and time when the service class was created
     */
    private Date createAt;

    /**
     * Date and time when the service class was updated
     */
    private Date updateAt;


}
