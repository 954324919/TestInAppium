package com.cmic.GoAppiumTest.biz.performanalyze;

import com.cmic.GoAppiumTest.helper.Tips;

@Tips(description = "获取屏幕流畅度的性能脚本", riskPoint = "部分机型可能不支持")
public class FPSAnalyze {
	// 两个关键指标60帧每秒以及16.67毫秒,表征肉眼视觉对图像刷新的临界值，大于此人就会意识到刷新卡顿 1000/60≈16.67
	// !(Fps的相关介绍)[https://testerhome.com/topics/4436]

}
