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
import java.util.List;

/**
 * Represents a Mockit service method.
 * This class implements the Serializable interface to support serialization.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MockitServiceMethodDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Class id
     */
    @NotBlank
    private String classId;

    /**
     * Name of the method
     */
    @NotBlank
    private String methodName;

    /**
     * Access modifier of the method (e.g., public, private)
     */
    @NotBlank
    private String accessModifer;

    /**
     * Return type of the method
     */
    @NotBlank
    private String returnType;

    /**
     * List of method parameters (comma-separated)
     */
    @NotBlank
    private String parameters;

    /**
     * Indicates whether the service method is mocked or not（0.disabled, 1.enabled）
     */
    @NotNull
    private Integer mockEnabled;

    /**
     * Marks if the service method is deleted（0.not deleted, 1.deleted）
     */
    private Integer deleted;

    /**
     * Additional information or notes
     */
    private String remarks;

    /**
     * Date and time when the service method was created
     */
    private Date createAt;

    /**
     * Date and time when the service method was updated
     */
    private Date updateAt;

    private String serviceId;

    private String className;

    private List<String> classIdList;

    private Boolean isMockData;

    private MockitMethodMockDataDTO mockDataDTO;
}
