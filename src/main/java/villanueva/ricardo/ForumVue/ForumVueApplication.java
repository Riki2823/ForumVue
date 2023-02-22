package villanueva.ricardo.ForumVue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import villanueva.ricardo.ForumVue.interceptors.TokenInterceptor;

@SpringBootApplication
public class ForumVueApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ForumVueApplication.class, args);
	}
	@Autowired
	TokenInterceptor tokenInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor).addPathPatterns(("/getprofile"));
	}
}
