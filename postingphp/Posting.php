<?php
    $con = mysqli_connect("localhost", "jamong", "cs183kkl", "jamong");
    mysqli_query($con,'SET NAMES utf8');
 
    $PostTitleData = $_POST["PostTitleData"];
    $PostPlaceData = $_POST["PostPlaceData"];
    $PostDateData = $_POST["PostDateData"];
    $PostColorData = $_POST["PostColorData"];
    $PostMoreInfoData = $_POST["PostMoreInfoData"];
    $PostImgData = $_POST["PostImgData"];

        $statement = mysqli_prepare($con, "INSERT INTO POST(PostTitleData, PostPlaceData, PostDateData, PostColorData, PostMoreInfoData, PostImgData) VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssssss"
        ,$PostTitleData, $PostPlaceData, $PostDateData, $PostColorData, $PostMoreInfoData, $PostImgData);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
 
    echo json_encode($response);
?>