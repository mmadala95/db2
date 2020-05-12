with zpopulation as(
    select cast(sum(ZPOP) as float) as pop,ZIP from ZIPPOP group by ZIP having not cast(sum(ZPOP) as float)=0
),
     joinrecords as
         (select DEA_NYC.MME,DEA_NYC.BUYER_ZIP as ZIP,zpopulation.pop,DEA_NYC.MME/zpopulation.pop as mme_normalized from DEA_NYC inner join zpopulation on DEA_NYC.BUYER_ZIP=zpopulation.zip ),
     joinZip as
         (select ZIP,sum(mme_normalized) as final  from joinrecords group by ZIP),
     rankedZip as
         (select ZIP,final,RANK() over (order by final desc ) as rank from joinZip)
select ZIP,final as MMETotal from rankedZip where rank<6;