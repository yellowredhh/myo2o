package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Shop;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopdao;

	@Test
	public void queryShopListTest() {
		Shop shopCondition = new Shop();
		shopCondition.setShopId(15L);
		List<Shop> shopList = shopdao.queryShopList(shopCondition);
		System.out.println(shopList.size());
	}

	@Test
	public void queryShopByShopIdTest() {
		//数据库中没有这个shopId为1的数据行,查询不报错,给输出了一个null.
		Long shopId = 49l;
		Shop shop = shopdao.queryShopByShopId(shopId);
		System.out.println(shop);
	}

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
		assertEquals(1, effectNumber);
	}

}
