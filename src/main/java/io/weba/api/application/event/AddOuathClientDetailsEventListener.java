package io.weba.api.application.event;

import io.weba.api.domain.oauth.OauthClientDetails;
import io.weba.api.domain.oauth.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddOuathClientDetailsEventListener {
    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    public AddOuathClientDetailsEventListener(OauthClientDetailsRepository oauthClientDetailsRepository) {
        this.oauthClientDetailsRepository = oauthClientDetailsRepository;
    }

    @EventListener
    public void handle(AddOuathClientDetailsEvent addOuathClientDetailsEvent) {
        OauthClientDetails oauthClientDetails = new OauthClientDetails(
                addOuathClientDetailsEvent.clientId().toString(),
                addOuathClientDetailsEvent.clientSecret().toString()
        );

        this.oauthClientDetailsRepository.add(oauthClientDetails);
    }
}
