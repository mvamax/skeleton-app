package io.app.rest;

import io.app.core.domain.PersonAllView;
import io.app.core.join.FieldException;
import io.app.core.service.PersonAllService;
import io.app.web.util.PageableTranslators;
import io.app.web.util.SortableUri;
import io.swagger.annotations.ApiParam;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonAllController {

    private final static String persons = "persons";

    private static final Logger log = LoggerFactory.getLogger(PersonAllController.class);

    private final PersonAllService personAllService;

    public PersonAllController(PersonAllService personAllService) {
	this.personAllService = personAllService;
    }

    @GetMapping(value = persons, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends PersonAllView>> searchPersonsAll(
	    @RequestParam(name = "type", required = false) Long type,
	    @ApiParam @SortableUri(allowableValues = { "toto" }, pageable = PageableTranslators.CYCLISTS) Pageable pageable)
		    throws URISyntaxException, FieldException, InterruptedException, ExecutionException {

	log.info(" ------------------------------Recherche de Person---------------------------------------");
	log.info("Paramètres :");
	log.info("------------------------------------------------------------------------------------------");
	System.out.println(pageable);
	// Page<Person> page =
	final List<? extends PersonAllView> list = personAllService.findAllUnionWithJpa(type);
	// HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
	// page,
	// ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().toString());
	// HttpStatus httpStatus= PaginationUtil.generateHttpStatus(page);
	return new ResponseEntity<>(list, null, HttpStatus.OK);

    }

}