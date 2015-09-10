package com.ggk.hrms.review.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class UtilDateEditor extends PropertyEditorSupport {

    private boolean isRequired = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public UtilDateEditor(boolean isRequired) {
        this.isRequired = isRequired;
        sdf.setLenient(false);

    }

    public void setAsText(String text) throws IllegalArgumentException {
        java.util.Date date = null;
        if (!this.isRequired && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            try {
                date = sdf.parse(text);
                setValue(date);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: "
                        + ex.getMessage());
            }
        }
    }

    public String getAsText() {
        Date date = (java.util.Date) getValue();
        if (date != null) {
            return sdf.format(date);

        } else {
            return "";
        }
    }
}
