package com.imooc.myo2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/*
 * 配置spring和junit整合,junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件在哪里
@ContextConfiguration({ "classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml" })
//不加这个注解,报Failed to load ApplicationContext错误
//ps:由于之前我上面的spring-dao.xml写错了,写成了spring-*.xml,所以无法加载配置文件,也就是不能加载ApplicationContext容器.
//@WebAppConfiguration("src/main/resources")
public class BaseTest {

}
