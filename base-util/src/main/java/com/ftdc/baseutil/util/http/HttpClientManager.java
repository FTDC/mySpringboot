package com.ftdc.baseutil.util.http;

import org.apache.http.*;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author Mark
 * @ClassName:HttpClientManager
 * @Description:httpClient客户端
 * @date 2014-12-5 上午11:24:21
 */
public class HttpClientManager {

	public static final Logger logger = LoggerFactory.getLogger(HttpClientManager.class);

	private HttpClient httpClient;

	private static ThreadLocal<HttpClient> clients = new ThreadLocal<>();

	private HttpClientManager() {
	}

	public static HttpClientManager getClient() {
		HttpClient client = clients.get();
		if (client == null) {
			logger.debug("create http connect begin");
			client = HttpConnectionManager.getSSLClient();
			logger.debug("create http connect end");
			clients.set(client);
		}
		HttpClientManager clientManager = new HttpClientManager();
		clientManager.httpClient = client;
		return clientManager;
	}

	/**
	 * 请求恢复策略
	 */
	public HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {

		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
			if (executionCount > 2) {// 已重发两次后不再发送
				return false;
			}

			if (exception instanceof HttpHostConnectException) {
				return true;
			}

			if (exception instanceof SSLHandshakeException) {
				return false;
			}

			HttpRequest httpRequest = (HttpRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = httpRequest instanceof HttpEntityEnclosingRequest;
			if (!idempotent) {
				return true;
			}
			return false;
		}
	};

	/**
	 * @param @param
	 *            url
	 * @param @return
	 * @return String
	 * @throws @Title:
	 *             httpPost
	 * @Description: http post请求，能自定义请求头
	 */
	public String httpPostWithoutParse(String url, Map<String, Object> headers, String body) {
		HttpPost post = new HttpPost(url);
		for (Map.Entry<String, Object> e : headers.entrySet()) {
			post.addHeader(e.getKey(), String.valueOf(e.getValue()));
		}
		try {
			post.setEntity(new ByteArrayEntity(body.getBytes("utf-8")));
		} catch (UnsupportedEncodingException x) {
			throw new RuntimeException(x);
		}
		HttpResponse response = null;
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*for (Header header : response.getAllHeaders()) {
			logger.debug("{}:{}", header.getName(), header.getValue());
		}*/

		int code = response.getStatusLine().getStatusCode();
//		logger.debug("服务器返回状态吗：{}", code);
		String result = "";
		if (code == HttpStatus.SC_OK) {// 如果请求成功
			try {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			logger.info("返回数据为:" + result);
		}
		if (null != post) {
			post.releaseConnection();
			shountdown();
		}
		return result;
	}

	/**
	 * @param @param
	 *            url
	 * @param @return
	 * @return String
	 * @throws @Title:
	 *             httpsGet
	 * @Description: 不带签名的get请求
	 */
	public String httpsGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*for (Header header : response.getAllHeaders()) {
			logger.debug("{}:{}", header.getName(), header.getValue());
		}*/

		int code = response.getStatusLine().getStatusCode();
//		logger.info("服务器返回的状态码为:{}", code);
		String result = "";
		if (code == HttpStatus.SC_OK) {// 如果请求成功
			try {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			logger.info("返回数据为: {}", result);
		}
		if (null != httpGet) {
			httpGet.releaseConnection();
			shountdown();
		}
		return result;
	}

	/**
	 * @param @param
	 *            url
	 * @param @return
	 * @return String
	 * @throws @Title:
	 *             httpPost
	 * @Description: http post请求，通过表单参数传值
	 */
	public String httpPost(String url, Map<String, String> formParam) {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> paramList = transformMap(formParam);
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "utf8");
			httpPost.setEntity(formEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse httpResponse = null;
		int code = 0;
		try {
			httpResponse = httpClient.execute(httpPost);
			code = httpResponse.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("请求失败");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("请求失败");
		}
//		logger.debug("服务器返回的状态码为: {}", code);
		String result = "";
		if (code == HttpStatus.SC_OK) {// 如果请求成功
			try {
				result = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			logger.debug("返回数据为: {}", result);
		}

		if (code == 307) {
			Header header = httpResponse.getFirstHeader("Location");
			result = httpPost(header.getValue(), formParam);
		}
		if (null != httpPost) {
			httpPost.releaseConnection();
			shountdown();
		}

		return result;
	}




	/**
	 * @param @param
	 *            params
	 * @param @return
	 * @return List<NameValuePair>
	 * @throws @Title:
	 *             transformMap
	 * @Description: 转换post请求参数
	 */
	private List<NameValuePair> transformMap(Map<String, String> params) {
		if (params == null || params.size() < 0) {// 如果参数为空则返回null;
			return new ArrayList<NameValuePair>();
		}
		logger.debug("当前请求的参数为:{}", params);
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : params.entrySet()) {
			paramList.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return paramList;
	}

	/**
	 * @param @param
	 *            url
	 * @param @param
	 *            s
	 * @param @return
	 * @return String
	 * @throws @Title:
	 *             httpPost
	 * @Description: http post请求,支持json参数
	 */
	public String httpPost(String url, String s) {
		HttpPost httpPost = new HttpPost(url);
		StringEntity param = null;
		logger.debug("请求的参数为: {}", s);
		try {
			param = new StringEntity(s, "utf-8");
			param.setContentEncoding("utf-8");
			httpPost.setEntity(param);

		} catch (Exception e) {
			e.printStackTrace();
		}
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Header header : response.getAllHeaders()) {
			logger.info(header.getName() + ":" + header.getValue());
		}

		int code = response.getStatusLine().getStatusCode();
//		logger.debug("服务器返回的状态码为:", code);
		String result = "";
		if (code == HttpStatus.SC_OK) {// 如果请求成功
			try {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			logger.debug("返回数据为:{}", result);
		}
		if (null != httpPost) {
			httpPost.releaseConnection();
			shountdown();
		}
		return result;
	}

	/**
	 * @param
	 * @return void
	 * @throws @Title:
	 *             shountdown
	 * @Description: 释放连接
	 */
	public void shountdown() {
		// HttpConnectionManager.httpShountdown();
		// httpClient.getConnectionManager().shutdown();
		httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public static String postJson(String url, String s, BiConsumer<Integer, String> consumer) {
        HttpPost httpPost = new HttpPost(url);
        logger.debug("请求的参数为: {}", s);
        StringEntity param = new StringEntity(s, "utf-8");
        param.setContentEncoding("utf-8");
        httpPost.setEntity(param);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        String result = null;
        try {
            HttpResponse response = createHttpclient().execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            logger.debug("服务器返回的状态码为:", code);
            if (code == HttpStatus.SC_OK) {// 如果请求成功
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.debug("返回数据为:{}", result);
            }
            if (consumer != null)
                consumer.accept(code, result);
        } catch (Exception e) {
            logger.info("发送通知报文异常", e);
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }
	
	   // 创建 httpClient
    private static HttpClient createHttpclient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(500); // 最大连接数
        cm.setDefaultMaxPerRoute(200);
//	    cm.setValidateAfterInactivity(2000);//设置空闲连接回收时间(单位ms)，默认2000  连接是放linklist,使用的时候检查是否过期,拿到可用的连接会从头部移到尾部,所以回收时间会影响到服务器存在CLOSE_WAIT的个数
//	    CookieStore cookieStore = new BasicCookieStore();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)    //请求超时时间
                .setSocketTimeout(10 * 1000)    //等待数据超时时间
                .setConnectionRequestTimeout(3 * 1000)  //获取连接超时时间
                .build();
        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
//	            .setDefaultCookieStore(cookieStore)  //设置cookie保持会话
                .build();
    }

}
