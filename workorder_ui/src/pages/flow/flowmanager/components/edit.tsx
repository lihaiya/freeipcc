import React, {useEffect} from 'react';
import { ProFormDigit,ProFormText, ProFormSelect,ProFormTextArea } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { IvrFlowType } from '../data';


export type PostFormValueType = Record<string, unknown> & Partial<IvrFlowType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<IvrFlowType>;
  statusOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();

  const { statusOptions } = props;

  useEffect(() => {
    form.resetFields();
    console.log('----',props)
    form.setFieldsValue({
      id: props.values.id,
      name: props.values.name,
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
        id: 'cc.ivrFlow.modify',
        defaultMessage: 'IVR基本信息',
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
                id: 'cc.ivrFlow.id',
                defaultMessage: 'Flow ID',
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
              name="name"
              label={intl.formatMessage({
                id: 'cc.ivrFlow.name',
                defaultMessage: '名称',
              })}
              width="xl"
              placeholder="请输入流程名称"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入流程名称！" defaultMessage="请输入流程名称！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={2}>
            <ProFormSelect
              valueEnum={statusOptions}
              name="status"
              label={intl.formatMessage({
                id: 'cc.ivrFlow.status',
                defaultMessage: '状态',
              })}
              width="xl"
              placeholder="请选择流程状态"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择流程状态！" defaultMessage="请选择流程状态！" />
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
                id: 'cc.ivrFlow.remark',
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
