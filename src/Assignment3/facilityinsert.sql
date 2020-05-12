
INSERT INTO CSE532.FACILITY (FacilityID, FacilityName, Description, Address1, Address2, City, State , ZipCode , CountyCode, County, Geolocation)
SELECT FacilityID, FacilityName, Description, Address1, Address2, City, State , ZipCode , CountyCode, County, DB2GSE.ST_POINT(LONGITUDE,LATITUDE,1) FROM CSE532.FACILITYORIGINAL;