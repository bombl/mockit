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

package cn.thinkinginjava.mockit.admin.model.entity;

import cn.thinkinginjava.mockit.admin.model.dto.MockitMethodMockDataDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Represents a Mockit service method.
 * This class implements the Serializable interface to support serialization.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mockit_service_method")
public class MockitServiceMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * Class id
     */
    @TableField("class_id")
    private String classId;

    /**
     * Name of the method
     */
    @TableField("method_name")
    private String methodName;

    /**
     * Access modifier of the method (e.g., public, private)
     */
    @TableField("access_modifer")
    private String accessModifer;

    /**
     * Return type of the method
     */
    @TableField("return_type")
    private String returnType;

    /**
     * List of method parameters (comma-separated)
     */
    @TableField("parameters")
    private String parameters;

    /**
     * Indicates whether the service method is mocked or not（0.disabled, 1.enabled）
     */
    @TableField("mock_enabled")
    private Integer mockEnabled;

    /**
     * Marks if the service method is deleted（0.not deleted, 1.deleted）
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * Additional information or notes
     */
    @TableField("remarks")
    private String remarks;

    /**
     * Date and time when the service method was created
     */
    @TableField("create_at")
    private Date createAt;

    /**
     * Date and time when the service method was updated
     */
    @TableField("update_at")
    private Date updateAt;
}
