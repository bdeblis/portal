package com.compsourceok.ldapdump;




import java.io.InputStream;
import java.io.OutputStream;

/**
 * This {@link com.btmatthews.maven.plugins.ldap.FormatHandler} is used to load data from or dump data
 * to LDIF files.
 *
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.2.0
 */
public final class LDIFFormatHandler extends AbstractFormatHandler {

    /**
     * Create the {@link LDIFFormatWriter} that will dump LDAP entries in LDIF format
     * to the target output stream.
     *
     * @param outputStream The target output stream.
     * @param logger       Used to log information or error messages.
     * @return A {@link LDIFFormatWriter} object.
     */
    @Override
    protected FormatWriter createWriter(final OutputStream outputStream,
                                        final org.slf4j.Logger logger) {
        return new LDIFFormatWriter(outputStream);
    }

    /**
     * Create {@link LDIFFormatReader} that reads LDIF change records from the source
     * input stream.
     *
     * @param inputStream The source input stream.
     * @param logger      Used to log information or error messages.
     * @return A {@link LDIFFormatReader} object.
     */
    @Override
    protected FormatReader openReader(final InputStream inputStream,
                                      final org.slf4j.Logger logger) {
        return new LDIFFormatReader(inputStream);
    }
}
