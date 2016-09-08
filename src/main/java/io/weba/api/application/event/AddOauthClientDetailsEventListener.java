package io.weba.api.application.event;

import io.weba.api.domain.oauth.OauthClientDetails;
import io.weba.api.domain.oauth.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddOauthClientDetailsEventListener {
    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    public AddOauthClientDetailsEventListener(OauthClientDetailsRepository oauthClientDetailsRepository) {
        this.oauthClientDetailsRepository = oauthClientDetailsRepository;
    }

    @EventListener
    public void handle(AddOauthClientDetailsEvent addOauthClientDetailsEvent) {
        OauthClientDetails oauthClientDetails = new OauthClientDetails(
                addOauthClientDetailsEvent.clientId().toString(),
                addOauthClientDetailsEvent.clientSecret().toString()
        );

        this.oauthClientDetailsRepository.add(oauthClientDetails);
    }
}
