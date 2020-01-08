package br.com.hapvida.ibmstorageservice;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
 
@SpringBootApplication
public class IbmstorageserviceApplication extends SpringBootServletInitializer{

//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(IbmstorageserviceApplication.class);
//    }
//	
	public static void main(String[] args) {
		//SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.cloud.ibm.com/identity/token";
		SpringApplication.run(IbmstorageserviceApplication.class, args);
	}

}