package cn.thinkinginjava.mockit.admin.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Batch enabled dto.
 */
public class BatchEnabledDTO implements Serializable {

    private static final long serialVersionUID = -7574242366401326562L;

    @NotEmpty
    @NotNull
    private List<@NotBlank String> ids;

    @NotNull
    private Integer enabled;

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
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled enabled
     */
    public void setEnabled(final Integer enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchEnabledDTO)) {
            return false;
        }
        BatchEnabledDTO that = (BatchEnabledDTO) o;
        return Objects.equals(ids, that.ids) && Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, enabled);
    }
}