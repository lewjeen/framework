package com.common.httpclient.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.common.httpclient.Page;
import com.common.httpclient.Request;
import com.common.httpclient.Site;





public class TestHttpClientRequest {
	 @Test
	    public void testCookie() {
	        Site site = Site.me().setDomain("www.diandian.com").addCookie("t", "43ztv9srfszl99yxv2aumx3zr7el7ybb");
	        HttpClientRequest httpClientDownloader = new HttpClientRequest();
	        Page download = httpClientDownloader.request(new Request("http://www.diandian.com"), site.toTask());
	        System.out.println(download);
	    }

}
