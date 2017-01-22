package io.app.web.response;

import io.app.web.param.Param;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PageableResponse<T> {

    ResponseEntity<List<T>> getResponse() throws InterruptedException, ExecutionException, URISyntaxException;

    void setParams(Param... params);

    void setPageableTranslator();

    void setResult(Supplier<Page<T>> result);

}
