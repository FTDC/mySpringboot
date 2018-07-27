package com.ftdc.baseutil.bean;

public class Result {

	@Override
	public String toString() {
		return "{"+"code:" +code+  ", "+"data:" + data + ", "+"msg:" + msg + "}";
	}

	private String code;
	private Object data;
	private String msg;

	public Result(String code, Object data,String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public void setValue(String code, Object data,String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
