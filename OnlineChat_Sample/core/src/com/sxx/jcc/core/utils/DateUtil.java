package com.sxx.jcc.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author raible
 * @version $Revision: 1.10 $ $Date: 2005/12/14 07:08:49 $
 */
public class DateUtil {
	// ~ Static fields/initializers
	// =============================================
	/**
	 * ��־
	 */
	private static Log log = LogFactory.getLog(DateUtil.class);

	/**
	 * ���ڸ�ʽ
	 */
	private static String datePattern = "yyyyMMdd";

	/**
	 * ʱ���ʽ
	 */
	private static String timePattern = "HH:mm";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (yyyyMMdd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             ת���쳣
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:mm
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             ת���쳣
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyyMMdd)
	 * @return a date object
	 * 
	 * @throws ParseException
	 *             ת���쳣
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + datePattern);
			}

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * �Զ��ж�strDate�ĸ�ʽ����ת��.Ŀǰ֧�����ָ�ʽ:yyyy-MM-dd,yyyy-MM-dd HH:mm
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date autoConvertStrToDate(String strDate)
			throws ParseException {
		Date aDate = null;
		String mask = null;
		if (StringUtils.isEmpty(strDate)) {
			return null;
		}

		if (strDate.length() == 10) {
			mask = "yyyy-MM-dd";
		} else if (strDate.length() == 16) {
			mask = "yyyy-MM-dd HH:mm";
		} else if(strDate.length() == 19){
			mask = "yyyy-MM-dd HH:mm:ss";
		}else {
			throw new IllegalArgumentException(
					"unknow date string.input strDate=" + strDate);
		}
		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + datePattern);
			}

			aDate = convertStringToDate(mask, strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * ���date1>date2 ���� 1 = 0 < -1
	 * 
	 * @param date1
	 *            ����1
	 * @param date2
	 *            ����2
	 * @return �ȽϽ��
	 */
	public static int compareDate(Date date1, Date date2) {
		String d1 = getDateTime(datePattern, date1);
		String d2 = getDateTime(datePattern, date2);

		if (d1 == null && d2 != null)
			return -1;
		else if (d1 != null && d2 == null)
			return 1;
		else if (d1 == null && d2 == null)
			return 0;
		else
			return d1.compareTo(d2);
	}

	public static Integer getPerAge(Date birthDay) throws ParseException {
		Integer perAge = null;
		Date today = new Date();
		SimpleDateFormat style = new SimpleDateFormat(datePattern);
		String todayAsStr = style.format(today);
		String birthAsStr = style.format(birthDay);
		int thisYear = Integer.parseInt(todayAsStr.substring(0, 4));
		int birthYear = Integer.parseInt(birthAsStr.substring(0, 4));
		if (Integer.parseInt(todayAsStr.substring(4))
				- Integer.parseInt(birthAsStr.substring(4)) >= 0) {
			perAge = new Integer(thisYear - birthYear);
		} else
			perAge = new Integer(thisYear - birthYear - 1);
		return perAge;
	}

	public static Date getNowDate() throws ParseException{
		String dateString=getDateTime("yyyy-MM-dd HH:mm:ss",new Date());
		return autoConvertStrToDate(dateString);
	}
	
	public static void main(String[] args) {
	  	try {
	  		Date date=new Date();
	  		System.out.println(date);
	  		SimpleDateFormat style = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  		System.out.println(style.format(date));
	  		System.err.println(autoConvertStrToDate(style.format(date)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
