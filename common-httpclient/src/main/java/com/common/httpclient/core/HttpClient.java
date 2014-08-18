package com.common.httpclient.core;



import com.common.httpclient.Page;
import com.common.httpclient.Request;
import com.common.httpclient.Task;



public interface HttpClient {
	
	public Page request(Request request,Task task);
	
	 public void setThread(int threadNum);

}
