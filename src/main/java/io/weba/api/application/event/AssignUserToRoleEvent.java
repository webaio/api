package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.role.RoleId;
import io.weba.api.domain.user.UserId;

import javax.validation.constraints.NotNull;

public class AssignUserToRoleEvent implements DomainEvent {
    @NotNull
    public UserId userId;

    @NotNull
    public RoleId roleId;

    public RoleId roleId() {
        return this.roleId;
    }

    public UserId userId() {
        return this.userId;
    }
}
