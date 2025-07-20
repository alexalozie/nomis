import React from "react";
import {Tab} from "semantic-ui-react";
import CarePlan from "./CarePlan";
import Assessment from "./Assessment";
import './tab.css';

const AssessmentCarePlanHome = (props) => {

    const [index, setIndex] = React.useState(0);
    const handleTabChange = (e, { activeIndex }) => setIndex(activeIndex);
    const panes = [
        {
            menuItem: { key: 'careplan', icon: 'medkit', content: 'Care Plans' },
            render: () => <CarePlan householdId={props.householdId}/>,
        },
        {
            menuItem: { key: 'users', icon: 'folder open', content: 'Achievement Checklist' },
            render: () => <Assessment setIndex={setIndex} householdId={props.householdId}/>,
        }

    ]

    return (
        <Tab
          //  menu={{ color:"red", inverted: true  }}
         //   menu={{ fluid: true, vertical: true, tabular: 'left' }}
            panes={panes} activeIndex={index}  onTabChange={handleTabChange}/>
    );
}

export default AssessmentCarePlanHome;