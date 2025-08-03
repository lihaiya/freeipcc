import React, {useEffect} from 'react';
import { ProFormDigit, ProFormText, ProFormSelect } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { GatewayType } from '../data';


export type PostFormValueType = Record<string, unknown> & Partial<GatewayType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<GatewayType>;
  typeOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();
  const { typeOptions } = props;

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      id: props.values.id,
      name: props.values.name,
      mediaHost: props.values.mediaHost,
      mediaPort: props.values.mediaPort,
      callerPrefix: props.values.callerPrefix,
      profile: props.values.profile,
      sipHeader1: props.values.sipHeader1,
      sipHeader2: props.values.sipHeader2,
      sipHeader3: props.values.sipHeader3,
      status: props.values.status,
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
        id: 'cc.gateway.modify',
        defaultMessage: '网关信息',
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
                id: 'cc.gateway.id',
                defaultMessage: 'gateway ID',
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
          <Col span={12} order={1}>
            <ProFormText
              name="name"
              label={intl.formatMessage({
                id: 'cc.gateway.name',
                defaultMessage: '名称',
              })}
              width="xl"
              placeholder="请输入名称"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入名称！" defaultMessage="请输入名称！" />,
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormSelect
              name="profile"
              valueEnum={typeOptions}
              label={intl.formatMessage({
                id: 'cc.gateway.profile',
                defaultMessage: 'profile',
              })}
              width="xl"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择profile！" defaultMessage="请选择profile！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormText
              name="mediaHost"
              label={intl.formatMessage({
                id: 'cc.gateway.mediaHost',
                defaultMessage: '地址',
              })}
              width="xl"
              placeholder="请输入网关地址"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入网关地址！" defaultMessage="请输入网关地址！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormText
              name="mediaPort"
              label={intl.formatMessage({
                id: 'cc.gateway.mediaPort',
                defaultMessage: '端口',
              })}
              width="xl"
              placeholder="请输入端口"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入端口！" defaultMessage="请输入端口！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormText
              name="callerPrefix"
              label={intl.formatMessage({
                id: 'cc.gateway.callerPrefix',
                defaultMessage: '主叫前缀',
              })}
              width="xl"
              placeholder="请输入主叫前缀"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormText
              name="calledPrefix"
              label={intl.formatMessage({
                id: 'cc.gateway.calledPrefix',
                defaultMessage: '被叫前缀',
              })}
              width="xl"
              placeholder="请输入被叫前缀"
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormText
              name="sipHeader1"
              label={intl.formatMessage({
                id: 'cc.gateway.sipHeader1',
                defaultMessage: 'sipHeader1',
              })}
              width="xl"
              placeholder="请输入sipHeader1"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormText
              name="sipHeader2"
              label={intl.formatMessage({
                id: 'cc.gateway.sipHeader2',
                defaultMessage: 'sipHeader2',
              })}
              width="xl"
              placeholder="请输入sipHeader2"
            />
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

export default PostForm;
