XQuery
  for $product in db2-fn:xmlcolumn('PRODUCT.DESCRIPTION')/product[description/weight="1 kg"]
      for $porder in db2-fn:xmlcolumn('PURCHASEORDER.PORDER')/PurchaseOrder[item/partid=$product/@pid]
      return 
      	<Item1Kg pid="{$product/@pid}" poid="{$porder/@PoNum}">
      	    {$product/description/name/text() }
        </Item1Kg>
@