package spring_boot;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Hello {
	
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用ID"
	private String myAppId="appId";
	//注意，此处填写的是：开发者配置->webhook 部分对应的 "第三方应用Secret"
	private String myAppSecret="41db945e-c880-4737-a4ee-4c7b63a6d60e";

	@RequestMapping("/webhook")
	public String home(@RequestHeader Map<String, String> header, @RequestParam String eid,@RequestParam String eventType,
	@RequestParam String eventId) {
		String contentBody = "eid=" + eid+ "&eventType=" + eventType + "&eventId=" + eventId;
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

	public static void main(String[] args) {
		SpringApplication.run(Hello.class, args);
	}
}
