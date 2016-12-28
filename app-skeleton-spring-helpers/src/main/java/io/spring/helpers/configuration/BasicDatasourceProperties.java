package io.spring.helpers.configuration;

/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Base class for configuration of a data source.
 *
 * @author Dave Syer
 * @author Maciej Walkowiak
 * @author Stephane Nicoll
 * @author Benedikt Ritter
 * @author Eddú Meléndez
 * @since 1.5.0
 */
public class BasicDatasourceProperties implements BeanClassLoaderAware,
		EnvironmentAware, InitializingBean {

	private ClassLoader classLoader;

	private Environment environment;
	

	/**
	 * Name of the datasource.
	 */
	private String name = "testdb";

	/**
	 * Generate a random datasource name.
	 */
	private boolean generateUniqueName;

	/**
	 * Fully qualified name of the connection pool implementation to use. By
	 * default, it is auto-detected from the classpath.
	 */
	private Class<? extends DataSource> type;

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL
	 * by default.
	 */
	private String driverClassName;

	/**
	 * JDBC url of the database.
	 */
	private String url;

	/**
	 * Login user of the database.
	 */
	private String username;

	/**
	 * Login password of the database.
	 */
	private String password;

	private EmbeddedDatabaseConnection embeddedDatabaseConnection = EmbeddedDatabaseConnection.NONE;

	private String uniqueName;

	/**
	 * Populate the database using 'data.sql'.
	 */
	private boolean initialize = true;

	private List<String> schema;

	private String platform = "all";

	private List<String> data;

	private String separator = ";";

	public List<String> getSchema() {
		return this.schema;
	}

	public void setSchema(List<String> schema) {
		this.schema = schema;
	}

	public String getSeparator() {
		return this.separator;
	}

	private boolean continueOnError = false;

	public boolean isContinueOnError() {
		return this.continueOnError;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public List<String> getData() {
		return this.data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public void afterPropertiesSet() throws Exception {
		this.embeddedDatabaseConnection = EmbeddedDatabaseConnection
				.get(this.classLoader);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGenerateUniqueName() {
		return this.generateUniqueName;
	}

	public void setGenerateUniqueName(boolean generateUniqueName) {
		this.generateUniqueName = generateUniqueName;
	}

	public Class<? extends DataSource> getType() {
		return this.type;
	}

	public void setType(Class<? extends DataSource> type) {
		this.type = type;
	}

	/**
	 * Return the configured driver or {@code null} if none was configured.
	 * 
	 * @return the configured driver
	 * @see #determineDriverClassName()
	 */
	public String getDriverClassName() {
		return this.driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Determine the driver to use based on this configuration and the
	 * environment.
	 * 
	 * @return the driver to use
	 * @since 1.4.0
	 */
	public String determineDriverClassName() {
		if (StringUtils.hasText(this.driverClassName)) {
			Assert.state(driverClassIsLoadable(), "Cannot load driver class: "
					+ this.driverClassName);
			return this.driverClassName;
		}
		String driverClassName = null;

		if (StringUtils.hasText(this.url)) {
			driverClassName = DatabaseDriver.fromJdbcUrl(this.url)
					.getDriverClassName();
		}

		if (!StringUtils.hasText(driverClassName)) {
			driverClassName = this.embeddedDatabaseConnection
					.getDriverClassName();
		}

		if (!StringUtils.hasText(driverClassName)) {
			throw new DataSourceBeanCreationException(
					this.embeddedDatabaseConnection, this.environment,
					"driver class");
		}
		return driverClassName;
	}

	private boolean driverClassIsLoadable() {
		try {
			ClassUtils.forName(this.driverClassName, null);
			return true;
		} catch (UnsupportedClassVersionError ex) {
			// Driver library has been compiled with a later JDK, propagate
			// error
			throw ex;
		} catch (Throwable ex) {
			return false;
		}
	}

	/**
	 * Return the configured url or {@code null} if none was configured.
	 * 
	 * @return the configured url
	 * @see #determineUrl()
	 */
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Determine the url to use based on this configuration and the environment.
	 * 
	 * @return the url to use
	 * @since 1.4.0
	 */
	public String determineUrl() {
		if (StringUtils.hasText(this.url)) {
			return this.url;
		}
		String url = this.embeddedDatabaseConnection
				.getUrl(determineDatabaseName());
		if (!StringUtils.hasText(url)) {
			throw new DataSourceBeanCreationException(
					this.embeddedDatabaseConnection, this.environment, "url");
		}
		return url;
	}

	private String determineDatabaseName() {
		if (this.generateUniqueName) {
			if (this.uniqueName == null) {
				this.uniqueName = UUID.randomUUID().toString();
			}
			return this.uniqueName;
		}
		return this.name;
	}

	/**
	 * Return the configured username or {@code null} if none was configured.
	 * 
	 * @return the configured username
	 * @see #determineUsername()
	 */
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Determine the username to use based on this configuration and the
	 * environment.
	 * 
	 * @return the username to use
	 * @since 1.4.0
	 */
	public String determineUsername() {
		if (StringUtils.hasText(this.username)) {
			return this.username;
		}
		if (EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName())) {
			return "sa";
		}
		return null;
	}

	/**
	 * Return the configured password or {@code null} if none was configured.
	 * 
	 * @return the configured password
	 * @see #determinePassword()
	 */
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Determine the password to use based on this configuration and the
	 * environment.
	 * 
	 * @return the password to use
	 * @since 1.4.0
	 */
	public String determinePassword() {
		if (StringUtils.hasText(this.password)) {
			return this.password;
		}
		if (EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName())) {
			return "";
		}
		return null;
	}

	public ClassLoader getClassLoader() {
		return this.classLoader;
	}

	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
	}

	public boolean isInitialize() {
		return this.initialize;
	}

	/**
	 * Initialize a {@link DataSourceBuilder} with the state of this instance.
	 * 
	 * @return a {@link DataSourceBuilder} initialized with the customizations
	 *         defined on this instance
	 */
	public DataSourceBuilder initializeDataSourceBuilder() {
		return DataSourceBuilder.create(getClassLoader()).type(getType())
				.driverClassName(determineDriverClassName())
				.url(determineUrl()).username(determineUsername())
				.password(determinePassword());
	}

	static class DataSourceBeanCreationException extends BeanCreationException {

		DataSourceBeanCreationException(EmbeddedDatabaseConnection connection,
				Environment environment, String property) {
			super(getMessage(connection, environment, property));
		}

		private static String getMessage(EmbeddedDatabaseConnection connection,
				Environment environment, String property) {
			StringBuilder message = new StringBuilder();
			message.append("Cannot determine embedded database " + property
					+ " for database type " + connection + ". ");
			message.append("If you want an embedded database please put a supported "
					+ "one on the classpath. ");
			message.append("If you have database settings to be loaded from a "
					+ "particular profile you may need to active it");
			if (environment != null) {
				String[] profiles = environment.getActiveProfiles();
				if (ObjectUtils.isEmpty(profiles)) {
					message.append(" (no profiles are currently active)");
				} else {
					message.append(" (the profiles \""
							+ StringUtils
									.arrayToCommaDelimitedString(environment
											.getActiveProfiles())
							+ "\" are currently active)");

				}
			}
			message.append(".");
			return message.toString();
		}

	}

}
