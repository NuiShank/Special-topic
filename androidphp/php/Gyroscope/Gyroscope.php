<?php
// Dreamweaver的函式庫
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  if (PHP_VERSION < 6) {
    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
  }

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}

$database_dblink = "sensor";
$username_dblink = "root";
$password_dblink = "密碼";
// 建立資料庫連線
$dblink = mysql_pconnect("localhost", $username_dblink, $password_dblink) or trigger_error(mysql_error(),E_USER_ERROR); 
mysql_query("SET NAMES utf8",$dblink); 
mysql_query("SET CHARACTER_SET_CLIENT=utf8",$dblink); 
mysql_query("SET CHARACTER_SET_RESULTS=utf8",$dblink); 
mysql_select_db($database_dblink, $dblink);

// 宣告utf-8的編碼header("Content-Type:text/html; charset=utf-8");
// 接收POST/GET的資料
$X=@$_REQUEST['X'];
$Y=@$_REQUEST['Y'];
$Z=@$_REQUEST['Z'];
$G=@$_REQUEST['G'];
$IMEI=@$_REQUEST['IMEI'];// 如果有資料if (strcmp(trim($X), "")!=0 || strcmp(trim($Y), "")!=0 || strcmp(trim($Z), "")!=0 )
{	
    	$insertSQL = sprintf("INSERT INTO `Gyroscope` (`X`,`Y` ,`Z`,`IMEI`) VALUES ('%s','%s','%s','%s');", $X, $Y, $Z,$IMEI);

      mysql_query($insertSQL, $dblink) or die(mysql_error());
    	
}

// 從資料庫撈出來資料
$query_rs = "SELECT * FROM `Gyroscope` ";
$rs = mysql_query($query_rs, $dblink) or die(mysql_error());
while ($row = mysql_fetch_assoc($rs))
{
echo "X=".$row['X']."\n\n";
echo "Y=".$row['Y']."\n\n";
echo "Z=".$row['Z']."\n\n";
echo "G=".$row['G']."\n\n";
echo "IMEI=".$row['IMEI']."\n\n";
echo $row['post_time']."<br>";
}
?>