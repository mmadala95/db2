for $item in doc("ord.xml")//item,
    $product in doc("cat.xml")//product[number=$item/@num]
return
    <item num="{$item/@num}"
     name="{$product/name}"
     quan="{$item/@quantity}"/>