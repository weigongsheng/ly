package com.xingyou5.model.security.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
 */
@Service("customAccessDecisionManager")
public class CustomAccessDecisionManager implements AccessDecisionManager {

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		Collection<GrantedAuthority> as = authentication.getAuthorities();
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			// ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
			for (GrantedAuthority ga : as) {
				if (needRole.trim().equals(ga.getAuthority().trim())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("Access is denied");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}