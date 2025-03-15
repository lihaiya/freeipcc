package com.sxx.jcc.common.utils.disruptor;

import com.lmax.disruptor.EventHandler;
import com.sxx.jcc.common.utils.event.UserDataEvent;
import com.sxx.jcc.common.utils.mail.Mail;
import com.sxx.jcc.webim.pojo.OnlineUser;
import com.sxx.jcc.webim.pojo.RequestLog;
import com.sxx.jcc.webim.pojo.StatusEvent;
import com.sxx.jcc.webim.pojo.UserHistory;
import com.sxx.jcc.webim.pojo.UserTraceHistory;

public class UserEventHandler implements EventHandler<UserDataEvent>{

	@Override
	public void onEvent(UserDataEvent arg0, long arg1, boolean arg2)
			throws Exception {
		if(arg0.getEvent() instanceof UserHistory){
			//UserEventRepository userEventRes = UKDataContext.getContext().getBean(UserEventRepository.class) ;
			//userEventRes.save((UserHistory)arg0.getEvent()) ;
		}else if(arg0.getEvent() instanceof UserTraceHistory){
			//UserTraceRepository userTraceRes = UKDataContext.getContext().getBean(UserTraceRepository.class) ;
			//userTraceRes.save((UserTraceHistory)arg0.getEvent()) ;
		}else if(arg0.getEvent() instanceof RequestLog){
			//RequestLogRepository requestLogRes = UKDataContext.getContext().getBean(RequestLogRepository.class) ;
			//requestLogRes.save((RequestLog)arg0.getEvent()) ;
		}else if(arg0.getEvent() instanceof OnlineUser){
			/**OnlineUserRepository onlineUserRes = UKDataContext.getContext().getBean(OnlineUserRepository.class) ;
			OnlineUser onlineUser = (OnlineUser)arg0.getEvent() ;
			List<OnlineUser> onlineUserList = onlineUserRes.findByUseridAndOrgi(onlineUser.getUserid(), onlineUser.getOrgi()) ;
			if(onlineUserList.size() == 0){
				onlineUserRes.save(onlineUser) ;
			}**/
		}if(arg0.getEvent() instanceof StatusEvent){
			/**StatusEvent statusEvent = (StatusEvent)arg0.getEvent() ;
			QualityConfig qConfig = UKTools.initQualityConfig(statusEvent.getOrgi()) ;
			if(qConfig.isPhonetic() && !StringUtils.isBlank(qConfig.getEngine())) {
				PhoneticTranscription trans = (PhoneticTranscription) UKDataContext.getContext().getBean(qConfig.getEngine()) ;
				if(trans!=null) {
					File voiceFile = UKTools.crawlVoiceRecord(statusEvent) ;
					if(voiceFile != null) {
						String taskid = trans.getLfasr(statusEvent.getUserid(), statusEvent.getOrgi(), statusEvent.getOrgan(), statusEvent, voiceFile.getAbsolutePath(), qConfig);
						statusEvent.setTranid(taskid);
						StatusEventRepository statusEventRes = UKDataContext.getContext().getBean(StatusEventRepository.class) ;
						statusEventRes.save(statusEvent) ;
					}
				}
			}**/
		}else if(arg0.getEvent() instanceof Mail){
			/**Mail mail = (Mail)arg0.getEvent() ;
			if(null!=mail&&!StringUtils.isBlank(mail.getEmail())) {
				UKTools.sendMail(mail.getEmail(), mail.getCc(), mail.getSubject(), mail.getContent(), mail.getFilenames());
			}**/
		}
	}

}
