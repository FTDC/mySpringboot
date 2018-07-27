package com.ftdc.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:rmi/rmi-member.xml"})
public class RmiClientConfig {
	
//	@Autowired
//    public Config config;
//
//	//mq属性
//	@Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(config.getAddress());
//        connectionFactory.setUsername(config.getUsername());
//        connectionFactory.setPassword(config.getPassword());
//        connectionFactory.setVirtualHost("/");
//        return connectionFactory;
//    }
//
//	//mq模板
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
//        return template;
//    }
}
