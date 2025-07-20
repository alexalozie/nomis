import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import axios from 'axios';
import {url} from '../../api';

require("highcharts/modules/exporting")(Highcharts);

const ViralLoadEligibility = () => {
    const [data6, setData6] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}visualization/viral-load-eligibility/${id}`)
            .then(response => setData6(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: 'column'
        },
        title: {
            text: data6.text
        },
        yAxis: {
            min: 0,
            title: {
                text: data6.Value
            },
        },
        // xAxis: data6.xAxis,
        labels: {
            items: [{
                style: {
                    pointWidth: 30,
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
                pointWidth: 30
            }
        },
        series: data6.series
    }
    return (
        <div id={"container6"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default ViralLoadEligibility;

