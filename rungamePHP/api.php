<?php 

  //--------------------------------------------------------------------------
  // Example php script for fetching data from mysql database
  // by Trystan Lea : openenergymonitor.org : GNU GPL
  //--------------------------------------------------------------------------
  $host = "localhost";
  $user = "root";
  $pass = "dear88990";

  $databaseName = "sensor";
  $tableName = "pedometer";

  //--------------------------------------------------------------------------
  // 1) Connect to mysql database
  //--------------------------------------------------------------------------
  include 'DB.php';
  $con = mysql_connect($host,$user,$pass);
  $dbs = mysql_select_db($databaseName, $con);

  //--------------------------------------------------------------------------
  // 2) Query database for data
  //--------------------------------------------------------------------------
  $result = mysql_query("SELECT * FROM $tableName order by log_id desc limit 1");            //query
  $array = mysql_fetch_row($result);                          //fetch result    

  //--------------------------------------------------------------------------
  // 3) echo result as json 
  //--------------------------------------------------------------------------
  echo json_encode($array);

?>
