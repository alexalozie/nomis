import React from 'react'
import { CFooter } from '@coreui/react'

const TheFooter = () => {
  return (
    <CFooter fixed={false}>
      <div>
        <a href="" target="_blank" rel="noopener noreferrer"> </a>
        <span className="ml-1">&copy; </span> Version 1.0
      </div>
      <div className="mfs-auto">
        <span className="mr-1">Powered by</span>
        <a href="" target="_blank" rel="noopener noreferrer">NOMIS</a>
      </div>
    </CFooter>
  )
}

export default React.memo(TheFooter)
