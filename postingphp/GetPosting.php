<?php
    $con = mysqli_connect("localhost", "jamong", "cs183kkl", "jamong");
    mysqli_query($con,'SET NAMES utf8');
 
    $GetPostTitleData = $_POST["GetPostTitleData"];
    $GetPostPlaceData = $_POST["GetPostPlaceData"];
    $GetPostDateData = $_POST["GetPostDateData"];
    $GetPostColorData = $_POST["GetPostColorData"];
    $GetPostMoreInfoData = $_POST["GetPostMoreInfoData"];
    $GetPostImgData = $_POST["GetPostImgData"];

        $statement = mysqli_prepare($con, "INSERT INTO GETPOST(GetPostTitleData, GetPostPlaceData, GetPostDateData, GetPostColorData, GetPostMoreInfoData, GetPostImgData) VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssssss"
        ,$GetPostTitleData, $GetPostPlaceData, $GetPostDateData, $GetPostColorData, $GetPostMoreInfoData, $GetPostImgData);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
 
    echo json_encode($response);
?>