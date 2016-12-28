package io.app.core.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinDescriptorsUtils {

	private static final Logger log = LoggerFactory
			.getLogger(JoinDescriptorsUtils.class);

	//
	public static List<JoinDescriptor> buildJoins(List<String> list,
			Class<?> type) throws FieldException {
		List<JoinDescriptor> joinDescriptors = new ArrayList<JoinDescriptor>();
		if (list != null) {
			List<String> listFieldsLowerCase = list.stream()
					.map(f -> f.toLowerCase()).collect(Collectors.toList());
			List<String> fieldsOnClassLowerCase = Arrays
					.asList(type.getDeclaredFields()).stream()
					.map(f -> f.getName().toLowerCase())
					.collect(Collectors.toList());

			log.debug("available fields " + fieldsOnClassLowerCase);
			for (String fieldParam : listFieldsLowerCase) {
				if (fieldsOnClassLowerCase.contains(fieldParam)) {
					joinDescriptors.add(new JoinDescriptor(fieldParam,
							JoinType.LEFT));
				} else {
					throw new FieldException(
							"errors.validation.fieldException",
							fieldsOnClassLowerCase);
				}
			}
		}
		return joinDescriptors;
	}

}
