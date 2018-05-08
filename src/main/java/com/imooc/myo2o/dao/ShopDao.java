package com.imooc.myo2o.dao;

import java.util.List;

import com.imooc.myo2o.entity.Shop;

public interface ShopDao {

	/*
	 * 根据给定的shop信息去查询所有符合这个给定信息的对应的所有的shop
	 */
	public List<Shop> queryShopList(Shop shopCondition);

	/*
	 * 根据给定的shopId去查询对应的shop消息.
	 */
	public Shop queryShopByShopId(Long shopId);

	/*
	 * 插入shop,如果返回1则插入成功,如果返回-1,则失败,这个-1是mybatis返回的.
	 */
	public int insertShop(Shop shop);

	public int updateShop(Shop shop);

	public int deleteShop(Shop shop);
}
