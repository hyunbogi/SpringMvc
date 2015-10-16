package com.hyunbogi.springmvc.web.converter;

import com.hyunbogi.springmvc.domain.Level;

import java.beans.PropertyEditorSupport;

public class LevelPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Level.valueOf(Integer.parseInt(text.trim())));
    }

    @Override
    public String getAsText() {
        return String.valueOf(((Level) getValue()).intValue());
    }
}
