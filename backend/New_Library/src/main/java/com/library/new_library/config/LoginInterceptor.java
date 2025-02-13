package com.library.new_library.config;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.library.new_library.annotation.PassToken;
import com.library.new_library.annotation.UserLoginToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LoginInterceptor implements HandlerInterceptor {
	// 签名过期时间 2天
	private static final long EXPIRE_TIME = 2*24*60*60*1000;
//    private static final long EXPIRE_TIME = 5*1000;
	// 签名秘钥 可以自己设定
	private static final String PRIVATE_KEY = "ThisIsSecret";
	
	public static String GenerateToken(String userId) {
		String token = Jwts.builder()
				.setId(userId)
				// 登录时间
				.setIssuedAt(new Date())
				// 失效时间
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
				// 使用SignatureAlgorithm.HS256算法加密，秘钥：是用户的密码
				.signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
				.compact();
		return token;
	}
	

	@Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception{
		String token = httpServletRequest.getHeader("token");// 从http请求头中取出token
		// 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有PassToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                	throw new RuntimeException("请重新登录");
                }
                // 解析token
                try {
					Jwts.parser()
                  		.setSigningKey(PRIVATE_KEY)
                  		.parseClaimsJws(token).getBody();
				} catch (Exception e) {
					throw new RuntimeException("token过期，请重新登录");
				}
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, 
    		Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, 
    		Object o, Exception e) throws Exception {
    }
}
