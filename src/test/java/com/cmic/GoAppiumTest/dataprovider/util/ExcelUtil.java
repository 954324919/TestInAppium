package com.cmic.GoAppiumTest.dataprovider.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cmic.GoAppiumTest.helper.Tips;

public class ExcelUtil {

	private static final String filePath = "F:/WorkSpace4Mars/GoAppiumTest/src/test/java/apps/dataprovider/search_data.xls";

	/**
	 * 
	 * @param filepath
	 *            文件路径
	 * @param sheetName
	 *            目标Sheet名
	 * @param clazz
	 *            转换的实体名
	 * @return
	 * @throws Exception
	 */
	@Tips(riskPoint = "只适合包含首行标题栏的数据格式")
	public static <T> List<T> readExcel(String filepath, String sheetName, Class<T> clazz) throws Exception {
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(filepath);
			if (isExcel2003(filepath)) {
				wb = new HSSFWorkbook(is);
			} else if (isExcel2007(filepath)) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new Exception("读取的不是excel文件");
			}
			List<T> result = new ArrayList<T>();// 对应excel文件
			Sheet sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
			List<String> titles = new ArrayList<String>();// 放置所有的标题
			int rowSize = sheet.getLastRowNum() + 1;
			for (int j = 0; j < rowSize; j++) {// 遍历行
				Row row = sheet.getRow(j);
				if (row == null) {// 略过空行
					continue;
				}
				int cellSize = row.getLastCellNum();// 行中有多少个单元格，也就是有多少列
				if (j == 0) {// 第一行是标题行
					for (int k = 0; k < cellSize; k++) {
						Cell cell = row.getCell(k);
						titles.add(cell.toString());
					}
				} else {// 其他行是数据行
					T t = clazz.newInstance();// 对应一个数据行
					for (int k = 0; k < titles.size(); k++) {
						Cell cell = row.getCell(k);
						String key = titles.get(k);
						String value = null;
						if (cell != null) {
							value = cell.toString();
						}
						String setMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
						Method setMethod = clazz.getMethod(setMethodName, new Class[] { String.class });
						setMethod.invoke(t, value);
					}
					result.add(t);
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 用于Appium的xml数据驱动形式
	 * @param filepath
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	@Tips(riskPoint = "只适合包含首行标题栏的数据格式")
	public static Object[][] readExcel(String filepath, String sheetName) throws Exception {
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(filepath);
			if (isExcel2003(filepath)) {
				wb = new HSSFWorkbook(is);
			} else if (isExcel2007(filepath)) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new Exception("读取的不是excel文件");
			}
			Sheet sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
			List<String> titles = new ArrayList<String>();// 放置所有的标题
			int rowSize = sheet.getLastRowNum() + 1;
			int realSize = 0;
			for (int j = 0; j < rowSize; j++) {// 遍历行
				Row row = sheet.getRow(j);
				if (row == null) {// 略过空行
					continue;
				}
				realSize++;
			}
			Object[][] temp = new Object[realSize - 1][];
			for (int j = 0; j < rowSize; j++) {// 遍历行
				Row row = sheet.getRow(j);
				if (row == null) {// 略过空行
					continue;
				}
				int cellSize = row.getLastCellNum();// 行中有多少个单元格，也就是有多少列
				if (j == 0) {// 第一行是标题行
					for (int k = 0; k < cellSize; k++) {
						Cell cell = row.getCell(k);
						titles.add(cell.toString());
					}
				} else {// 其他行是数据行
					temp[j - 1] = new Object[cellSize];
					for (int k = 0; k < titles.size(); k++) {
						Cell cell = row.getCell(k);
						if (cell != null) {
							temp[j - 1][k] = cell.toString();
						}
					}
				}
			}
			return temp;
		} catch (Exception e) {
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	public static <T> void writeExcel(OutputStream os, String filePath, List<T> data) throws Exception {
		Workbook wb = null;
		try {
			if (isExcel2003(filePath)) {
				wb = new HSSFWorkbook();
			} else if (isExcel2007(filePath)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("当前文件不是excel文件");
			}
			if (data != null && data.size() > 0) {
				Class clazz = data.get(0).getClass();
				Field[] fd = clazz.getDeclaredFields();
				for (Field ld : fd) {

				}
				for (Field ld : fd) {
					String propertyName = ld.getName();
					propertyName = "get" + propertyName.substring(0, 1).toUpperCase()
							+ propertyName.substring(1, propertyName.length());
					Method method = clazz.getMethod(propertyName, new Class[] {});
					try {
						List<Object> ectlist = new ArrayList<>();
						ectlist.add(method.invoke(clazz, new Object[] {}));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			throw new Exception();
		}

	}

	private static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	private static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	/** 检查文件名是否为空或者是否是Excel格式的文件 */
	public static boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || !(isExcel2007(filePath)))) {
			return false;
		}
		/** 检查文件是否存在 */
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}
		return true;
	}
}
