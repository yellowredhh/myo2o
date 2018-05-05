package com.imooc.myo2o.util;

public class PathUtil {

	private static String separator = System.getProperty("file.separator");

	private static String basePath = "";

	/*
	 * 根据系统类型获取图片的绝对路径,不要把这个路径设置在classpath路径下,因为项目重新部署的时候会清空classpath文件夹下的其它文件
	 * 实际生成环境中一般都是把这个目录部署在其他服务器上
	 * windows下的文件夹分隔符是"\",linux和mac是"/";
	 */
	public static String getImgBasePath() {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("win")) {
			basePath = "F:/Projects/eclipse/Myo2o/";
		} else {
			basePath = "/home/huanghong/Myo2o/";
		}
		return basePath.replace("/", separator);
	}
	
	/*
	 * 获取商铺图片的相对路径(这个路径和basePath一起组成了全路径)
	 */
	public static String getShopImagePath(Long shopId) {
		//其实当我这个相对路径和上面方法中的绝对路径进行组合的时候会多出来一个"/"符号,但是现在没有报错了.通过日志可以看到自动去掉了重复的"/".
		String shopImagePath = "/upload/images/item/shop/" + shopId + "/";
		return shopImagePath.replace("/", separator);
	}
}
