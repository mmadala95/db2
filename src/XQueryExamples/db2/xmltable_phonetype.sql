SELECT PHONEINFO.*
FROM CUSTOMER,
XMLTABLE('$INFO/customerinfo/phone'
COLUMNS
TYPE VARCHAR(32) PATH '@type',
PHONE VARCHAR(32) PATH '.')
AS PHONEINFO@