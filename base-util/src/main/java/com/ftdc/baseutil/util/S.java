package com.ftdc.baseutil.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 * 
 * @author peibenzhen
 * 
 */
public class S {

	public static final Logger logger = LoggerFactory.getLogger(S.class);

	/**
	 * 将null转成空字符串
	 * 
	 * @param str
	 *            原字符串
	 * @return ""或原字符串
	 */
	public static String nullToBlank(String str) {
		if (str == null) {
			return "";
		}

		return str;
	}

	/**
	 * 判断字符串是否为空，包括null、空字符串和空格
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals("") || str.trim().equals("null")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符串是否为数字组合，包括0开头
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}

		if (!str.matches("[0-9]*")) {
			return false;
		}

		return true;
	}

	/**
	 * 判断字符串是否为整数
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isInt(String str) {
		if (str == null) {
			return false;
		}

		if (!str.matches("([-]?[1-9][0-9]*)|0")) {
			return false;
		}

		BigInteger bi = new BigInteger(str);
		BigInteger minValue = new BigInteger(String.valueOf(Integer.MIN_VALUE));
		BigInteger maxValue = new BigInteger(String.valueOf(Integer.MAX_VALUE));
		if (bi.compareTo(minValue) < 0 || bi.compareTo(maxValue) > 0) {
			return false;
		}

		return true;
	}

	/**
	 * 判断字符串是否为长整数
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isLong(String str) {
		if (str == null) {
			return false;
		}
		if (!str.matches("([-]?[1-9][0-9]*)|0")) {
			return false;
		}

		BigInteger bi = new BigInteger(str);
		BigInteger minValue = new BigInteger(String.valueOf(Long.MIN_VALUE));
		BigInteger maxValue = new BigInteger(String.valueOf(Long.MAX_VALUE));
		if (bi.compareTo(minValue) < 0 || bi.compareTo(maxValue) > 0) {
			return false;
		}

		return true;
	}

	/**
	 * 判断字符串是否为小数，且小数最多为几个，当maxFraction为-1时忽略小数位数的判断
	 * 
	 * @param str
	 *            字符串
	 * @param maxFraction
	 *            小数允许的最大个数
	 * @return 是或否
	 */
	public static boolean isDecimal(String str, int maxFraction) {
		if (str == null) {
			return false;
		}

		if (!str.matches("0|([+-]?[1-9][0-9]*)|([+-]?[1-9][0-9]*\\.[0-9]+)|([+-]?0\\.[0-9]+)")) {
			return false;
		}

		if (maxFraction != -1) {
			if (!str.matches("([+-]?[1-9][0-9]*)|([+-]?[1-9][0-9]*\\.[0-9]{1," + maxFraction + "})|([+-]?0\\.[0-9]{1,"
					+ maxFraction + "})")) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 返回字符串的字符数，无论为汉字、数字或字母，均为1个长度
	 * 
	 * @param str
	 *            字符串
	 * @return 字符数
	 */
	public static int len(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		return str.length();
	}

	/**
	 * lentrim:去除掉左右空格之后的实际长度.<br/>
	 * 
	 * @author huangchao
	 * @date 2018年1月5日 下午9:15:32 *
	 * @param str
	 * @return
	 * @since JDK 1.8
	 **/
	public static int lentrim(String str) {
		if (str == null || str.trim().equals("")) {
			return 0;
		}
		return str.trim().length();
	}

	/**
	 * 返回字符串的字节数，汉字为2个长度，字母或数字为1个长度
	 * 
	 * @param str
	 *            字符串
	 * @return 字节数
	 */
	public static int byteLen(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		return str.getBytes().length;
	}

	/**
	 * 判断字符串是否为日期型
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isDate(String str) {
		if (str == null) {
			return false;
		}

		Pattern pattern = Pattern.compile("(\\d{4})\\-(\\d{1,2})\\-(\\d{1,2})");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}

		int year = Integer.parseInt(matcher.group(1));
		int month = Integer.parseInt(matcher.group(2));
		int day = Integer.parseInt(matcher.group(3));
		boolean isLeap = false;

		if (year % 4 == 0 && year % 100 != 0) {
			isLeap = true;
		} else {
			if (year % 400 == 0) {
				isLeap = true;
			}
		}

		if (month < 1 || month > 12) {
			return false;
		} else {
			if (day < 1 || day > 31) {
				return false;
			} else {
				if (month == 4 || month == 6 || month == 9 || month == 11) {
					if (day == 31) {
						return false;
					}
				} else {
					if (month == 2) {
						if (isLeap) {
							if (day > 29) {
								return false;
							}
						} else {
							if (day > 28) {
								return false;
							}
						}
					}
				}
			}
		}

		return true;

	}

	/**
	 * 判断字符串是否为日期时间型
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isDatetime(String str) {
		if (str == null) {
			return false;
		}

		Pattern pattern = Pattern.compile("(\\d{4})\\-(\\d{1,2})\\-(\\d{1,2})\\s(\\d{1,2}):(\\d{1,2}):(\\d{1,2})");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}

		int year = Integer.parseInt(matcher.group(1));
		int month = Integer.parseInt(matcher.group(2));
		int day = Integer.parseInt(matcher.group(3));
		int hour = Integer.parseInt(matcher.group(4));
		int minute = Integer.parseInt(matcher.group(5));
		int second = Integer.parseInt(matcher.group(6));
		boolean isLeap = false;

		if (year % 4 == 0 && year % 100 != 0) {
			isLeap = true;
		} else {
			if (year % 400 == 0) {
				isLeap = true;
			}
		}

		if (month < 1 || month > 12) {
			return false;
		} else {
			if (day < 1 || day > 31) {
				return false;
			} else {
				if (month == 4 || month == 6 || month == 9 || month == 11) {
					if (day == 31) {
						return false;
					}
				} else {
					if (month == 2) {
						if (isLeap) {
							if (day > 29) {
								return false;
							}
						} else {
							if (day > 28) {
								return false;
							}
						}
					}
				}
			}
		}

		if (hour > 23 || minute > 59 || second > 59) {
			return false;
		}

		return true;

	}

	/**
	 * 是否为登录名
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isLoginName(String str) {
		if (str == null) {
			return false;
		}

		Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}

		return true;
	}

	/**
	 * 验证字符串是否为手机号
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isMob(String str) {
		if (str == null) {
			return false;
		}
		// ^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[6-8])|(18[0-9]))\\d{8}$
		// 1[3-8]\\d{9}
		return str
				.matches("^((13[0-9])|(14[5|6|7|8])|(15([0-3]|[5-9]))|(16([6]))|(17[6-8])|(18[0-9])|(19[8|9]))\\d{8}$");
	}

	/**
	 * 验证字符串是否为邮箱
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isEmail(String str) {
		if (str == null) {
			return false;
		}

		return str.matches(".+@[a-zA-Z0-9_\\-\\.]+");
	}

	/**
	 * 验证字符串是否为网址
	 * 
	 * @param str
	 *            字符串
	 * @return 是或否
	 */
	public static boolean isWebsite(String str) {
		if (str == null) {
			return false;
		}

		return str.matches("http[s]?://.+");
	}

	/**
	 * 获取uuid
	 * 
	 * @return uuid
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 产生随机的六位数
	 * 
	 * @return
	 */
	public static String getSix() {
		Random rad = new Random();

		String result = rad.nextInt(1000000) + "";

		if (result.length() != 6) {
			return getSix();
		}
		return result;
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String createBillNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String prefix = sdf.format(new Date());
		long randomNum = Math.round(Math.random() * 1000);
		String billNo = prefix + randomNum;
		logger.info("billNo:" + billNo);
		return billNo;
	}

	/**
	 * 计算两个时间时间戳多少分钟
	 * 
	 * @return
	 */
	public static long interval(long hqtime) {
		Long s = (System.currentTimeMillis() - hqtime) / (1000 * 60);
		return s;
	}

	public static boolean validatePwd(String pwd) {
		if (pwd == null) {
			return false;
		}
		// 必填字母数字
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		return pwd.matches(reg);

	}

	/**
	 * 获取请求真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 去掉小鼠末尾多余0
	 * 
	 * @param str
	 * @return
	 */
	public static String removeZero(String str) {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(str);
	}

	public static void main(String[] args) {
		System.out.println(isMob("19900000000"));
	}

	/**
	 * 生成随机密码(数字，字母区分大小写6位)
	 * 
	 * @return
	 */
	public static String getRandomPwd() {
		String randomPwd = "";
		int length = 6; // 密码长度
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				randomPwd += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				randomPwd += String.valueOf(random.nextInt(10));
			}
		}
		return randomPwd;
	}

	/**
	 * 从数组中随机选择一个
	 * 
	 * @param temp
	 * @return
	 */
	public static String selRandom(String[] temp) {
		int index = (int) (Math.random() * temp.length);// 随机数乘以数组长度，那么它的取值就在0-length之间
		return temp[index];
	}

	/**
	 * 从大到小匹配数相加
	 * 
	 * @param keys
	 *            数组
	 * @param dou
	 *            金额
	 * @return
	 */
	public static List<Sum> preSum(Double[] keys, Double dou)

	{
		List<Sum> re = new ArrayList<Sum>();
		Arrays.sort(keys, new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				Double a = (Double) arg0;
				Double b = (Double) arg1;
				int temp = Double.compare(a, b);
				return -temp;
			}
		});
		System.err.println(JSONObject.toJSONString(keys));
		for (Double temp : keys) {
			Integer i = Integer.valueOf((int) (dou / temp));
			if (i >= 1) {
				dou = dou - temp * i;
				do {
					Sum sum = new Sum();
					sum.setA(temp);
					sum.setB(temp);
					re.add(sum);
					i = i - 1;
				} while (i > 0);
			}
		}
		if (dou > 0) {
			Sum sum = new Sum();
			sum.setA(keys[keys.length - 1]);
			sum.setB(dou);
			re.add(sum);
		}
		return re;
	}

}
