package com.imooc.myo2o.service;

import java.io.File;

import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;

public interface ShopService {
	public ShopExecution addShop(Shop shop, File shopImg);
}
