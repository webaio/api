package io.weba.api.application.event;

import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AssignUserToRoleEventListener {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserUpdater userUpdater;

    @Autowired
    public AssignUserToRoleEventListener(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserUpdater userUpdater
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userUpdater = userUpdater;
    }

    @EventListener
    public void handle(AssignUserToRoleEvent assignUserToRoleEvent) {
        User user = this.userRepository.findBy(assignUserToRoleEvent.userId());
        Role role = this.roleRepository.findBy(assignUserToRoleEvent.roleId());

        if (user != null && role != null) {
            user.addRole(role);

            this.userUpdater.update(user);
        }
    }
}
