package com.pwc.us.webui.stripes.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import net.sourceforge.stripes.validation.DateTypeConverter;
import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.ValidationError;


public class TimeTypeConverter extends DateTypeConverter {
    private static final String[] TIME_FORMAT = { "hh:mm a" };
    @Override
    protected String[] getFormatStrings() {
        return TIME_FORMAT;
    }
    
    /**
     * Attempts to convert a String to a Date object.  Pre-processes the input by invoking the
     * method preProcessInput(), then uses an ordered list of DateFormat objects (supplied by
     * getDateFormats()) to try and parse the String into a Date.
     */
    @Override
    public Date convert(String input,
                        Class<? extends Date> targetType,
                        Collection<ValidationError> errors) {

        // Step 1: pre-process the input to make it more palatable
        String parseable = preProcessInput(input);

        // Step 2: try really hard to parse the input
        Date date = null;

        for (DateFormat format : getDateFormats()) {
            try {
                date = format.parse(parseable);
                break;
            }
            catch (ParseException pe) { /* Do nothing, we'll get lots of these. */ }
        }

        // Step 3: If we successfully parsed, return a date, otherwise send back an error
        if (date != null) {
            return date;
        }
        else {
            errors.add( new ScopedLocalizableError("converter.time", "invalidTime") );
            return null;
        }
    }
}

