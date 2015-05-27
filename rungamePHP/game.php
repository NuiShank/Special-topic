<!---------------------------------------------------------------------------------------------
Example client script for JQUERY:AJAX -> PHP:MYSQL example
by Trystan Lea : openenergymonitor.org : GNU GPL

I recommend going to http://jquery.com/ for the great documentation there about all of this
---------------------------------------------------------------------------------------------->
<?php
mysql_connect("localhost","root","dear88990");//連結伺服器
mysql_select_db("sensor");//選擇資料庫
mysql_query("set names utf8");//以utf8讀取資料，讓資料可以讀取中文
$media="run.gif";

 if (@$_POST["delete"]!="") {
$str = "TRUNCATE TABLE pedometer";
mysql_query($str);
$media=@$_POST['size'];
}

 if (@$_POST["change"]!="") {
$media=@$_POST['size'];
}


?>




<html>
  <head>
    <script language="javascript" type="text/javascript" src="jquery.js"></script>
  <script id="source" language="javascript" type="text/javascript">

 function test() 
  {

    //-------------------------------------------------------------------------------------------
    // 2) Send a http request with AJAX http://api.jquery.com/jQuery.ajax/
    //-------------------------------------------------------------------------------------------
    $.ajax({                                      
      url: 'api.php',                  //the script to call to get data          
      data: "",                        //you can insert url argumnets here to pass to api.php for example "id=5&parent=6"
      dataType: 'json',                //data format      
      success: function(data)          //on recieve of reply
      {
        var id = data[1];              //get id
        var step = data[2];           //get name             
        //--------------------------------------------------------------------------------------
        // 3) Update html content
        //--------------------------------------------------------------------------------------
        $('#output').html("<b>IMEI: </b>"+id+"<b> STEP: </b>"+step);     //Set output element html
        //recommend reading up on jquery selectors they are awesome http://api.jquery.com/category/selectors/   
        if(step>0){ // show runner             
              var count =parseInt(step)+1;
              var str="content"+count.toString();
              document.getElementById(str).style.visibility = "visible";
              //hidden runner
              var str2="content"+step.toString();
              document.getElementById(str2).style.visibility = "hidden";                       
          }
      } 
    });
 //per ms reload function 
  window.setTimeout("test();", 20);
  }

 
  </script>  

  </head>
  <body>
  <h2>Running Game (更新速度20毫秒) </h2>
  <div id="output">IMEI:? STEP: 0</div>

<div id="container" style="width:1000px">

<div id="content1" style="height:40px;width:5%;float:left;visibility:visible;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content2" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content3" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content4" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content5" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content6" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content7" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content8" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content9" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content10" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content11" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content12" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content13" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content14" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content15" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content16" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content17" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content18" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content19" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<div id="content20" style="height:40px;width:5%;float:left;visibility:hidden;">
<img src="<?php echo $media; ?>" width="40"></div>
<script type="text/javascript">test();</script>


<form id="form1" name="form1" method="post" action="">
<p></p>圖片：<br>
    <input  type="radio" name="size"   value="ren_q_2.gif"  ><img src="ren_q_2.gif" width="35">
    <input  type="radio" name="size"   value="dog.gif"><img src="dog.gif" width="35">
    <input  type="radio" name="size"   value="cat.gif"><img src="cat.gif" width="35">
    <input  type="radio" name="size"   value="run.gif" checked><img src="run.gif" width="35">
    <input type="submit" name="change" id="change" value="圖片更換" /><br>
  <p>
    <input type="submit" name="delete" id="delete" value="刪除資料庫" />
  </p> 
</form>

  
  </body>
</html>  
