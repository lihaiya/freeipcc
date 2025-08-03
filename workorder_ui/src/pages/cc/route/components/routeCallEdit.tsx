import React, {useEffect} from 'react';
import {ProFormDigit, ProFormText, ProFormSelect, ProFormCheckbox} from '@ant-design/pro-form';
import {Form, Modal, Row, Col, Divider} from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { RouteCallType } from '../routeCallData';


export type PostFormValueType = Record<string, unknown> & Partial<RouteCallType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<RouteCallType>;
  routeCalls: string[];
  routeGatewayIds: string[];
  asrOptions: any;
  asrHangupOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();
  const { routeCalls } = props;
  const { asrOptions } = props;
  const { asrHangupOptions } = props;

  useEffect(() => {
    form.resetFields();
    console.log('----',props)
    form.setFieldsValue({
      id: props.values.id,
      routeNum: props.values.routeNum,
      numMax: props.values.numMax,
      numMin: props.values.numMin,
      asrFlag: props.values.asrFlag+"",
      hangupFlag: props.values.hangupFlag+"",
      routeGatewayIds : props.routeGatewayIds,
      callerChange: props.values.callerChange,
      callerChangeNum: props.values.callerChangeNum,
      calledChange: props.values.calledChange,
      calledChangeNum: props.values.calledChangeNum,
      createBy: props.values.createBy,
      createTime: props.values.createTime,
      updateBy: props.values.updateBy,
      updateTime: props.values.updateTime,
      remark: props.values.remark,
    });
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
    <Modal
      width={640}
      title={intl.formatMessage({
        id: 'cc.routeCall.modify',
        defaultMessage: '字冠路由',
      })}
      visible={props.visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form form={form} onFinish={handleFinish} initialValues={props.values}>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormDigit
              name="id"
              label={intl.formatMessage({
                id: 'cc.routeCall.id',
                defaultMessage: 'route ID',
              })}
              width="xl"
              placeholder="请输入ID"
              disabled
              hidden={!props.values.id}
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
          <Col span={24} order={1}>
            <ProFormText
              name="routeNum"
              label={intl.formatMessage({
                id: 'cc.routeCall.routeNum',
                defaultMessage: '路由号码',
              })}
              width="xl"
              placeholder="请输入路由号码"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入路由号码！" defaultMessage="请输入路由号码！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormText
              name="numMax"
              label={intl.formatMessage({
                id: 'cc.routeCall.numMax',
                defaultMessage: '号码匹配最大',
              })}
              width="xl"
              placeholder="请输入号码匹配最大"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormText
              name="numMin"
              label={intl.formatMessage({
                id: 'cc.routeCall.numMin',
                defaultMessage: '号码匹配最小',
              })}
              width="xl"
              placeholder="请输入号码匹配最小"
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormDigit
              name="callerChangeNum"
              label={intl.formatMessage({
                id: 'cc.routeCall.callerChangeNum',
                defaultMessage: '主叫替换号码',
              })}
              width="xl"
              placeholder="请输入主叫替换号码"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormDigit
              name="calledChangeNum"
              label={intl.formatMessage({
                id: 'cc.routeCall.calledChangeNum',
                defaultMessage: '被叫替换号码',
              })}
              width="xl"
              placeholder="请输入被叫替换号码"
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormSelect
              name="asrFlag"
              valueEnum={asrOptions}
              label={intl.formatMessage({
                id: 'cc.routeCall.asrFlag',
                defaultMessage: '开启ASR识别',
              })}
              width="xl"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormSelect
              name="hangupFlag"
              valueEnum={asrHangupOptions}
              label={intl.formatMessage({
                id: 'cc.routeCall.hangupFlag',
                defaultMessage: '识别之后是否挂机',
              })}
              width="xl"
            />
          </Col>
        </Row>
        <Divider orientation="left" orientationMargin="0" >网关</Divider>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormCheckbox.Group
              name="routeGatewayIds"
              options={routeCalls}
            />
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

export default PostForm;
