<?php 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 //Getting values
 $idno = $_POST['idno'];
 $lastname = $_POST['lastname'];
 $firstname = $_POST['firstname'];
 $email = $_POST['email'];
 $dob = $_POST['dob'];
 $gender = $_POST['gender'];
 
 //Creating an sql query
 $sql = "INSERT INTO tblUser (idno, lastname, firstname, email, dob, gender) VALUES ('$idno','$lastname','$firstname', '$email', '$dob', '$gender')";
 
 //Importing our db connection script
 require_once('dbConnect.php');
 
//do some validation before inserting data

 //Executing query to database
 if(mysqli_query($con,$sql)){
 echo 'Account created Successfully';
 }else{
 echo 'Could not create Account';
 }
 
 //Closing the database 
 mysqli_close($con);
 }