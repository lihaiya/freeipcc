import React, { DragEvent } from 'react';
import classnames from 'classnames';

// 可用节点
const allowedNodes = [
  {
    name: '开始',
    className: "input-node",
    type: 'innode',
  },
  {
    name: '处理',
    className: "default-node",
    type: 'customernode', // 这是自定义节点类型
  },
  {
    name: '结束',
    className: "output-node",
    type: 'overnode',
  },
];
const onDragStart = (event: DragEvent, nodeType: string, name: string) => {
  event.dataTransfer.setData('application/reactflow', nodeType);
  event.dataTransfer.setData('application/reactflow/name', name);
  event.dataTransfer.effectAllowed = 'move';
};

const Sidebar = () => {
  return (
    <div className="sider">
      <div className="nodes">
        {allowedNodes.map((x, i) => (
          <div
            key={`${x.type}-${i}`}
            className={classnames(["sider-node", x.className])}
            onDragStart={e => onDragStart(e, x.type,x.name)}
            draggable
          >
            {x.name}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Sidebar;
