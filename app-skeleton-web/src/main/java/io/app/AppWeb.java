package io.app;

import io.app.core.config.Constants;
import io.app.core.config.DefaultConfigurationUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.management.MalformedObjectNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class AppWeb extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(AppWeb.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		DefaultConfigurationUtil.addConfiguration(application.application(), Constants.CORE_CONF, Constants.WEB_CONF);
		try {
			log.info(InetAddress.getLocalHost().getHostAddress());
			log.info(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return application.sources(AppWeb.class);
		
	}

	public static void main(String[] args) throws UnknownHostException, MalformedObjectNameException, NullPointerException {
		SpringApplication app = new SpringApplication(AppWeb.class);
		//TODO recupérer une variable d'environnement pour valoriser le chemin vers le properties
		DefaultConfigurationUtil.addConfiguration(app, Constants.CORE_CONF, Constants.WEB_CONF);
		Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:{}\n\t" +
                "External: \thttp://{}:{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));
	}
}
