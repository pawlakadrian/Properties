package configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EnvironmentProperty {
    private static Logger logger = LoggerFactory.getLogger(EnvironmentProperty.class);

    private final String APP_ENV;

    public static EnvironmentProperty getInstance() {
        return EnvironmentPropertySingleton.INSTANCE;
    }

    private void initEnv() {
        if (!this.APP_ENV.isEmpty()) {
            loadAllEnvironmentPropertiesToSystem(this.APP_ENV);
        } else {
            assertThat(false, equalTo(true));
        }
    }

    private EnvironmentProperty() {
        this.APP_ENV = initAppEnv();
        this.initEnv();
    }

    private static String initAppEnv() {
        return PropertiesStore.ENVIRONMENT.isSpecified() ? PropertiesStore.ENVIRONMENT.getValue() : " ";
    }

    private void loadAllEnvironmentPropertiesToSystem(String app_env) {
        setSystemPropertiesFromPathUrl(app_env);
    }

    private static void setSystemPropertiesFromPathUrl(String dirName) {
        URL url = EnvironmentProperty.class.getClassLoader().getResource(dirName);
        if (url != null) {
            Properties environmentProperties = new Properties();

            try {
                Stream<Path> files = Files.walk(Paths.get(url.toURI()));


                try {
                    ((List) files.filter((x$0) -> {
                        return Files.isRegularFile(x$0, new LinkOption[0]);
                    }).collect(Collectors.toList())).forEach((path) -> {
                        try {
                            environmentProperties.load(new FileInputStream(path.toString()));
                        } catch (IOException var3) {
                            logger.error("error 1");

                        }

                    });
                } catch (Exception e) {
                    logger.error("error 2");

                } finally {
                    if (files != null) {
                        try {
                            files.close();
                        } catch (Throwable var13) {
                            logger.error("error 3");
                        }
                    } else {
                        files.close();
                    }
                }

            } catch (Exception r) {
                logger.error("error 4");

            }

            logger.debug("#### Loading property from uri {}", url.toString());
            environmentProperties.forEach((propertyName, propertyValue) -> {
                if (System.getProperty(propertyName.toString()) == null) {
                    System.setProperty(propertyName.toString(), propertyValue.toString());
                    logger.debug("****Loading environment property {} = {} ", propertyName.toString(), propertyValue.toString());
                }

            });
            logger.debug("#### Properties loaded from {} : {} ", dirName, environmentProperties.size());
        } else {
            logger.warn("No such property directory '{}' present in the resources ,make sure you are providing correct directory name.", dirName);
        }
    }

    private static class EnvironmentPropertySingleton {
        private static final EnvironmentProperty INSTANCE = new EnvironmentProperty();
    }
}
