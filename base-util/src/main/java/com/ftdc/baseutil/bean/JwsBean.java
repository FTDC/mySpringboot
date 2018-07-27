package com.ftdc.baseutil.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class JwsBean {

	protected Map<String, String> pm;
	
	//功能控制
	//# 虚拟卡
	protected static final String VIR_LIANX = "virtual_card_lianxin";
	protected static final String VIR_BANK = "virtual_card_bank";
	//# 收款码
	protected static final String REC_QRCODE_WEIX = "is_support_weixin_receipt";
	protected static final String REC_QRCODE_ALI = "is_support_alipay_receipt";
	protected static final String REC_QRCODE_LIANX = "is_support_lianxin_receipt";
	//# 扫码支付
	protected static final String SCAN_LIANX = "scan_code_lianxin";
	protected static final String SCAN_BANK = "scan_code_bank";
	//# 充值
	protected static final String RECHARGE_BANK = "is_support_bank";
	protected static final String RECHARGE_ALI = "recharge_support_ali";
	protected static final String RECHARGE_WX = "recharge_support_wx";
	//# 付款码
	protected static final String PAY_QRCODE_LIANX = "is_support_lianxin_pay";
	protected static final String PAY_QRCODE_BANK = "is_support_bank_pay";
	//# 转帐
	protected static final String TRANSFER_TO_BANK = "transfer_to_bank";
	protected static final String TRANSFER_TO_WALLET = "transfer_to_wallet";
	
	protected static final String TRANSFER_FOR_WALLET = "transfer_for_wallet";
	protected static final String TRANSFER_FOR_BANK = "transfer_for_bank";
	

	
	public void initParams(Map<String, String> paramsMap,HttpServletRequest req) {
		pm = new HashMap<String, String>();
		if (null!=paramsMap) {
			Iterator<Map.Entry<String, String>> entries = paramsMap.entrySet().iterator(); 
			while (entries.hasNext()) { 
			  Map.Entry<String, String> entry = entries.next(); 
//			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			  pm.put(entry.getKey(), String.valueOf(entry.getValue()));
			}

		}
		String curOperId = (String) req.getAttribute("operId");
		String curMuId = (String) req.getAttribute("muId");
		if (curOperId != null) {
			pm.put("curOperId", curOperId);
		}
		if (curMuId != null) {
			pm.put("curMuId", curMuId);
		}
	}

	public abstract void validate(Result result);

	public abstract void execute(HttpServletRequest req, HttpServletResponse resp, Result result);

}
