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
 * Represents a Mockit service class.
 * This class implements the Serializable interface to support serialization.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mockit_service_class")
public class MockitServiceClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * Service id
     */
    @TableField("service_id")
    private String serviceId;

    /**
     * Name of the service class
     */
    @TableField("class_name")
    private String className;

    /**
     * Indicates whether the service class is mocked or not（0.disabled, 1.enabled）
     */
    @TableField("mock_enabled")
    private Integer mockEnabled;

    /**
     * Marks if the service class is deleted（0.not deleted, 1.deleted）
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * Additional information or notes
     */
    @TableField("remarks")
    private String remarks;

    /**
     * Date and time when the service class was created
     */
    @TableField("create_at")
    private Date createAt;

    /**
     * Date and time when the service class was updated
     */
    @TableField("update_at")
    private Date updateAt;


}
