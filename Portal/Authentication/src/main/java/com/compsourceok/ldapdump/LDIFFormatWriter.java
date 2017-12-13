

package com.compsourceok.ldapdump;


import com.unboundid.ldap.sdk.Entry;
import com.unboundid.util.ByteStringBuffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This {@link FormatWriter} writes LDAP entries to an underlying output stream.
 *
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.2.0
 */
public final class LDIFFormatWriter implements FormatWriter {

    /**
     * The underlying output stream.
     */
    private final OutputStream ldifOutputStream;
    /**
     * The system dependent end of line marker.
     */
    private final byte[] eol;
    /**
     * Used by the {@link #printEntry(com.unboundid.ldap.sdk.Entry)} method to determine if the
     * first entry has already been output.
     */
    private boolean first = true;

    /**
     * Initialize the {@link LDIFFormatWriter} by keeping a reference to the underlying output stream.
     *
     * @param outputStream The underlying output stream.
     */
    public LDIFFormatWriter(final OutputStream outputStream) {
        ldifOutputStream = outputStream;
        final String eolString = System.getProperty("line.separator", "\n");
        eol = eolString.getBytes();
    }

    /**
     * Write the LDAP entry to the underlying output stream in LDIF format.
     *
     * @param entry The directory entry.
     * @throws IOException If there was a problem writing to the underlying output stream.
     */
    @Override
    public void printEntry(final Entry entry) throws IOException {
        if (entry != null) {
            final ByteStringBuffer buffer = new ByteStringBuffer();
            entry.toLDIF(buffer, 77);
            if (!first) {
                ldifOutputStream.write(eol);
            } else {
                first = false;
            }
            ldifOutputStream.write(buffer.toByteArray());
        }
    }

    /**
     * Close the {@link LDIFFormatWriter} which does not require any processing.
     */
    @Override
    public void close() {
    }
}
