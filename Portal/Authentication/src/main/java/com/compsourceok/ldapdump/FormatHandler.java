

package com.compsourceok.ldapdump;

import com.unboundid.ldap.sdk.LDAPInterface;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The interface that is implemented by objects that handle the DSML and LDIF file formats.
 *
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.2.0
 */
public interface FormatHandler {

    /**
     * Reads directory entries from the input stream and loads them in the LDAP directory server.
     *
     * @param connection   The connection to the LDAP directory server.
     * @param inputStream  The input stream from which directory entries will be read.
     * @param ignoreErrors If {@code true} then loading will continue if an error occurs.
     * @param logger       Used to log information or error messages.
     */
    void load(LDAPInterface connection, InputStream inputStream, boolean ignoreErrors, org.slf4j.Logger logger);

    /**
     * Dump the results of a search against the LDAP directory server to an output stream.
     *
     * @param connection   The connection to the LDAP directory server.
     * @param base         The base DN from which to start the search.
     * @param filter       Query used to filter the directory entries.
     * @param outputStream The output stream to which the directory entries are to be written.
     * @param logger       Used to log information or error messages.
     */
    void dump(LDAPInterface connection, String base, String filter, OutputStream outputStream, org.slf4j.Logger logger);
}