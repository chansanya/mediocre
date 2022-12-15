package com.chansan.core.security.authentication;


import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import com.chansan.modules.sms.ISmsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaptchaAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final UserDetailsService userDetailsService;
    private final ISmsService smsService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public CaptchaAuthenticationProvider(UserDetailsService userDetailsService, ISmsService smsService) {
        this.userDetailsService = userDetailsService;
        this.smsService = smsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.isInstanceOf(CaptchaAuthenticationToken.class, authentication,
                () -> messages.getMessage("CaptchaAuthenticationProvider.onlySupports", "Only CaptchaAuthenticationToken is supported"));

        CaptchaAuthenticationToken unAuthenticationToken = (CaptchaAuthenticationToken) authentication;

        String phone = unAuthenticationToken.getName();
        String rawCode = (String) unAuthenticationToken.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

        // 此处省略对UserDetails 的可用性 是否过期  是否锁定 是否失效的检验  建议根据实际情况添加  或者在 UserDetailsService 的实现中处理
        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("Bad credentials");
        }

        // 验证码校验
        if (smsService.verifyCaptcha(phone, rawCode)) {
            //验证通过 更新凭证
            return createSuccessAuthentication(authentication, userDetails);
        } else {
            throw new BadCredentialsException("captcha is not matched");
        }

    }

    /**
     * 认证成功将非授信凭据转为授信凭据.
     * 封装用户信息 角色信息。
     *
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        CaptchaAuthenticationToken authenticationToken = new CaptchaAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //认证器是否是 CaptchaAuthenticationToken
        return CaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(userDetailsService, "userDetailsService must not be null");
        Assert.notNull(smsService, "captchaService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
    
}
