package io.app.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(generator = "IdOrGenerated")
	@GenericGenerator(name = "IdOrGenerated", strategy = "io.app.core.generator.UseExistingOrGenerateSequenceGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "adresse_id_seq"),
			@Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000") })
	private Long id;

	@Column(name = "city")
	private String city;

	@Column(name = "content")
	private String content;

}
