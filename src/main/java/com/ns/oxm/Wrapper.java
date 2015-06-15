package com.ns.oxm;

import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import com.google.common.collect.Lists;

public class Wrapper<T> {

    private List<T> items;

    public Wrapper() {
        items = Lists.newArrayList();
    }

    public Wrapper(List<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax = true)
    public List<T> getItems() {
        return items;
    }

}
