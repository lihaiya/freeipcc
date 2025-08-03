import React, {useEffect, useState} from 'react';
import {
  ReactFlowProvider,
} from 'reactflow';
import { FlowContextProvider} from './context';

import Sidebar from './Sidebar';
import 'reactflow/dist/style.css';
import './flow.css';
import './flownode.css';
import Toolbar from "@/pages/flow/flowmap/Toolbar";
import Graph from './Graph';
import Modal from "@/pages/flow/flowmap/components/Modal";
import {useLocation} from "umi";

export interface IProps{
  meta: { flowId: string,name: string }
}
const DnDFlow: React.FC<IProps> = (props) => {
  const { meta } = props
  const location = useLocation();
  useEffect(() => {
    console.log('>>>>>>>>>>>>>>props.useLocation=',location)
    //props.meta.flowId=location.state.flowId
    //props.meta.name = location.state.name
    //console.log('---index: flowId=,name=',meta.flowId,meta.name)
  }, [props])

  return (
    <div className="container">
      <FlowContextProvider>
        <ReactFlowProvider>
          <Toolbar flowId={location.state.flowId} name={location.state.name}/>
          <div className="main">
            <Graph flowId={location.state.flowId} name={location.state.name}/>
          </div>
          <Modal />
        </ReactFlowProvider>
      </FlowContextProvider>
    </div>
  );
};

export default DnDFlow;
DnDFlow.defaultProps = {
  meta: { flowId: 'test-meta-flow-id',name: 'test-meta-flow-name' },
}
