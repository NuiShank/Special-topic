
<?php
mysql_connect("localhost","root","密碼");//連結伺服器
mysql_select_db("sensor");//選擇資料庫
mysql_query("set names utf8");//以utf8讀取資料，讓資料可以讀取中文

if(@$_POST['IMEI']!="" OR @$_POST['count']!="" ){
 $IMEI=$_POST['IMEI'];
  $count=$_POST['count'];
 $data=mysql_query("select * from Accelermeter where IMEI like '%$IMEI%'"); 
}else{
 $data=mysql_query("select * from Accelermeter");
}


?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>資料篩選</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="">
  <p>IMEI：
    <input name="IMEI" type="text" id="IMEI" value="" />
  </p>
    <p>前幾筆：
    <input name="count" type="number" id="count" value="" />
  </p>
  <p>
    <input type="submit" name="button" id="button" value="搜尋" />
  </p>
</form>
<p></p>
<table width="700" border="1">
   <tr>
    <td >id</td>
    <td >IMEI</td>
    <td >X</td>
    <td >Y</td>
    <td >Z</td>
    <td >G</td>
    <td >time</td>
  </tr>
  <?php
  if(@$count<=0)
  {$count=mysql_num_rows($data);}
  for($i=1;$i<=$count;$i++){
$rs=mysql_fetch_row($data);
?>
  <tr>
    <td><?php echo $rs[0]?></td>
    <td><?php echo $rs[1]?></td>
    <td><?php echo $rs[2]?></td>
    <td><?php echo $rs[3]?></td>
    <td><?php echo $rs[4]?></td>
    <td><?php echo $rs[5]?></td>
    <td><?php echo $rs[6]?></td>

  </tr>
  
  <?php
}
?>
</table>
<p>&nbsp;</p>
</body>
</html>