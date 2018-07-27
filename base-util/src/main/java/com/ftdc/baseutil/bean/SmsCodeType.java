package com.ftdc.baseutil.bean;

/**
 * 短信验证码类型
 * 
 * @author mingsong.peng
 * @date 2018-04-09 14:11
 */
public enum SmsCodeType {
	// 1-注册，2-重置密码,3-支付API,4-更换手机号,5-登录
	TYPE_REG(1, "reg"), TYPE_RESET_PWD(2, "reset"), TYPE_PAY_API(3, "payApi"), TYPE_CHANGE_MOBILE(4,
			"changeMobile"), TYPE_LOGIN(5, "login");

	public static String prefix = "verifyCode";
	private int key;
	private String value;

	public String getStrkey() {
		return String.valueOf(key);
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private SmsCodeType(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

}
