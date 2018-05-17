package com.cmic.GoAppiumTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.LogUtil;

@Tips(description = "图片对比")
public class ImageUtil {

	/**
	 * 将图片截图进行比较
	 * 
	 * @param standard
	 *            标准图片路径
	 * @param actual
	 *            需要比较图片路径
	 * @param x
	 *            截图起始横坐标
	 * @param y
	 *            截图起始纵坐标
	 * @param width
	 *            截图宽度
	 * @param height
	 *            截图高度
	 * @return 图片相似度 100为完全相同
	 */
	public static double comparePartPic(String path1, String path2, int x, int y, int width, int height) {
		LogUtil.i("开始图片截图对比");
		LogUtil.i("图片1：" + path1);
		LogUtil.i("图片2：" + path2);
		// 标准图片
		BufferedImage image1 = getImageFromFile(new File(path1));
		BufferedImage image2 = getImageFromFile(new File(path2));
		// 截图比较
		LogUtil.i("截图：起始坐标[" + x + "," + y + "],宽" + width + ",高" + height);
		BufferedImage subimage1 = getSubImage(image1, x, y, width, height);
		BufferedImage subimage2 = getSubImage(image2, x, y, width, height);
		return compareImage(subimage1, subimage2);
	}

	/**
	 * 基本属性，如宽高像素要保持一致，否则无法比较
	 * @param myImage
	 * @param otherImage
	 * @return
	 */
	@Tips(description = "比较图片返回相似率")
	public static double compareImage(BufferedImage myImage, BufferedImage otherImage) {
		if (otherImage.getWidth() != myImage.getWidth()) {
			LogUtil.e("图片宽度不一致");
			return 0;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			LogUtil.e("图片高度不一致");
			return 0;
		}
		int width = myImage.getWidth();
		int height = myImage.getHeight();
		int numDiffPixels = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}
		double numberPixels = height * width;
		double samePixels = numberPixels - numDiffPixels;
		double samePercent = samePixels / numberPixels * 100;
		LogUtil.i("相似像素数量：" + samePixels + " 不相似像素数量：" + numDiffPixels + " 相似率：" + String.format("%.1f", samePercent)
				+ "%");
		return samePercent;
	}

	public static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	public static BufferedImage getImageFromFile(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// if failed, then copy it to local path for later check:TBD
			// FileUtils.copyFile(f, new File(p1));
			LogUtil.e("加载图片文件报错,路径为{}", e.getMessage());
		}
		return img;
	}

	@Deprecated
	@Tips(description = "获取图片像素")
	public static String[][] getPX(String args) {
		int[] rgb = new int[3];
		File file = new File(args);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		String[][] list = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
			}
		}
		return list;
	}

	@Deprecated
	@Tips(description = "图片比对")
	public static int compareImage(String imgPath1, String imgPath2) {
		String[] images = { imgPath1, imgPath2 };
		if (images.length == 0) {
			System.out.println("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int xiangsi = 0;
		int busi = 0;
		int i = 0, j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		list1 = getPX(images[1]);
		list2 = getPX(images[0]);
		i = 0;
		j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		String baifen = "";
		try {
			baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");
			baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
		} catch (Exception e) {
			baifen = "0";
		}
		if (baifen.length() <= 0) {
			baifen = "0";
		}
		if (busi == 0) {
			baifen = "100";
		}
		LogUtil.i("相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%");
		return Integer.parseInt(baifen);
	}

}
