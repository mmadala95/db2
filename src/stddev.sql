DROP PROCEDURE cse532.STD@
CREATE PROCEDURE cse532.STD(OUT standardDeviation FLOAT )
    LANGUAGE SQL
    BEGIN
        DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
        DECLARE std FLOAT ;
        DECLARE salary FLOAT ;
        DECLARE num INTEGER;
        DECLARE salSum FLOAT ;
        DECLARE salSumSquare FLOAT ;
        DECLARE stdDev CURSOR for  SELECT salary from cse532.EMPLOYEE;
        SET std=0;
        SET num=0;
        SET salSum=0;
        SET salSumSquare=0;
        open stdDev;
        FETCH FROM stdDev INTO salary;
        WHILE(SQLSTATE = '00000') DO
            SET num = num + 1;
            SET salSum=salSum+salary;
            SET salSumSquare=salSumSquare+(salary*salary);
            FETCH FROM stdDev INTO salary;
        END WHILE;
        SET std=SQRT(((salSumSquare/num))-((salSum/num)*(salSum/num)));

        close stdDev;
        set standardDeviation=std;
    END@
call cse532.STD(?)@




