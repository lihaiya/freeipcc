// Modal/index.jsx

import React, { useContext, useRef } from "react";
import { Modal } from "antd";
import RelationNodeForm from "./RelationNodeForm";
import { FlowContext, Actions } from "../../context";
import EdgeEditForm from "@/pages/flow/flowmap/components/Modal/EdgeEditForm";

// 通过节点类型来切换对应的表单组件
const componentsMap = {
  node: RelationNodeForm,
  edge: EdgeEditForm,
};

export const FlowModal: React.FC = () => {
//export default function FlowModal() {
  const formRef = useRef();
  const { state, dispatch } = useContext(FlowContext);
  const { modalConfig } = state;

  const handleOk = () => {
    // 组件内部需要暴露一个 submit 方法
    formRef.current.submit().then(() => {
      dispatch({ type: Actions.CLOSE_MODAL });
    });
  };

  const handleCancel = () => dispatch({ type: Actions.CLOSE_MODAL });

  const Component = componentsMap[modalConfig.itemType];

  return (
    <Modal width={900} title="编辑节点" visible={modalConfig.visible} onOk={handleOk} onCancel={handleCancel}>
      {Component && <Component ref={formRef} />}
    </Modal>
  );
}
export default FlowModal
