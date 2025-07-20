import React from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'

// Load Highcharts modules
require("highcharts/modules/exporting")(Highcharts);
// Age categories
var categories = [
    '0-4', '5-9', '10-14', '15-19',
    '20-24', '25-29', '30-34', '35-39', '40-44',
    '45-49', '50-54', '55-59', '60-64', '65-69',
    '70-74', '75-79', '80-84', '85-89', '90-94',
    '95-99', '100 + '
];

//var Highcharts  = Highcharts

var options = {
    chart: {
        type: 'column'
    },
    title: {
        text: 'OVC_SERV [ Comprehensive ]'
    },
    xAxis: {
        categories: ['Jan.', 'Feb.', 'March', 'April', 'May', 'June']
    },

    series: [{
        type: 'column',
        name: 'OVC_SERV_Prevention',
        data: [50, 100, 200, 250, 300]
    }, {
        type: 'column',
        name: 'OVC_SERV_Comprehensive',
        data: [20, 30, 50, 70, 60]
    }, ]
}


const Chart = () => {
    return (

        <div>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default Chart;