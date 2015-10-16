package com.hyunbogi.springmvc.web.converter;

import com.hyunbogi.springmvc.domain.Level;
import org.springframework.core.convert.converter.Converter;

public class StringToLevelConverter implements Converter<String, Level> {
    @Override
    public Level convert(String source) {
        return Level.valueOf(Integer.parseInt(source));
    }
}
