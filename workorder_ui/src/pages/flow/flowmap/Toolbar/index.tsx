import React, {useContext, useEffect} from "react";
import classnames from "classnames";
import { FlowContext,Actions } from "../context";
import {message} from "antd";
import { addIvrFlow } from '../dataservice';
import {IvrFlowType} from "@/pages/flow/ivr/data";

export type UserFormProps = {
  flowId: string;
  name: string;
};

const Toolbar: React.FC<UserFormProps> = (props) => {
  const { state, dispatch } = useContext(FlowContext);
  const { reactFlowInstance } = state;
  const { flowId, name } = props;

  const handleEdit = () => {
    const { selectedItem } = state;
    console.log('----selectedNode=',selectedItem)
    if(selectedItem == null || selectedItem==undefined){
      message.warn("Nothing selected");
      return
    }
    console.log('selectred node=', selectedItem);
    console.log('reactFlowInstance', reactFlowInstance);
    dispatch({
      type: Actions.OPEN_MODAL,
      payload: {
        id: selectedItem.id,
        type: "relation",
        item: selectedItem,
      },
    });
  };

  // 保存
  const handleSave = async() => {
    const parm: IvrFlowType = {
      id: parseInt(flowId),
      content: JSON.stringify(reactFlowInstance.toObject())
    }
    console.log('---save flow:', parm);
    const resp = await addIvrFlow({ ...parm });
    if(resp.code === 200) {
      message.success('保存成功');
    } else {
      message.error(resp.msg);
    }
  };

  // 重置节点
  // const handleRest = () => {};

  return (
    <div className="toolbar">
      {/* <button className={classnames(["button"])} onClick={handleRest}>
        重置
      </button> */}
      <div className="toolbar-title">{flowId}-{name}</div>
      <div className="toolbar-button">
        <button className={classnames(["button"])} onClick={handleSave}>
          保存
        </button>
      </div>
    </div>
  );
}
export default Toolbar
