package com.imooc.myo2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.myo2o.Exceptions.ProductCategoryExecutionException;
import com.imooc.myo2o.dao.ProductCategoryDao;
import com.imooc.myo2o.dto.ProductCategoryExecution;
import com.imooc.myo2o.entity.ProductCategory;
import com.imooc.myo2o.enums.ProductCategoryStateEnum;
import com.imooc.myo2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	ProductCategoryDao productCategoryDao;

	@Override
	public List<ProductCategory> getProductCategoryList(Long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryExecutionException {
		if (productCategoryList != null && productCategoryList.size() != 0) {
			try {
				int effectNumber = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNumber <= 0) {
					throw new ProductCategoryExecutionException("创建店铺失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
				}
			} catch (ProductCategoryExecutionException pcee) {
				throw new ProductCategoryExecutionException("batchAddProductCategory error:" + pcee.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryExecutionException {
		//TODO 将此类别下的商品里的类别id置为空
		
		try {
			int effectNumber = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectNumber <= 0) {
				throw new ProductCategoryExecutionException("删除商品分类失败");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (ProductCategoryExecutionException e) {
			throw new ProductCategoryExecutionException("deleteProductCategoryError:" + e.getMessage());
		}
	}
}
