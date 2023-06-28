package cn.thinkinginjava.mockit.admin.model.dto;

import cn.thinkinginjava.mockit.common.constant.MockConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Batch common dto.
 */
public class BatchCommonDTO implements Serializable {

    private static final long serialVersionUID = -7574242366401326562L;

    @NotEmpty
    @NotNull
    private List<@NotBlank String> ids;

    @NotNull
    private Boolean enabled;

    /**
     * Gets the value of ids.
     *
     * @return the value of ids
     */
    public List<String> getIds() {
        return ids;
    }

    /**
     * Sets the ids.
     *
     * @param ids ids
     */
    public void setIds(final List<String> ids) {
        this.ids = ids;
    }

    /**
     * Gets the value of enabled.
     *
     * @return the value of enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Gets the value of enabled.
     *
     * @return the value of enabled
     */
    public Integer getEnabledValue() {
        return enabled ? MockConstants.YES : MockConstants.NO;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled enabled
     */
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchCommonDTO)) {
            return false;
        }
        BatchCommonDTO that = (BatchCommonDTO) o;
        return Objects.equals(ids, that.ids) && Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, enabled);
    }
}