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

import java.util.Date;

/**
 * Represents the mock data for a Mockit service method.
 * This class implements the Serializable interface to support serialization.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MockitServiceMethodMockDataDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Method id
     */
    private String methodId;

    /**
     * Mock data for the method
     */
    private String mockValue;

    /**
     * Indicates whether the mock data is mocked or not（0.disabled, 1.enabled）
     */
    private Integer mockEnabled;

    /**
     * Marks if the mock data is deleted（0.not deleted, 1.deleted）
     */
    private Integer deleted;

    /**
     * Additional information or notes
     */
    private String remarks;

    /**
     * Date and time when the mock data was created
     */
    private Date createAt;

    /**
     * Date and time when the mock data was updated
     */
    private Date updateAt;


}
