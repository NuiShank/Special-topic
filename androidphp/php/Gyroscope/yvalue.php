<?php
/**
 * Database connection details
 * This can be stored in a seperate file called config.php and included
 * via include('config.php');
 *
 * Fake example details!
 *
 * $db_host = 'localhost';
 * $db_database = 'bigcompanyinc';
 * $db_user = 'corporate_stooge';
 * $db_password = 'password1';
 */



$db = mysql_connect("localhost","root","密碼");
mysql_select_db("sensor");

/**
 * Used to produce a Google Chart for the weekly average prices of widgets
 */
function generateGoogleChart()
{
    //Get the data from the database
    $sqlQuery = "SELECT log_id, Y FROM Gyroscope";
    $sqlResult = mysql_query($sqlQuery);
    while ($row = mysql_fetch_assoc($sqlResult)) {
        $priceResults[] = $row;
    }

    //Set-up the values for the Axis on the chart
    $maxWeeks = count($priceResults);
	//Set chd parameter to no value
    $chd = '';
    //Limit in my example represents weeks of the year
    $limit = 200;

    //Start to compile the prices data
    for ($row = 0; $row < $limit; $row++) {
    	//Check for a value if one exists, add to $chd
        if(isset($priceResults[$row]['Y']))
        {
            $chd .= $priceResults[$row]['Y'];
        }
		//Check to see if row exceeds $maxWeeks
        if ($row < $maxWeeks) {
        	//It doesn't, so add a comma and add the price to array $scaleValues
            $chd .= ',';
            $scaleValues[] = $priceResults[$row]['Y'];
        } else if ($row >= $maxWeeks && $row < ($limit - 1)) {
        	//Row exceeds, so add null value with comma
            $chd .= '_,';
        } else {
        	//Row exceeds and is last required value, so just add a null value
            $chd .= '_';
        }
    }

	//Use the $scaleValues array to define my Y Axis 'buffer'
    $YScaleMax = round(max($scaleValues)) + 5;
    $YScaleMin = round(min($scaleValues)) - 5;
    //Generate the number of weeks of the year for A Axis labels
    $graphSequence = generateSequence(1, 200, "|");

    //Set the Google Image Chart API parameters
    $cht = 'lc';//Set the chart type
    $chxl = '1:|' . $graphSequence . '2:|Y-value|3:|次數||4:|次數';//custom axis labels
    $chxp = '2,50|4,50';//Axis Label Positions
    $chxr = '0,' . $YScaleMin . ',' . $YScaleMax . '|1,1,200|3,1,12|5,' . $YScaleMin . ',' . $YScaleMax . '';//Axis Range
    $chxs = '0,252525,10,1,l,676767|1,252525,10,0,l,676767|2,03619d,13|4,03619d,13|5,252525,10,1,l,676767';//Axis Label Styles
    $chxtc = '0,5|1,5|5,5';//Axis Tick Mark Styles
    $chxt = 'y,x,y,x,x,r';//Visible Axes
    $chs = '918x300';//Chart Size in px
    $chco = 'FF0000';//Series Colours
    $chds = '' . $YScaleMin . ',' . $YScaleMax . '';//Custom Scaling
    $chg = '-1,-1,1,5';//Grid lines
    $chls = '1';//line styles
    $chm = 'o,252525,0,-1,3';//Shape Markers

    //Build the URL
    $googleUrl = 'http://chart.apis.google.com/chart?';
    $rawUrl = $googleUrl . 'cht=' . $cht . '&chxl=' . $chxl . '&chxp=' . $chxp . '&chxr=' . $chxr . '&chxs=' . $chxs . '&chxtc=' . $chxtc . '&chxt=' . $chxt . '&chs=' . $chs . '&chco=' . $chco . '&chd=t:' . $chd . '&chds=' . $chds . '&chg=' . $chg . '&chls=' . $chls . '&chm=' . $chm;

    $output = $rawUrl;

    return $output;
}

/**
 * A nicer way to test arrays
 */
function displayArray($val)
{
    echo "<pre>";
    print_r($val);
    echo "</pre>";
    return;
}

/**
 * a simple numeric sequence generator. requires a min and max for the sequence, and can take an optional seperator
 */
function generateSequence($min, $max, $seperator = "")
{
    $output = '';
    for ($i = $min; $i <= $max; $i++)
    {
        $output .= $i . $seperator;
    }
    return $output;
}

$chart = generateGoogleChart();

$html = '<div id="chart">';
$html .= '<img src="' . $chart . '" alt="Widget weekly average price chart for 2011" />';
$html .= '</div>';

echo $html;