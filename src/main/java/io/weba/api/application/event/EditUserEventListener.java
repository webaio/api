package io.weba.api.application.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EditUserEventListener {

    @EventListener
    public void handle(EditUserEvent editUserEvent) {

    }
}
