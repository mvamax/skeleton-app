package io.app.external.service.impl;

import io.app.external.domain.ExternalData;
import io.app.external.query.ExternalDataListQuery;
import io.app.external.service.ExternalDataService;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExternalDataServiceImpl implements ExternalDataService {

	private ExternalDataListQuery  externalDataListQuery;
	
	@Autowired
	public ExternalDataServiceImpl(@Qualifier("externalDatasource") DataSource externalDatasource) {
		super();
		this.externalDataListQuery = new ExternalDataListQuery(externalDatasource);
	}
	
	public List<ExternalData> listExternalData(){
		return externalDataListQuery.execute();
	}
	
	
}
