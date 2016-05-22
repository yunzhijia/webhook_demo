package spring_boot;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebHookUtil { 

	private static Logger logger = LoggerFactory.getLogger(WebHookUtil.class);
	
	public static boolean checkAuth(String appid,String appkey,String content,Map<String,String> headers){
		boolean ret = false;
		if(content!=null &&headers!=null){
			String contentType = headers.get("Content-Type");
			if(contentType==null){
				contentType = headers.get("content-type");
			}
			String contentMd5 = headers.get("Content-MD5");
			if(contentMd5==null){
				contentMd5 = headers.get("content-md5");
			}
			String date = headers.get("Date");
			if(date==null){
				date = headers.get("date");
			}
			String auth = headers.get("Authorization");
			if(auth==null){
				auth = headers.get("authorization");
			}
			logger.info("auth header:content-type={},md5={},date={},authorization={}",contentType,contentMd5,date,auth);
			String md5 = WebHookUtil.getContentMd5(content);
			logger.info("auth header:local product MD5={},check={}",md5,(md5!=null&&md5.equals(contentMd5)));
			
			if(md5!=null&&md5.equals(contentMd5)){
				String authorization = WebHookUtil.getAuthorization( appid, appkey,contentMd5, contentType, date);
				logger.info("auth header:local authorization={},check={}",authorization,(auth!=null&&auth.equals(authorization)));
				if(auth!=null&&auth.equals(authorization)){
					ret = true;
				}
			}			
		}
		
		return ret;
	}
	
	public static String getAuthorization(String appid,String appkey,String contentMd5,String contentType,String date){

		String auth = appid +":"+sha(appkey,contentMd5,contentType,date);
		return auth;
	}
	
	public static String sha(String... data){
        String str = "";
        int n = data.length;
        for (int i=0;i<n;i++){
            str =str+data[i];
        }
        return DigestUtils.shaHex(str);
    }	
	
	public static String getContentMd5(String content){
		
		return Base64.encodeBase64String(DigestUtils.md5(content));
	}
	
	
	public static String getGMTTime(){
		Date d=new Date();
		DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z",Locale.US);		
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return format.format(d);
	}	
	
	public static Map<Object,Object> getHeaders(String appid,String appkey,String content,String contentType){
		Map<Object,Object> headers = new HashMap<Object,Object>();
		String contentMd5 = WebHookUtil.getContentMd5(content);
		String date = WebHookUtil.getGMTTime();
		
		headers.put("Content-Md5",contentMd5 );
		headers.put("Content-Type",contentType );
		headers.put("Date", date);
		headers.put("Authorization", WebHookUtil.getAuthorization(appid,appkey,contentMd5, contentType, date));
		headers.put("User-Agent", "kingdee yunzhijia webhook client-1.0");
		
		return headers;		
		
	}
	
	public static Map<String,Object> getCommonParams(String appid, String access_token){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("appid", appid);
		params.put("access_token", access_token);
		return params;
	}	
	
}
