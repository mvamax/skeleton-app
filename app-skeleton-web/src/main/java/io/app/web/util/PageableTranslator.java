package io.app.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageableTranslator implements Translate<Sort> {

    private final Map<String, String> m;

    private final Sort defaultSort;

    public PageableTranslator(Map<String, String> m, Sort defaultSort) {
	super();
	this.m = m;
	this.defaultSort = defaultSort;
    }

    public Set<String> getTriParams() {
	return m.keySet();
    }

    @Override
    public Sort translate(Sort s) {
	final List<Order> orders = new ArrayList<Order>();

	s.forEach(order -> {
	    System.out.println("property" + order.getProperty());
	    final String keyTranslation = this.m.get(order.getProperty());
	    System.out.println("property transalation" + keyTranslation);
	    if (keyTranslation == null) {
		System.out.println("exception");
		throw new RuntimeException("sortable parmi:" + this.m.keySet());
	    }
	    orders.add(new Order(order.getDirection(), keyTranslation));
	});

	return new Sort(orders);
    }

    public Sort getDefaultSort() {
	return this.defaultSort;
    }
}
