import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import axios from 'axios';
import {url} from '../../api';


require("highcharts/modules/exporting")(Highcharts);

const OVCTreatmentStatus = () => {
    const [data5, setData5] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}visualization/hiv-status/${id}`)
            .then(response => setData5(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: 'column'

        },
        title: {
            text: data5.text
        },
        // xAxis: data5.xAxis,
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
                pointWidth: 30
            }
        },
        series: data5.series
    }
    return (
        <div id={"container5"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default OVCTreatmentStatus;

