package main.project.server.oauth.wrapper;

import lombok.Getter;
import lombok.Setter;
import main.project.server.oauth.data.OAuthAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class CustomDefaultOAuth2User extends DefaultOAuth2User {

    private OAuthAttributes oAuthAttributes;

    public CustomDefaultOAuth2User(Collection<? extends GrantedAuthority> authorities,
                                   Map<String, Object> attributes,
                                   String nameAttributeKey,
                                   OAuthAttributes oAuthAttributes) {
        super(authorities, attributes, nameAttributeKey);
        this.oAuthAttributes = oAuthAttributes;
    }

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */

}
