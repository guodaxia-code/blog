package xyz.worldzhile.blog.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class MyLocaleResolver implements LocaleResolver {


    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        // 获取请求的语言参数
        String language = httpServletRequest.getParameter("language");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(language)){
            //zh_CN
            String[] split = language.split("_");
                //国家和地区
            if (split.length==2) {
                locale = new Locale(split[0], split[1]);
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
