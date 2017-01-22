package io.app.rest;

import io.app.core.domain.Person;
import io.app.core.join.FieldException;
import io.app.core.service.PersonService;
import io.app.web.param.Param;
import io.app.web.param.Rule1ParamValidator;
import io.app.web.response.PageableResponse;
import io.app.web.util.PageableTranslators;
import io.app.web.util.SortableUri;
import io.swagger.annotations.ApiParam;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {

    private final static String cyclists = "cyclists";

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;
    private final PageableResponse<Person> pageableResponse;

    public PersonController(PersonService personService, PageableResponse<Person> pageableResponse) {
	this.personService = personService;
	this.pageableResponse = pageableResponse;
    }

    @SortableUri(allowableValues = { "toto" }, pageable = PageableTranslators.CYCLISTS)
    @GetMapping(value = cyclists, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> searchPersons(
	    @RequestParam(name = "search", required = false) String search,
	    @RequestParam(name = "boolean", required = false, defaultValue = "false") Boolean contact,
	    @ApiParam @SortableUri(allowableValues = { "toto" }, pageable = PageableTranslators.CYCLISTS) Pageable pageable)
	    throws URISyntaxException, FieldException, InterruptedException, ExecutionException {

	log.info(" ------------------------------Recherche de Person---------------------------------------");
	log.info("Paramètres :");
	log.info(" * search : " + search);
	log.info(" * boolean : " + contact);
	log.info("------------------------------------------------------------------------------------------");
	System.out.println(pageable);
	// final Pageable newPageable =
	// PageableTranslators.CYCLISTS.translate(pageable);
	pageableResponse.setParams(new Param(Rule1ParamValidator.class, search));
	pageableResponse.setResult(() -> personService.searchByLastnameAndFirstnameByContactNotNull(search, contact,
		pageable));
	return pageableResponse.getResponse();

	// Page<Person> page =
	// personService.searchByLastnameAndFirstnameByContactNotNull(search,
	// contact, pageable);
	// HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
	// page,
	// ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().toString());
	// HttpStatus httpStatus= PaginationUtil.generateHttpStatus(page);
	// return new ResponseEntity<>(page.getContent(), headers, httpStatus);

    }

    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> fetch(@RequestParam(name = "search", required = false) String search,
	    Pageable pageable, HttpServletResponse response) {
	final String res = personService.fetch(search, pageable, new StringBuilder());
	return ResponseEntity.ok().body(res);
    }

    @GetMapping(value = "/fetchH", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> fetchH() {
	final String res = personService.fetchHibernate(new StringBuilder());
	return ResponseEntity.ok().body(res);
    }

}