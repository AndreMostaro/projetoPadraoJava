package com.projeto.padrao.security.token;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RawAccessJwtToken implements JwtToken {

    private String token;

    @Override
    public String getToken() {
        return this.token;
    }
}
