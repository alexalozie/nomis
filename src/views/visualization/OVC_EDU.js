import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import {url} from '../../api';
import axios from 'axios';

require("highcharts/modules/exporting")(Highcharts);

const EnrollmentStreamChart = () => {
    const [data, setData] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}dashboard/edu-summary/${id}`)
            .then(response => setData(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: 'OVC_EDU [By Gender]'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: data

    }

    return (
        <div>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default EnrollmentStreamChart;
