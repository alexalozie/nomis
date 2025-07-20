import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import {url} from '../../api';
import axios from 'axios';


require("highcharts/modules/exporting")(Highcharts);

const VCByAge = () => {
    const [data2, setData2] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}dashboard/vc-by-age-chart/${id}`)
            .then(response => setData2(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: 'column'
        },
        title: {
            text: data2.text
        },
        xAxis: data2.xAxis,
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
        series: data2.series
    }

    return (
        <div id={"container2"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default VCByAge;

