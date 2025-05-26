jdbc 

spring security

JWT Token

Redis



redise eklenen tokenlar ile login ve logout islemleri. login yapildiginda bir token dondorulur ve redise key username value token olarak yazilir.Token ile redisteki duration süresi aynidir.

Logoout yapildiginde token blackList:token key'i olarak redise eklenir value ise true'dir. 

Her istek oncesi calisan doFilterInternal metodunda token blackListteden mi ? validate mi ? token extractUsername ile redisteki tokena karsilik gelen key (Username) esit mi ? kontrolleri yapilir.
