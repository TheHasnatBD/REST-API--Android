<?php
/**
 * Created by PhpStorm.
 * User: hasnat
 * Date: 1/14/19
 * Time: 10:12 PM
 */
require "config.php";

$email = $_GET['email'];
$password = $_GET['password'];

$query = "SELECT * FROM YOUR-TABLE-NAME WHERE email = '$email' AND password = '$password'";
$result = mysqli_query($conn, $query);

if (!mysqli_num_rows($result) > 0){
    $status = "login_failed";
    echo json_encode(array("response"=>$status));

}else{ // for non-registered user
    $row = mysqli_fetch_assoc($result);
    $name = $row['name'];
    $email = $row['email'];
    $phone = $row['phone'];
    $created_at = $row['created_at'];

    $status = "data";
    echo json_encode(array("response"=>$status, "name"=>$name, "email"=>$email, "phone"=> $phone, "created_at" =>$created_at));
}


mysqli_close($conn);
