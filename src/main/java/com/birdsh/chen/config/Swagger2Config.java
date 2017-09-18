package com.birdsh.chen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: Swagger2Config.java 
 * @Description:Swagger的配置类
 * @author chenhuihui
 * @time 2017年9月18日上午10:22:59
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class Swagger2Config{

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.birdsh.chen")).paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("个人博客统一管理平台").description("个人博客统一管理平台").termsOfServiceUrl("http://www.birdsh.com")
				.version("1.0").build();
	}

}
