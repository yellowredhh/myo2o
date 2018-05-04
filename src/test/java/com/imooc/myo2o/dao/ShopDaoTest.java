package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Shop;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopdao;

	@Test
	public void insertShopTest() {
		Shop shop = new Shop();
		shop.setOwnerId(9L);
		shop.setShopName("will");
		shop.setEnableStatus(0);
		int test = shopdao.insertShop(shop);
		assertEquals(1, test);
	}

	@Test
	public void updateShopTest() {
		Shop shop = new Shop();
		shop.setShopId(29L);
		shop.setShopAddr("huanghong");
		int effectNumber = shopdao.updateShop(shop);
		assertEquals(1, effectNumber);
	}

	@Test
	public void deleteShopTest() {
		Shop shop = new Shop();
		shop.setShopId(30L);
		int effectNumber = shopdao.deleteShop(shop);
		assertEquals(1,effectNumber);
	}

}
