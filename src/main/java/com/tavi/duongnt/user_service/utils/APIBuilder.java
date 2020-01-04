package com.tavi.duongnt.user_service.utils;

import java.util.LinkedList;
import java.util.List;

public class APIBuilder {
    private List<String> names;
    private List<String> values;
    private String link;

    public APIBuilder(String link) {
        this.link = link;
        names = new LinkedList<>();
        values = new LinkedList<>();
    }

    public APIBuilder addParam(String name, String value) {
        this.names.add(name);
        this.values.add(value);
        return this;
    }

    public String build() {
        if (!names.isEmpty()) {
            StringBuilder builder = new StringBuilder(link + "?" + names.get(0)+"="+values.get(0));
            for (int i = 1; i < names.size();i++){
                builder.append("&");
                builder.append(names.get(i));
                builder.append("=");
                builder.append(values.get(i));
            }
            return builder.toString();
        }
        return link;
    }
}
