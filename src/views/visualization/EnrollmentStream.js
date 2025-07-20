import React, {useEffect, useState} from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import {url} from '../../api';
import axios from 'axios';


require("highcharts/modules/exporting")(Highcharts);

const EnrollmentStream = () => {
    const [data4, setData4] = useState([]);
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    const convertData = (data) => {
        setData4(JSON.parse(JSON.stringify(data)))
    }

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        axios
            .get(`${url}visualization/enrolment-stream/${id}`)
            .then(response => convertData(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
    }, []);


    const options = {
        chart: {
            type: "pie"
        },
        title: {
            text: "OVC_SERV(Enrolment Stream)"
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: "pointer",
                dataLabels: {
                    enabled: true
                },
                showInLegend: false
            }
        },

        series: [
            {
                name: "",
                color: "#006600",
                lineWidth: 1,
                marker: {
                    enabled: false,
                    symbol: "circle",
                    radius: 3,
                    states: {
                        hover: {
                            enabled: true,
                            lineWidth: 1
                        }
                    }
                },
                data: data4
            }
        ]

    };


    return (
        <div id={"container4"}>
            <HighchartsReact
                highcharts={Highcharts}
                options={options}
            />
        </div>
    )
}

export default EnrollmentStream;
