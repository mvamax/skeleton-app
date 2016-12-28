package io.app.external.query;

import io.app.external.domain.ExternalData;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

public class ExternalDataListQuery extends MappingSqlQuery<ExternalData> {

	
	private static String query ="select "
    		+ " t.id as id, "
    		+ " t.content  as content "
    		+ " from data t";
	
	
    public ExternalDataListQuery(DataSource ds) {
        super(ds,query);
        compile();
    }

    @Override
    protected ExternalData mapRow(ResultSet rs, int rowNumber) throws SQLException {
    	ExternalData externalData = new ExternalData();
    	externalData.setId(rs.getString("id"));
    	externalData.setContent(rs.getString("content"));
        return externalData;
    }

}