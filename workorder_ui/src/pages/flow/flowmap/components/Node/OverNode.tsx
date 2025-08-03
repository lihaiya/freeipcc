import React, {FC, memo, useMemo} from "react";
import {Handle, NodeProps, Position} from "reactflow";
import {Typography} from "antd";

const CustomNode: FC<NodeProps> = ({data, isConnectable=true }) => {
  const handleVisability = true;
  const nodeStyle = {
    border: "1px solid",
    borderRadius: 10,
    width: 100,
    display: "flex",
    padding: 10,
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
    alignSelf: "center",
    backgroundColor: data.backgroundColor ? data.backgroundColor : "#ffffff",
    borderColor: data.borderColor ? data.borderColor : "#000000"
  };

  const handleStyle = useMemo(
    () => ({
      height: handleVisability ? 10 : 0,
      width: handleVisability ? 10 : 0
    }),
    [handleVisability]
  );
  const handleTargetStyle = useMemo(
    () => ({
      height: handleVisability ? 10 : 0,
      width: handleVisability ? 10 : 0,
      background: '#ff0072',
    }),
    [handleVisability]
  );

  const handleStyleSideBottom = useMemo(
    () => ({
      height: handleVisability ? 10 : 0,
      width: handleVisability ? 10 : 0,
      //top: "70%",
      background: '#ff0072'
    }),
    [handleVisability]
  );

  const handleStyleSideTop = useMemo(
    () => ({
      height: handleVisability ? 8 : 0,
      width: handleVisability ? 8 : 0,
      //top: "30%"
    }),
    [handleVisability]
  );

  const header = useMemo(
    () => ({
      fontSize: 10
    }),
    []
  );

  return (
    <div className="relation-node">
      <Handle
        style={handleTargetStyle}
        type="target"
        id="leftBottom"
        position={Position.Left}
        isConnectable={isConnectable}
      />
      <Typography  style={header}>
        {data.displayName || data.label}
      </Typography>
    </div>
  );
}

export default memo (CustomNode);
