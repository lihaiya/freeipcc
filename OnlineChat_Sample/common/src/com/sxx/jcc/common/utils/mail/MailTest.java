package com.sxx.jcc.common.utils.mail;

import java.util.ArrayList;
import java.util.List;

public class MailTest {

	public static void main(String[] args) {
		//收件人
		List<String> recipient = new ArrayList<String>();
		recipient.add("saj1123@163.com");
		//recipient.add("saj1002@163.com");
		//抄送人
		List<String> recipientCC = new ArrayList<String>();
		recipientCC.add("saj1002@163.com");
		
		try {
			MailUtil.sendEmail(recipient, recipientCC, "测试邮件功能", "工单过期啦!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
