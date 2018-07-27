package com.ftdc.rmi;

import com.ftdc.rmi.member.iface.MemberInterface;
import com.ftdc.rmi.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RmiServerConfig {

	@Value("${rmi.port}")
	private int port;

	@Autowired
	private MemberService memberService;

	@Bean
	public RmiServiceExporter memberServiceExporter() {
		RmiServiceExporter exporter = new RmiServiceExporter();
		exporter.setServiceInterface(MemberInterface.class);
		exporter.setService(memberService);
		exporter.setServiceName("member/member");
		exporter.setRegistryPort(port);

		return exporter;
	}
	
}
