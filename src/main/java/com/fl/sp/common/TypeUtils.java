/*
  Created: 方磊
  Date: 2017年7月25日  下午3:15:22

*/
package com.fl.sp.common;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeUtils {
    /**
     * 判断对象是否为空,(包括null and "")
     *
     * @param o 判断对象
     * @return 为空返回True, 不为空返回False
     */
    public static boolean isEmpty(Object o) {
        return (o == null || o.toString().equals("")) ? true : false;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static boolean isEmpty(List<?> list) {
        return (list == null || list.size() <= 0);
    }

    /**
     * 判断是否是数字字符串
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串中是否含有字符串数组元素，有一个返回true;一个没有返回false
     *
     * @param url 字符串
     * @param arr 字符串元素数组
     * @return
     */
    public static boolean IsStringContainsStr(String url, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (url.contains(arr[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断是否是Get请求
     *
     * @param request
     * @return
     */
    public static boolean isGet(HttpServletRequest request) {
        String method = request.getMethod();
        if (method.equals("GET")) {
            return true;
        }
        return Boolean.FALSE;
    }

    /**
     * 根据文件名判断是否是图片类型
     * @param filename
     * @return
     */
    public static boolean isImage(String filename) {
        String hzm = filename.substring(filename.lastIndexOf(".") + 1);
        boolean bz = false;
        switch (hzm.toLowerCase()) {
            case "gif":
            case "png":
            case "bmp":
            case "jpg":
            case "jpeg":
                bz = true;
                break;
            default:
                bz = false;
                break;
        }
        return bz;
    }
    public static boolean isH5Video(String filename) {
        String hzm = filename.substring(filename.lastIndexOf(".") + 1);
        boolean bz = false;
        switch (hzm.toLowerCase()) {
            case "mp4":
            case "ogg":
            case "mov":
                bz = true;
                break;
            default:
                bz = false;
                break;
        }
        return bz;
    }
}
