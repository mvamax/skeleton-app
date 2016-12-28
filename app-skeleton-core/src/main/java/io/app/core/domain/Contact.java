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
@Table(name="contact")
public class Contact {

	@Id
	@GeneratedValue(generator = "IdOrGenerated")
    @GenericGenerator(name = "IdOrGenerated", strategy = "io.app.core.generator.UseExistingOrGenerateSequenceGenerator", 
	parameters = { @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "person_id_seq"),
				   @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000")})
	private Long id ;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
