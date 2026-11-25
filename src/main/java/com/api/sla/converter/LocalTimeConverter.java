
package com.api.sla.converter;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@FacesConverter(forClass = LocalTime.class)
public class LocalTimeConverter implements Converter<LocalTime> {

    @Override
    public LocalTime getAsObject(
            FacesContext context,
            UIComponent component,
            String value
    ) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return LocalTime.parse(value.trim());

        } catch (DateTimeParseException e) {

            throw new ConverterException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Horário inválido.",
                            "Informe o horário no formato HH:mm."
                    ),
                    e
            );
        }
    }

    @Override
    public String getAsString(
            FacesContext context,
            UIComponent component,
            LocalTime value
    ) {

        if (value == null) {
            return "";
        }

        return String.format(
                "%02d:%02d",
                value.getHour(),
                value.getMinute()
        );
    }
}
