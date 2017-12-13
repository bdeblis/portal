call \unboundid-ldapsdk-2.3.6-se\tools\generate-source-from-schema.bat ^
     --hostname localhost ^
     --port 10389 ^
     --outputDirectory \tmp ^
     --structuralClass inetOrgPerson ^
     --rdnAttribute cn ^
     --defaultParentDN "m-oid=1.3.6.1.4.1.1234567.5.2.5, ou=objectclasses, cn=compsourceok, ou=schema" ^
     --packageName com.pwc.us.authentication.model ^
     --className LdapCashier