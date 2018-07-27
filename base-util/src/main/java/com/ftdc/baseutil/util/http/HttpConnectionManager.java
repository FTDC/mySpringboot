package com.ftdc.baseutil.util.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Mark
 * @ClassName:HttpConnectionManager
 * @Description:httpclient连接管理类
 * @date 2014-12-5 上午11:24:11
 */
public class HttpConnectionManager {

    public static HttpParams httpParams;

    public static final int HTTP_CONNECTIONTIMEOUT = 10 * 1000;  //连接超时时间

    public static final int HTTP_SOTIMEOUT = 10 * 1000; //读取超时时间

    public static final int SOCKET_BUFFERSIZE = 1024 * 4;

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";

    public static final String HTTP = "http";

    public static final String HTTPS = "https";

    public static final int MAXTOTAL = 400;

    public static final int MAZXPREROUTE = 200;

    /**
     * 连接参数设置
     */
    static {
        httpParams = new BasicHttpParams();// 设置http参数
        HttpConnectionParams.setConnectionTimeout(httpParams,
                HTTP_CONNECTIONTIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_SOTIMEOUT);
        HttpConnectionParams.setSocketBufferSize(httpParams, SOCKET_BUFFERSIZE);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, CHARSET_UTF8);
        HttpProtocolParams.setUseExpectContinue(httpParams, Boolean.FALSE);
        HttpProtocolParams.setUserAgent(httpParams, USER_AGENT);
    }

    /**
     * @param @return
     * @return HttpClient
     * @throws
     * @Title: getInstance
     * @Description: 获得httpclient对象
     */
    public static HttpClient getHttpInstance() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HTTP, 80, PlainSocketFactory
                .getSocketFactory()));
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(
                schemeRegistry);
        connectionManager.setMaxTotal(MAXTOTAL);
        connectionManager.setDefaultMaxPerRoute(MAZXPREROUTE);
        HttpClient httpClient = new DefaultHttpClient(connectionManager);
        httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,HTTP_CONNECTIONTIMEOUT);//连接超时
		httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,HTTP_SOTIMEOUT);//数据传输时间
        return httpClient;
    }

    /**
     * @param @return
     * @return HttpClient
     * @throws
     * @Title: getInstance
     * @Description: 获得httpclient对象
     */
    public static HttpClient getSSLClient() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HTTP, 80, PlainSocketFactory
                .getSocketFactory()));
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(
				schemeRegistry);
        connectionManager.setMaxTotal(MAXTOTAL);
        connectionManager.setDefaultMaxPerRoute(MAZXPREROUTE);
        HttpClient httpClient = new SSLClient(connectionManager);
        httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,HTTP_CONNECTIONTIMEOUT);//连接超时
		httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,HTTP_SOTIMEOUT);//数据传输时间
        return httpClient;
    }

    /**
     * @param @return
     * @return HttpClient
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws
     * @Title: getInstance
     * @Description: 获得httpclient对象
     */
    public static HttpClient getHttpSInstance() {

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {

                }

                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sf = new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme httpsScheme = new Scheme("https", 443, sf);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(
                schemeRegistry);
        connectionManager.setMaxTotal(MAXTOTAL);
        connectionManager.setDefaultMaxPerRoute(MAZXPREROUTE);
        HttpClient httpClient = new DefaultHttpClient(connectionManager);
        httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,HTTP_CONNECTIONTIMEOUT);//连接超时
		httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,HTTP_SOTIMEOUT);//数据传输时间
        return httpClient;
    }

    public static void httpShountdown() {
        getHttpInstance().getConnectionManager().shutdown();
    }

    public static void httpsShountdown() {
        getHttpSInstance().getConnectionManager().shutdown();
    }
}
