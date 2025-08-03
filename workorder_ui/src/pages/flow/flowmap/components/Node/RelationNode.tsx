
import React, { memo, FC, CSSProperties, useCallback,useContext } from 'react';
import { Handle,NodeProps } from "reactflow";
import { FlowContext } from "../../context";

const RelationNode: FC<NodeProps> = ({data, isConnectable=true }) => {
  const { dispatch, state } = useContext(FlowContext);
  const currentNode = state.flowData.get(id) || {};

  return (
    <div className="relation-node">
      <div className="relation-node-title">{data.label}</div>
      {/* 提供一个入口和一个出口 */}
      <Handle type="target" position="top" isConnectable={isConnectable} />
      <Handle type="source" position="bottom" isConnectable={isConnectable} />
    </div>
  );
};

export default React.memo(RelationNode);
