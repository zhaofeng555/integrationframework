package hjg.testHttpclient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClient2 {
	public void getUrl(String url, String encoding) throws ClientProtocolException, IOException {
		// 默认的client类。
		HttpClient client = new DefaultHttpClient();
		// 设置为get取连接的方式.
		HttpGet get = new HttpGet(url);
		// 得到返回的response.
		HttpResponse response = client.execute(get);
		// 得到返回的client里面的实体对象信息.
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println("内容编码是：" + entity.getContentEncoding());
			System.out.println("内容类型是：" + entity.getContentType());
			// 得到返回的主体内容.
			InputStream instream = entity.getContent();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream, encoding));
				System.out.println(reader.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				instream.close();
			}
		}

		// 关闭连接.
		client.getConnectionManager().shutdown();
	}

	public void postUrlWithParams(String url, Map params, String encoding) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			HttpPost httpost = new HttpPost(url);
			// 添加参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null && params.keySet().size() > 0) {
				Iterator iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
				}
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			System.out.println("Login form get: " + response.getStatusLine() + entity.getContent());
			dump(entity, encoding);
			System.out.println("Post logon cookies:");
			List<Cookie> cookies = httpclient.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).toString());
				}
			}

		} finally {
			// 关闭请求
			httpclient.getConnectionManager().shutdown();
		}
	}

	private static void dump(HttpEntity entity, String encoding) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
		System.out.println(br.readLine());
	}

	public String getSessionId(String url, Map params, String encoding, String url2) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		try {

			HttpPost httpost = new HttpPost(url);
			// 添加参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null && params.keySet().size() > 0) {
				Iterator iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
				}
			}
			// 设置请求的编码格式
			httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			// 登录一遍
			httpclient.execute(httpost);
			// 然后再第二次请求普通的url即可。
			httpost = new HttpPost(url2);
			BasicResponseHandler responseHandler = new BasicResponseHandler();
			System.out.println(httpclient.execute(httpost, responseHandler));
		} finally {
			// 关闭请求
			httpclient.getConnectionManager().shutdown();
		}
		return "";
	}

	// 第一个参数，网络连接；第二个参数，保存到本地文件的地址
	public void getFile(String url, String fileName) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
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

			byte[] charts = httpClient.execute(get, handler);
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(charts);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public void multiThread() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = new BasicHttpParams();
		// 设置允许链接的做多链接数目
		ConnManagerParams.setMaxTotalConnections(params, 200);
		// 设置超时时间.
		ConnManagerParams.setTimeout(params, 10000);
		// 设置每个路由的最多链接数量是20
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
		// 设置到指定主机的路由的最多数量是50
		HttpHost localhost = new HttpHost("127.0.0.1", 80);
		connPerRoute.setMaxForRoute(new HttpRoute(localhost), 50);
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
		// 设置链接使用的版本
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		// 设置链接使用的内容的编码
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		// 是否希望可以继续使用.
		HttpProtocolParams.setUseExpectContinue(params, true);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		httpClient = new DefaultHttpClient(cm, params);
	}

	public static void getUrl2(String url, String encoding) throws ClientProtocolException, IOException {
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
			dump(entity, encoding);
		}
	}

	public void setTimeout() {
		HttpClient httpClient = new DefaultHttpClient();
		// 在服务端设置一个保持持久连接的特性.
		// HTTP服务器配置了会取消在一定时间内没有活动的链接，以节省系统的持久性链接资源.
		((AbstractHttpClient) httpClient).setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						try {
							return Long.parseLong(value) * 1000;
						} catch (Exception e) {

						}
					}
				}
				HttpHost target = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if ("www.baidu.com".equalsIgnoreCase(target.getHostName())) {
					return 5 * 1000;
				} else
					return 30 * 1000;
			}
		});
	}
}
