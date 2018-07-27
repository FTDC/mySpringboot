package com.ftdc.baseutil.util;

/**
 * 字符型ID类
 * 
 * @author peibenzhen
 *
 */
public class ID {

	/**
	 * 根据类型获取id
	 * 
	 * @param type
	 *            类型
	 * @param number
	 *            数值
	 * @return id
	 */
	public static String getId(int type, int number) {
		int len = String.valueOf(number).length();
		if (type < 1 || type > 5 || len > 5) {
			return null;
		}

		String[] arr = new String[] { "", "Q", "C", "P", "PG", "M" };
		String yymmdd = Dttm.toYYMMDD(System.currentTimeMillis());
		StringBuffer buf = new StringBuffer(arr[type] + yymmdd);
		for (int i = 0; i < 5 - len; i++) {
			buf.append("0");
		}
		buf.append(String.valueOf(number));

		return buf.toString();
	}

}
