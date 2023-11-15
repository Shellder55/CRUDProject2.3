package crud;

import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Application.class.getName());
		try {
			SpringApplication.run(Application.class, args);
			logger.info("Сервер успешно запущен");
		} catch (Exception exception){
			exception.printStackTrace();
			logger.error("Сервер не запустился");
		}
	}

}
