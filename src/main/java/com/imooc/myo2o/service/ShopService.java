package com.imooc.myo2o.service;

import java.io.InputStream;

import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;

public interface ShopService {
	
	/*
	 * 根据店铺id查询店铺信息
	 */
	public Shop queryShopByShopId(Long shopId);

	/*
	 * 修改店铺
	 */
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String FileName);

	/*
	 * 增加商铺
	 */
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String FileName);
}
