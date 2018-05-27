package com.cmic.GoAppiumTest.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import com.cmic.GoAppiumTest.bean.BarChartData;
import com.cmic.GoAppiumTest.bean.LineCharEntity;

public class JFreeCharUtil {
	private static String NO_DATA_MSG = "数据加载失败";
	private static Font FONT = new Font("宋体", Font.BOLD, 14);
	public static Color[] CHART_COLORS = { new Color(31, 129, 188), new Color(92, 92, 97), new Color(144, 237, 125),
			new Color(255, 188, 117), new Color(153, 158, 255), new Color(255, 117, 153), new Color(253, 236, 109),
			new Color(128, 133, 232), new Color(158, 90, 102), new Color(255, 204, 102) };// 颜色

	static {
		setChartTheme();
	}

	public JFreeCharUtil() {
	}

	/**
	 * 中文主题样式 解决乱码
	 */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		chartTheme.setExtraLargeFont(FONT);
		// 设置图例的字体
		chartTheme.setRegularFont(FONT);
		// 设置轴向的字体
		chartTheme.setLargeFont(FONT);
		chartTheme.setSmallFont(FONT);
		chartTheme.setTitlePaint(new Color(51, 51, 51));
		chartTheme.setSubtitlePaint(new Color(85, 85, 85));

		chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		chartTheme.setLegendItemPaint(Color.BLACK);//
		chartTheme.setChartBackgroundPaint(Color.WHITE);

		chartTheme.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 20D));
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence

		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS,
				OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		chartTheme.setDrawingSupplier(drawingSupplier);

		chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		chartTheme.setBaselinePaint(Color.WHITE);
		chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

		chartTheme.setItemLabelPaint(Color.black);
		chartTheme.setThermometerPaint(Color.white);// 温度计

		ChartFactory.setChartTheme(chartTheme);
	}

	/**
	 * 必须设置文本抗锯齿
	 */
	public static void setAntiAlias(JFreeChart chart) {
		chart.setTextAntiAlias(false);

	}

	/**
	 * 设置图例无边框，默认黑色边框
	 */
	public static void setLegendEmptyBorder(JFreeChart chart) {
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

	}

	/**
	 * 创建类别数据集合
	 */
	public static DefaultCategoryDataset createDefaultCategoryDataset(List<FreeCharDataEntity> dataSource,
			String[] categories) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (FreeCharDataEntity entity : dataSource) {
			String name = entity.getName();
			List<Object> list = entity.getData();
			if (list != null && categories != null && list.size() == categories.length) {
				for (int index = 0; index < list.size(); index++) {
					String value = list.get(index) == null ? "" : list.get(index).toString();
					if (isPercent(value)) {
						value = value.substring(0, value.length() - 1);
					}
					if (isNumber(value)) {
						dataset.setValue(Double.parseDouble(value), name, categories[index]);
					}
				}
			}
		}
		return dataset;
	}

	/**
	 * 创建类别数据集合[单类型category为""||Mul类型category不为""]
	 */
	public static DefaultCategoryDataset createDefaultDatasetInLineChatData(List<LineCharEntity> dataSource) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (LineCharEntity data : dataSource) {
			dataset.setValue(data.yAxis, data.category, data.xAxis);
		}
		return dataset;
	}

	/**
	 * 创建类别数据集合
	 */
	public static DefaultCategoryDataset createDefaultDatasetInChatData(List<BarChartData> dataSource) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (BarChartData data : dataSource) {
			dataset.setValue(data.dataValue, data.dataCategory, data.dataCategorySet);
		}
		return dataset;
	}

	/**
	 * 创建类别数据集合
	 */
	public static DefaultCategoryDataset createDefaultCategoryDataset(Vector<FreeCharDataEntity> series,
			String[] categories) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (FreeCharDataEntity serie : series) {
			String name = serie.getName();
			List<Object> data = serie.getData();
			if (data != null && categories != null && data.size() == categories.length) {
				for (int index = 0; index < data.size(); index++) {
					String value = data.get(index) == null ? "" : data.get(index).toString();
					if (isPercent(value)) {
						value = value.substring(0, value.length() - 1);
					}
					if (isNumber(value)) {
						dataset.setValue(Double.parseDouble(value), name, categories[index]);
					}
				}
			}

		}
		return dataset;
	}

	/**
	 * 创建饼图数据集合
	 */
	public static DefaultPieDataset createDefaultPieDataset(String[] categories, Object[] datas) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < categories.length && categories != null; i++) {
			String value = datas[i].toString();
			if (isPercent(value)) {
				value = value.substring(0, value.length() - 1);
			}
			if (isNumber(value)) {
				dataset.setValue(categories[i], Double.valueOf(value));
			}
		}
		return dataset;

	}

	/**
	 * 创建时间序列数据
	 */
	public static TimeSeries createTimeseries(String category, Vector<Object[]> dateValues) {
		TimeSeries timeseries = new TimeSeries(category);

		if (dateValues != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (Object[] objects : dateValues) {
				Date date = null;
				try {
					date = dateFormat.parse(objects[0].toString());
				} catch (Exception e) {
				}
				String sValue = objects[1].toString();
				double dValue = 0;
				if (date != null && isNumber(sValue)) {
					dValue = Double.parseDouble(sValue);
					timeseries.add(new Day(date), dValue);
				}
			}
		}

		return timeseries;
	}

	/**
	 * 设置 折线图样式
	 *
	 * @param plot
	 * @param isShowDataLabels
	 *            是否显示数据标签 默认不显示节点形状
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels) {
		setLineRender(plot, isShowDataLabels, false);
	}

	/**
	 * 设置折线图样式
	 *
	 * @param plot
	 * @param isShowDataLabels
	 *            是否显示数据标签
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels, boolean isShapesVisible) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

		renderer.setStroke(new BasicStroke(1.5F));
		if (isShowDataLabels) {
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
					StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance()));
			renderer.setBasePositiveItemLabelPosition(
					new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
		}
		renderer.setBaseShapesVisible(isShapesVisible);// 数据点绘制形状
		setXAixs(plot);
		setYAixs(plot);

	}

	/**
	 * 设置时间序列图样式
	 *
	 * @param plot
	 * @param isShowData
	 *            是否显示数据
	 * @param isShapesVisible
	 *            是否显示数据节点形状
	 */
	public static void setTimeSeriesRender(Plot plot, boolean isShowData, boolean isShapesVisible) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);
		xyplot.setInsets(new RectangleInsets(10, 10, 5, 10));

		XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();

		xyRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyRenderer.setBaseShapesVisible(false);
		if (isShowData) {
			xyRenderer.setBaseItemLabelsVisible(true);
			xyRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
			xyRenderer.setBasePositiveItemLabelPosition(
					new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
		}
		xyRenderer.setBaseShapesVisible(isShapesVisible);// 数据点绘制形状

		DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
		domainAxis.setAutoTickUnitSelection(false);
		DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy-MM")); // 第二个参数是时间轴间距
		domainAxis.setTickUnit(dateTickUnit);

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setBaseToolTipGenerator(xyTooltipGenerator);

		setXY_XAixs(xyplot);
		setXY_YAixs(xyplot);

	}

	/**
	 * 设置时间序列图样式 -默认不显示数据节点形状
	 *
	 * @param plot
	 * @param isShowData
	 *            是否显示数据
	 */

	public static void setTimeSeriesRender(Plot plot, boolean isShowData) {
		setTimeSeriesRender(plot, isShowData, false);
	}

	/**
	 * 设置时间序列图渲染：但是存在一个问题：如果timeseries里面的日期是按照天组织， 那么柱子的宽度会非常小，和直线一样粗细
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */

	public static void setTimeSeriesBarRender(Plot plot, boolean isShowDataLabels) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);

		XYBarRenderer xyRenderer = new XYBarRenderer(0.1D);
		xyRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());

		if (isShowDataLabels) {
			xyRenderer.setBaseItemLabelsVisible(true);
			xyRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		}

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setBaseToolTipGenerator(xyTooltipGenerator);
		setXY_XAixs(xyplot);
		setXY_YAixs(xyplot);

	}

	/**
	 * 设置柱状图渲染
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */
	public static void setBarRenderer(CategoryPlot plot, boolean isShowDataLabels) {

		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setMaximumBarWidth(0.075);// 设置柱子最大宽度

		if (isShowDataLabels) {
			renderer.setBaseItemLabelsVisible(true);
		}

		setXAixs(plot);
		setYAixs(plot);
	}

	/**
	 * 设置堆积柱状图渲染
	 *
	 * @param plot
	 */

	public static void setStackBarRender(CategoryPlot plot) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plot.setRenderer(renderer);
		setXAixs(plot);
		setYAixs(plot);
	}

	/**
	 * 设置类别图表(CategoryPlot) X坐标轴线条颜色和样式
	 *
	 * @param plot
	 */
	public static void setXAixs(CategoryPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 *
	 * @param plot
	 */
	public static void setYAixs(CategoryPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// Y坐标轴颜色
		axis.setTickMarkPaint(lineColor);// Y坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));

		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距

	}

	/**
	 * 设置XY图表(XYPlot) X坐标轴线条颜色和样式
	 *
	 * @param plot
	 */
	public static void setXY_XAixs(XYPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置XY图表(XYPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 *
	 * @param plot
	 */
	public static void setXY_YAixs(XYPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// X坐标轴颜色
		axis.setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlinesVisible(false);

		plot.getRangeAxis().setUpperMargin(0.12);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.12);// 设置底部Y坐标轴间距

	}

	/**
	 * 设置饼状图渲染
	 */
	public static void setPieRender(Plot plot) {

		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		PiePlot piePlot = (PiePlot) plot;
		piePlot.setInsets(new RectangleInsets(0, 0, 0, 0));
		piePlot.setCircular(true);// 圆形

		// piePlot.setSimpleLabels(true);// 简单标签
		piePlot.setLabelGap(0.01);
		piePlot.setInteriorGap(0.05D);
		piePlot.setLegendItemShape(new Rectangle(10, 10));// 图例形状
		piePlot.setIgnoreNullValues(true);
		piePlot.setLabelBackgroundPaint(null);// 去掉背景色
		piePlot.setLabelShadowPaint(null);// 去掉阴影
		piePlot.setLabelOutlinePaint(null);// 去掉边框
		piePlot.setShadowPaint(null);
		// 0:category 1:value:2 :percentage
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));// 显示标签数据
	}

	/**
	 * 是不是一个%形式的百分比
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPercent(String str) {
		return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
	}

	/**
	 * 是不是一个数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
	}

	/**
	 * 构建线性图表
	 * 
	 * @author cmic
	 *
	 */
	public static class LineChartBuilder {
		private String xAxisName = "X轴";// x轴名
		private String yAxisName = "Y轴";// y轴名
		private String title = "标题名称";// 标题名
		// private int width;//宽度
		// private int height;//高度
		private String imgSavePath;
		private DefaultCategoryDataset dataSource;

		public LineChartBuilder setXAxisName(String xAxisName) {
			this.xAxisName = xAxisName;
			return this;
		}

		public LineChartBuilder setYAxisName(String yAxisName) {
			this.yAxisName = yAxisName;
			return this;
		}

		public LineChartBuilder setTitle(String title) {
			this.title = title;
			return this;
		}

		public LineChartBuilder setImagePath(String imgSavePath) {
			this.imgSavePath = imgSavePath;
			return this;
		}

		public LineChartBuilder setDataSource(DefaultCategoryDataset dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public void outputImage() {
			if (imgSavePath == null) {
				throw new RuntimeException("图片生成地址没有指定");
			} else if (dataSource == null) {
				throw new RuntimeException("数据源没有指定");
			}
			// 2：创建Chart[创建不同图形]
			JFreeChart chart = ChartFactory.createLineChart(title, xAxisName, yAxisName, dataSource);
			// 3:设置抗锯齿，防止字体显示不清楚
			setAntiAlias(chart);// 抗锯齿
			// 4:对柱子进行渲染[[采用不同渲染]]
			setLineRender(chart.getCategoryPlot(), false, true);//
			// 5:对其他部分进行渲染
			setXAixs(chart.getCategoryPlot());// X坐标轴渲染
			setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
			// 设置标注无边框
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setRangeGridlinePaint(Color.BLUE);// 纵坐标格线颜色
			plot.setDomainGridlinePaint(Color.BLACK);// 横坐标格线颜色
			plot.setDomainGridlinesVisible(true);// 显示横坐标格线
			plot.setRangeGridlinesVisible(true);// 显示纵坐标格线

			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			DecimalFormat decimalformat1 = new DecimalFormat("##.##");// 数据点显示数据值的格式
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
			// 上面这句是设置数据项标签的生成器
			renderer.setItemLabelsVisible(true);// 设置项标签显示
			renderer.setBaseItemLabelsVisible(true);// 基本项标签显示
			// 上面这几句就决定了数据点按照设定的格式显示数据值
			plot.setRenderer(renderer); // 给柱图添加呈现器

			// 设置标注无边框
			chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
			chart.getTitle().setMargin(10, 0, 0, 0);
			try {
				ChartUtilities.saveChartAsJPEG(new File(imgSavePath), chart, 840, 540);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 构建柱形图表
	 * 
	 * @author cmic
	 *
	 */
	public static class BarChartBuilder {
		private String xAxisName = "X轴";// x轴名
		private String yAxisName = "Y轴";// y轴名
		private String title = "标题名称";// 标题名
		// private int width;//宽度
		// private int height;//高度
		private String imgSavePath;
		private DefaultCategoryDataset dataSource;

		public BarChartBuilder setXAxisName(String xAxisName) {
			this.xAxisName = xAxisName;
			return this;
		}

		public BarChartBuilder setYAxisName(String yAxisName) {
			this.yAxisName = yAxisName;
			return this;
		}

		public BarChartBuilder setTitle(String title) {
			this.title = title;
			return this;
		}

		public BarChartBuilder setImagePath(String imgSavePath) {
			this.imgSavePath = imgSavePath;
			return this;
		}

		public BarChartBuilder setDataSource(DefaultCategoryDataset dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public BarChartBuilder setDataSource(List<BarChartData> chartDatas) {
			this.dataSource = createDefaultDatasetInChatData(chartDatas);
			return this;
		}

		public void outputImage() {
			if (imgSavePath == null) {
				throw new RuntimeException("图片生成地址没有指定");
			} else if (dataSource == null) {
				throw new RuntimeException("数据源没有指定");
			}
			JFreeChart chart = ChartFactory.createBarChart(title, xAxisName, yAxisName, dataSource);
			// 3:设置抗锯齿，防止字体显示不清楚
			setAntiAlias(chart);// 抗锯齿
			// 4:对柱子进行渲染
			setBarRenderer(chart.getCategoryPlot(), false);//
			// 5:对其他部分进行渲染
			setXAixs(chart.getCategoryPlot());// X坐标轴渲染
			setYAixs(chart.getCategoryPlot());// Y坐标轴渲染

			CategoryPlot plot = chart.getCategoryPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setRangeGridlinePaint(Color.BLUE);// 纵坐标格线颜色
			plot.setDomainGridlinePaint(Color.BLACK);// 横坐标格线颜色
			plot.setDomainGridlinesVisible(true);// 显示横坐标格线
			plot.setRangeGridlinesVisible(true);// 显示纵坐标格线

			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			DecimalFormat decimalformat1 = new DecimalFormat("##.##");// 数据点显示数据值的格式
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
			// 上面这句是设置数据项标签的生成器
			renderer.setItemLabelsVisible(true);// 设置项标签显示
			renderer.setBaseItemLabelsVisible(true);// 基本项标签显示
			// 上面这几句就决定了数据点按照设定的格式显示数据值
			plot.setRenderer(renderer); // 给柱图添加呈现器

			// 设置标注无边框
			chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
			chart.getTitle().setMargin(10, 0, 0, 0);
			try {
				ChartUtilities.saveChartAsJPEG(new File(imgSavePath), chart, 840, 540);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class FreeCharDataEntity {
		private String name;
		private List<Object> data;

		public FreeCharDataEntity(String name, List<Object> data) {
			this.name = name;
			this.data = data;
		}

		public FreeCharDataEntity(String name, Double[] data) {
			this.name = name;
			List<Object> tempList = new ArrayList<Object>();
			tempList.addAll(Arrays.asList(data));
			this.data = tempList;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Object> getData() {
			return data;
		}

		public void setData(List<Object> data) {
			this.data = data;
		}
	}
}
