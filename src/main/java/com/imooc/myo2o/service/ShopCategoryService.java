package com.imooc.myo2o.service;

import java.util.List;

import com.imooc.myo2o.entity.ShopCategory;

public interface ShopCategoryService {
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
