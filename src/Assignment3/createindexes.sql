drop index cse532.facilityidx;
drop index cse532.zipidx;
drop index cse532.facility_index;
drop index cse532.us_index;
drop index cse532.zip_index;
create index cse532.facilityidx on cse532.facility(geolocation) extend using db2gse.spatial_index(0.85, 2, 5);

create index cse532.zipidx on cse532.uszip(shape) extend using db2gse.spatial_index(0.85, 2, 5);

create index cse532.facility_index on cse532.facility(ZIPCODE);
create index cse532.us_index on cse532.USZIP(ZCTA5CE10);

CREATE index cse532.zip_index on cse532.ZIPPOP(ZPOP,ZIP);

runstats on table cse532.facility and indexes all;

runstats on table cse532.uszip and indexes all;