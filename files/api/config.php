<?php

$conn = mysqli_connect("localhost", "USER-NAME", "PASSWORD", "DATABASE-NAME");

if($conn){
    //echo "success connection";
} else{
    echo "failed to connect";
}

?>