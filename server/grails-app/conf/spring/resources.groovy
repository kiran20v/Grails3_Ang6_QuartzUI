// Place your Spring DSL code here
import com.micro.MyJob

beans = {
	
	quartzJobMapFactory(org.springframework.beans.factory.config.MapFactoryBean) {
        sourceMap = [
                  "myJob":MyJob.class, 
                  "myJob2":MyJob.class 
                ] 
     }
}
