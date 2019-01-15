<?php
/**
 * Created by PhpStorm.
 * User: hasnat
 * Date: 1/14/19
 * Time: 10:12 PM
 */
require "config.php";

$name = $_GET['name'];
$email = $_GET['email'];
$password = $_GET['password'];
$phone = $_GET['phone'];

$query = "SELECT * FROM user WHERE email = '$email'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result)>0){
    $status = "exists";
}else{ // for non-registered user
    $insert_query = "INSERT INTO YOUR-TABLE-NAME(name, email, password, phone) VALUES('$name','$email','$password','$phone')";
    if (mysqli_query($conn, $insert_query)){
        $status = "inserted";
    } else{
        $status = "error";
    }
}

echo json_encode(array("response"=>$status));

mysqli_close($conn);
