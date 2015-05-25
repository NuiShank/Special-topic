
<?php
    $database_dblink = "sensor";
    $username_dblink = "root";
    $password_dblink = "dear88990";
    // 建立資料庫連線
    $dblink = mysql_pconnect("localhost", $username_dblink, $password_dblink) or trigger_error(mysql_error(),E_USER_ERROR); 
	mysql_query("SET NAMES utf8",$dblink); 
	mysql_query("SET CHARACTER_SET_CLIENT=utf8",$dblink); 
	mysql_query("SET CHARACTER_SET_RESULTS=utf8",$dblink); 
	mysql_select_db($database_dblink, $dblink);
    // 從資料庫撈出來資料
    $query_rs = "SELECT * FROM `pedometer` order by log_id desc limit 1 ";
    $rs = mysql_query($query_rs, $dblink) or die(mysql_error());
    while ($row = mysql_fetch_assoc($rs))
    {
    $count=$row['Step'];
    }


?>

<!DOCTYPE html>
<html>
<head>
<!--<link type="text/css" rel="stylesheet" href="default.css">-->
<meta http-equiv="refresh" content="10000"> <!--content/sec-->
<script stype="textjavascript">

	var count = <?php echo $count; ?>;
	
	//document.write(count); print count value

	/*function test() {
        if(count > 0){
            document.getElementById("set"+i).style.display="none";
        }     
        document.getElementById("set"+current).style.display="block";
	}*/

	var content = ["content1","content2","content3","content4","content5"];

	function test() {
		if(count>=0){ // show runner
		document.getElementById(content[count]).style.visibility = "visible";
		var countdel = count;

			if(count>0){ //hidden runner
			countdel--;
			document.getElementById(content[countdel]).style.visibility = "hidden";
			}

		//count++;
		window.setTimeout("test();", 500);
		}
	}

	document.write(count);

</script>
</head>
<body>



<div id="container" style="width:1000px">

<div id="header" style="background-color:#ffffff;">
<h1 style="margin-bottom:0;">123</h1></div>

<div id="content1" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="ren_q_2.gif"></div>
<div id="content2" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="ren_q_2.gif"></div>
<div id="content3" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="ren_q_2.gif"></div>
<div id="content4" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="ren_q_2.gif"></div>
<div id="content5" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>
<div id="content" style="background-color:#EEEEEE;height:40px;width:5%;float:left;">
<img src="ren_q_2.gif"></div>

<script type="text/javascript">test();</script>

<!--<button onclick="test()">Try it</button>-->

</div>

</body>
</html>