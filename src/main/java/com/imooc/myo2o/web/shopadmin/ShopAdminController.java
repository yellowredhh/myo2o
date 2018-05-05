package com.imooc.myo2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = RequestMethod.GET)
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation")
	//@ResponseBody 不要乱加注释
	/*
	 * @ResponseBody这个注解表示该方法的返回结果直接写入HTTP response body中，一般在异步获取数据时使用。
	 * 在使用@RequestMapping后，返回值通常解析为跳转路径。加上@responsebody后，
	 * 返回结果直接写入HTTP response body中，不会被解析为跳转路径。比如异步请求，
	 * 希望响应的结果是json数据，那么加上@responsebody后，就会直接返回json数据。
	 */
	public String shopOperation() {
		return "shop/shopoperation";
	}
}
