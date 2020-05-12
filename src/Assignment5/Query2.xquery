for $d in  distinct-values( doc("purchaseorders.xml")//PurchaseOrders/PurchaseOrder/item/partid)
let $items:= doc("purchaseorders.xml")//PurchaseOrders/PurchaseOrder/item[partid=$d]
order by $d
return <totalCost partid="{$d}">{round-half-to-even(sum($items/(quantity*price)),2)}</totalCost>