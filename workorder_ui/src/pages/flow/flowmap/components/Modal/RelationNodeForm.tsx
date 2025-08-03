import React, {useContext, useEffect, useImperativeHandle, useState} from "react";
import {Form, Row, Col} from "antd";
import { FlowContext, Actions } from "../../context";
import {FormattedMessage, useIntl} from "umi";
import { ProFormSelect,ProFormText } from "@ant-design/pro-components";

import AceEditor from "react-ace";

import "ace-builds/src-noconflict/mode-javascript";
import "ace-builds/src-noconflict/theme-solarized_dark";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";

function RelationNodeForm(props: any, ref: any) {
  const { state, dispatch } = useContext(FlowContext);
  const { flowData, modalConfig } = state;
  const [showTransferTo, setShowTransferTo] = useState<boolean>(false);
  const [showPlayType, setShowPlayType] = useState<boolean>(false);
  const [showScript, setShowScript] = useState<boolean>(false);
  const [showTTSInput, setShowTTSInput] = useState<boolean>(false);
  const [showPlayFile, setShowPlayFile] = useState<boolean>(false);

  const [form] = Form.useForm();

  //const initialValues = flowData.get(modalConfig.nodeId) || {};
  const initialValues = modalConfig.item || {};

  const intl = useIntl();


  useImperativeHandle(ref, () => ({
    // 将 submit 方法暴露给父组件
    submit: () => {
      return form
        .validateFields()
        .then((values) => {
          dispatch({
            type: Actions.SET_FLOW_NODE,
            payload: {
              id: modalConfig.itemId,
              ...values,
            },
          });
        })
        .catch((err) => {
          return false;
        });
    },
  }));

  useEffect(() => {
    form.resetFields();
    setShowTransferTo(false);
    setShowPlayType(false);
    setShowScript(false);
    setShowTTSInput(false)
    setShowPlayFile(false)
  }, [modalConfig.itemId, form]);

  const handleNodeChange =(value: string) =>{
    console.log('----selected value=',value)
    setShowTTSInput(false)
    setShowPlayFile(false)
    if(value == 'dtmf'){
      setShowPlayType(true);
      setShowTransferTo(false);
    }else if(value == 'transfer'){
      setShowPlayType(false);
      setShowTransferTo(true);
    }else if(value == 'script'){

    }
  }

  const handlePlayTypeChange =(value: string) =>{
    if(value == 'file'){
      setShowPlayFile(true)
      setShowTTSInput(false)
    }else if(value == 'tts'){
      setShowPlayFile(false)
      setShowTTSInput(true)
    }
  }
  return (
    <Form form={form} initialValues={initialValues.data}>
      <Row gutter={[16, 16]}>
        <Col span={8} order={1}>
          <ProFormText
            name='text'
            label={intl.formatMessage({
              id: 'system.User.user_id',
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
        <Col span={8} order={1}>
          <ProFormText
            name='label'
            label={intl.formatMessage({
              id: 'system.User.user_id',
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
                options={[
                  {
                    value: "",
                    label: "请选择",
                  },
                  {
                    value: "file",
                    label: "文件",
                  },
                  {
                    value: "tts",
                    label: "TTS",
                  },
                ]}
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
                name="dtmfLength"
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
                options={[
                  {
                    value: "to_queue",
                    label: "队列",
                  },
                  {
                    value: "to_ivr",
                    label: "IVR",
                  },
                  {
                    value: "to_device",
                    label: "号码",
                  },
                ]}
                width="md"
                name="toTarget"
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
                width="600px"
                name="UNIQUE_ID_OF_DIV"
                editorProps={{ $blockScrolling: true }}
                setOptions={{
                  enableBasicAutocompletion: true,
                  enableLiveAutocompletion: true,
                  enableSnippets: true,
                  showLineNumbers: true,
                }}
              />
            </Col>
          </Row>
        ):null
      }
      <Row gutter={[16, 16]}>
        <Col span={24} order={1}>
          <ProFormText
            name="dtmfLength"
            label={intl.formatMessage({
              id: 'system.User.user_id',
              defaultMessage: '收码长度',
            })}
            width="xl"
            placeholder="收码长度"
            rules={[
              {
                required: false,
                message: <FormattedMessage id="请输入收码长度！" defaultMessage="请输入收码长度！" />,
              },
            ]}
          />
        </Col>
      </Row>
    </Form>
  );
}
export default React.forwardRef(RelationNodeForm);
