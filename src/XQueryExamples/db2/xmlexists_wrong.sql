SELECT XMLQUERY('$INFO/customerinfo/name') FROM CUSTOMER
WHERE XMLEXISTS('$INFO/customerinfo/phone="416-555-1358"')@