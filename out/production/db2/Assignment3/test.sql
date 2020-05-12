-- CREATE TABLE cse532.sample_points (id VARCHAR(16), geom DB2GSE.ST_POINT);
--
-- !db2se register_spatial_column sample
-- -tableSchema      cse532
-- -tableName        sample_points
-- -columnName       geom
-- -srsName          nad83_srs_1
-- ;

INSERT into CSE532.SAMPLE_POINTS (ID, GEOM)
select FACILITYID,DB2GSE.ST_POINT(LATITUDE,LONGITUDE,1) from CSE532.FACILITYORIGINAL;