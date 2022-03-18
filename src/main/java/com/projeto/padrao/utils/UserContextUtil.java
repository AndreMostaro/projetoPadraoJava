package com.projeto.padrao.utils;

import com.projeto.padrao.security.UserContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Optional;

public final class UserContextUtil implements Serializable {


    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserContext) {
            return ((UserContext) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public static Optional<String> getCurrentUserToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(Boolean.FALSE);
            if (!ObjectUtils.isEmpty(session)) {
                String token = (String) session.getAttribute(HttpHeaders.AUTHORIZATION);
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserContext) {
            return ((UserContext) authentication.getPrincipal()).getUser().getId();
        }
        return null;
    }
}
