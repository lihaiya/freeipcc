import React, {useEffect, useState} from "react";
import {Form, Row, Col, Modal} from "antd";
import {FormattedMessage, useIntl} from "umi";
import { ProFormSelect,ProFormText } from "@ant-design/pro-components";
import AceEditor from "react-ace";
import ace from 'ace-builds';
import "ace-builds/src-noconflict/mode-javascript";
import "ace-builds/src-noconflict/theme-solarized_dark";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";

import {ProFormDigit} from "@ant-design/pro-form";
import {NodeType} from "@/pages/flow/flowmap/components/Modal/data";

export type NodeFormValueType = Record<string, unknown> & Partial<NodeType>;

export type NodeFormProps = {
  onCancel: (flag?: boolean, formVals?: NodeFormValueType) => void;
  onSubmit: (values: NodeFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<NodeType>;
  enterValue: string;
  nodeLabel: string;
  optType: string;
  playType: string;
  playFile: string;
  ttsInput: string;
  toTarget: string;
  scriptContent: string;
  dtmfLength: string;
  ivrOptions: any;
  queueOptions: any;
};

const NodeForm: React.FC<NodeFormProps> = (props) => {
  const [form] = Form.useForm();
  const { ivrOptions,queueOptions } = props;
  const [flowId, setFlowId] = useState<any>('');
  const [scriptValue, setScriptValue] = useState<any>('');
  const [showEnterValue, setShowEnterValue] = useState<boolean>(false);
  const [showTransferTo, setShowTransferTo] = useState<boolean>(false);
  const [showPlayType, setPlayType] = useState<boolean>(false);
  const [showDtmf, setShowDtmf] = useState<boolean>(false);
  const [showScript, setShowScript] = useState<boolean>(false);
  const [showTTSInput, setShowTTSInput] = useState<boolean>(false);
  const [showPlayFile, setShowPlayFile] = useState<boolean>(false);
  const [showTargetToIvr, setTargetToIvr] = useState<boolean>(false);
  const [showTargetToQueue, setTargetToQueue] = useState<boolean>(false);
  const [showTargetToMobile, setTargetToMobile] = useState<boolean>(false);
  const [showTargetToScript, setTargetToScript] = useState<boolean>(false);

  const intl = useIntl();

  const handleNodeChange = (value: string) =>{
    //console.log('----selected value=',value)
    setShowTransferTo(false);
    setShowTTSInput(false)
    setShowPlayFile(false)
    setShowScript(false);
    setPlayType(false);
    setShowDtmf(false);

    setTargetToIvr(false);
    setTargetToQueue(false);
    setTargetToMobile(false);
    setTargetToScript(false);

    if(value == 'dtmf'){
      setShowDtmf(true);
      setPlayType(true);
    }else if(value == 'transfer'){
      setShowTransferTo(true);
    }else if(value == 'script'){
      setShowScript(true);
    }
  }

  const handleTransferChange = (value: string) =>{
    setTargetToIvr(false);
    setTargetToQueue(false);
    setTargetToMobile(false);
    setTargetToScript(false);
    console.log("handleTransferChange----value=,prop_value=",value,props.values.toTarget)
    if (value != props.values.toTarget){
      form.setFieldsValue({
        targetValue: "",
      });
    }
    if(value == "to_queue"){
      setTargetToQueue(true);
    }else if(value == "to_ivr"){
      setTargetToIvr(true);
    }else if(value == "to_device"){
      setTargetToMobile(true);
    }else if(value == "to_queue_script"){
      setTargetToScript(true);
    }else if(value == "to_agent_script"){
      setTargetToScript(true);
    }
  }

  const handlePlayTypeChange = (value: string) =>{
    setShowTTSInput(false)
    setShowPlayFile(false)

    if(value == 'file'){
      setShowPlayFile(true)
    }else if(value == 'tts'){
      setShowTTSInput(true)
    }
  }
  const handleOk = () => {
    console.log('--------editor='+scriptValue)
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
    form.resetFields();
  };
  const handleFinish = async (values: Record<string, any>) => {
    props.onSubmit({...values,...{scriptContent:scriptValue}} as NodeFormValueType);
    return true;
  };

  useEffect(() => {
    form.resetFields();
    setShowTransferTo(false);
    setShowDtmf(false);
    setShowScript(false);
    setShowTTSInput(false)
    setShowPlayFile(false)
    setTargetToIvr(false);
    setTargetToQueue(false);
    setTargetToMobile(false);
    setTargetToScript(false);
    setFlowId(props.values.flowId);
    setScriptValue(props.values.scriptContent)
    console.log('----nodeEdit node.id=',props.values.nodeId)
    form.setFieldsValue({
      flowId: props.values.flowId,
      nodeId: props.values.nodeId,
      enterValue: props.values.enterValue,
      nodeLabel: props.values.nodeLabel,
      pid: props.values.pid,
      optType: props.values.optType,
      playType: props.values.playType,
      playFile: props.values.playFile,
      ttsInput: props.values.ttsInput,
      toTarget: props.values.toTarget,
      targetValue: props.values.targetValue,
      scriptContent: props.values.scriptContent,
      dtmfMax: props.values.dtmfMax,
      dtmfTimeout: props.values.dtmfTimeout,
      dtmfTries: props.values.dtmfTries,
      dtmfTerminators: props.values.dtmfTerminators,
    });
    handleNodeChange(props.values.optType);
    handlePlayTypeChange(props.values.playType)
    handleTransferChange(props.values.toTarget)
  }, [form, props]);

  return (
    <Modal
      width={900}
      title={intl.formatMessage({
        id: 'ivr.flow.modify_node',
        defaultMessage: '编辑节点信息',
      })}
      visible={props.visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form form={form} onFinish={handleFinish} initialValues={props.values}>
        <Row gutter={[16, 16]}>
          <Col span={8} order={1}>
            <ProFormDigit
              name="flowId"
              label={intl.formatMessage({
                id: 'ivr.flow.flow_id',
                defaultMessage: 'flowId',
              })}
              width="xl"
              placeholder="请输入流程ID"
              hidden={true}
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入流程ID！" defaultMessage="请输入流程ID！" />,
                },
              ]}
            />
          </Col>
          <Col span={8} order={1}>
            <ProFormText
              name="nodeId"
              label={intl.formatMessage({
                id: 'ivr.flow.flow_nodeId',
                defaultMessage: 'nodeId',
              })}
              width="xl"
              placeholder="请输入节点ID"
              hidden={true}
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入节点ID！" defaultMessage="请输入节点ID！" />,
                },
              ]}
            />
          </Col>
          <Col span={8} order={1}>
            <ProFormText
              name="pid"
              label={intl.formatMessage({
                id: 'ivr.flow.flow_pid',
                defaultMessage: 'pid',
              })}
              width="xl"
              placeholder="pid"
              hidden={true}
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入父节点ID！" defaultMessage="请输入父节点ID！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          {
            props.values.enterValue?(
              <Col span={8} order={1}>
                <ProFormText
                  name='enterValue'
                  label={intl.formatMessage({
                    id: 'ivr.flow.enter_value',
                    defaultMessage: '进入值',
                  })}
                  width="xl"
                  placeholder="进入值"
                  rules={[
                    {
                      required: true,
                      message: <FormattedMessage id="请输入进入值！" defaultMessage="请输入进入值！" />,
                    },
                  ]}
                />
              </Col>
            ):null
          }
          <Col span={8} order={1}>
            <ProFormText
              name='nodeLabel'
              label={intl.formatMessage({
                id: 'ivr.flow.node_label',
                defaultMessage: '节点名称',
              })}
              width="xl"
              placeholder="节点名称"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入名称！" defaultMessage="请输入名称！" />,
                },
              ]}
            />
          </Col>
          <Col span={8} order={1}>
            <ProFormSelect
              valueEnum={{
                dtmf: '按键放音',
                transfer: '转移',
                script: '脚本',
              }}
              width="md"
              fieldProps={{//这里使用了select的onChange方法，必须使用这样的写法来进行调用onChange方法
                onChange:(val) => handleNodeChange(val),
              }}
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请选择节点类型！" defaultMessage="请选择节点类型！" />,
                },
              ]}
              name="optType"
              label={`节点类型`}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          {
            showPlayType?(
              <Col span={8} order={1}>
                <ProFormSelect
                  valueEnum={{
                    file: '文件',
                    tts: 'TTS',
                  }}
                  width="md"
                  fieldProps={{
                    onChange:(val) => handlePlayTypeChange(val),
                  }}
                  name="playType"
                  label={`放音类型`}
                />
              </Col>
            ):null
          }
          {
            showPlayFile?(
              <Col span={8} order={1}>
                <ProFormSelect
                  options={[
                    {
                      value: "1",
                      label: "文件1",
                    },
                    {
                      value: "2",
                      label: "文件2",
                    },
                  ]}
                  width="md"
                  name="playFile"
                  label={`放音文件`}
                />
              </Col>
            ):null
          }
          {
            showTTSInput?(
              <Col span={24} order={1}>
                <ProFormText
                  name="ttsInput"
                  label={intl.formatMessage({
                    id: 'system.User.user_id',
                    defaultMessage: 'TTS文本',
                  })}
                  width="xl"
                  placeholder="TTS文本"
                  rules={[
                    {
                      required: false,
                      message: <FormattedMessage id="请输入TTS文本！" defaultMessage="请输入TTS文本！" />,
                    },
                  ]}
                />
              </Col>
            ):null
          }
          {
            showTransferTo?(
              <Col span={8} order={1}>
                <ProFormSelect
                  valueEnum={{
                    to_queue: '队列',
                    to_ivr: 'IVR',
                    to_device: '号码',
                    to_queue_script: '队列判断',
                    to_agent_script: '坐席判断',
                  }}
                  width="md"
                  fieldProps={{
                    onChange:(val) => handleTransferChange(val),
                  }}
                  name="toTarget"
                  label={`转移类型`}
                />
              </Col>
            ):null
          }

          {
            showTargetToIvr?(
              <Col span={8} order={1}>
                <ProFormSelect
                  valueEnum={ivrOptions}
                  width="md"
                  name="targetValue"
                  label={`转移至`}
                />
              </Col>
            ):null
          }
          {
            showTargetToQueue?(
              <Col span={8} order={1}>
                <ProFormSelect
                  valueEnum={queueOptions}
                  width="md"
                  name="targetValue"
                  label={`转移至`}
                />
              </Col>
            ):null
          }
          {
            showTargetToMobile?(
              <Col span={8} order={1}>
                <ProFormText
                  width="md"
                  name="targetValue"
                  label={`转移至`}
                />
              </Col>
            ):null
          }
          {
            showTargetToScript?(
              <Col span={8} order={1}>
                <ProFormText
                  width="md"
                  name="targetValue"
                  label={`转移至`}
                />
              </Col>
            ):null
          }
        </Row>
        {
          showScript?(
            <Row gutter={[16, 16]}>
              <Col span={24} order={1}>
                <AceEditor
                  mode="javascript"
                  theme="solarized_dark"
                  height="400px"
                  width="800px"
                  name="scriptJsx"
                  onBlur={e => {
                    const editor = ace.edit("scriptJsx"); // 获取编辑器
                    // editor.getValue(); // 获取编辑器的值
                    setScriptValue(editor.getValue()); // 保存编辑器的值
                  }}
                  value={scriptValue}
                  editorProps={{ $blockScrolling: true }}
                  setOptions={{
                    enableBasicAutocompletion: true,
                    enableLiveAutocompletion: true,
                    enableSnippets: true,
                    showLineNumbers: true,
                    tabSize: 2,
                  }}
                />
              </Col>
            </Row>
          ):null
        }
        {
          showDtmf?(
            <Row gutter={[16, 16]}>
              <Col span={6} order={1}>
                <ProFormDigit
                  name="dtmfMax"
                  label={intl.formatMessage({
                    id: 'ivr.flow.dtmf_length',
                    defaultMessage: '收码长度',
                  })}
                  width="xl"
                  placeholder="收码长度"
                  value={1}
                  rules={[
                    {
                      required: false,
                      message: <FormattedMessage id="请输入收码长度！" defaultMessage="请输入收码长度！" />,
                    },
                  ]}
                />
              </Col>
              <Col span={6} order={1}>
                <ProFormDigit
                  name="dtmfTimeout"
                  label={intl.formatMessage({
                    id: 'ivr.flow.dtmf_timeout',
                    defaultMessage: '收码时长',
                  })}
                  width="xl"
                  value={2}
                  placeholder="收码时长"
                  rules={[
                    {
                      required: false,
                      message: <FormattedMessage id="请输入收码时长！" defaultMessage="请输入收码时长！" />,
                    },
                  ]}
                />
              </Col>
              <Col span={6} order={1}>
                <ProFormDigit
                  name="dtmfTries"
                  label={intl.formatMessage({
                    id: 'ivr.flow.dtmf_tries',
                    defaultMessage: '放音次数',
                  })}
                  width="xl"
                  value={3}
                  placeholder="放音次数"
                  rules={[
                    {
                      required: false,
                      message: <FormattedMessage id="请输入放音次数！" defaultMessage="请输入放音次数！" />,
                    },
                  ]}
                />
              </Col>
              <Col span={6} order={1}>
                <ProFormSelect
                  options={[
                    {
                      value: "#",
                      label: "#",
                    },
                  ]}
                  value={"#"}
                  width="md"
                  name="dtmfTerminators"
                  label={`按键结束符`}
                />
              </Col>
            </Row>
          ):null
        }
      </Form>
    </Modal>
  );
}
export default NodeForm;
