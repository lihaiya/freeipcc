// context/reducer.js
import { globalActionTypes as Actions } from './actions';
import { ReactFlowInstance,NodeProps } from 'reactflow';
import {Edge} from "bizcharts";

// 保存画布实例
const setInstance = (state: any, reactFlowInstance: ReactFlowInstance) => ({
  ...state,
  reactFlowInstance,
});

// 设置节点/连线数据
const setElements = (state: any, elements: any) => ({
  ...state,
  elements: Array.isArray(elements) ? elements : [],
});

const setFlowEdge = (state: any, edge: any) => {
  const edgeId = edge?.id;
  if (!edgeId) return state;
  const tEdge = state.reactFlowInstance.getEdge(edgeId)
  tEdge.data.text= edge.text
  tEdge.data = {
    ...tEdge.data,
    text: edge.text,
  };
  return state;
};

// 保存节点配置信息
const setFlowNode = (state: any, node: any) => {
  console.log('---setFlowNode=,',node)
  const nodeId = node?.id;
  if (!nodeId) return state;
  state.flowData.set(nodeId, node);
  const tnode  = state.reactFlowInstance.getNode(nodeId)
  tnode.data.label= node.label
  tnode.data = {
    ...tnode.data,
    label: node.label,
  };
  return setFlowEdge(state,node)
  //return state;
};



//当前选中的节点
const setSelectedNode = (state: any, node: NodeProps) =>{
  if(node == undefined){
    state.selectedItem = undefined
    return state;
  }
  const nodeId = node?.id;
  if (!nodeId) return state;
  state.selectedItem = node
  console.log('--------invoke setSelectedNode=',state.selectedItem)
  return state;
}

// 删除节点，同时删除节点配置信息
const removeFlowNode = (state: any, node: NodeProps) => {
  const { id } = node;
  const { flowData } = state;
  const res = { ...state };

  if (flowData.get(id)) {
    flowData.delete(id);
    //res.elements = removeElements([node], state.elements);
  }
  return res;
};

const openModal = (state: any, payload: any) =>
  payload?.id
    ? {
        ...state,
        modalConfig: {
          visible: true,
          itemType: payload.type,
          itemId: payload.id,
          item: payload.item,
        },
      }
    : state;

const closeModal = (state: any) => ({
  ...state,
  modalConfig: {
    visible: false,
    itemType: "",
    itemId: "",
    item: undefined,
  },
});

// 管理所有处理函数
const handlerMap = {
  [Actions.SET_INSTANCE]: setInstance,
  [Actions.SET_FLOW_NODE]: setFlowNode,
  [Actions.SET_FLOW_EDGE]: setFlowEdge,
  [Actions.REMOVE_FLOW_NODE]: removeFlowNode,
  [Actions.OPEN_MODAL]: openModal,
  [Actions.CLOSE_MODAL]: closeModal,
  [Actions.SET_ELEMENTS]: setElements,
  [Actions.SET_SELECTED_NODE]: setSelectedNode,
};

const reducer = (state: any , action: any) => {
  console.log('-----------------invoke reduce=',action)
  const { type, payload } = action;
  const handler = handlerMap[type];
  const res = typeof handler === "function" && handler(state, payload);
  return res || state;
}
export default reducer
/**export const reducer = (state, action) => {
  const { type, payload } = action;
  const handler = handlerMap[type];
  const res = typeof handler === "function" && handler(state, payload);
  return res || state;
};**/
