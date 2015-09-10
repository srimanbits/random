package com.ggk.hrms.review.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class SqlDateEditor extends PropertyEditorSupport {

    private boolean isRequired = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public SqlDateEditor(boolean isRequired) {
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
                setValue(new java.sql.Date(date.getTime()));
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: "
                        + ex.getMessage());
            }
        }
    }

    public String getAsText() {
        Date value = (java.sql.Date) getValue();
        if (value != null) {
            java.util.Date d = new java.util.Date(value.getTime());
            return sdf.format(d);

        } else {
            return "";
        }
    }
}
