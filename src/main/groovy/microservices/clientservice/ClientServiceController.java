package microservices.clientservice;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;




@RestController
public class ClientServiceController {

	private final  List<Client> storage=new LinkedList<>();
	
	@Autowired
	private  ServiceRestClient serviceRestClient;
	

	@RequestMapping(value="/api/client",method=RequestMethod.POST)
    public void  addClient(@RequestBody Client client) throws JsonGenerationException, JsonMappingException, IOException {
		storage.add(client);
		serviceRestClient
		.forService("dupaaaaa")
		.post()
		.onUrl("/api/client")
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
