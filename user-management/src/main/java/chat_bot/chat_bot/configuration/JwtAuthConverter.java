package chat_bot.chat_bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    @Value("${principle-attribute}")
    private String principleAttribut;
    @Value("${resourceId}")
    private String resourceId;
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        System.out.println("JWT Claims: " + jwt.getClaims());

        String claimName = JwtClaimNames.SUB; // Default claim

        if (principleAttribut != null) {
            claimName = principleAttribut;
        }

        System.out.println("Looking for claim: " + claimName);

        if (!jwt.hasClaim(claimName)) {
            System.out.println("ERROR: Claim '" + claimName + "' is missing in JWT.");
            return null; // Handle missing claim
        }

        String claimValue = jwt.getClaim(claimName);
        System.out.println("Retrieved claim value: " + claimValue);
        return claimValue;
    }




    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        System.out.println("JWT Claims: " + jwt.getClaims());

        if (resourceId == null || resourceId.isEmpty()) {
            System.out.println("ERROR: 'resourceId' property is not set!");
            return Set.of();
        }

        Object resourceAccessObj = jwt.getClaim(resourceId);

        if (resourceAccessObj == null) {
            System.out.println("WARNING: JWT does not contain claim: " + resourceId);
            return Set.of();
        }

        if (!(resourceAccessObj instanceof Map)) {
            System.out.println("ERROR: Expected '" + resourceId + "' to be a Map, but got: " + resourceAccessObj);
            return Set.of();
        }

        Map<String, Object> resourceAccess = (Map<String, Object>) resourceAccessObj;
        Object rolesObj = resourceAccess.get("roles");

        if (rolesObj == null) {
            System.out.println("WARNING: 'roles' missing inside '" + resourceId + "'");
            return Set.of();
        }

        if (!(rolesObj instanceof Collection)) {
            System.out.println("ERROR: Expected 'roles' to be a Collection, but got: " + rolesObj);
            return Set.of();
        }

        Collection<String> resourceRoles = (Collection<String>) rolesObj;
        System.out.println("Extracted Roles: " + resourceRoles);

        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
