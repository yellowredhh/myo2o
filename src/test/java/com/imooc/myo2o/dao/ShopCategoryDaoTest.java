package com.imooc.myo2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void queryShopCategoryDaoTest() {
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setParentId(11L);
		//List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
		System.out.println(shopCategoryList);
	}
}
