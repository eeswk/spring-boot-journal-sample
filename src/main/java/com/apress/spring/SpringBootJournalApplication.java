package com.apress.spring;

import com.apress.spring.domain.Journal;
import com.apress.spring.redis.Producer;
import com.apress.spring.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.List;


@SpringBootApplication
public class SpringBootJournalApplication implements CommandLineRunner, ApplicationRunner{
    private static final Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);


    @Bean
    InitializingBean saveData(JournalRepository repo) {
        return () -> {
            repo.save(new Journal("스프링 부트 입문", "오늘부터 스프링 부트 공부시작하기", "01/01/2020"));
            repo.save(new Journal("간단한 스프링 부트 프로젝트", "스프링 부트 프로젝트 만듬", "01/02/2020"));
            repo.save(new Journal("스프링 부트 분석", "스프링 부트를 자세히 살펴봄", "02/01/2020"));
            repo.save(new Journal("스프링 부트 클라우드", "클라우드 파운드리를 응용한 스타일", "03/01/2020"));
        };
    }

    public static void main(String[] args) {
        //SpringApplication.run(SpringBootJournalApplication.class, args);
        /*
        SpringApplication app = new SpringApplication(SpringBootJournalApplication.class);
        // 더많은 기능을 추가
        app.run(args);
        */
        Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);

        new SpringApplicationBuilder(SpringBootJournalApplication.class)
                .listeners(new ApplicationListener<ApplicationEvent>() {
                    @Override
                    public void onApplicationEvent(ApplicationEvent applicationEvent) {
                        log.info("#### > " + applicationEvent.getClass().getCanonicalName());
                    }
                })
                .profiles("prod","cloud")

                .run(args);
    }

    @Bean
    String info(){
        return "그냥 간단한 문자열이다.";
    }

    @Autowired
    String info;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("## > ApplicationRunner 구현체");
        log.info("info 빈에 엑세스 " + info);
        args.getNonOptionArgs().forEach(file -> log.info(file));
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("## > CommandLineRunner 구현체");
        log.info("info 빈에 엑세스 " + info);
        for (String arg : args) {
            log.info(arg);
        }
    }


    @Bean

    CommandLineRunner myMethod() {
        return args -> {
            log.info("## > CommandLineRunner 람다1 구현체");
            log.info("info 빈에 엑세스 " + info);
            for (String arg : args) {
                log.info(arg);
            }
        };
    }

    @Bean
    CommandLineRunner myMethod2() {
        return args -> {
            log.info("## > CommandLineRunner 람다2 구현체");
            log.info("info 빈에 엑세스 " + info);
            for (String arg : args) {
                log.info(arg);
            }

            log.info(" > 서버 IP : " + serverIp);
            log.info(" > 애플리케이션명 : " + props.getName());
            log.info(" > 애플리케이션 정보 : " + props.getDescription());
        };
    }

    @Value("${topic}")
    String topic;

    @Bean
    CommandLineRunner sendMessage(Producer producer) {
        return args ->  {
            log.info(" > 레디스 : ");
            producer.sendTo(topic, "스프링 부트와 레디스 메시징 처리");
        };
    }

    @Value("${myapp.server-ip}")
    String serverIp;

    @Autowired
    MyAppProperites props;

    @Component
    @ConfigurationProperties(prefix = "myapp")
    public static class MyAppProperites {
        private String name;
        private String description;
        private String serverIp;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServerIp() {
            return serverIp;
        }

        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }
    }


    @Component
    static class MyComponent {
        private static final Logger log = LoggerFactory.getLogger(MyComponent.class);

        public MyComponent(ApplicationArguments args) {
            boolean enable = args.containsOption("enable");

            if (enable)
                log.info("## > enable 옵션을 줬음.");

            List<String> _args = args.getNonOptionArgs();
            log.info("## > 다른 인자....");
            if (!_args.isEmpty()) {
                _args.forEach(file -> log.info(file));
            }
        }

    }

}
