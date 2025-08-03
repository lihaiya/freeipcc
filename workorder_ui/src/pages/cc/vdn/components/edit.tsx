import React, { useEffect } from 'react';
import { ProFormDigit, ProFormText, ProFormRadio, ProFormTextArea } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { VdnType } from '../data';


export type VdnFormValueType = Record<string, unknown> & Partial<VdnType>;

export type VdnFormProps = {
  onCancel: (flag?: boolean, formVals?: VdnFormValueType) => void;
  onSubmit: (values: VdnFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<VdnType>;
  statusOptions: any;
};

const VdnTypeForm: React.FC<VdnFormProps> = (props) => {
  const [form] = Form.useForm();
  const { statusOptions } = props;

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      id: props.values.id,
      phone: props.values.phone,
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
    props.onSubmit(values as VdnFormValueType);
  };

  return (
    <Modal
      width={640}
      title={intl.formatMessage({
        id: 'cc.vdn.modify',
        defaultMessage: '接入号码信息',
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
                id: 'cc.vdn:id',
                defaultMessage: 'ID',
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
              name="phone"
              label={intl.formatMessage({
                id: 'cc:vdn:phone',
                defaultMessage: '接入号码',
              })}
              width="xl"
              placeholder="请输入接入号码"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入接入号码！" defaultMessage="请输入接入号码！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormRadio.Group
              valueEnum={statusOptions}
              name="status"
              label={intl.formatMessage({
                id: 'cc.vdn.status',
                defaultMessage: '状态',
              })}
              width="xl"
              labelCol={{ span: 24 }}
              placeholder="请输入状态"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入状态！" defaultMessage="请输入状态！" />,
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
                id: 'system.Post.remark',
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

export default VdnTypeForm;
