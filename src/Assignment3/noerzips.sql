with facilityData as (
    SELECT SUBSTR(F.ZIPCODE,1,5) as zipcode,F.ZIPCODE as originalZip, FC.ATTRIBUTEVALUE as type ,F.FACILITYID from CSE532.FACILITY F inner join CSE532.FACILITYCERTIFICATION FC on F.FACILITYID=FC.FACILITYID
),
     emergencyData as (
         SELECT zipcode,sum(case when type='Emergency Department' then 1 else 0 End) as HasEmergency from facilityData group by zipcode
     ),
    modifiedData as (
    select zipcode,case when HasEmergency=0 then False else True End as hasEmergency from emergencyData
    ),
     getShape as (
         SELECT  fd.zipcode,fd.hasEmergency,us.SHAPE from modifiedData fd inner join CSE532.USZIP us on fd.zipcode=us.ZCTA5CE10
     ),

     hasNoED as(
         SELECT zipcode,hasEmergency,SHAPE from getShape where hasEmergency=FALSE
     ) ,


     getNeighbours as (
         SELECT hne.zipcode,hne.hasEmergency,gs.zipcode as neiZip,gs.hasEmergency as neiHas from hasNoED hne,getShape gs where DB2GSE.ST_Intersects(hne.SHAPE,gs.SHAPE)=1
     ),
     getTotal as (
SELECT zipcode,sum(case when neiHas=TRUE then 1 else 0 end) as erCount from getNeighbours group by zipcode )

select count(*) from getTotal where erCount=0;