
with cteMonthly as (
    select sum(DOSAGE_UNIT)  as month_count,
           YEAR(TRANSACTION_DATE) || CASE
                                         WHEN (CAST(MONTH(TRANSACTION_DATE) AS INT) < 10)
                                             THEN '0' || CAST(MONTH(TRANSACTION_DATE) AS INT)
                                         ELSE CAST(MONTH(TRANSACTION_DATE) AS CHAR(2))
               END            as key
    from DEA_NYC
    group by YEAR(TRANSACTION_DATE) || CASE
                                           WHEN (CAST(MONTH(TRANSACTION_DATE) AS INT) < 10)
                                               THEN '0' || CAST(MONTH(TRANSACTION_DATE) AS INT)
                                           ELSE CAST(MONTH(TRANSACTION_DATE) AS CHAR(2))
        END
)
SELECT key as YearMonth ,month_count as MonthlyCount, cast(avg(month_count) over (order by key ROWS BETWEEN 1 PRECEDING AND  1 FOLLOWING ) as decimal(30,3)) as SmoothCount  from cteMonthly;
