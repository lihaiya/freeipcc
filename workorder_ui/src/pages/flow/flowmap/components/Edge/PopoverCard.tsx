import React, { useContext } from "react";
import { FlowContext } from "../../context";

export default function PopoverCard({ edge, source, target }) {
  const {
    state: { flowData },
  } = useContext(FlowContext);

  const sourceNode = flowData.get(source);
  const targetNode = flowData.get(target);

  return (
    <div className="linkedge-card">
      <div className="linkedge-card-item">
        <div className="linkedge-card-item__title">{sourceNode?.label}</div>
        <div className="linkedge-card-item__tips">{sourceNode?.remark}</div>
      </div>
      <div className="linkedge-card-item__icon">-></div>
      <div className="linkedge-card-item">
        <div className="linkedge-card-item__title">{targetNode?.label}</div>
        <div className="linkedge-card-item__tips">{targetNode?.remark}</div>
      </div>
    </div>
  );
}
