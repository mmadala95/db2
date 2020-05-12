for $d in  distinct-values( doc("ord.xml")//item/@dept)
let $items:= doc("ord.xml")//item[@dpt=$d]
order by $d
return <department name="{$d}" totalQuantity="{sum($items/@quantity)}"/>