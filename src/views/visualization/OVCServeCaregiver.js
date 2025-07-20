import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official';
import {url} from '../../api';
import axios from 'axios';

require("highcharts/modules/exporting")(Highcharts);

const OVCServeCaregiver = () => {
    const [data3, setData3] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}visualization/serv-caregiver/${id}`)
            .then(response => setData3(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);

    const options = {
        chart: {
            type: 'column'
        },
        title: {
            text: "OVC_SERV"
        },
        xAxis: data3.xAxis,
        yAxis: {
            min: 0,
            title: {
                text: 'Values'
            }
        },
        legend: {
            align: 'left',
            x: 70,
            verticalAlign: 'top',
            y: 70,
            floating: true,
            backgroundColor:
                Highcharts.defaultOptions.legend.backgroundColor || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false,
            reversed: true
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true
                },
            }
        },
        series: data3.series
    }

    return (
        <div id={"container3"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default OVCServeCaregiver;
