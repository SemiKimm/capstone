<?php
    $con = mysqli_connect("localhost", "jamong", "cs183kkl", "jamong");
    mysqli_query($con,'SET NAMES utf8');
 
    $category=$_POST["category"];
    $color=$_POST["color"];
    $local=$_POST["local"];
    $place=$_POST["place"];
    $date1=$_POST["date1"];
    $date2=$_POST["date2"];

    if(!empty($category) && !empty($place)){
        $sql="SELECT * FROM GETPOST WHERE GetPostCategoryData LIKE '$category' AND GetPostColorData LIKE '$color' AND GetPostLocalData LIKE '$local' OR GetPostPlaceData LIKE '%".$place."%'";
        $statement=mysqli_query($con,$sql);
    }
    else if(!empty($category)&&empty($place)){
        $sql="SELECT * FROM GETPOST WHERE GetPostCategoryData LIKE '$category' AND GetPostColorData LIKE '$color' AND GetPostLocalData LIKE '$local'";
        $statement=mysqli_query($con,$sql);
    }
    else{
        $sql = "SELECT * FROM GETPOST";
        //php 변수를 stmt에 바인드
        $statement=mysqli_query($con,$sql);
    }
    $data = array();  
    if($statement){
        while($response=mysqli_fetch_array($statement)){
            array_push($data, 
                array('GetPostTitleData'=>$response[0],
                'GetPostCategoryData'=>$response[1],
                'GetPostLocalData'=>$response[2],
                'GetPostPlaceData'=>$response[3],
                'GetPostDateData'=>$response[4],
                'GetPostColorData'=>$response[5],
                'GetPostMoreInfoData'=>$response[6],
                'GetPostImgData'=>$response[7]
                )
            );
        }

        echo json_encode(array("success"=>true,"getpostdata"=>$data));
    }
    else{
        $response = array();
        $response["success"] = true;
        echo json_encode($response);
    }

?>