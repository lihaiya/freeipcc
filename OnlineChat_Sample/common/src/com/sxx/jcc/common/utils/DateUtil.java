package com.sxx.jcc.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	private static String defaultDatePattern = "yyyy-MM-dd";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";

		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}

		return (returnValue);
	}
	
	//当前月的第一天     
	public static String monthFirstDate(Date date) throws ParseException{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(date);
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   
        Date beginTime=cal.getTime();  
        String beginTime1=datef.format(beginTime)+" 00:00:00";
		return beginTime1;
	}
	
	//当前月的最后一天     
	public static String monthLastDate(Date date) throws ParseException{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(date);
		cal.set( Calendar.DATE, 1 );  
        cal.roll(Calendar.DATE, - 1 );  
        Date endTime=cal.getTime(); 
		String endTime1=datef.format(endTime)+" 23:59:59";  
		return endTime1;
	}
	
	

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.parse(strDate);
	}

	/**
	 * 在日期上增加数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数个整日(n为负数则是减少数日)
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数个小时(n为负数则是减少数小时)
	 */
	public static Date addHour(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, n);
		return cal.getTime();
	}
	
	/**
	 * 在日期上增加数个分钟(n为负数则是减少数分钟)
	 */
	public static Date addMinute(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, n);
		return cal.getTime();
	}

	public static Date parseDate(String str, String[] parsePatterns) throws ParseException {
		return DateUtils.parseDate(str, parsePatterns);
	}
	/**
	 * 字符串转化日期
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parseDate1(String str, String format) {
		try {
			if (str == null || str.equals("")) {
				return null;
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return simpleDateFormat.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转化为日期,通用性相对较强
	 * 
	 * @param dateString
	 *            具有日期格式的字符串
	 * @param DataFormat
	 *            日期格式
	 * @return Date
	 */
	public static Date stringToDate(String dateString, String DataFormat) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DataFormat);
			date = sdf.parse(dateString);
		} catch (ParseException ex) {
			return null;
		}
		return date;
	}

	/**
	 * 求出两个时间段的时间差（精确到天/小时/分）
	 */
	public static String timeLeft(Date timeNow, Date timeLimit) {
		if (timeNow == null || timeLimit == null) {
			return "0";
		}
		long now = timeNow.getTime();
		long limit = timeLimit.getTime();
		int day = (int) (Math.abs(now - limit) / (3600000 * 24));
		int hour = (int) (Math.abs(now - limit) % (3600000 * 24)) / 3600000;
		int minute = (int) ((Math.abs(now - limit) % (3600000 * 24)) % 3600000) / 60000;
		String timeLeft = "0";
		StringBuffer sb = new StringBuffer();
		if (now < limit) {
			sb.append("剩余").append(day).append("天").append(hour).append("小时").append(minute)
					.append("分");
		}
		if (now > limit) {
			sb.append("超过").append(day).append("天").append(hour).append("小时").append(minute)
					.append("分");
		}
		timeLeft = sb.toString();
		return timeLeft;
	}

	/**
	 * 时间一是否超过时间二
	 */
	public static String isExceed(Date timeNow, Date timeLimit) {
		if (timeNow == null || timeLimit == null) {
			return "false";
		}
		long now = timeNow.getTime();
		long limit = timeLimit.getTime();
		if (now > limit) {
			return "true";
		}
		return "false";
	}

	/**
	 * 求出两个时间段的时间差(精确到小时)
	 */
	public static int timeInterval(Date timeNow, Date timeLimit) {
		if (timeNow == null || timeLimit == null) {
			return 0;
		}
		long now = timeNow.getTime();
		long limit = timeLimit.getTime();
		int interval = (int) ((now - limit) / 3600000);
		return interval;
	}
	
	/**
	 * 求出两个时间段的时间差(精确到分钟)
	 */
	public static int timeIntervalMin(Date timeNow, Date timeLimit) {
		if (timeNow == null || timeLimit == null) {
			return 0;
		}
		long now = timeNow.getTime();
		long limit = timeLimit.getTime();
		int interval = (int) ((now - limit) / 60000);
		return interval;
	}

	/**
	 * 求出两个时间段的时间差(精确到秒)
	 */
	public static int timeIntervalSecond(Date timeNow, Date timeLimit) {
		if (timeNow == null || timeLimit == null) {
			return 0;
		}
		long now = timeNow.getTime();
		long limit = timeLimit.getTime();
		int interval = (int) ((now - limit) / 1000);
		return interval;
	}

	/**
	 * 按照小时添加时间(减去下班时间)
	 */
	public static Date addHours(Date currentDate, int num, String amStart, String amEnd,
			String pmStart, String pmEnd) throws Exception {
		if (currentDate == null) {
			return null;
		}
		// 求出下班时间的时间差，包括中午和晚上
		long midDay = Timestamp.valueOf("2007-04-09 " + pmStart + ":00").getTime()
				- Timestamp.valueOf("2007-04-09 " + amEnd + ":00").getTime();
		long midNight = Timestamp.valueOf("2007-04-10 " + amStart + ":00").getTime()
				- Timestamp.valueOf("2007-04-09 " + pmEnd + ":00").getTime();
		Calendar calendar = Calendar.getInstance();
		// 判断是否是工作时间，如果不是的话currentDate变为下次上班时间，并过一秒
		if (!isWorkTime(currentDate, amStart, amEnd, pmStart, pmEnd)) {
			// 是否是中午休息
			if (isDay(currentDate, amEnd, pmStart)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = df.format(currentDate);
				String[] nowDay = now.split(" ");
				// 组织成date型
				currentDate = df.parse(nowDay[0] + " " + pmStart + ":01");
			}
			// 是否是夜间休息凌晨之后上班之前
			else if (isAfterMidNight(currentDate, amStart)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = df.format(currentDate);
				String[] nowDay = now.split(" ");
				// 组织成date型
				currentDate = df.parse(nowDay[0] + " " + amStart + ":01");
			}
			// 下午下班后，晚上凌晨前
			else {
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				day = day + 1;
				calendar.set(Calendar.DAY_OF_MONTH, day);
				Date dateTemp = calendar.getTime();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = df.format(dateTemp);
				String[] nowDay = now.split(" ");
				// 组织成date型
				currentDate = df.parse(nowDay[0] + " " + amStart + ":01");
			}
		}
		// 对时间间隔进行for循环，发现下班时间跳过，并加上相应的时间间隔
		calendar.setTimeInMillis(currentDate.getTime());
		for (int i = 0; i < num; i++) {
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			hour = hour + 1;
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			currentDate = calendar.getTime();
			while (!isWorkTime(currentDate, amStart, amEnd, pmStart, pmEnd)) {
				if (isDay(currentDate, amEnd, pmStart)) {
					currentDate = new Date((currentDate.getTime() + midDay));
					calendar.setTimeInMillis(currentDate.getTime());
				} else {
					currentDate = new Date((currentDate.getTime() + midNight));
					calendar.setTimeInMillis(currentDate.getTime());
				}
			}
		}
		long time = currentDate.getTime();
		return new Date(time);
	}

	// 是否是上班时间
	private static boolean isWorkTime(Date date, String amStart, String amEnd, String pmStart,
			String pmEnd) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(date);
		String[] nowDay = now.split(" ");
		// 组织成date型
		Date amstart = df.parse(nowDay[0] + " " + amStart + ":00");
		Date amend = df.parse(nowDay[0] + " " + amEnd + ":00");
		Date pmstart = df.parse(nowDay[0] + " " + pmStart + ":00");
		Date pmend = df.parse(nowDay[0] + " " + pmEnd + ":00");
		if ((date.after(amstart) && date.before(amend))
				|| (date.after(pmstart) && date.before(pmend))) {
			return true;
		}
		return false;
	}

	// 非工作时间---中午还是晚上，只能在!isWorkTime嵌套下使用
	private static boolean isDay(Date date, String amEnd, String pmStart) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(date);
		String[] nowDay = now.split(" ");
		// 组织成date型
		Date amend = df.parse(nowDay[0] + " " + amEnd + ":00");
		Date pmstart = df.parse(nowDay[0] + " " + pmStart + ":00");
		if (date.after(amend) && date.before(pmstart)) {
			return true;
		}
		return false;
	}

	// 是否在午夜之后
	private static boolean isAfterMidNight(Date date, String amStart) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(date);
		String[] nowDay = now.split(" ");
		// 组织成date型
		Date amstart = df.parse(nowDay[0] + " " + amStart + ":00");
		if (date.before(amstart)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		//System.out.println(DateUtil.monthFirstDate(new Date()));
		//System.out.println(DateUtil.monthLastDate(new Date()));
		System.out.println(DateUtil.getSecondByHms("2"));
	}
	
    public static String format2(Date date) {

        return format(date,"yyyy-MM-dd HH:mm:ss");
    }
    
    public static int MonthBetweenDate(Date early, Date late){
		int result=0;
		Calendar c = Calendar.getInstance();
		c.setTime(early);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		c.setTime(late);
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH);
		if (year == year1) {
			result = month1 - month;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (year1 - year) + month1 - month;// 两个日期相差几个月，即月份差
		}
		return result;
	}
    
    public static long getSecondByHms(String hms){
    	if(StringUtils.isBlank(hms)){
    		return 0;
    	}
    	Date date = null ;
		try {
			date = DateUtil.parse(hms,"HH:mm:ss");
	    	long sec = date.getHours()*60*60+date.getMinutes()*60+date.getSeconds();
			return sec;
		} catch (ParseException e) {
			return 0;
		}
	}
    
    /**
	 * 日期按月增加
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date dateIncreaseByMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.MONTH, months);
		return cal.getTime();
	}
	
    Calendar cal = Calendar.getInstance();   
    
    public static String getNowDateStr() {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String nowDate = sdf.format(date)+" 00:00:00";
		return nowDate;
    }
    
    public static String getNowDateEndStr() {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String nowDate = sdf.format(date)+" 23:59:59";
		return nowDate;
    }
    
    public static String getYesterdayStart(){
    	Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        Date time = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
    }
    
    public static String getYesterdayEnd(){
    	Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        Date time = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 23:59:59";
    }
    
    //本周第一天
    public static String getWeekStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
    }
    
    //上周第一天
    public static String getLastWeekStart() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,2);
		c.add(Calendar.DATE, -7);
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd  00:00:00");
		return startSdf.format(c.getTime());
    }
    
    /**
     * 获取本周的最后一天
     * @return String
     * **/
    public static String getWeekEnd(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 23:59:59";
    }
    
    /** 获得上周最后一天:yyyy-MM-dd  HH:mm:ss */
	public static String getLastWeekEnd() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,2);
		c.add(Calendar.DATE, -1);
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd  23:59:59");
		return endSdf.format(c.getTime());
	}
	
    /**
     * 获取本月开始日期
     * @return String
     * **/
    public static String getMonthStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
    }
    
    public static String getMonthEnd(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 23:59:59";
    }
    
    /**
     * 获取上月开始日期
     * @return String
     * **/
    public static String getPreMonthStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
    }
    
    
    /**
     * 获取上月最后一天
     * @return String
     * **/
    public static String getPreMonthEnd(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 23:59:59";
    }
    
    public static void main(){
    	System.out.println(DateUtil.getWeekStart());
    	System.out.println(DateUtil.getWeekEnd());
    	System.out.println(DateUtil.getLastWeekStart());
    	System.out.println(DateUtil.getLastWeekEnd());
    }
}
