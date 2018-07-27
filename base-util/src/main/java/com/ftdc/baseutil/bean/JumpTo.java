package com.ftdc.baseutil.bean;

/**
 * 极光推送消息通知跳转情况
 * 
 * @author Administrator
 *
 */
public enum JumpTo {
	DEFAULT(99, "联心钱包"), NO_JUMP(9, "通知"), // 无跳转
	JUMP_TO_ORDER(0, "交易成功"), // 账单
	JUMP_TO_CREDIT(1, "信用卡还款"), // 信用卡还款
	JUMP_TO_LOGIN(2, "账号封停"), // 账号封停
	JUMP_TO_ORDETAIL(3, "账单详情"), // 账单详情
	JUMP_TO_BALANCE(4, "余额明细"), // 余额明细
	JUMP_TO_VISUALCARD(5, "虚拟卡");// 记录

	private int value;
	private String title;

	public String getValue() {
		return String.valueOf(value);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private JumpTo(Integer value, String title) {
		this.value = value;
		this.title = title;
	}

}
