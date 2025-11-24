package com.example.basicspringnewsfeed.common.entity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver{

    // 2025-11-24 : 어노테이션 검증 : Controller Method의 Parameter가 CurrentUser인지 체크.
    @Override
    public boolean supportsParameter(MethodParameter parameter){
        // 여러 Resolver가 있다 가정했을 때의 충돌 방지.
        return parameter.hasParameterAnnotation(AuthUser.class)
                && parameter.getParameterType().equals(CurrentUser.class);
    }

    // 2025-11-24 : 실제 Parameter 값 생성.
    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ){
        // 이 요청을 보낸 유저의 인증 정보 : Authentication.
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        // 필터에서 인증을 안했거나, JWT를 통해 인증 롼료된 유저가 아니라면, 예외.
        if(authentication==null||!(authentication.getPrincipal() instanceof CustomUserDetails principal)){
            throw new AccessDeniedException("Invalid Access");
        }
        // Controller용 DTO로 변환.
        return CurrentUser.from(principal);
    }
}
