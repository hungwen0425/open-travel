package com.opentravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class OpenTravelApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(OpenTravelApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  歐亞旅遊資訊管理系統啟動成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}