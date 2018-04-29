package com.imooc.myo2o.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;

public class AreaDaoTest extends BaseTest {
	
	@Autowired
	private AreaDao areadao;
	
	@Test
	public void queryAreaDao(){
		List<Area> arealist = areadao.queryArea();
		assertEquals(2,arealist.size());
		System.out.println(arealist);
	}
}
