package io.app.web.util;

import java.util.HashMap;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

public enum PageableTranslators {

    DEFAULT(), CYCLISTS("toto", "toto.table", "titi", "titi.table");

    private PageableTranslator translator;

    PageableTranslators(String... keys) {
	final HashMap<String, String> map = Maps.newHashMap();
	Assert.isTrue(keys.length % 2 == 0, "translator initialisation must be even");
	for (int i = 0; i < keys.length; i = i + 2) {
	    map.put(keys[i], keys[i + 1]);
	}
	this.translator = new PageableTranslator(map, null);
    }

    public Sort translate(Sort s) {
	return this.translator.translate(s);
    }

    public Set<String> getTriAllowableValues() {
	return this.translator.getTriParams();
    }

    public Sort getDefaultSort() {
	return this.translator.getDefaultSort();
    }

}
