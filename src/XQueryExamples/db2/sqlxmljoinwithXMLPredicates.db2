SELECT POID, PID,
       XMLCAST( XMLQUERY('data($PORDER/PurchaseOrder/@Status)') AS VARCHAR(64) ) AS STATUS
FROM PURCHASEORDER, PRODUCT
WHERE 
     XMLEXISTS('$DESCRIPTION/product/@pid[xs:string(.) = $PORDER/PurchaseOrder/item/partid/xs:string(.)]')
@