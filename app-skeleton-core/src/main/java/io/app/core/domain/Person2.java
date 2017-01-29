package io.app.core.domain;

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
@Table(name = "person2")
public class Person2 {

    @Id
    @GeneratedValue(generator = "IdOrGenerated")
    @GenericGenerator(name = "IdOrGenerated", strategy = "io.app.core.generator.UseExistingOrGenerateSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "person2_id_seq"),
	    @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000") })
    private Long id;

    @Column(name = "firstname2", nullable = false)
    private String firstname2;

    @Column(name = "lastname2", nullable = false)
    private String lastname2;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", referencedColumnName = "id", nullable = true)
    private Contact contact;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private final Set<Address> addresses = new HashSet<Address>();

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFirstname2() {
	return firstname2;
    }

    public void setFirstname2(String firstname2) {
	this.firstname2 = firstname2;
    }

    public String getLastname2() {
	return lastname2;
    }

    public void setLastname2(String lastname2) {
	this.lastname2 = lastname2;
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

}
