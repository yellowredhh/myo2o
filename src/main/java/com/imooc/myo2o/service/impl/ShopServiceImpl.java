package com.imooc.myo2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.myo2o.Exceptions.ShopExecutionException;
import com.imooc.myo2o.dao.ShopDao;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.enums.ShopStateEnums;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.ImageUtils;
import com.imooc.myo2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	/*这个方法完成了添加店铺并给店铺添加缩略图的功能,先添加店铺信息,然后添加缩略图的相对路径属性,然后更新店铺信息(把添加的缩略图路径属性刷新进去)
	 * (non-Javadoc)
	 * @see com.imooc.myo2o.service.ShopService#addShop(com.imooc.myo2o.entity.Shop, java.io.File)
	 * @return 	ShopExecution是service层的处理结果,里面包含了处理状态和shop等信息
	 */
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		if (shop == null) {
			//throw new ShopExecutionException(ShopStateEnums.NULL_SHOP_INFO.getStateInfo());
			return new ShopExecution(ShopStateEnums.NULL_SHOP_INFO);
		} else {
			try {
				shop.setEnableStatus(ShopStateEnums.CHECK.getState());
				shop.setCreateTime(new Date());
				shop.setLastEditTime(new Date());
				//添加店铺信息
				int effectNumber = shopDao.insertShop(shop);
				if (effectNumber <= 0) {
					throw new ShopExecutionException("添加店铺信息失败");
				} else {
					if (shopImgInputStream != null) {
						try {
							/*
							 * 在这里更改了shop对象的属性,由于java对于形参不论是基本数据类型还是对象类型都是采用的值传递
							 *基本数据类型:直接传递数据的拷贝
							 *对象类型:传递的是该对象所指向的堆对象的指针的拷贝,也就是两者会指向同一个堆对象,所以这里改变了shop的属性,在方法外面的shop属性也会改变.
							 */
							//给shop实例添加店铺图片的相对路径属性
							addShopImg(shop, shopImgInputStream, fileName);
						} catch (Exception e) {
							throw new ShopExecutionException("添加店铺缩略图失败:" + e.getMessage());
						}
						effectNumber = shopDao.updateShop(shop);
						if (effectNumber <= 0) {
							throw new ShopExecutionException("更新图片地址失败");
						}
					}
				}
			} catch (Exception e) {
				throw new ShopExecutionException("addShopError:" + e.getMessage());
			}
		}
		//添加成功,返回商铺在"审核中"的状态标识以及商品信息.
		return new ShopExecution(ShopStateEnums.CHECK, shop);
	}

	//首先通过shopId获取到商铺的
	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		//通过shopId来获取商铺的图片的相对路径(这个方法里面有一个硬编码加入了相对路径)
		String shopImagePath = PathUtil.getShopImagePath(shop.getShopId());
		//生成缩略图,并生成新的商品缩略图相对路径
		String shopImgAddr = ImageUtils.generateThumbnail(shopImgInputStream, shopImagePath, fileName);
		//更新商品缩略图的相对路径
		shop.setShopImg(shopImgAddr);
	}
}