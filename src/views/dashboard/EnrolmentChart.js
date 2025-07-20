import React, {useEffect,useState, lazy } from 'react'
import axios from 'axios';
import {url} from '../../api/index';
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'


require("highcharts/modules/exporting")(Highcharts);

const EnrolmentChart = () => {
    const [data1, setData1] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        console.log(`${url}dashboard/enrolment-chart/${id}`)
        axios
            .get(`${url}dashboard/enrolment-chart/${id}`)
            .then(response => setData1(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: 'column'
        },
        title: {
            text: data1.text
        },
        xAxis: data1.xAxis,

        labels: {
            items: [{
                style: {
                    pointWidth: 20,
                    left: '50px',
                    top: '18px',
                    color: (
                        Highcharts.defaultOptions.title.style &&
                        Highcharts.defaultOptions.title.style.color
                    ) || 'black'
                }
            }]
        },
        plotOptions: {
            series: {
                pointWidth: 40
            }
        },
        series: data1.series
    }

    return (
        <div id={"container1"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default EnrolmentChart;
