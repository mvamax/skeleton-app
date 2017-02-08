package io.app.core.test;

import io.app.core.domain.Provenance;
import io.app.core.domain.ProvenanceA;
import io.app.core.domain.ProvenanceB;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(value = JUnit4.class)
public class ProvenanceTest {

    @Test
    public void test() {
	System.out.println("test");
	final Provenance p1 = new ProvenanceA();
	final Provenance p2 = new ProvenanceB();
	p1.setNom("a");
	p2.setNom("b");
	final ProvenanceA a = Provenance.as(p1, ProvenanceA.class);
	System.out.println(a);
	System.out.println(Provenance.filter(Arrays.asList(p1, p2), ProvenanceB.class));
    }
}
