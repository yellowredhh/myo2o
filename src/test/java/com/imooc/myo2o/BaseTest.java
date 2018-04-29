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
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
@WebAppConfiguration("src/main/resources")
public class BaseTest {

}
