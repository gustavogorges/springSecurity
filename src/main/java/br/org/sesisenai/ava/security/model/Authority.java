package br.org.sesisenai.ava.security.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    GET,
    POST,
    PUT;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
