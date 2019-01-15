// Place your Spring DSL code here
import com.micro.MyJob

beans = {
	
	quartzJobMapFactory(org.springframework.beans.factory.config.MapFactoryBean) {
        sourceMap = [
                  "job1":MyJob.class, 
                  "job2":MyJob.class 
                ] 
     }
}
