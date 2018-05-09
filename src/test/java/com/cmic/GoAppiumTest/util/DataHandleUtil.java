package com.cmic.GoAppiumTest.util;

import java.util.ArrayList;

import com.cmic.GoAppiumTest.helper.Tips;

@Tips(description = "用于进行数据处理的工具类")
public class DataHandleUtil {

	@Tips(description = "确定检出水平系数，一般为0.01或0.05，越大越宽松，与G1，Gn作比较；若G1（Gn）大于临界值，则剔除，反之保留")
	public static final double ALPHA = 0.05;

	@Tips(description = "格拉布斯准则剔除异常值")
	public ArrayList<Double> calc(ArrayList<Double> dataArrayList) {
		int length = dataArrayList.size();
		// 因为格拉布斯准则只能对大于等于3个数据进行判断，所以数据量小于3时，直接返回
		if (dataArrayList.size() < 3) {
			return dataArrayList;
		}
		// 首先对数据进行排序，我这里用了最基本的冒泡法
		dataArrayList = bubbleSort(dataArrayList, length);
		// 求出数据平均值和标准差
		double average = calcAverage(dataArrayList);
		double standard = calcStandard(dataArrayList, length, average);
		// 求助最小值和最大值G1，Gn
		double dubMin = average - dataArrayList.get(0);
		double dubMax = dataArrayList.get(length - 1) - average;
		double G1 = dubMin / standard;
		double Gn = dubMax / standard;
		// 做比较，是否剔除
		if (G1 > calcG(ALPHA, length)) {
			dataArrayList.remove(0);
			if (Gn > calcG(ALPHA, length)) {
				dataArrayList.remove(length - 2);
			}
		} else if (Gn > calcG(ALPHA, length)) {
			dataArrayList.remove(length - 1);
		}
		return dataArrayList;
	}

	@Tips(description = "格拉布斯准则临界值表，这里alpha为0.05")
	private double calcG(double alpha, int n) {
		double[] N = { 1.1546847100299753, 1.4962499999999703, 1.763678479497787, 1.9728167175443088,
				2.1391059896012203, 2.2743651271139984, 2.386809875078279, 2.4820832497170997, 2.564121252001767,
				2.6357330437346365, 2.698971864039854, 2.755372404941574, 2.8061052912205966, 2.8520798130619083,
				2.894013795424427, 2.932482154393285, 2.9679513293748547, 3.0008041587489247, 3.031358153993366,
				3.0598791335206963, 3.086591582831163, 3.1116865231590722, 3.135327688211162, 3.157656337622164,
				3.178795077984819, 3.198850919445483, 3.2179177419513314, 3.2360783011390764, 3.2534058719727748,
				3.26996560491852, 3.2858156522011304, 3.301008108808857, 3.31558980320037, 3.329602965279218,
				3.3430857935316243, 3.356072938839107, 3.368595919061223, 3.3806834758032323, 3.3923618826659503,
				3.403655212591846, 3.41458557057518, 3.4251732969213213, 3.435437145364717, 3.4453944396432576,
				3.4550612115453876, 3.464452322969104, 3.4735815741386, 3.482461799798589, 3.491104954935569,
				3.4995221913492585, 3.507723926208097, 3.5157199035634887, 3.5235192496631433, 3.5311305227901078,
				3.5385617582575746, 3.5458205091071684, 3.5529138829882037, 3.5598485756350797 };
		return N[n - 3];
	}

	@Tips(description = "冒泡排序")
	public ArrayList<Double> bubbleSort(ArrayList<Double> arr, int n) {
		// TODO Auto-generated method stub
		double temp = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (arr.get(j) > arr.get(j + 1)) {
					temp = arr.get(j);
					arr.set(j, arr.get(j + 1));
					arr.set(j + 1, temp);
				}
			}
		}
		return arr;
	}

	@Tips(description = "求平均值")
	public double calcAverage(ArrayList<Double> sample) {
		// TODO Auto-generated method stub
		double sum = 0;
		int cnt = 0;
		for (int i = 0; i < sample.size(); i++) {
			sum += sample.get(i);
			cnt++;
		}

		return (double) sum / cnt;
	}

	@Tips(description = "求标准差")
	public double calcStandard(ArrayList<Double> array, int n, double average) {
		// TODO Auto-generated method stub
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += ((double) array.get(i) - average) * ((double) array.get(i) - average);
		}
		return (double) Math.sqrt((sum / (n - 1)));
	}
}
