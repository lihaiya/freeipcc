import React from 'react';
import classnames from 'classnames';
import { useStore  } from 'reactflow';

// 可用节点
const allowedNodes = [
  {
    name: 'Input Node',
    className: "input-node",
    type: 'input',
  },
  {
    name: 'Relation Node',
    className: "relation-node",
    type: 'relation', // 这是自定义节点类型
  },
  {
    name: 'Output Node',
    className: "output-node",
    type: 'output',
  },
];
const FlowSider = () =>{
  // 获取画布上的节点
  const nodes = useStore ((store) => store.transform);
  const onDragStart = (evt: any, nodeType: any) => {
    // 记录被拖拽的节点类型
    evt.dataTransfer.setData('application/reactflow', nodeType);
    evt.dataTransfer.effectAllowed = 'move';
  };

  return (
    <div className="sider">
      <div className="nodes">
        {allowedNodes.map((x, i) => (
          <div
            key={`${x.type}-${i}`}
            className={classnames(["sider-node", x.className])}
            onDragStart={e => onDragStart(e, x.type)}
            draggable
          >
            {x.name}
          </div>
        ))}
      </div>
    </div>
  );
}
export default FlowSider
