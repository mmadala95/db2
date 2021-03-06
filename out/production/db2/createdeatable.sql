
CREATE TABLE cse532.dea_nyc(
    REPORTER_DEA_NO VARCHAR(150),
    REPORTER_BUS_ACT VARCHAR(150),
    REPORTER_NAME VARCHAR(150),
    REPORTER_ADDL_CO_INFO VARCHAR(150),
    REPORTER_ADDRESS1 VARCHAR(150),
    REPORTER_ADDRESS2 VARCHAR(150),
    REPORTER_CITY VARCHAR(150),
    REPORTER_STATE VARCHAR(150),
    REPORTER_ZIP INT,
    REPORTER_COUNTY VARCHAR(150),
    BUYER_DEA_NO VARCHAR(150),
    BUYER_BUS_ACT VARCHAR(150),
    BUYER_NAME VARCHAR(150),
    BUYER_ADDL_CO_INFO VARCHAR(150),
    BUYER_ADDRESS1 VARCHAR(150),
    BUYER_ADDRESS2 VARCHAR(150),
    BUYER_CITY VARCHAR(150),
    BUYER_STATE VARCHAR(150),
    BUYER_ZIP INT,
    BUYER_COUNTY VARCHAR(150),
    TRANSACTION_CODE VARCHAR(150),
    DRUG_CODE INT,
    NDC_NO VARCHAR(150),
    DRUG_NAME VARCHAR(150),
    QUANTITY DECIMAL(20,5) ,
    UNIT VARCHAR(150),
    ACTION_INDICATOR VARCHAR(150),
    ORDER_FORM_NO VARCHAR(150),
    CORRECTION_NO VARCHAR(150),
    STRENGTH VARCHAR(150) ,
    TRANSACTION_DATE DATE ,
    CALC_BASE_WT_IN_GM DECIMAL (20,5),
    DOSAGE_UNIT DECIMAL (20,5),
    TRANSACTION_ID INT,
    Product_Name VARCHAR(150),
    Ingredient_Name VARCHAR(150),
    Measure VARCHAR(150),
    MME_Conversion_Factor INT,
    Combined_Labeler_Name VARCHAR(150),
    Reporter_family VARCHAR(150),
    dos_str VARCHAR(150),
    MME DECIMAL (20,5)

)COMPRESS YES;