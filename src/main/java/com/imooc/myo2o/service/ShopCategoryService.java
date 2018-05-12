package com.imooc.myo2o.service;

import java.util.List;

import com.imooc.myo2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 根据查询条件获取shopCategoryList
	 * @param shopCategoryCondition
	 * @return
	 */
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
