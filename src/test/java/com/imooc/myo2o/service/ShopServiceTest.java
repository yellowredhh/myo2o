package com.imooc.myo2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
		ShopExecution shopExecution = shopService.addShop(shop, shopImg);
		assertEquals(0, shopExecution.getState());
	}
}
