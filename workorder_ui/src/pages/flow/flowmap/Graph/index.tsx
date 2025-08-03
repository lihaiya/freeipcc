import React, {
  useRef,
  useContext,
  useCallback,
  DragEvent,
  MouseEvent as ReactMouseEvent,
  useEffect,
  useState
} from "react";
import ReactFlow, {
  addEdge, applyEdgeChanges, applyNodeChanges,
  Connection,
  Controls,
  Edge, EdgeTypes, MarkerType, Node, NodeOrigin, Position,
  ReactFlowInstance,
  useEdgesState,
  useNodesState,
  useReactFlow
} from "reactflow";

// 引入 Provider
import { FlowContext, Actions } from '../context';
import ColorSelectorNode from '../components/Node/ColorSelectorNode';
import CustomEdge from '../components/Edge/CustomEdge';
import CustomEdge2 from '../components/Edge/CustomEdge2';
import RelationNode from "../components/Node/RelationNode";
import CustomerNode from "../components/Node/CustomerNode";
import InNode from "../components/Node/InNode";
import OverNode from "../components/Node/OverNode";
import {addIvrFlow, addIvrFlowStep, getIvrFlow, getIvrFlowStep, removeIvrFlowStep} from '../dataservice';
import UpdateForm from '../components/Modal/nodeEdit';
import {NodeType} from "@/pages/flow/flowmap/components/Modal/data";
import {message} from "antd";
import {IvrFlowType} from "@/pages/flow/flowmanager/data";
import {getAllIvrFlowList} from "@/pages/flow/flowmanager/service";
import {getAllGroupList} from "@/pages/cc/group/service";

const initialNodes = [
  {
    id: '0',
    type: 'input',
    data: { label: '开始', seq: 0 , text: '',pid: '-1'},
    position: { x: 10, y: 50 },
    deletable: false,
    sourcePosition: Position.Right,
  },
];

export type UserFormProps = {
  flowId: number;
  name: string;
};
const nodeTypes = {
  selectorNode: ColorSelectorNode,
};
const customerNodeTypes = {
  customernode: CustomerNode,
  innode: InNode,
  overnode: OverNode,
};

// 自定义节点
const nodeRltTypes = {
  relation: RelationNode,
};

const edgeTypes: EdgeTypes = {
  custom: CustomEdge,
  custom2: CustomEdge2,
};

const onDragOver = (event: DragEvent) => {
  event.preventDefault();
  event.dataTransfer.dropEffect = 'move';
};

let id = 1;
const getId = () => `${id++}`;
const nodeOrigin: NodeOrigin = [0.5, 0.5];

const getHash = (len: number) => {
  let length = Number(len) || 8;
  const arr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".split("");
  const al = arr.length;
  let chars = "";
  while (length--) {
    chars += arr[parseInt(Math.random() * al, 10)];
  }
  return chars;
}

const fitViewOptions = {
  padding: 3,
};

export const FlowGraph:  React.FC<UserFormProps> = (props) => {
  const { state, dispatch } = useContext(FlowContext);
  const { reactFlowInstance } = state;
  const { flowId, name } = props;
  // 画布的 DOM 容器，用于计算节点坐标
  const graphWrapper = useRef(null);
  const connectingNodeId = useRef(null);
  const flowInstance = useRef(null);
  const { project } = useReactFlow();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<NodeType>();
  const [ivrOptions, setIvrOptions] = useState<any>([]);
  const [queueOptions, setQueueOptions] = useState<any>([]);
  const [currentNode, setCurrentNode] = useState<Node>();
  const [currentEdge, setCurrentEdge] = useState<Edge>();
  //const [reactFlowInstance, setReactFlowInstance] = useState<ReactFlowInstance>();
  //let reactFlowInstance: ReactFlowInstance;
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);
  const { setViewport } = useReactFlow();
  useEffect(() => {
    id = 1;
    console.log('---graph flow=,props',flowId,props)
    if (flowId != undefined){
      getIvrFlow(flowId).then((res) => {
        if (res.code === 200 && res.data.content!='' && res.data.content != null && res.data.content != undefined) {
          const flow = JSON.parse(res.data.content) || { nodes: [], edges: [] }
          if (flow) {
            const { x = 0, y = 0, zoom = 1 } = flow.viewport;
            setNodes(flow.nodes || []);
            setEdges(flow.edges || []);
            setViewport({ x, y, zoom });
          }
        }
      });
      getAllIvrFlowList().then((res) => {
        if (res.code === 200) {
          const opts = {};
          res.data.forEach((item: any) => {
            opts[item.id] = item.name;
          });
          setIvrOptions(opts);
        }
      });
      getAllGroupList().then((res) => {
        if (res.code === 200) {
          const opts = {};
          res.data.forEach((item: any) => {
            opts[item.id] = item.name;
          });
          setQueueOptions(opts);
        }
      });
    }
  }, [props]);


  const setReactFlowInstance = (instance: ReactFlowInstance) => {
    flowInstance.current = instance;
    dispatch({
      type: Actions.SET_INSTANCE,
      payload: instance,
    });
  };
  // 画布加载完毕，保存当前画布实例
  const onInit = (instance: ReactFlowInstance) => setReactFlowInstance(instance);
  //const onConnect = useCallback((params) => setEdges((eds) => addEdge(params, eds)), []);
  // 连线
  const onConnect = (params: Connection | Edge) => setEdges((eds) => addEdge(
    {...params,
      type: "custom",
      markerEnd: {type: MarkerType.Arrow},
      data: { text: '默认' },
    }, eds));

  const onConnectStart = useCallback((_, { nodeId }) => {
    connectingNodeId.current = nodeId;
  }, []);

  const onConnectEnd = useCallback(
    (event) => {
      const targetIsPane = event.target.classList.contains('react-flow__pane');
      const node = flowInstance.current.getNode(connectingNodeId.current)
      const seq = node.data.seq +1
      node.data.seq =seq;
      console.log('---get start node = ',node)
      if (targetIsPane) {
        // we need to remove the wrapper bounds, in order to get the correct position
        const { top, left } = graphWrapper.current.getBoundingClientRect();
        const nodeid = getHash(8);
        console.log('--graph getid=',nodeid)
        //const type="customernode"
        const newNode = {
          id: nodeid,
          type: 'default',
          // we are removing the half of the node width (75) to center the new node
          position: project({ x: event.clientX - left - 75, y: event.clientY - top }),
          sourcePosition: Position.Right,
          targetPosition: Position.Left,
          data: { label: `Node ${seq}`, seq: 0, pid: connectingNodeId.current, text: '默认'},
        };

        setNodes((nds) => nds.concat(newNode));
        setEdges((eds) => eds.concat({ id: nodeid, source: connectingNodeId.current, target: nodeid, targetHandle: 'leftBottom' , type: "custom", markerEnd: {type: MarkerType.Arrow},data: { text: '默认' }}));
      }
    },
    [project]
  );

  const onNodeClick = (event: ReactMouseEvent, node: any) =>{
    console.log('-----onNodeClick',node)
    /**dispatch({
      type: Actions.SET_SELECTED_NODE,
      payload: node,
    });**/
  }

  const handleRemove = async (nodeType: NodeType) => {
    const hide = message.loading('正在删除节点');
    try {
      const resp = await removeIvrFlowStep(nodeType);
      hide();
      const ivrFlow: IvrFlowType ={
        id: flowId,
        content: JSON.stringify(reactFlowInstance.toObject()),
      }
      addIvrFlow(ivrFlow).then(()=>{
        message.success('节点删除成功');
      })
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  const onNodesDelete =(nodes: Node[]) =>{
    const nodeId = nodes[0].id;
    const nodeType: NodeType ={
      flowId: flowId,
      nodeId: nodeId,
      flowContent: JSON.stringify(reactFlowInstance.toObject()),
    }
    handleRemove(nodeType)
  }

  const onEdgeDoubleClick = (event: ReactMouseEvent, edge: Edge)=>{
    console.log('---select edge:',edge)
    dispatch({
      type: Actions.OPEN_MODAL,
      payload: {
        id: edge.id,
        type: "edge",
        item: edge,
      },
    });
  }

  const onNodeDoubleClick = (event: ReactMouseEvent, node: Node)=> {
    setCurrentNode(node);
    setCurrentEdge(undefined);
    let edgeText = ""
    if(node.id !='0'){
      const tEdge = state.reactFlowInstance.getEdge(node.id)
      setCurrentEdge(tEdge);
      edgeText = tEdge.data.text
    }
    getIvrFlowStep(flowId, node.id).then((res) => {
      if (res.code === 200) {
        if(res.data == null || res.data==undefined){
          res.data={
            ...res.data,
            flowId: flowId,
            nodeId: node.id,
            enterValue: edgeText,
            nodeLabel: node.data.label,
            pid: node.data.pid,
            dtmfMax: 1,
            dtmfTimeout: 3,
            dtmfTries: 3,
            dtmfTerminators: '#',
          }
        }
        setCurrentRow(res.data)
        setModalVisible(true);
      }
    });
    /**const nodeType: NodeType ={
      flowId: flowId,
      nodeId: node.id,
      enterValue: edgeText,
      nodeLabel: node.data.label,
      pid: node.data.pid,
      optType: '',
      playType: '',
      playFile: '',
      ttsInput: '',
      toTarget: '',
      scriptContent: '',
      dtmfMax: 1,
      dtmfTimeout: 3,
      dtmfTries: 3,
      dtmfTerminators: '#',
      flowContent: '',
    }
    setCurrentRow(nodeType)
    setModalVisible(true);**/

    /**node.data.text = tEdge.data.text
     dispatch({
      type: Actions.OPEN_MODAL,
      payload: {
        id: node.id,
        type: "node",
        item: node,
      },
    });**/
  }

  const setFlowNode = (label: string) =>{
    console.log('---setFlowNode nodeid=',currentNode?.id)
    setNodes((nds) =>
      nds.map((node) => {
        if (node.id === currentNode?.id) {
          // it's important that you create a new object here
          // in order to notify react flow about the change
          node.data = {
            ...node.data,
            label: label,
          };
        }
        return node;
      })
    );
  }

  const setFlowEdge = (label: string) =>{
    console.log('---setflowedge edigeid=',currentEdge?.id)
    setEdges((eds) =>
      eds.map((edge) => {
        if (edge.id === currentEdge?.id) {
          // it's important that you create a new object here
          // in order to notify react flow about the change
          edge.data = {
            ...edge.data,
            text: label,
          };
        }
        return edge;
      })
    );
  }

  const onPaneClick = ()=>{
    dispatch({
      type: Actions.SET_SELECTED_NODE,
      payload: undefined,
    });
  }

  const handleUpdate = async (fields: IvrFlowType, nodeFields: NodeType) => {
    const hide = message.loading('正在配置');
    nodeFields.flowContent = fields.content;
    try {
      console.log("-----------scriptContent="+nodeFields.scriptContent)
      const resp = await addIvrFlowStep(nodeFields);
      hide();
      if (resp.code === 200) {
        message.success('节点配置成功');
      } else {
        message.error(resp.msg);
      }
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  return (
    <div className="graph" ref={graphWrapper}>
      <ReactFlow
        nodes={nodes}
        edges={edges}
        nodeTypes={customerNodeTypes}
        edgeTypes={edgeTypes}
        onEdgesChange={onEdgesChange}
        onNodesChange={onNodesChange}
        onNodeClick={onNodeClick}
        onNodesDelete={onNodesDelete}
        onEdgeDoubleClick={onEdgeDoubleClick}
        onNodeDoubleClick={onNodeDoubleClick}
        /**onSelectionChange={(selectedElements) => {
          const node = selectedElements?.[0] as Node
          setSelectedNode(node)
        }}**/
        onPaneClick={onPaneClick}
        onConnect={onConnect}
        onConnectStart={onConnectStart}
        onConnectEnd={onConnectEnd}
        onInit={onInit}
        deleteKeyCode={['Delete']}
        selectNodesOnDrag={false}
        fitViewOptions={fitViewOptions}
      >
        <Controls />
      </ReactFlow>
      <UpdateForm
        onSubmit={async (values) => {
          let success = false;
          setFlowNode(values.nodeLabel)
          console.log('values.enterValue=',values.enterValue)
          if(values.enterValue !='' && values.enterValue!=undefined){
            setFlowEdge(values.enterValue)
          }
          if (values.flowId) {
            const parm: IvrFlowType = {
              id: flowId,
              content: JSON.stringify(reactFlowInstance.toObject())
            }
           success = await handleUpdate(parm, { ...values } as NodeType);
          } else {
            message.error('未获取到流程ID！');
            setModalVisible(false);
          }
          //setModalVisible(false);
          if (success) {
            setModalVisible(false);
          }
        }}
        onCancel={() => {
          setModalVisible(false);
        }}
        visible={modalVisible}
        values={currentRow || {}}
        ivrOptions={ivrOptions}
        queueOptions={queueOptions}
      />
    </div>
  );
}
export default FlowGraph
