import React, {useEffect,useState, lazy } from 'react'
import axios from 'axios';
import {url} from '../../api/index';
import {
  CWidgetDropdown,
  CRow,
  CCol,
  CDropdown,
} from '@coreui/react'
import ChartLineSimple from '../charts/ChartLineSimple'
import {GiTeamUpgrade} from 'react-icons/gi';
import {FaChild} from 'react-icons/fa';
import {GiFamilyHouse} from 'react-icons/gi'


const WidgetsDropdown = () => {
    const  [summaryData, setSummaryData] = useState({});
    const id = window.localStorage.getItem('selectedProjectAtLogin').replaceAll('"','');

    useEffect(() => {
        // GET request using axios inside useEffect React hook
        console.log(`${url}dashboard/summary/${id}`)
        axios
            .get(`${url}dashboard/summary/${id}`)
            .then(response => setSummaryData(response.data))
            .catch(error => console.log(error));

        // empty dependency array means this effect will only run once (like componentDidMount in classes)
        }, []);

  // render
  return (
    <CRow>
        <CCol sm="6" lg="3">
        <CWidgetDropdown
            color="twitter"
            header={summaryData.totalHousehold}
            text="Total Household"
            footerSlot={
                <div
                    className="mt-3 mx-3"
                    style={{height: '70px'}}
                >
                </div>
            }
        >
            <CDropdown>
                <GiFamilyHouse />
            </CDropdown>
        </CWidgetDropdown>
        </CCol>

        <CCol sm="6" lg="3">
            <CWidgetDropdown
                color="vimeo"
                header={summaryData.totalVc}
                text="Total VC"
                footerSlot={
                    <div
                        className="mt-3 mx-3"
                        style={{height: '70px'}}
                    >
                    </div>
                }
            >
                <CDropdown>
                    <FaChild />
                </CDropdown>
            </CWidgetDropdown>
        </CCol>

      <CCol sm="6" lg="3">
              <CWidgetDropdown
                  color="danger"
                  header={summaryData.totalPositive}
                  text="Total VC Positive"
                  footerSlot={
                      <div
                          className="mt-3 mx-3"
                          style={{height: '70px'}}
                      >
                      </div>
                  }>
                  <CDropdown>
                      <GiTeamUpgrade />
                  </CDropdown>
              </CWidgetDropdown>
          </CCol>

        <CCol sm="6" lg="3">
            <CWidgetDropdown
                color="xing"
                header={summaryData.totalLinked}
                text="Total VC Linked to Care"
                footerSlot={
                    <div
                        className="mt-3 mx-3"
                        style={{height: '70px'}}
                    >
                    </div>
                }>
                <CDropdown>
                    <GiTeamUpgrade />
                </CDropdown>
            </CWidgetDropdown>
        </CCol>




    </CRow>
  )
}

export default WidgetsDropdown
