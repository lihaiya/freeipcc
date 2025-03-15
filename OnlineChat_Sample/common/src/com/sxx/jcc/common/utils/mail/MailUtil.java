package com.sxx.jcc.common.utils.mail;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @author andy
 *
 */
public class MailUtil {
	public  static void sendEmail(List<String> recipient,List<String> recipientCC,String title,String content) throws Exception{
		if(CollectionUtils.isEmpty(recipient)){
			return ;
		}
		MailSender sender = new MailSender();
		sender.send(recipient, recipientCC, title, content, null);
	}
}
