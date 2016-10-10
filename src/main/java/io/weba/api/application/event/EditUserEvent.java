package io.weba.api.application.event;

import java.util.UUID;
import io.weba.api.application.base.DomainEvent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditUserEvent implements DomainEvent {
    public UUID userId;

    @Size(min = 2, max = 50)
    public String password;

    @Min(value = 0)
    @Max(value = 1)
    @NotNull
    public Integer enabled;

    @NotNull
    public String role;

    public UUID userId() {
        return this.userId;
    }

    public Boolean enabled() {
        return this.enabled == 1;
    }

    public String role() {
        return this.role;
    }
}
