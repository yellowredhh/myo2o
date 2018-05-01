package com.imooc.myo2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;

public class AreaServiceTest extends BaseTest{
	
	@Autowired
	private AreaService areaService;
	
	@Test
	public void getAreaListTest(){
		List<Area> areaList = areaService.getAreaList();
		assertEquals("东苑",areaList.get(0).getAreaName());
	}

}