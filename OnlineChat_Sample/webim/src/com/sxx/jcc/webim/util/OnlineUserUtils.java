package com.sxx.jcc.webim.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.server.message.NewRequestMessage;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SessionConfig;
import com.sxx.jcc.webim.pojo.AgentReport;
import com.sxx.jcc.webim.pojo.MessageDataBean;
import com.sxx.jcc.webim.pojo.MessageInContent;
import com.sxx.jcc.webim.service.ISysAgentUserService;
import com.sxx.jcc.webim.service.ServiceQuene;
import com.sxx.jcc.webim.util.router.RouterHelper;

public class OnlineUserUtils {
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie data = null;
		if (request != null && request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(key)) {
					data = cookie;
					break;
				}
			}
		}
		return data != null ? data.getValue() : null;
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		try {
			tmp.append(java.net.URLDecoder.decode(src, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return tmp.toString();
	}

	public static String getKeyword(String url) {
		Map<String, String[]> values = new HashMap<String, String[]>();
		try {
			parseParameters(values, url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer strb = new StringBuffer();
		String[] data = values.get("q");
		if (data != null) {
			for (String v : data) {
				strb.append(v);
			}
		}
		return strb.toString();
	}

	public static String getSource(String url) {
		String source = "0";
		try {
			URL addr = new URL(url);
			source = addr.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return source;
	}

	public static NewRequestMessage newRequestMessage(String user , String nickname, String orgi,
			String session, String appid, String ip, String osname,
			String browser , String headimg , IP ipdata , String channel , String skill , String agent, String title, String url , String traceid, String initiator ,String eventid) throws Exception {
		// 坐席服务请求，分配 坐席
		NewRequestMessage data = new NewRequestMessage();
		data.setAppid(appid);
		data.setOrgi(orgi);
		data.setUserid(user);
		data.setSession(session);
		data.setType(XKDataContext.MessageTypeEnum.NEW.toString());
		data.setId(XKTools.genID());
		ISysAgentUserService service = XKDataContext.getContext().getBean(ISysAgentUserService.class);
		AgentUser agentUser = service.findAgentUserByuserId(user);
		String username = "Guest_"+XKTools.genIDByKey(user);
		if (agentUser == null) {
			agentUser = new AgentUser(data.getUserid(),channel,
					data.getUserid(), null, data.getOrgi(), data.getAppid()); // 创建排队用户的信息，当前用户只能在队列里存在一次，用UserID作为主键ID存储
				
			agentUser.setNickname(username);
			agentUser.setUsername(username);
			// agentUser.setId(data.getUserid());
		}
		
		agentUser.setOsname(osname);
		agentUser.setBrowser(browser);
		agentUser.setAppid(appid);
		agentUser.setSessionid(session);
		
		if (ipdata != null) {
			agentUser.setCountry(ipdata.getCountry());
			agentUser.setProvince(ipdata.getProvince());
			agentUser.setCity(ipdata.getCity());
			if(!StringUtils.isBlank(ip)){
				agentUser.setRegion(ipdata.toString() + "[" + ip + "]");
			}else{
				agentUser.setRegion(ipdata.toString());
			}
		}

		agentUser.setOwner(eventid);
		agentUser.setHeadimgurl(headimg);
		
		agentUser.setStatus(null); // 修改状态
		agentUser.setTitle(title);
		agentUser.setUrl(url);
		agentUser.setTraceid(traceid);
		agentUser.setOwner(eventid); //智能IVR的 EventID
		

		MessageInContent inMessage = new MessageInContent();
		inMessage.setChannelMessage(data);
		inMessage.setAgentUser(agentUser);
		inMessage.setMessage(data.getMessage());
		inMessage.setFromUser(data.getUserid());
		inMessage.setToUser(data.getAppid());
		inMessage.setId(data.getId());
		inMessage.setMessageType(data.getType());
		inMessage.setNickName(agentUser.getNickname());
		inMessage.setOrgi(data.getOrgi());
		inMessage.setUser(agentUser);
		
	
		 
		agentUser.setSkill(skill);
		agentUser.setAgent(agent);

		MessageDataBean outMessageDataBean = null ;
		
		SessionConfig sessionConfig = ServiceQuene.initSessionConfig(data.getOrgi()) ;
		AgentReport report = ServiceQuene.getAgentReport(data.getOrgi()) ;
		
		if(sessionConfig.isHourcheck() && !XKTools.isInWorkingHours(sessionConfig,"webim")){
			data.setMessage(sessionConfig.getNotinwhmsg());
		}else{
			if(report.getAgents() == 0){
				data.setNoagent(true);
			}
			data.setQueueindex(report.getInquene());
			outMessageDataBean = RouterHelper.getRouteInstance().handler(inMessage);
			if (outMessageDataBean != null) {
				data.setMessage(outMessageDataBean.getMessage());
				
				if(outMessageDataBean.getAgentUser()!=null){
					data.setAgentserviceid(outMessageDataBean.getAgentUser().getAgentserviceid());
				}
			}
		}
		
		return data;
	}
	
	
	public static NewRequestMessage newRequestMessage(String userid, String orgi,
			String session, String appid, String ip, String osname,
			String browser , String channel , String skill , String agent , String nickname, String title, String url , String traceid, String initiator) throws Exception {
		IP ipdata = null ;
		/**if(!StringUtils.isBlank(ip)){
			ipdata = IPTools.getInstance().findGeography(ip);
		}**/
		if(StringUtils.isBlank(nickname)){
			nickname = "Guest_" + XKTools.genIDByKey(userid);
		}
		
		return newRequestMessage(userid , nickname, orgi, session, appid, ip, osname, browser , "" , ipdata , channel , skill , agent , title ,url , traceid , initiator , session) ;
	}
	
	public static NewRequestMessage newRequestMessage(String openid , String nickname, String orgi,
			String session, String appid , String headimg , String country , String province , String city , String channel , String skill , String agent, String initiator) throws Exception {
		IP ipdata = new IP() ;
		ipdata.setCountry(country);
		ipdata.setProvince(province);
		ipdata.setCity(city);
		return newRequestMessage(openid , nickname , orgi, session, appid, null , null , null , headimg , ipdata , channel , skill , agent , null , null , null , initiator , session) ;
	}

	public static void parseParameters(Map<String, String[]> map, String data,
			String encoding) throws UnsupportedEncodingException {
		if ((data == null) || (data.length() <= 0)) {
			return;
		}

		byte[] bytes = null;
		try {
			if (encoding == null)
				bytes = data.getBytes();
			else
				bytes = data.getBytes(encoding);
		} catch (UnsupportedEncodingException uee) {
		}
		parseParameters(map, bytes, encoding);
	}

	public static void parseParameters(Map<String, String[]> map, byte[] data,
			String encoding) throws UnsupportedEncodingException {
		if ((data != null) && (data.length > 0)) {
			int ix = 0;
			int ox = 0;
			String key = null;
			String value = null;
			while (ix < data.length) {
				byte c = data[(ix++)];
				switch ((char) c) {
				case '&':
					value = new String(data, 0, ox, encoding);
					if (key != null) {
						putMapEntry(map, key, value);
						key = null;
					}
					ox = 0;
					break;
				case '=':
					if (key == null) {
						key = new String(data, 0, ox, encoding);
						ox = 0;
					} else {
						data[(ox++)] = c;
					}
					break;
				case '+':
					data[(ox++)] = 32;
					break;
				case '%':
					data[(ox++)] = (byte) ((convertHexDigit(data[(ix++)]) << 4) + convertHexDigit(data[(ix++)]));

					break;
				default:
					data[(ox++)] = c;
				}
			}

			if (key != null) {
				value = new String(data, 0, ox, encoding);
				putMapEntry(map, key, value);
			}
		}
	}

	private static void putMapEntry(Map<String, String[]> map, String name,
			String value) {
		String[] newValues = null;
		String[] oldValues = (String[]) (String[]) map.get(name);
		if (oldValues == null) {
			newValues = new String[1];
			newValues[0] = value;
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	private static byte convertHexDigit(byte b) {
		if ((b >= 48) && (b <= 57))
			return (byte) (b - 48);
		if ((b >= 97) && (b <= 102))
			return (byte) (b - 97 + 10);
		if ((b >= 65) && (b <= 70))
			return (byte) (b - 65 + 10);
		return 0;
	}

	// public static void main(String[] args){
	// System.out.println(getKeyword("http://www.so.com/link?url=http%3A%2F%2Fwww.r3yun.com%2F&q=R3+Query%E5%AE%98%E7%BD%91&ts=1484181457&t=e2ad49617cd5de0eb0937f3e2a84669&src=haosou"))
	// ;
	// System.out.println(getSource("https://www.google.com.hk/")) ;
	// }
}
