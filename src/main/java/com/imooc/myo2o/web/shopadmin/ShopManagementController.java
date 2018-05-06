package com.imooc.myo2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.enums.ShopStateEnums;
import com.imooc.myo2o.service.AreaService;
import com.imooc.myo2o.service.ShopCategoryService;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.CodeUtil;
import com.imooc.myo2o.util.HttpServletRequestUtil;
import com.imooc.myo2o.util.ImageUtils;
import com.imooc.myo2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ShopCategoryService shopCategoryService;

	@RequestMapping("/getshopinitinfo")
	@ResponseBody
	private Map<String, Object> getshopinitinfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> areaList = areaService.getAreaList();
		List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
		try {
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping("/registershop")
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//判断验证码输入是否正确
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}

		//1.接受并转化相应的参数,包括店铺信息,图片信息等
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		try {
			//将从前端传过来的JSON字符串转换为Shop对象.
			mapper.readValue(shopStr, Shop.class);
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
		//解析传入的图片
		Shop shop = null;
		MultipartRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest MultiparthttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) MultiparthttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		if (shop != null & shopImg != null) {
			//这个店主的信息以后通过session可以获取到,现在先硬编码一下
			shop.setOwnerId(1L);
			//			//这里需要一个CommonsMultipartFile转换为File的方法.
			//			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtils.getRandomFileName());
			//			try {
			//				//这里创建出了shopImgFile文件作为一个用于转换的临时文件.
			//				shopImgFile.createNewFile();
			//			} catch (IOException e) {
			//				modelMap.put("success", false);
			//				modelMap.put("errMsg", e.getMessage());
			//				return modelMap;
			//			}
			//			try {
			//				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			//			} catch (IOException e) {
			//				modelMap.put("success", false);
			//				modelMap.put("errMsg", e.getMessage());
			//				return modelMap;
			//			}
			ShopExecution se;
			try {
				//这个addShop()方法传入的后两个参数之所以不合并成传入一个CommonsMultipartFile类型,是因为在做测试的时候CommonsMultpartFile类型很难弄出来.权衡利弊还是多传入一个参数比较好.
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnums.CHECK.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			/*
			 * 网络上流传的CommonsMultipartFile转File的方法行不通,坑啊
			DiskFileItem fi = (DiskFileItem)(shopImg.getFileItem()); 
			File f = fi.getStoreLocation();
			*/
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	}

	//后来发现thumbnails的of方法可以接受一个InputStream参数,所以对代码做了比较大的改动,不需要这个转换方法了
	//
	//	/*
	//	 * CommonsMultipartFile有一个getInputStream方法,所以可以创建一个InputStream转File的方法.
	//	 */
	//	private static void inputStreamToFile(InputStream ins, File file) {
	//		FileOutputStream fos = null;
	//		try {
	//			fos = new FileOutputStream(file);
	//			int count = 0;
	//			byte[] byteBuffer = new byte[1024];
	//			if (ins.read(byteBuffer) != -1) {
	//				fos.write(byteBuffer, 0, count);
	//			}
	//		} catch (IOException e) {
	//			throw new RuntimeException("创建io流失败");
	//		} finally {
	//			try {
	//				if (ins != null) {
	//					ins.close();
	//				}
	//				if (fos != null) {
	//					fos.close();
	//				}
	//			} catch (Exception e) {
	//				throw new RuntimeException("关闭io流失败");
	//			}
	//		}
	//
	//	}
	//
}
