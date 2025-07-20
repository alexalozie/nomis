import React from "react";
import {Tab} from "semantic-ui-react";
import CarePlan from "./CarePlan";
import Assessment from "./Assessment";
import ProvideService from "./ProvideService";

const ServiceHome = (props) => {

    const [index, setIndex] = React.useState(0);
    const handleTabChange = (e, { activeIndex }) => setIndex(activeIndex);
    const panes = [
        {
            menuItem: 'Provide Service',
            render: () => <ProvideService setIndex={setIndex} member={props.member.id}/>,
        },
        {
            menuItem: 'Service History',
            render: () => <CarePlan householdId={props.householdId}/>,
        }
    ]

    return (
        <Tab menu={{ secondary: true, pointing: true }} panes={panes} activeIndex={index}  onTabChange={handleTabChange}/>
    );
}

export default ServiceHome;