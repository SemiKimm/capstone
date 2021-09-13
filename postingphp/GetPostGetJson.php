<?php  

$link=mysqli_connect("localhost", "jamong", "cs183kkl", "jamong" );  
if (!$link)  
{  
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();  
}  

mysqli_set_charset($link,"utf8"); 


$sql="select * from GETPOST";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
   
   while($row=mysqli_fetch_array($result)){
       array_push($data, 
           array('GetPostTitleData'=>$row[0],
           'GetPostPlaceData'=>$row[1],
           'GetPostDateData'=>$row[2],
           'GetPostColorData'=>$row[3],
           'GetPostMoreInfoData'=>$row[4],
           'GetPostImgData'=>$row[5]
       ));
   }

   
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("getpostdata"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

}  
else{  
   echo "SQL문 처리중 에러 발생 : "; 
   echo mysqli_error($link);
} 


mysqli_close($link);  
  
?>