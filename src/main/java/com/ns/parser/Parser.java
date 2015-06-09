package com.ns.parser;

import com.ns.exception.ParseException;

public interface Parser<T> {

    T parse(String string) throws ParseException;

}
