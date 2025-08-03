import React, {useEffect, useState} from 'react';
import {
  ProFormDigit,
  ProFormText,
  ProFormSelect,
  ProFormCheckbox
} from '@ant-design/pro-form';
import { Form, Row, Col,Drawer,Button,Space,Divider  } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { GroupType } from '../data';

export type PostFormValueType = Record<string, unknown> & Partial<GroupType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<GroupType>;
  statusOptions: any;
  agentStrategyOptions: any;
  overFlowOptions: any;
  agents: string[];
  agentIds: string[];
  ivrOptions: any;
  queueOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();
  const { agentStrategyOptions } = props;
  const { overFlowOptions } = props;
  const { agents } = props;
  const { ivrOptions,queueOptions } = props;
  const [overFlowValueOptions, setOverFlowValueOptions] = useState<any>([]);
  const [busyValueOptions, setBusyValueOptions] = useState<any>([]);

  const overFlowChangeHandler = (value: string) =>{
    if(value == '1'){
      //to group
      setOverFlowValueOptions(queueOptions)
    }else if(value == '2'){
      //to ivr
      setOverFlowValueOptions(ivrOptions)
    }else if(value == '3'){
      //to vdn
      setOverFlowValueOptions([])
    }
  }
  const busyChangeHandler = (value: string) =>{
    if(value == '1'){
      //to group
      setBusyValueOptions(queueOptions)
    }else if(value == '2'){
      //to ivr
      setBusyValueOptions(ivrOptions)
    }else if(value == '3'){
      //to vdn
      setBusyValueOptions([])
    }
  }


  useEffect(() => {
    form.resetFields();
    overFlowChangeHandler(props.values.queueInoutHandle);
    busyChangeHandler(props.values.queueTimeoutHandle);

    form.setFieldsValue({
      id: props.values.id,
      name: props.values.name,
      agentIds : props.agentIds,
      companyId: props.values.companyId,
      afterInterval: props.values.afterInterval,
      callerDisplayId: props.values.afterInterval,
      recordType: props.values.recordType,
      levelValue: props.values.levelValue,
      ttsEngine: props.values.ttsEngine,
      playContent: props.values.playContent,
      evaluate: props.values.evaluate,
      queuePlay: props.values.queuePlay,
      transferPlay: props.values.transferPlay,
      callTimeOut: props.values.callTimeOut,
      groupType: props.values.groupType,
      notifyPosition: props.values.notifyPosition,
      notifyRate: props.values.notifyRate,
      notifyContent: props.values.notifyContent,
      callMemory: props.values.callMemory,
      queueInout: props.values.queueInout,
      queueInoutHandle: props.values.queueInoutHandle,
      queueInoutValue: props.values.queueInoutValue,
      queueTimeOut: props.values.queueTimeOut,
      queueTimeoutHandle: props.values.queueTimeoutHandle,
      queueTimeoutValue: props.values.queueTimeoutValue,
      agentStrategy: props.values.agentStrategy,
      status: props.values.status,
      createBy: props.values.createBy,
      createTime: props.values.createTime,
      updateBy: props.values.updateBy,
      updateTime: props.values.updateTime,
      remark: props.values.remark,
    });
    console.log('---edit---',props.agents)
  }, [form, props]);

  const intl = useIntl();
  const handleOk = () => {
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
    form.resetFields();
  };
  const handleFinish = (values: Record<string, any>) => {
    props.onSubmit(values as PostFormValueType);
  };

  return (
      <Drawer
        title={intl.formatMessage({
          id: 'cc.group.modify',
          defaultMessage: '队列维护',
        })}
        width={720}
        visible={props.visible}
        onClose = {handleCancel}
        extra={
          <Space>
            <Button onClick={handleCancel}>取消</Button>
            <Button onClick={handleOk} type="primary">
              提交
            </Button>
          </Space>
        }
      >
      <Form form={form} onFinish={handleFinish} initialValues={props.values}>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormDigit
              name="id"
              label={intl.formatMessage({
                id: 'cc.group.id',
                defaultMessage: 'ID',
              })}
              width="xl"
              placeholder="请输入ID"
              disabled
              hidden={true}
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入ID！" defaultMessage="请输入ID！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={6} order={1}>
            <ProFormDigit
              name="levelValue"
              label={intl.formatMessage({
                id: 'cc.group.levelValue',
                defaultMessage: '优先级',
              })}
              width="xl"
              initialValue={1}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入队列优先级！" defaultMessage="请输入队列优先级！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={2}>
            <ProFormText
              name="name"
              label={intl.formatMessage({
                id: 'cc.group.name',
                defaultMessage: '名称',
              })}
              width="xl"
              placeholder="请输入队列名称"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入队列名称！" defaultMessage="请输入队列名称！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={3}>
            <ProFormSelect
              name="agentStrategyType"
              valueEnum={agentStrategyOptions}
              label={intl.formatMessage({
                id: 'cc:group:agentStrategyType',
                defaultMessage: '策略',
              })}
              width="xl"
              placeholder="请输入队列策略"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入策略！" defaultMessage="请输入策略！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Divider orientation="left" orientationMargin="0" >溢出设置</Divider>
        <Row gutter={[16, 16]}>
          <Col span={6} order={1}>
            <ProFormDigit
              name="queueInout"
              label={intl.formatMessage({
                id: 'cc.group.inout',
                defaultMessage: '排队人数',
              })}
              width="xl"
              initialValue={5}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入排队人数！" defaultMessage="请输入排队人数！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={2}>
            <ProFormSelect
              name="queueInoutHandle"
              valueEnum={overFlowOptions}
              label={intl.formatMessage({
                id: 'cc.group.inoutHandle',
                defaultMessage: '溢出处理',
              })}
              fieldProps={{
                onChange:(val) => overFlowChangeHandler(val),
              }}
              width="xl"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择溢出处理！" defaultMessage="请选择溢出处理！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={3}>
            <ProFormSelect
              name="queueInoutValue"
              valueEnum={overFlowValueOptions}
              label={intl.formatMessage({
                id: 'cc.group.inoutValue',
                defaultMessage: '溢出转移',
              })}
              width="xl"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请选择溢出转移！" defaultMessage="请选择溢出转移！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Divider orientation="left" orientationMargin="0" >超时设置</Divider>
        <Row gutter={[16, 16]}>
          <Col span={6} order={1}>
            <ProFormDigit
              name="queueTimeOut"
              label={intl.formatMessage({
                id: 'cc.group.timeOut',
                defaultMessage: '等待时间',
              })}
              width="xl"
              initialValue={30}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入等待时间！" defaultMessage="请输入等待时间！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={2}>
            <ProFormSelect
              name="queueTimeoutHandle"
              valueEnum={overFlowOptions}
              label={intl.formatMessage({
                id: 'cc.group.timeoutHandle',
                defaultMessage: '超时处理',
              })}
              fieldProps={{
                onChange:(val) => busyChangeHandler(val),
              }}
              width="xl"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择超时处理！" defaultMessage="请选择超时处理！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={9} order={3}>
            <ProFormSelect
              name="queueTimeoutValue"
              valueEnum={busyValueOptions}
              label={intl.formatMessage({
                id: 'cc.group.timeoutValue',
                defaultMessage: '超时转移',
              })}
              width="xl"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请选择超时转移！" defaultMessage="请选择超时转移！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Divider orientation="left" orientationMargin="0" >队列坐席</Divider>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormCheckbox.Group
              name="agentIds"
              options={agents}
            />
          </Col>
        </Row>
      </Form>
    </Drawer>
  );
};

export default PostForm;
