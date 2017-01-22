package io.app.web.response;

import io.app.web.param.Param;
import io.app.web.param.ParamValidatorExecutor;
import io.app.web.util.PaginationUtil;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class SimplePageableResponse<T> implements PageableResponse<T>{

    ParamValidatorExecutor executor;

    private List<Param> params;
    private Supplier<Page<T>> result;

    public SimplePageableResponse(ParamValidatorExecutor executor) {
	this.executor=executor;
    }

    @Override
    public ResponseEntity<List<T>> getResponse() throws InterruptedException, ExecutionException, URISyntaxException {
	if(params!=null){
	    executor.validate(params);
	}
	final Page<T> page = result.get();
	final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
		page, ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().toString());
	final HttpStatus httpStatus= PaginationUtil.generateHttpStatus(page);
	return new ResponseEntity<>(page.getContent(), headers, httpStatus);
    }

    @Override
    public void setParams(Param... params) {
	this.params = Arrays.asList(params);
    }

    @Override
    public void setResult(Supplier<Page<T>> result) {
	this.result = result;
    }

    @Override
    public void setPageableTranslator() {
	// TODO Auto-generated method stub

    }




}
