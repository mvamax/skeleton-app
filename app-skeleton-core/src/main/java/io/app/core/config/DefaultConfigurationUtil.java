package io.app.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class DefaultConfigurationUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultConfigurationUtil.class);

	private static final String SPRING_CONFIG_NAME = "spring.config.name";
	private static final String SPRING_PROFILES_DEFAULT = "spring.profiles.default";
	private static final String SPRING_BATCH_JOB_ENABLED = "spring.batch.job.enabled";
	
	/****
	 * Spécifie la liste des prefix de properties à charger par spring boot
	 * </br> </br> l'ordre des champs de confNames a une importance</br> example
	 * : si confNames = "core","web" alors spring tentera de charger les
	 * fichiers du classpath dans l'ordre:
	 * <ul>
	 * <li>core.properties</li>
	 * <li>web.properties</li>
	 * <li>core-{profile}.properties</li>
	 * <li>web-{profile}.properties</li>
	 * </ul>
	 * 
	 * @param app
	 *            l'instance SpringApplication qui va être lancée via run
	 * @param confNames
	 *            suite de string spécifiant le préfix des properties à charger.
	 */
	public static void addConfiguration(SpringApplication app, String... confNames) {

		readExternalProperties();
		
		Properties props = new Properties();
		log.info("{} <- {}",SPRING_CONFIG_NAME,Arrays.asList(confNames));
		props.put(SPRING_CONFIG_NAME, confNames);
		log.info("{} <- dev",SPRING_PROFILES_DEFAULT);
		props.put(SPRING_PROFILES_DEFAULT, "dev");
		log.info("{} <- false",SPRING_BATCH_JOB_ENABLED);
		props.put(SPRING_BATCH_JOB_ENABLED,false);
		app.setDefaultProperties(props);
    }
	
	private static void readExternalProperties(){
		try {
			//On lit le properties externe s'il existe il est donné par la prod via la variable d'environnement PRODUCTION_PROPERTIES_PATH
			String propertiesPath=System.getProperty(Constants.PRODUCTION_PROPERTIES_PATH);
			if(propertiesPath!=null){
				log.info("recherche du properties de production -> {},",propertiesPath);
				Properties props =  new Properties();
				props.load(new FileInputStream(propertiesPath+"application.properties"));
				Enumeration<Object>  e = props.keys();
				while (e.hasMoreElements()) {
				      String key = (String) e.nextElement();
				      log.debug(key + " -- " + props.getProperty(key));
				      System.setProperty(key,props.getProperty(key));
				}
			}else{
				log.info("pas de properties de production renseigné");
			}
		} catch (IOException e) {
			log.info("erreur lors de la lecture du properties externe {}", e.getMessage());
			e.printStackTrace();
		}
	}

}
