package io.weba.api.ui.rest.security.oauth2.authorization;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
//        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) authentication.getUserAuthentication().getPrincipal();
//        additionalInfo.put("user_id", userDetailsAdapter.domainUser.getId());
//        additionalInfo.put("username", userDetailsAdapter.domainUser.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
