package com.ftdc.baseutil.util;

import java.util.Calendar;

/**
 * 日期处理工具
 * 
 * @author peibenzhen
 *
 */
public class Dttm {

	/**
	 * 将时间戳转成yyyyMMdd格式
	 * 
	 * @param t
	 *            时间戳
	 * @return 字符串
	 */
	public static String toYYYYMMDD(long t) {

		StringBuffer buf = new StringBuffer();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);
		int yyyy = c.get(Calendar.YEAR);
		int mm = c.get(Calendar.MONTH) + 1;
		int dd = c.get(Calendar.DAY_OF_MONTH);

		buf.append(yyyy);
		if (mm < 10) {
			buf.append("0");
		}
		buf.append(mm);
		if (dd < 10) {
			buf.append("0");
		}
		buf.append(dd);

		return buf.toString();
	}

	/**
	 * 将时间戳转成yyMMdd格式
	 * 
	 * @param t
	 *            时间戳
	 * @return 字符串
	 */
	public static String toYYMMDD(long t) {

		StringBuffer buf = new StringBuffer();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);
		int yyyy = c.get(Calendar.YEAR);
		int mm = c.get(Calendar.MONTH) + 1;
		int dd = c.get(Calendar.DAY_OF_MONTH);

		buf.append(String.valueOf(yyyy).substring(2, 4));
		if (mm < 10) {
			buf.append("0");
		}
		buf.append(mm);
		if (dd < 10) {
			buf.append("0");
		}
		buf.append(dd);

		return buf.toString();
	}

	/**
	 * 获取年月日
	 * @return
	 */
	public static String ymd() {
		Calendar rightNow = Calendar.getInstance();
		Integer year = rightNow.get(Calendar.YEAR);
		Integer month = rightNow.get(Calendar.MONTH) + 1; // 第一个月从0开始，所以得到月份＋1
		Integer day = rightNow.get(rightNow.DAY_OF_MONTH);
		String ymd = year + "/" + month + "/" + day;
		return ymd;
	}

}
