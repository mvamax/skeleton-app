package io.app.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Provenance {

    protected String nom;

    public static <T extends Provenance> T as(Provenance p, Class<T> clazz) {
	if (clazz.isInstance(p)) {
	    return clazz.cast(p);
	}
	return null;
    }

    public static <T extends Provenance> List<T> filter(List<Provenance> pList, Class<T> clazz) {
	if (pList != null) {
	    return pList.stream().filter(p -> clazz.isInstance(p)).map(p -> Provenance.as(p, clazz))
		    .collect(Collectors.toList());
	}
	return new ArrayList<T>();
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    @Override
    public String toString() {
	return "Provenance " + this.getClass() + "[nom=" + nom + "]";
    }

}
