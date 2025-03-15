package com.sxx.jcc.common.utils;

import java.util.Date;

public class IdGenerator {
	private static int num = 100000;
	private static int num_ = 100000;
	
	private IdGenerator(){
		
	}
	
	/**
	 * get unique user id
	 * user id  range from 100000 to 900000 , each second
	 * @return a String Object of the unique id
	 */
	public synchronized static String getUniqueId(){
		String currentDay = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        int cnt = ++num;
        if(cnt>900000){
        	num=100000;
        }
		return currentDay+cnt;
	}
	
	public synchronized static String getUniqueIdForOrder(){
		String currentDay = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        int cnt = ++num_;
        if(cnt>900000){
        	num_=100000;
        }
		return "D"+currentDay+cnt;
	}
	public synchronized static String getUniqueIdForService(){
		String currentDay = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        int cnt = ++num_;
        if(cnt>900000){
        	num_=100000;
        }
		return "S"+currentDay+cnt;
	}
}
