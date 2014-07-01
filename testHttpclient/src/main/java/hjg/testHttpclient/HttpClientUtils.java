package hjg.testHttpclient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author von gosling 2011-12-12
 */
public class HttpClientUtils {
	private String encoding = "utf-8";
	private String url;

	public void testGet() throws Exception {
		// 默认的client类。
		HttpClient client = new DefaultHttpClient();
		// 设置为get取连接的方式.
		HttpGet get = new HttpGet(url);
		// 得到返回的response.
		HttpResponse response = client.execute(get);
		// 得到返回的client里面的实体对象信息.
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println(entity.getContentEncoding());
			System.out.println(entity.getContentType());
			// 得到返回的主体内容.
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream, encoding));
			System.out.println(reader.readLine());
			// EntityUtils 处理HttpEntity的工具类
			// System.out.println(EntityUtils.toString(entity));
		}

		// 关闭连接.
		client.getConnectionManager().shutdown();
	}

	public void testPost() throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		// 添加参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("p", "1"));
		formparams.add(new BasicNameValuePair("t", "2"));
		formparams.add(new BasicNameValuePair("e", "3"));

		UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpost.setEntity(urlEntity);

		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine() + entity.getContent());
		// dump(entity, encoding);
		System.out.println("Post logon cookies:");
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			System.out.println("- " + cookies.get(i).toString());
		}
		// 关闭请求
		httpclient.getConnectionManager().shutdown();
	}

	public String saveSession() throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());

		HttpPost httpost = new HttpPost(url);
		// 添加参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("p", "1"));
		formparams.add(new BasicNameValuePair("t", "2"));
		formparams.add(new BasicNameValuePair("e", "3"));
		// 设置请求的编码格式
		httpost.setEntity(new UrlEncodedFormEntity(formparams, Consts.UTF_8));
		// 登录一遍
		httpclient.execute(httpost);
		// 然后再第二次请求普通的url即可。
		httpost = new HttpPost(url);
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		System.out.println(httpclient.execute(httpost, responseHandler));
		httpclient.getConnectionManager().shutdown();

		return "";
	}

	public void saveCookie() throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		// 添加参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("uname", "name"));
		formparams.add(new BasicNameValuePair("pass", "e0c10f451217b93f76c2654b2b729b85"));
		formparams.add(new BasicNameValuePair("auto_login", "0"));
		formparams.add(new BasicNameValuePair("a", "1"));
		formparams.add(new BasicNameValuePair("backurl", "1"));

		UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpost.setEntity(urlEntity);
		HttpContext localContext = new BasicHttpContext();

		HttpResponse response = httpclient.execute(httpost, localContext);
		HttpEntity entity = response.getEntity();
		// 打印获取值
		System.out.println(Arrays.toString(response.getAllHeaders()));
		System.out.println(EntityUtils.toString(entity));

		// 第二次请求，使用上一次请求的Cookie
		DefaultHttpClient httpclient2 = new DefaultHttpClient();
		HttpPost httpost2 = new HttpPost("http://my.ifeng.com/?_c=index&_a=my");
		// 获取上一次请求的Cookie
		CookieStore cookieStore2 = httpclient2.getCookieStore();
		// 下一次的Cookie的值，将使用上一次请求
		CookieStore cookieStore = httpclient.getCookieStore();
		List<Cookie> list = cookieStore.getCookies();
		for (Cookie o : list) {
			System.out.println(o.getName() + " = " + o.getValue() + " 12");
			;
			cookieStore2.addCookie(o);
		}

		HttpResponse response2 = httpclient2.execute(httpost2);
		HttpEntity entity2 = response2.getEntity();
		System.out.println(Arrays.toString(response2.getAllHeaders()));
		System.out.println(EntityUtils.toString(entity2));
	}

	public void getContext() throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		// 设置为get取连接的方式.
		HttpGet get = new HttpGet(url);
		HttpContext localContext = new BasicHttpContext();
		// 得到返回的response.第二个参数，是上下文，很好的一个参数！
		httpclient.execute(get, localContext);

		// 从上下文中得到HttpConnection对象
		HttpConnection con = (HttpConnection) localContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
		System.out.println("socket超时时间：" + con.getSocketTimeout());

		// 从上下文中得到HttpHost对象
		HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		System.out.println("最终请求的目标:" + target.getHostName() + ":" + target.getPort());

		// 从上下文中得到代理相关信息.
		HttpHost proxy = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_PROXY_HOST);
		if (proxy != null)
			System.out.println("代理主机的目标:" + proxy.getHostName() + ":" + proxy.getPort());

		System.out.println("是否发送完毕:" + localContext.getAttribute(ExecutionContext.HTTP_REQ_SENT));

		// 从上下文中得到HttpRequest对象
		HttpRequest request = (HttpRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
		System.out.println("请求的版本:" + request.getProtocolVersion());
		Header[] headers = request.getAllHeaders();
		System.out.println("请求的头信息: ");
		for (Header h : headers) {
			System.out.println(h.getName() + "--" + h.getValue());
		}
		System.out.println("请求的链接:" + request.getRequestLine().getUri());

		// 从上下文中得到HttpResponse对象
		HttpResponse response = (HttpResponse) localContext.getAttribute(ExecutionContext.HTTP_RESPONSE);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println("返回结果内容编码是：" + entity.getContentEncoding());
			System.out.println("返回结果内容类型是：" + entity.getContentType());
		}
	}

	public void testProxy() {
		// HttpParams
		HttpParams httpParams = new BasicHttpParams();
		// HttpConnectionParams 设置连接参数
		// 设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, 60000);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		// schemeRegistry.register(
		// new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		// 设置最大连接数
		cm.setMaxTotal(200);
		// 设置每个路由默认最大连接数
		cm.setDefaultMaxPerRoute(20);
		// // 设置代理和代理最大路由
		// HttpHost localhost = new HttpHost("locahost", 80);
		// cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		// 设置代理，
		HttpHost proxy = new HttpHost("10.36.24.3", 60001);
		httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		HttpClient httpClient = new DefaultHttpClient(cm, httpParams);
	}

	public void testReconn() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 可以自动重连
		HttpRequestRetryHandler requestRetryHandler2 = new HttpRequestRetryHandler() {
			// 自定义的恢复策略
			public synchronized boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				// 设置恢复策略，在发生异常时候将自动重试3次
				if (executionCount > 3) {
					// 超过最大次数则不需要重试
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// 服务停掉则重新尝试连接
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// SSL异常不需要重试
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
				if (!idempotent) {
					// 请求内容相同则重试
					return true;
				}
				return false;
			}
		};
		httpClient.setHttpRequestRetryHandler(requestRetryHandler2);
	}

	public void testDoMyHandler() throws Exception {
		String fileName = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		// 定义一个类处理URL返回的结果
		ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
			public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toByteArray(entity);
				} else {
					return null;
				}
			}
		};
		// 不同于
		// httpClient.execute(request)，返回值是HttpResponse；返回值右ResponseHandler决定
		byte[] charts = httpClient.execute(get, handler);
		FileOutputStream out = new FileOutputStream(fileName);
		out.write(charts);
		out.close();

		httpClient.getConnectionManager().shutdown();
	}
}
