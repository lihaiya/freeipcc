package com.sxx.jcc.core.cache;

import org.apache.commons.lang3.StringUtils;

public class WoseqGen {

	public static void main(String[] args) {
		String numStr= "20190821768909";
		System.out.println(numStr.substring(8, numStr.length()));
		//System.out.println( StringUtils.leftPad(numStr+"", 6, '0'));

	}

}
