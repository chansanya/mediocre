package com.chansan.core.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private String captcha;

    /**
     * 此构造函数用来初始化未授信凭据.
     *
     * @param principal   the principal
     * @param captcha the captcha
     * @see CaptchaAuthenticationToken#CaptchaAuthenticationToken(Object, String, Collection)
     */
    public CaptchaAuthenticationToken(Object principal, String captcha) {
        super(null);
        this.principal =  principal;
        this.captcha = captcha;
        setAuthenticated(false);
    }



    /**
     * 此构造函数用来初始化授信凭据.
     *
     * @param principal       the principal
     * @param captcha     the captcha
     * @param authorities the authorities
     * @see CaptchaAuthenticationToken#CaptchaAuthenticationToken(Object, String)
     */
    public CaptchaAuthenticationToken(Object principal, String captcha,  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.captcha = captcha;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getCredentials() {
        return this.captcha;
    }

    public Object getPrincipal() {
        return this.principal;
    }



    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        captcha = null;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException( "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


}
