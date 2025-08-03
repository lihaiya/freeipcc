import React, {useEffect} from 'react';
import { ProFormDigit, ProFormText, ProFormSelect } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { DisplayType } from '../displayData';


export type PostFormValueType = Record<string, unknown> & Partial<DisplayType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<DisplayType>;
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
      phone: props.values.phone,
      type: props.values.type,
      location: props.values.location,
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
      title="企业号码"
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
                id: 'company.phone.id',
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
          <Col span={24} order={1}>
            <ProFormText
              name="phone"
              label={intl.formatMessage({
                id: 'company.phone.phone',
                defaultMessage: '号码',
              })}
              width="xl"
              placeholder="请输入企业号码"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入企业号码！" defaultMessage="请输入企业号码！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={2}>
            <ProFormSelect
              valueEnum={typeOptions}
              name="type"
              label={intl.formatMessage({
                id: 'company.phone.type',
                defaultMessage: '类型',
              })}
              width="xl"
              placeholder="请输入号码类型"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入号码类型！" defaultMessage="请输入号码类型！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="location"
              label={intl.formatMessage({
                id: 'company.phone.location',
                defaultMessage: '归属地',
              })}
              width="xl"
              placeholder="请输入归属地"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入归属地！" defaultMessage="请输入归属地！" />
                  ),
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
