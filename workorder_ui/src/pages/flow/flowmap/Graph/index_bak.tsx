import React, {useRef, useContext, useCallback, DragEvent, MouseEvent as ReactMouseEvent} from "react";
import ReactFlow, {
  addEdge, applyEdgeChanges, applyNodeChanges,
  Connection,
  Controls,
  Edge, EdgeTypes, MarkerType, Node, NodeOrigin, Position,
  ReactFlowInstance,
  useEdgesState,
  useNodesState
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

const initialNodes: Node[] = [
  {
    id: '1',
    type: 'input',
    data: { label: 'input node' },
    position: { x: 250, y: 5 },
  },
];
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

let id = 0;
const getId = () => `dndnode_${id++}`;
const nodeOrigin: NodeOrigin = [0.5, 0.5];

const getHash = async (len: number) => {
  let length = Number(len) || 8;
  const arr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".split("");
  const al = arr.length;
  let chars = "";
  while (length--) {
    chars += arr[parseInt(Math.random() * al, 10)];
  }
  return chars;
}

export const FlowGraph = () => {
  const { state, dispatch } = useContext(FlowContext);
  const { reactFlowInstance } = state;
  //const [reactFlowInstance, setReactFlowInstance] = useState<ReactFlowInstance>();
  //let reactFlowInstance: ReactFlowInstance;
  const [nodes, setNodes] = useNodesState([]);
  const [edges, setEdges] = useEdgesState([]);
  const setReactFlowInstance = (instance: ReactFlowInstance) => {
    dispatch({
      type: Actions.SET_INSTANCE,
      payload: instance,
    });
  };

  // 连线
  const onConnect = (params: Connection | Edge) => setEdges((eds) => addEdge(
    {...params,
      type: "custom",
      markerEnd: {type: MarkerType.Arrow},
      data: { text: 'custom edge' },
    }, eds));

  const onNodesChange = useCallback(
    (changes) => setNodes((ns) => applyNodeChanges(changes, ns)),
    []
  );
  const onEdgesChange = useCallback(
    (changes) => setEdges((es) => applyEdgeChanges(changes, es)),
    []
  );

  // 画布加载完毕，保存当前画布实例
  const onInit = (instance: ReactFlowInstance) => setReactFlowInstance(instance);
  //const onLoad = (instance: ReactFlowInstance) => setReactFlowInstance(instance);

  const onNodeClick = (event: ReactMouseEvent, node: any) =>{
    console.log('-----onNodeClick',node)
    dispatch({
      type: Actions.SET_SELECTED_NODE,
      payload: node,
    });
  }

  const onPaneClick = ()=>{
    dispatch({
      type: Actions.SET_SELECTED_NODE,
      payload: undefined,
    });
  }

  // 画布的 DOM 容器，用于计算节点坐标
  const graphWrapper = useRef(null);

  const onDrop = (event: DragEvent) => {
    event.preventDefault();
    if (reactFlowInstance) {
      const reactFlowBounds = graphWrapper.current.getBoundingClientRect();
      const type = event.dataTransfer.getData('application/reactflow');
      const name = event.dataTransfer.getData('application/reactflow/name');
      const position = reactFlowInstance.project({
        x: event.clientX-reactFlowBounds.left,
        y: event.clientY-reactFlowBounds.top,
      });
      const nodeId = getId();
      const newNode: Node = {
        id: nodeId,
        type,
        position,
        data: {id:nodeId, label: name,type: type},
      };

      setNodes((nds) => nds.concat(newNode));
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
        /**onSelectionChange={(selectedElements) => {
          const node = selectedElements?.[0] as Node
          setSelectedNode(node)
        }}**/
        onPaneClick={onPaneClick}
        onConnect={onConnect}
        onInit={onInit}
        onDrop={onDrop}
        onDragOver={onDragOver}
        nodeOrigin={nodeOrigin}
        deleteKeyCode={['Delete']}
        selectNodesOnDrag={false}
      >
        <Controls />
      </ReactFlow>
    </div>
  );
}
export default FlowGraph
