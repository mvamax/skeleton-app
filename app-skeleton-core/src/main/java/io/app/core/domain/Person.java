package io.app.core.domain;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@Entity
@Table(name ="person")
public class Person {
	
	@Id
	@GeneratedValue(generator = "IdOrGenerated")
    @GenericGenerator(name = "IdOrGenerated", strategy = "io.app.core.generator.UseExistingOrGenerateSequenceGenerator", 
	parameters = { @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "person_id_seq"),
				   @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000")})
	private Long id ;

	@Column(name = "firstname", nullable = false)
	private String firstname ;
	
	@Column(name = "lastname", nullable = false)
	private String lastname ;
	
	@OneToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "contact_id", referencedColumnName = "id", nullable=true)
	private Contact contact;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private Set<Address> addresses = new HashSet<Address>();
	
	@Column(name = "birthday", nullable = true)
	private ZonedDateTime birthday ;

	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public Contact getContact() {
		return contact;
	}



	public void setContact(Contact contact) {
		this.contact = contact;
	}



	public Set<Address> getAddresses() {
		return addresses;
	}



	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}



	public ZonedDateTime getBirthday() {
		return birthday;
	}



	public void setBirthday(ZonedDateTime birthday) {
		this.birthday = birthday;
	}



	@Override
	public String toString() {
		return "Person [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", contact=" + contact + ", addressList="
				+ addresses + ", birthday=" + birthday + "]";
	}
	
	
}
