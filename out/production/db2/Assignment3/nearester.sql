select F.FACILITYNAME,F.ADDRESS1,F.ADDRESS2,F.GEOLOCATION as location, db2gse.st_distance(db2gse.st_point( -72.993983,40.824369,1), F.GEOLOCATION, 'STATUTE MILE') as distance
from CSE532.FACILITY F INNER JOIN CSE532.FACILITYCERTIFICATION FC on F.FACILITYID=FC.FACILITYID
where db2gse.st_contains(db2gse.st_buffer(db2gse.st_point( -72.993983,40.824369,1),'0.25'), F.GEOLOCATION) = 1
  and FC.ATTRIBUTEVALUE='Emergency Department'order by distance
fetch first 1 rows only;