package com.ftdc.baseutil.util.sms;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信
 * 
 * @author mingsong.peng
 * @date 2018-04-17 17:05
 */
public class PushSms {

	/**
	 * 智能匹配模板接口发短信
	 * 
	 * @param apikey
	 *            apikey
	 * @param text
	 *            短信内容
	 * @param mobile
	 *            接受的手机号
	 * @return JSON格式字符串
	 * @throws IOException
	 */
	public static String sendSms(String apikey, String text, String mobile, String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return post(url, params);
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url
	 *            提交的URL
	 * @param paramsMap
	 *            提交<参数，值>Map
	 * @return 提交响应
	 */
	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

}
