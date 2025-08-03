// context/index.js
import React, { createContext, useReducer } from 'react';
import reducer from './reducer';
import { globalActionTypes as Actions } from './actions';

const initState = {
  // 画布实例
  reactFlowInstance: null,
  selectedItem: null,
  // 节点数据、连线数据
  elements: [],
  // 画布数据
  flowData: new Map(),
  // 弹窗信息
  modalConfig: {
    visible: false,
    itemType: '',
    itemId: '',
    text:'',
    item: undefined,
  },
};
const FlowContext = createContext({});

const FlowContextProvider: React.FC = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initState);
  return (
    <FlowContext.Provider value={{ state, dispatch }}>
      {children}
    </FlowContext.Provider>
  );
}

export { FlowContext, FlowContextProvider, Actions };






