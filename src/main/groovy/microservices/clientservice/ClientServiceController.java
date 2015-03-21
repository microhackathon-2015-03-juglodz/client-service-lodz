package microservices.clientservice;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;




@RestController
public class ClientServiceController {

	private final  List<Client> storage=new LinkedList<>();
	
	@Autowired
	private  ServiceRestClient serviceRestClient;
	
	@Autowired 
	private AsyncRetryExecutor executor;

	@RequestMapping(value="/api/client",method=RequestMethod.POST)
    public void  addClient(@RequestBody Client client) throws JsonGenerationException, JsonMappingException, IOException {
		storage.add(client);
		serviceRestClient
		.forService("dupaaaaa")
		.retryUsing(executor.withMaxRetries(5))
		.post()
		.withCircuitBreaker(
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ClientDetails")),
				new MethodClosure(this, "unable to contcat reporting")
				
		)
		.onUrl("/api/clients")
		.body(new ObjectMapper().writeValueAsString(client))
		.withHeaders()
			.contentTypeJson()
		.andExecuteFor()
			.anObject()
			.ofType(String.class);
    }
	
	
	@RequestMapping(value="/api/client",method=RequestMethod.GET)
    public List<Client>  showClients() {
		return storage;
	}
}
