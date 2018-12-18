/*
  Created: 方磊
  Date: 2017年8月10日  上午9:57:36

*/
package com.fl.sp.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeUtils {
	/**
	 * md5编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		return DigestUtils.md5Hex(str).toUpperCase();
	}

	/**
	 * 
	 * @param str 要编码的字符串
	 * @param format 编码格式
	 * @return
	 */
	public static String urlEncode(String str, String format) {
		try {
			return URLEncoder.encode(str, format);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 
	 * @param str 要解码的字符串
	 * @param format 编码格式
	 * @return
	 */
	public static String urlDecode(String str, String format) {
		try {
			return URLDecoder.decode(str, format);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 判断字符串str编码类型
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str){
		String encoding = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(),encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "GBK";
		try {
			if (str.equals(new String(str.getBytes(),encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(),encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(),encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
