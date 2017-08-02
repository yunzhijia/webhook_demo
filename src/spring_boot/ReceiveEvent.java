package spring_boot;

/**
 * erp产品线用来接收webhook回调示例
 */
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ReceiveEvent {
	//6786133, 436942097,8031c3cb-c08e-4b1d-bc8f-53b18da7a401  
	//2704254,926658455,2ea077e0-15c9-49b9-8dba-95ef23a720b0
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用ID"
	private String myAppId="926658455"; 
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用Secret"
	private String myAppSecret="2ea077e0-15c9-49b9-8dba-95ef23a720b0";
	
	private String myAppIdKdy="338769033"; 
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用Secret"
	private String myAppSecretKdy="066fa1f1-477c-4bf5-9247-3665c9e581a0";

	private String zlzId=""; 
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用Secret"
	private String zlzSecret="";
	
	
	@RequestMapping("/webhookEvent")
	public String home(@RequestHeader Map<String, String> header, @RequestParam(required=false) String eid,@RequestParam String eventType,
	@RequestParam String eventId,@RequestParam String createTime) {
		String contentBody = "eid=" + eid+ "&eventType=" + eventType + "&eventId=" + eventId+"&createTime="+createTime;
		System.out.println(contentBody);
		Map<String,String> paramsMap = new TreeMap<String,String>();
		paramsMap.put("eid", eid);
		paramsMap.put("eventType", eventType);
		paramsMap.put("eventId", eventId);
		paramsMap.put("createTime", createTime);
		contentBody = mapToString(paramsMap);
		if(WebHookUtil.checkAuth(myAppId, myAppSecret, contentBody, header)){
			System.out.println("接收到一个合法推送，内容为： "+contentBody);
			//处理推送的逻辑写在这里
			//.....
		}else{
			System.out.println("接收到一个非法推送");
			return "not ok";
		}
		
		return "ok";
	}
	
	@RequestMapping("/webhookEventKdy")
	public String home(@RequestHeader Map<String, String> header,@RequestParam String eventType,
	@RequestParam String eventId,@RequestParam String createTime) {
		String contentBody = "eventType=" + eventType + "&eventId=" + eventId+"&createTime="+createTime;
		System.out.println("kdy:"+contentBody);
		Map<String,String> paramsMap = new TreeMap<String,String>();
		paramsMap.put("eventType", eventType);
		paramsMap.put("eventId", eventId);
		paramsMap.put("createTime", createTime);
		contentBody = mapToString(paramsMap);
		if(WebHookUtil.checkAuth(myAppIdKdy, myAppSecretKdy, contentBody, header)){
			System.out.println("Kdy接收到一个合法推送，内容为 ： "+contentBody);
			//处理推送的逻辑写在这里，最好启一个线程，然后直接走下边的返回。
			//.....
		}else{
			System.out.println("Kdy接收到一个非法推送");
			return "not ok";
		}
		
		return "ok";
	}
	
	@RequestMapping("/webhookEventZlz")
	public String zlz(@RequestHeader Map<String, String> header, @RequestParam(required=false) String eid,@RequestParam String eventType,
	@RequestParam String eventId,@RequestParam String oldPhone,@RequestParam String newPhone,@RequestParam String createTime) {
		String contentBody = "eid=" + eid+ "&eventType=" + eventType + "&eventId=" + eventId+"&createTime="+createTime;
		System.out.println(contentBody);
		Map<String,String> paramsMap = new TreeMap<String,String>();
		paramsMap.put("eid", eid);
		paramsMap.put("eventType", eventType);
		paramsMap.put("eventId", eventId);
		paramsMap.put("oldPhone", oldPhone);
		paramsMap.put("newPhone", newPhone);
		paramsMap.put("createTime", createTime);
		contentBody = mapToString(paramsMap);
		if(WebHookUtil.checkAuth(zlzId, zlzSecret, contentBody, header)){
			System.out.println("接收到一个合法推送，内容为： "+contentBody);
			//处理推送的逻辑写在这里，最好启一个线程，然后直接走下边的返回。
			//.....
		}else{
			System.out.println("接收到一个非法推送");
			return "not ok";
		}
		
		return "ok";
	}
	
	// 按key字段顺序排序，组装k1=v1&k2=v2形式
	private String mapToString(Map<String, String> map) {
	    StringBuilder sb = new StringBuilder();
	    Set<String> keys = map.keySet();
	    for (String key : keys) {
	        sb.append(key).append("=").append(map.get(key)).append("&");
	    }
	    if (sb.length() > 0) {
	        return sb.substring(0, sb.length() - 1);
	    } else {
	        return sb.toString();
	    }
	}

	public static void main(String[] args) {
		SpringApplication.run(ReceiveEvent.class, args);
	}
}
