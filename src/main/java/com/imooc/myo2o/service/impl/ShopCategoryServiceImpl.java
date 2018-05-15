package com.imooc.myo2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.myo2o.dao.ShopCategoryDao;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.ShopCategoryExecution;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.enums.ShopCategoryStateEnum;
import com.imooc.myo2o.service.ShopCategoryService;
import com.imooc.myo2o.util.ImageUtils;
import com.imooc.myo2o.util.PathUtil;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

//	@Autowired
//	private JedisUtil.Strings jedisStrings;
//	@Autowired
//	private JedisUtil.Keys jedisKeys;
	@Autowired
	private ShopCategoryDao shopCategoryDao;

//	private static String SCLISTKEY = "shopcategorylist";

	@Override
	public List<ShopCategory> getFirstLevelShopCategoryList() throws IOException {
		/*	String key = SCLISTKEY;
			List<ShopCategory> shopCategoryList = null;
			ObjectMapper mapper = new ObjectMapper();
			if (!jedisKeys.exists(key)) {
				ShopCategory shopCategoryCondition = new ShopCategory();
				// 当shopCategoryId不为空的时候，查询的条件会变为 where parent_id is null
				shopCategoryCondition.setShopCategoryId(-1L);
				shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
				String jsonString = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, jsonString);
			} else {
				String jsonString = jedisStrings.get(key);
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
				shopCategoryList = mapper.readValue(jsonString, javaType);
			}
			return shopCategoryList;
			*/
		ShopCategory shopCategoryCondition = new ShopCategory();
		// 当shopCategoryId不为空的时候，查询的条件会变为 where parent_id is null
		shopCategoryCondition.setShopCategoryId(-1L);
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

	@Override
	public List<ShopCategory> getShopCategoryList(Long parentId) throws IOException {
		/*
			String key = SCLISTKEY + "_" + parentId;
			List<ShopCategory> shopCategoryList = null;
			ObjectMapper mapper = new ObjectMapper();
			if (!jedisKeys.exists(key)) {
				ShopCategory shopCategoryCondition = new ShopCategory();
				shopCategoryCondition.setParentId(parentId);
				shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
				String jsonString = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, jsonString);
			} else {
				String jsonString = jedisStrings.get(key);
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
				shopCategoryList = mapper.readValue(jsonString, javaType);
			}
			return shopCategoryList;
			*/
		ShopCategory shopCategoryCondition = new ShopCategory();
		shopCategoryCondition.setParentId(parentId);
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);

	}

	@Override
	public List<ShopCategory> getAllSecondLevelShopCategory() throws IOException {
		/*
		
		String key = SCLISTKEY + "ALLSECOND";
		List<ShopCategory> shopCategoryList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			ShopCategory shopCategoryCondition = new ShopCategory();
			// 当shopCategoryDesc不为空的时候，查询的条件会变为 where parent_id is not null
			shopCategoryCondition.setShopCategoryDesc("ALLSECOND");
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String jsonString = mapper.writeValueAsString(shopCategoryList);
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			shopCategoryList = mapper.readValue(jsonString, javaType);
		}
		return shopCategoryList;
		
		*/
		ShopCategory shopCategoryCondition = new ShopCategory();
		// 当shopCategoryDesc不为空的时候，查询的条件会变为 where parent_id is not null
		shopCategoryCondition.setShopCategoryDesc("ALLSECOND");
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

	@Override
	public ShopCategory getShopCategoryById(Long shopCategoryId) {
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		try {
			shopCategoryList = getFirstLevelShopCategoryList();
			shopCategoryList.addAll(getAllSecondLevelShopCategory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ShopCategory sc : shopCategoryList) {
			if (shopCategoryId == sc.getShopCategoryId()) {
				return sc;
			}
		}
		ShopCategory sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
		if (sc != null) {
			return sc;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder imageHolder) {
		if (shopCategory != null) {
			shopCategory.setCreateTime(new Date());
			shopCategory.setLastEditTime(new Date());
			if (imageHolder != null) {
				addThumbnail(shopCategory, imageHolder);
			}
			try {
				int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
				if (effectedNum > 0) {
					/*
					String prefix = SCLISTKEY;
					Set<String> keySet = jedisKeys.keys(prefix + "*");
					for (String key : keySet) {
						jedisKeys.del(key);
					}
					*/
					return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("添加店铺类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder imageHolder,
			boolean thumbnailChange) {
		if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
			shopCategory.setLastEditTime(new Date());
			if (imageHolder != null && thumbnailChange == true) {
				ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
				if (tempShopCategory.getShopCategoryImg() != null) {
					ImageUtils.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
				}
				addThumbnail(shopCategory, imageHolder);
			}
			try {
				int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
				if (effectedNum > 0) {
					/*
						String prefix = SCLISTKEY;
						Set<String> keySet = jedisKeys.keys(prefix + "*");
						for (String key : keySet) {
							jedisKeys.del(key);
						}
					*/
					return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("更新店铺类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ShopCategoryExecution removeShopCategory(long shopCategoryId) {
		if (shopCategoryId > 0) {
			try {
				ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategoryId);
				if (tempShopCategory.getShopCategoryImg() != null) {
					ImageUtils.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
				}
				int effectedNum = shopCategoryDao.deleteShopCategory(shopCategoryId);
				if (effectedNum > 0) {
					/*
						String prefix = SCLISTKEY;
						Set<String> keySet = jedisKeys.keys(prefix + "*");
						for (String key : keySet) {
							jedisKeys.del(key);
						}
					*/
					return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS);
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("删除店铺类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ShopCategoryExecution removeShopCategoryList(List<Long> shopCategoryIdList) {
		if (shopCategoryIdList != null && shopCategoryIdList.size() > 0) {
			try {
				List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryByIds(shopCategoryIdList);
				for (ShopCategory shopCategory : shopCategoryList) {
					if (shopCategory.getShopCategoryImg() != null) {
						ImageUtils.deleteFileOrPath(shopCategory.getShopCategoryImg());
					}
				}
				int effectedNum = shopCategoryDao.batchDeleteShopCategory(shopCategoryIdList);
				if (effectedNum > 0) {
					/*
						String prefix = SCLISTKEY;
						Set<String> keySet = jedisKeys.keys(prefix + "*");
						for (String key : keySet) {
							jedisKeys.del(key);
						}
					*/
					return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS);
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("删除店铺类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
		}
	}

	private void addThumbnail(ShopCategory shopCategory, ImageHolder iamgeHolder) {
		String dest = PathUtil.getShopCategoryImagePath();
		String thumbnailAddr = ImageUtils.generateNormalImg(iamgeHolder, dest);
		shopCategory.setShopCategoryImg(thumbnailAddr);
	}
}
