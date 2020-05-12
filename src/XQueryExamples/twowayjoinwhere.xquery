for $item in doc("ord.xml")//item,
    $product in doc("cat.xml")//product
where $item/@num = $product/number
return
    <item num="{$item/@num}"
     name="{$product/name}"
     quan="{$item/@quantity}"/>
