package com.imooc.myo2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService;

	@Test
	public void addShopTest() {
		Shop shop = new Shop();
		shop.setOwnerId(9L);
		shop.setShopName("addShopTest");
		File shopImg = new File("C:/Users/hh/Pictures/manyCoder.jpg");
		//记得把shopImg文件放到src/test/resources/目录下,因为这个程序是在/src/test/java/目录下面运行的.否则报Can't read input file!
		ShopExecution shopExecution = shopService.addShop(shop, shopImg);
		assertEquals(0, shopExecution.getState());
	}
}
