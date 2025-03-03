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
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${principle-attribute:preferred_username}")
    private String principalAttribute; // Fixed typo from "principleAttribut"

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(), // Scope-based roles (e.g., from "scope" claim)
                extractRealmRoles(jwt).stream() // Realm roles from "realm_access"
        ).collect(Collectors.toSet());

        String principal = getPrincipalClaimName(jwt);
        System.out.println("Authorities: " + authorities);
        return new JwtAuthenticationToken(jwt, authorities, principal);
    }

    private String getPrincipalClaimName(Jwt jwt) {
        System.out.println("JWT Claims: " + jwt.getClaims());
        String claimName = principalAttribute != null && !principalAttribute.isEmpty()
                ? principalAttribute
                : JwtClaimNames.SUB;

        String claimValue = jwt.getClaimAsString(claimName);
        if (claimValue == null) {
            System.out.println("WARNING: Claim '" + claimName + "' not found, falling back to 'sub'");
            claimValue = jwt.getClaimAsString(JwtClaimNames.SUB);
        }
        System.out.println("Principal claim: " + claimName + " = " + claimValue);
        return claimValue != null ? claimValue : "unknown";
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            System.out.println("WARNING: 'realm_access' not found in JWT.");
            return Set.of();
        }

        Object rolesObj = realmAccess.get("roles");
        if (rolesObj == null || !(rolesObj instanceof Collection)) {
            System.out.println("WARNING: 'roles' not found or invalid in 'realm_access': " + rolesObj);
            return Set.of();
        }

        Collection<String> realmRoles = (Collection<String>) rolesObj;
        System.out.println("Extracted Realm Roles: " + realmRoles);

        return realmRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }
}