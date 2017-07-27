<?php
	/*
		author: Eric Wamari
		website: http://www.harlertechnologies.com
		
		My Database is harlerte_mkopo
	*/
	
	//Defining Constants
	//define('HOST','192.185.174.40');
	define('HOST','localhost');
	define('USER','harlerte_mkopo');
	define('PASS','rz3P{N&oEW=z=nd2r6');
	define('DB','harlerte_mkopopopote');
	
	//Connecting to Database
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');