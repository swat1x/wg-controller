package ru.swat1x.wgcontroller.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ContentBlockBuilder {

    public static ContentBlockBuilder create(String title) {
        return new ContentBlockBuilder(title);
    }

    String title;
    List<String> content = new ArrayList<>();

    public ContentBlockBuilder put(String key, Object value) {
        if(value != null) content.add("%s = %s".formatted(key, value));
        return this;
    }

    public String build() {
        var builder = new StringBuilder();
        builder.append("[%s]".formatted(title)).append("\n");
        content.forEach(line -> builder
                .append(line)
                .append("\n")
        );
        builder.append("\n");
        return builder.toString();
    }




}
