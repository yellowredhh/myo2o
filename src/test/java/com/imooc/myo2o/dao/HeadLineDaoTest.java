package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest {

	@Autowired
	private HeadLineDao headLineDao;

	@Test
	public void queryHeadLineTest() {
		List<HeadLine> headLinLlist = headLineDao.queryHeadLine(new HeadLine());
		assertEquals(4, headLinLlist.size());
		System.out.println(headLinLlist);
	}
}
