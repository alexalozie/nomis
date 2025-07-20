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
        text: 'OVC_SERV & HIVSTAT by Age Category-FEMALE'
    },
   
    xAxis: {
        categories: ['Unknown Age_…', '<1yrsF', '1-4yrsF', '5-9yrsF', '10-14yrsF', '15-17yrsF', '18-20yrsF', '18+yrsF'],
        title: {
            text: null
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Population (millions)',
            align: 'high'
        },
        labels: {
            overflow: 'justify'
        }
    },
    tooltip: {
        valueSuffix: ' millions'
    },
    plotOptions: {
        bar: {
            dataLabels: {
                enabled: true
            }
        }
    },
    // legend: {
    //     layout: 'vertical',
    //     align: 'right',
    //     verticalAlign: 'top',
    //     x: -40,
    //     y: 80,
    //     floating: true,
    //     borderWidth: 1,
    //     backgroundColor:
    //         Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
    //     shadow: true
    // },
    credits: {
        enabled: false
    },
    series: [{
        name: 'OVC_SERV',
        data: [107, 31, 635, 203, 2,99,76,89]
    }, {
        name: 'OVC_HIVSTAT',
        data: [133, 156, 947, 408, 667,908,987,123]
    }]
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