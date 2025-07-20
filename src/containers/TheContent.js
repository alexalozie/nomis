import React, { Suspense } from 'react';
import { useState } from "react";
import {
  Redirect,
  Route,
  Switch
} from 'react-router-dom'
import { CContainer, CFade } from '@coreui/react'
import { Spinner } from 'reactstrap';

// routes config
import routes from '../routes'
import {PrivateRoute} from "../PrivateRoute";
import _ from 'lodash';
// import { authentication } from "./../_services/authentication";

// const RoleRoute = ({ role, roles = [], ...props }) => {
//     return authentication.userHasRole(role) ? (
//         <Route {...props} />
//     ) : (
//         <Redirect to=".." />
//     );
// };


const loading = (
  <div className="pt-10 text-center">
    <div className="sk-spinner sk-spinner-pulse">
    <Spinner style={{ width: '3rem', height: '3rem' }} />
    </div>
  </div>
)

const roles = ["Reviewer", "DEC", "Assessing Officer", "M&E Officer", "Community case work", "System Administrator"];

const TheContent = () => {
      return (
    <main className="c-main">
      <CContainer fluid>
        <Suspense fallback={loading}>
          <Switch>
            {routes.map((route, idx) => {
              return route.component && (
                  route.isPrivate ?
                      <PrivateRoute
                          key={idx}
                          exact path={route.path}
                          name={route.name}
                          component={route.component}  />
                    :
                <Route
                  key={idx}
                  exact path={route.path}
                  name={route.name}
                  component={route.component}/>
              )
            })}
            <Redirect from="/" to="/" />
          </Switch>
        </Suspense>
      </CContainer>
    </main>
  )
}

export default React.memo(TheContent)
