package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.Shop;

public interface ShopDao {
	
	/*
	 * 插入shop,如果返回1则插入成功,如果返回-1,则失败,这个-1是mybatis返回的.
	 */
	public int insertShop(Shop shop);
	
	public int updateShop(Shop shop);
	
	public int deleteShop(Shop shop);
}
