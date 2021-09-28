<?php  

$link=mysqli_connect("localhost", "jamong", "cs183kkl", "jamong" );  
if (!$link)  
{  
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();  
}  

mysqli_set_charset($link,"utf8"); 


$sql="select * from POST";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
   
   while($row=mysqli_fetch_array($result)){
       array_push($data, 
           array('PostTitleData'=>$row[0],
           'PostPlaceData'=>$row[1],
           'PostDateData'=>$row[2],
           'PostColorData'=>$row[3],
           'PostMoreInfoData'=>$row[4],
           'PostImgData'=>$row[5]
       ));
   }

   
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

}  
else{  
   echo "SQL문 처리중 에러 발생 : "; 
   echo mysqli_error($link);
} 


mysqli_close($link);  
  
?>