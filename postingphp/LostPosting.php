<?php
    $con = mysqli_connect("localhost", "jamong", "cs183kkl", "jamong");
    mysqli_query($con,'SET NAMES utf8');
 
    $LostPostTitleData = $_POST["LostPostTitleData"];
    $LostPostPlaceData = $_POST["LostPostPlaceData"];
    $LostPostDateData = $_POST["LostPostDateData"];
    $LostPostColorData = $_POST["LostPostColorData"];
    $LostPostMoreInfoData = $_POST["LostPostMoreInfoData"];
    $LostPostImgData = $_POST["LostPostImgData"];

        $statement = mysqli_prepare($con, "INSERT INTO LOSTPOST(LostPostTitleData, LostPostPlaceData, LostPostDateData, LostPostColorData, LostPostMoreInfoData, LostPostImgData) VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssssss"
        ,$LostPostTitleData, $LostPostPlaceData, $LostPostDateData, $LostPostColorData, $LostPostMoreInfoData, $LostPostImgData);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
 
    echo json_encode($response);
?>