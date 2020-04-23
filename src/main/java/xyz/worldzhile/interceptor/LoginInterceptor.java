package xyz.worldzhile.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import xyz.worldzhile.blog.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }
        return true;
    }
}
