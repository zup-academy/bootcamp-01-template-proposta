package com.github.marcoscoutozup.proposta.security;

import com.auth0.jwt.JWT;

public class JwtDecoder {

    public static String retornarEmailDoToken(String token){
        return JWT.decode(token.substring(7))
                .getClaim("email")
                .asString();
    }
}
