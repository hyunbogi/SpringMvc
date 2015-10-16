package com.hyunbogi.springmvc.web.converter;

import com.hyunbogi.springmvc.domain.Level;
import org.springframework.core.convert.converter.Converter;

public class LevelToStringConverter implements Converter<Level, String> {
    @Override
    public String convert(Level source) {
        return String.valueOf(source.intValue());
    }
}
