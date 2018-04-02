package com.viewfunction.messageExchangeService;

import com.viewfunction.messageEngine.exchange.restful.MessageExchangeService;
import com.viewfunction.messageExchangeService.util.ApplicationLauncherUtil;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ServletComponentScan(basePackages = { "com.viewfunction.messageEngine"})
@ComponentScan(basePackages = { "com.viewfunction.messageEngine"})
@EnableDiscoveryClient
public class MessageExchangeServiceApplication {

    @Autowired
    private Bus cxfBus;

    @Autowired
    private MessageExchangeService messageExchangeService;

    @Bean
    public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
        return new JacksonJaxbJsonProvider();
    }

    @Bean
    public JAXBElementProvider jaxbElementProvider() {
        return new JAXBElementProvider();
    }

    @Bean
    public Server cxfRestFulServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(cxfBus);
        endpoint.setAddress("/messageExchange");
        endpoint.setServiceBeans(Arrays.asList(messageExchangeService));
        endpoint.setProviders(Arrays.asList(jacksonJaxbJsonProvider(), jaxbElementProvider()));
        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }

	public static void main(String[] args) {
		SpringApplication.run(MessageExchangeServiceApplication.class, args);
		ApplicationLauncherUtil.printApplicationConsoleBanner();
	}
}
