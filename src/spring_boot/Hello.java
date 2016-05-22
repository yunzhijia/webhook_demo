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
	
	private String myAppId="appId";
	private String myAppSecret="appSecret";

	@RequestMapping("/webhook")
	public String home(@RequestHeader Map<String, String> header, @RequestBody String contentBody) {
		if(WebHookUtil.checkAuth(myAppId, myAppSecret, contentBody, header)){
			System.out.println("接收到一个合法推送，内容为： "+contentBody);
			//处理推送的逻辑写在这里
			//.....
		}else{
			System.out.println("接收到一个非法推送");
		}
		
		return "ok";
	}

	public static void main(String[] args) {
		SpringApplication.run(Hello.class, args);
	}
}
