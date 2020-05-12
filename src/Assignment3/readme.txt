Task-1:
    -Created and ran appropiate scripts- import_zip.sql,createfacilititytable.sql,createfacilititycertificationtable.sql
    -Used "insert into" to  facilityinsert.sql
    -Created new indices for uszip , facility and zippop in createindexes.sql

Task-2:
    -used ST_point(long,lati) to represent given point
    -used ST_buffer to create a boundary shape
    -used ST_contains to validate geolocation in buffer
    -used ST_distance to get distance in STATUTE MILE

    result: Long Island Community Hospital 101 Hospital Road - POINT (-72.978035 40.778915) - +3.24604202930554E+000

Task-3:
    -facilityData to get attribute values and substring 8 digit zips
    -emergencyData,modifiedData to group by zip and append a column of type boolean to mention if it "hasEmergency"
    -getShape to get shapes of all zips from uszip
    -hasNoED to get zips with no emergency department alone
    -getNeighbours to get neighbours of all zips from hasNoED using ST_Intercepts
    -getTotal to count number of emrgency wards
    -return total zips with no emergency department

    result: 208

Task-4:
    without Index:
        -nearester.sql runs in 1 sec 373ms
        -noerzips.sql runs in 1 sec 618ms
    with Index:
         -nearester.sql runs in 244ms
         -noerzips.sql runs in 1 sec 415ms


Task-5:
    Tables created:
    -neighbours to store list of all neighbours of each zip
    -neiZips to use as a array for validating if the current selected neighbour is already part of a previous merge
    -newNeighbour for storing result

    Process:
    Approach is to gegt all neighbours and order by zip,neighbourzip and store in a table for layer usage, once the table is set, we run
    a stored procedure on top.

    for getting neighbours:
    -group by zips,
    -get population from zipop,
    -get shape from uszip,
    -get neighbours from st_intersects of uszip.shape and current shape
    -get neightbours pop by joining with zippop.

    Current avg:9475
    Current total zips:32976

    for stored procedure:
    -for a group of zip we try to append neighbour zip population if its popluation is leass than
     average and current neighbour not part of previous merged ones.
    -we also maintain running average and count of zips.
    -when a zip is merged we update its current population, and append it to neiZips.
    -Finally before a new zip group we insert values into newNeighbour.


