package com.imooc.myo2o;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpuSpeedTest {
	public static void main(String[] args) {

		List<Date> filterLists = new ArrayList<>();
		Date aa = new Date();

		for (int i = 0; i < 1000000; i++) {
			filterLists.add(new Date());
		}

		Date a = new Date();
		for (int j = 0; j < 1000000; j++) {
			System.out.println(filterLists.get(j));
		}
		Date b = new Date();

		Date c = new Date();
		filterLists.stream().forEach(s -> System.out.println(s));
		Date d = new Date();

		long buildtime = a.getTime() - aa.getTime();
		long interval = b.getTime() - a.getTime();
		long interval2 = d.getTime() - c.getTime();
		System.out.println("创建时间:" + buildtime);
		System.out.println("两个时间相差1:" + interval);
		System.out.println("两个时间相差2:" + interval2);
	}

}
