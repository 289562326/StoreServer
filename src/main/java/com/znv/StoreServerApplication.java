package com.znv;

import com.znv.framework.component.ApplicationContextHelper;
import com.znv.peim.test.RedisCapability;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan(basePackages = { "com.znv" ,"com.znv.peim"})
@EnableAutoConfiguration()
public class StoreServerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(StoreServerApplication.class, args);
		RedisCapability test = (RedisCapability) ApplicationContextHelper.getBean(RedisCapability.class);
		test.init();
		test.start();

//			File file = new File("D:\\03-znv-peim\\svn_project\\PEIM\\trunk\\V1.30\\src\\ApplicationServer\\icap\\icap.lib\\NvDataAccess4j.xml");
//			DatabaseManager.initDataBase();
	}


}
