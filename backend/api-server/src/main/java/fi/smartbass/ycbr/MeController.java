package fi.smartbass.ycbr;

import java.time.Instant;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    private final Log LOGGER = LogFactory.getLog(MeController.class);

    @GetMapping("/me")
    public UserInfoDto getMe(Authentication auth) {
        LOGGER.info("getMe() called: " + auth);
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            final String email = (String) jwtAuth.getTokenAttributes()
                .getOrDefault(StandardClaimNames.EMAIL, "");
            final List<String> roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
            final String firstname = (String) jwtAuth.getTokenAttributes()
                    .getOrDefault(StandardClaimNames.GIVEN_NAME, "");
            final String lastname = (String) jwtAuth.getTokenAttributes()
                    .getOrDefault(StandardClaimNames.FAMILY_NAME, "");
            final Long exp = Optional.ofNullable(jwtAuth.getTokenAttributes()
                .get(JwtClaimNames.EXP)).map(expClaim -> {
                    if(expClaim instanceof Long lexp) {
                        return lexp;
                    }
                    if(expClaim instanceof Instant iexp) {
                        return iexp.getEpochSecond();
                    }
                    if(expClaim instanceof Date dexp) {
                        return dexp.toInstant().getEpochSecond();
                    }
                    return Long.MAX_VALUE;
                }).orElse(Long.MAX_VALUE);
            return new UserInfoDto(auth.getName(), email, firstname, lastname, roles, exp);
        }
        return UserInfoDto.ANONYMOUS;
    }

    /**
     * @param username a unique identifier for the resource owner in the token (sub claim by default)
     * @param email OpenID email claim
     * @param firstname resource owner given name
     * @param lastname resource owner family name
     * @param roles Spring authorities resolved for the authentication in the security context
     * @param exp seconds from 1970-01-01T00:00:00Z UTC until the specified UTC date/time when the access token expires
     */
    public static record UserInfoDto(String username, String email, String firstname, String lastname, List<String> roles, Long exp) {
        public static final UserInfoDto ANONYMOUS = new UserInfoDto("", "", "", "", java.util.List.of(), null);
    }
}
