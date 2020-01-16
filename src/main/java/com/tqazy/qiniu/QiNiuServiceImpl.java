package com.tqazy.qiniu;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 七牛云自定义服务接口实现类
 * @author 陶其
 * @date 2020-01-16
 */
public class QiNiuServiceImpl implements QiNiuService{

    private String domainName;
    private String path;
    private String dateTime;
    private String key;
    private static String KEY1 = "KEY1";
    private static String KEY2 = "KEY2";
    @Override
    public String initParam(String domainName, String path, String dateTime, String key ){
        this.domainName = domainName;
        String url = null;
        try {
            //1. 转换时间格式为时间戳
            this.dateTime = Long.toHexString(getTimeStampByString(dateTime));
            //2. 资源地址编码
            this.path = getUrlEncodeOfPath(path);

            // 判断KEY有效性，优先KEY2
            if(KEY1.equals(key)){
                this.key = KEY1_NUM;
            }else if(KEY2.equals(key)){
                this.key = KEY2_NUM;
            }else{
                return null;
            }
            String s = this.key + this.path + this.dateTime;
            //3. 加密
            String sign = getMD5OfS(s);
            url = this.domainName + this.path + "?sign=" + sign + "&t=" + this.dateTime;

        } catch (Exception e) {
            System.out.println("七牛云自定义服务接口实现发生异常！");
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 将时间字符串转换为时间戳
     * @param dateTime 时间
     * @return 时间戳
     * @throws ParseException
     */
    private Long getTimeStampByString(String dateTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        if(StringUtils.isNotBlank(dateTime)){
            Date date = simpleDateFormat.parse(dateTime);
            long timeStamp = date.getTime()/1000;
            return timeStamp;
        }else{
            return null;
        }
    }

    /**
     * 将path进行编码
     * @param path 资源地址
     * @return 编码后的资源地址
     */
    private String getUrlEncodeOfPath(String path) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(path)) {
            String tempStr = "";
            String[] paths = path.split("/");
            for(int i = 0; i < paths.length; i++){
                if("".equals(paths[i])){
                    continue;
                }
                tempStr += "/" + URLEncoder.encode(paths[i],"UTF-8");
            }
            return tempStr;
        }else{
            return null;
        }
    }

    /**
     * 将S进行MD5加密
     * @param s
     * @return
     */
    private String getMD5OfS(String s) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        // 准备要加密的数据
        byte[] b = s.getBytes();
        // 加密
        byte[] digest = md5.digest(b);
        // 十六进制的字符
        char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7' , '8', '9', 'A', 'B', 'C', 'D', 'E','F' };
        StringBuffer sign = new StringBuffer();
        // 处理成十六进制的字符串(通常)
        for (byte bb : digest) {
            sign.append(chars[(bb >> 4) & 15]);
            sign.append(chars[bb & 15]);
        }
        return sign.toString();
    }
}
