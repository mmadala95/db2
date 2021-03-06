-- DROP TABLE CSE532.neighbours;
-- CREATE TABLE CSE532.neighbours(
--                                   zip varchar(32),
--                                   pop DECIMAL(23,10),
--                                   neighbour varchar(32),
--                                   neipop DECIMAL(23,10))COMPRESS YES;
--
--
-- INSERT into CSE532.neighbours
-- with zipdata as (
--     select  zip,avg(zpop) as pop from CSE532.ZIPPOP  where ZPOP>0 group by zip
-- ),
--      getShape as (
--          select z.ZIP,z.pop,us.SHAPE from zipdata z inner join CSE532.USZIP us on z.ZIP=us.ZCTA5CE10
--      ),
--      getNeighbours as (
--          select  gs.ZIP,gs.pop,us.ZCTA5CE10 as neighbour from getShape gs, CSE532.USZIP us where DB2GSE.ST_Intersects(gs.SHAPE,us.SHAPE)=1
--      ),
--      getTotalPop as (
--          select gn.ZIP,gn.pop,gn.neighbour,gs.pop as neiPop from getNeighbours gn inner join getShape gs on gn.neighbour=gs.zip
--      )
-- select * from getTotalPop;

CREATE table CSE532.neizips (zip varchar (32))
CREATE table CSE532.newNeighbour (zip varchar(32),
                                  pop DECIMAL(23,10),
                                  neighbour varchar(32),
                                  neipop DECIMAL(23,10))COMPRESS YES;

DROP PROCEDURE cse532.merger@
end
CREATE PROCEDURE cse532.merger(OUT standardDeviation FLOAT )
    LANGUAGE SQL
    BEGIN
        DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
        DECLARE zip double ;
        DECLARE pop double ;
        DECLARE neighbour double ;
        DECLARE neipop double ;
        DECLARE prevzip double;

        DECLARE totavg double;
        DECLARE totzips double;
        DECLARE popSum double;
        DECLARE currzippop double;

        DECLARE neighbour CURSOR for  SELECT zip,pop,neighbour,neipop from cse532.neighbours;
        SET totavg=9475;
        SET totzips=32976;
        SET popSum=312462997;
        SET prevzip = NULL;


        open neighbour;
        FETCH FROM neighbour INTO zip,pop,neighbour,neipop;
        WHILE(SQLSTATE = '00000') DO
        IF( zip != prevzip) then:
            if( prevzip != null ):
                INSERT into
            set currentzippop=pop;
            prevzip = zip

            FETCH FROM neighbour INTO zip,pop,neighbour,neipop;
        END WHILE;
        SET std=SQRT(((salSumSquare/num))-((salSum/num)*(salSum/num)));

        close neighbour;
        set standardDeviation=std;
    END@
call cse532.STD(?)@























