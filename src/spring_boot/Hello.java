package spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Hello {

  @RequestMapping("/webhook")
  public String home(String a,String b) {
	  System.out.println("a is:"+a);
	  System.out.println("b is: "+b);
	  return "Hello";
  }

  public static void main(String[] args) {
    SpringApplication.run(Hello.class, args);
  }
}
