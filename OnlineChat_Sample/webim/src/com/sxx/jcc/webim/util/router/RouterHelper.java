package com.sxx.jcc.webim.util.router;

public class RouterHelper {
	private static Router router = new MessageRouter();
	
	public static Router getRouteInstance(){
		return router ;
	}
}
