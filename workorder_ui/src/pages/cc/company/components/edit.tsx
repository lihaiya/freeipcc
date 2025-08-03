import React, {useEffect} from 'react';
import { ProFormDigit, ProFormText, ProFormSelect,ProFormTextArea } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { CompanyType } from '../data';


export type PostFormValueType = Record<string, unknown> & Partial<CompanyType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<CompanyType>;
  typeOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();

  const { typeOptions } = props;

  useEffect(() => {
    form.resetFields();
    console.log('----',props)
    form.setFieldsValue({
      id: props.values.id,
      name: props.values.name,
      contact: props.values.contact,
      phone: props.values.phone,
      balance: props.values.balance,
      billType: props.values.billType,
      payType: props.values.payType,
      secretType: props.values.secretType,
      secretKey: props.values.secretKey,
      ivrLimit: props.values.ivrLimit,
      agentLimit: props.values.agentLimit,
      groupLimit: props.values.groupLimit,
      groupAgentLimit: props.values.groupAgentLimit,
      recordStorage: props.values.recordStorage,
      notifyUrl: props.values.notifyUrl,
      status: props.values.status,
      state: props.values.state,
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
        id: 'cc.company.modify',
        defaultMessage: '公司信息',
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
                id: 'cc.company.id',
                defaultMessage: 'Company ID',
              })}
              width="xl"
              placeholder="请输入ID"
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
          <Col span={24} order={1}>
            <ProFormText
              name="name"
              label={intl.formatMessage({
                id: 'cc.company.name',
                defaultMessage: '公司名称',
              })}
              width="xl"
              placeholder="请输入公司名称"
              disabled={!!props.values.name}
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入公司名称！" defaultMessage="请输入公司名称！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormText
              name="contact"
              label={intl.formatMessage({
                id: 'cc.company.contact',
                defaultMessage: '公司联系人',
              })}
              width="xl"
              placeholder="请输入公司联系人"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入公司联系人！" defaultMessage="请输公司联系人！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormText
              name="phone"
              label={intl.formatMessage({
                id: 'cc.company.phone',
                defaultMessage: '联系电话',
              })}
              width="xl"
              placeholder="请输入联系电话"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入联系电话！" defaultMessage="请输入联系电话！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormDigit
              name="ivrLimit"
              label={intl.formatMessage({
                id: 'cc.company.ivrLimit',
                defaultMessage: 'IVR通道数',
              })}
              width="xl"
              placeholder="请输入IVR通道数"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入IVR通道数！" defaultMessage="请输入IVR通道数！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormDigit
              name="agentLimit"
              label={intl.formatMessage({
                id: 'cc.company.agentLimit',
                defaultMessage: '开通座席',
              })}
              width="xl"
              placeholder="请输入开通座席"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入开通座席！" defaultMessage="请输入开通座席！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormDigit
              name="groupLimit"
              label={intl.formatMessage({
                id: 'cc.company.groupLimit',
                defaultMessage: '技能组数',
              })}
              width="xl"
              placeholder="请输入技能组数"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入技能组数！" defaultMessage="请输入技能组数！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormDigit
              name="groupAgentLimit"
              label={intl.formatMessage({
                id: 'cc.company.groupAgentLimit',
                defaultMessage: '技能组坐席数',
              })}
              width="xl"
              placeholder="请输入技能组坐席数"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入技能组坐席数！" defaultMessage="请输入技能组坐席数！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormDigit
              name="recordStorage"
              label={intl.formatMessage({
                id: 'cc.company.recordStorage',
                defaultMessage: '录音保存年数',
              })}
              width="xl"
              initialValue={3}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入录音保存年数！" defaultMessage="请输入录音保存年数！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={2}>
            <ProFormSelect
              valueEnum={typeOptions}
              name="state"
              label={intl.formatMessage({
                id: 'cc.company.state',
                defaultMessage: '公司类型',
              })}
              width="xl"
              placeholder="请输入公司类型"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入公司类型！" defaultMessage="请输入公司类型！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="notifyUrl"
              label={intl.formatMessage({
                id: 'cc.company.notifyUrl',
                defaultMessage: '话单回调',
              })}
              width="xl"
              placeholder="请输入话单回调"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入话单回调！" defaultMessage="请输入话单回调！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormTextArea
              name="remark"
              label={intl.formatMessage({
                id: 'cc.company.remark',
                defaultMessage: '备注',
              })}
              width="xl"
              placeholder="请输入备注"
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入备注！" defaultMessage="请输入备注！" />,
                },
              ]}
            />
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

export default PostForm;
