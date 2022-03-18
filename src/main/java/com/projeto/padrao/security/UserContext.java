package com.projeto.padrao.security;

import com.projeto.padrao.dto.ModelDTO;
import com.projeto.padrao.security.token.RawAccessJwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor
public class UserContext {

    @Getter private final String username;

    @Getter private final List<GrantedAuthority> authorities;

    @Getter private final ModelDTO user;

    @Getter private final RawAccessJwtToken rawAccessToken;

    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        return create(username, authorities, null);
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities, RawAccessJwtToken rawAccessToken) {
        return create(username, authorities, null, rawAccessToken);
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities, ModelDTO user, RawAccessJwtToken rawAccessToken) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Operação inválida! O campo 'username' não pode ser vazio ou nulo.");
        }
        return new UserContext(username, authorities, user, rawAccessToken);
    }
}
