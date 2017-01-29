package io.app;

import io.spring.helpers.configuration.Constants;
import io.spring.helpers.configuration.DefaultConfigurationUtil;

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
	} catch (final UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return application.sources(AppWeb.class);

    }

    public static void main(String[] args) throws UnknownHostException, MalformedObjectNameException,
	    NullPointerException {
	final SpringApplication app = new SpringApplication(AppWeb.class);
	DefaultConfigurationUtil.addConfiguration(app, Constants.CORE_CONF, Constants.WEB_CONF);
	final Environment env = app.run(args).getEnvironment();
	log.info("\n----------------------------------------------------------\n\t"
		+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:{}\n\t"
		+ "External: \thttp://{}:{}\n----------------------------------------------------------", env
		.getProperty("spring.application.name"),
		env.getProperty("server.port") == null ? "8080" : env.getProperty("server.port"), InetAddress
			.getLocalHost().getHostAddress(), env.getProperty("server.port"));
    }
}
