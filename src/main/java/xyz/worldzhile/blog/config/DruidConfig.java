package xyz.worldzhile.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DruidConfig {

    //获取yml里许多的配置信息
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return  druidDataSource;
    }

    //后台监控: web.xml
    //SpringBoot 配置 Servlet、Filter、Listener(差不多)
    @Bean
    public ServletRegistrationBean registrationBean(){
        ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");


        Map<String, String> initParameters=new HashMap<>();

//       添加配置
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","admin");
//        允许谁可以访问
        initParameters.put("allow","");
//       禁止谁可以访问
        initParameters.put("","127.1.1.1");

        bean.setInitParameters(initParameters);   //设置初始化参数
        return bean;
    }
}
