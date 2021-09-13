<?php  

$link=mysqli_connect("localhost", "jamong", "cs183kkl", "jamong" );  
if (!$link)  
{  
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();  
}  

mysqli_set_charset($link,"utf8"); 


$sql="select * from LOSTPOST";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
   
   while($row=mysqli_fetch_array($result)){
       array_push($data, 
           array('LostPostTitleData'=>$row[0],
           'LostPostPlaceData'=>$row[1],
           'LostPostDateData'=>$row[2],
           'LostPostColorData'=>$row[3],
           'LostPostMoreInfoData'=>$row[4],
           'LostPostImgData'=>$row[5]
       ));
   }

   
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("lostpostdata"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

}  
else{  
   echo "SQL문 처리중 에러 발생 : "; 
   echo mysqli_error($link);
} 


mysqli_close($link);  
  
?>