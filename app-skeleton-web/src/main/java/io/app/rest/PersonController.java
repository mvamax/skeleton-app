package io.app.rest;

import io.app.core.domain.Person;
import io.app.core.join.FieldException;
import io.app.core.service.PersonService;
import io.app.web.util.PaginationUtil;
import io.app.web.util.SortableUri;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class PersonController {

	private final static String cyclists = "cyclists";
	
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);
	
	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@SortableUri(allowableValues={"toto"})
	@GetMapping(value = cyclists, produces = MediaType.APPLICATION_JSON_VALUE)
//	@Validations(
//			@Validation( bean="", params={a,b})
//	)
	public  ResponseEntity<List<Person>> searchPersons (
			@RequestParam(name = "search", required = false) String search ,
//			@RequestParam(name = "fields", required = false) List<String> fields ,
			@RequestParam(name = "boolean", required = false, defaultValue="false") Boolean contact ,
			@RequestParam  Pageable pageable) throws URISyntaxException, FieldException {
				
			log.info(" ------------------------------Recherche de Person---------------------------------------");
			log.info("Paramètres :");
			log.info(" * search : " + search);
			log.info(" * boolean : " + contact);
			log.info("------------------------------------------------------------------------------------------");
			Page<Person> page = personService.searchByLastnameAndFirstnameByContactNotNull(search, contact, pageable);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
					page, ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().toString());
			HttpStatus httpStatus= PaginationUtil.generateHttpStatus(page);
			return new ResponseEntity<>(page.getContent(), headers, httpStatus);	

	}
	
	@GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> fetch(
			@RequestParam(name = "search", required = false) String search ,
			Pageable pageable,
			HttpServletResponse response) {
		String res = personService.fetch(search,pageable,new  StringBuilder());
		return ResponseEntity.ok().body(res);
	}
	
	
	@GetMapping(value = "/fetchH", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> fetchH() {
		String res = personService.fetchHibernate(new  StringBuilder());
		return ResponseEntity.ok().body(res);
	}

	
}