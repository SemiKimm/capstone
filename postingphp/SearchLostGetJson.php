<?php
    $con = mysqli_connect("localhost", "jamong", "cs183kkl", "jamong");
    mysqli_query($con,'SET NAMES utf8');
 
    $category=$_POST["category"];
    $color=$_POST["color"];
    $local=$_POST["local"];
    $place=$_POST["place"];
    $date1=$_POST["date1"];
    $date2=$_POST["date2"];

    if(!empty($category)){
        $sql="SELECT * FROM LOSTPOST WHERE LostPostCategoryData Like '$category'";
        $statement=mysqli_query($con,$sql);
    }
    else{
        $sql = "SELECT * FROM LOSTPOST";
        //php 변수를 stmt에 바인드
        $statement=mysqli_query($con,$sql);
    }
    $data = array();  
    if($statement){
        while($response=mysqli_fetch_array($statement)){
            array_push($data, 
                array('LostPostTitleData'=>$response[0],
                'LostPostCategoryData'=>$response[1],
                'LostPostLocalData'=>$response[2],
                'LostPostPlaceData'=>$response[3],
                'LostPostDateData'=>$response[4],
                'LostPostColorData'=>$response[5],
                'LostPostMoreInfoData'=>$response[6],
                'LostPostImgData'=>$response[7]
                )
            );
        }

        echo json_encode(array("success"=>true,"lostpostdata"=>$data));
    }
    else{
        $response = array();
        $response["success"] = true;
        echo json_encode($response);
    }

?>