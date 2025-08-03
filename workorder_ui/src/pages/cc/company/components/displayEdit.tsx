import React, {useEffect} from 'react';
import {ProFormDigit, ProFormText, ProFormSelect, ProFormCheckbox} from '@ant-design/pro-form';
import {Form, Modal, Row, Col, Divider} from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { DisplayType } from '../displayData';


export type PostFormValueType = Record<string, unknown> & Partial<DisplayType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<DisplayType>;
  typeOptions: any;
  strategyOptions: any;
  phones: string[];
  phoneIds: string[];
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();
  const { phones } = props;
  const { strategyOptions } = props;
  const { typeOptions } = props;

  useEffect(() => {
    form.resetFields();
    console.log('----',props)
    form.setFieldsValue({
      id: props.values.id,
      phone: props.values.name,
      phoneIds : props.phoneIds,
      type: props.values.type,
      strategy: props.values.strategy,
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
      title="企业号码池"
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
                id: 'company.display.id',
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
              name="name"
              label={intl.formatMessage({
                id: 'company.display.name',
                defaultMessage: '名称',
              })}
              width="xl"
              placeholder="请输入号码池名称"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入号码池名称！" defaultMessage="请输入号码池名称！" />,
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
                id: 'company.display.type',
                defaultMessage: '类型',
              })}
              width="xl"
              placeholder="请输入类型"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入类型！" defaultMessage="请输入类型！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={2}>
            <ProFormSelect
              valueEnum={strategyOptions}
              name="strategy"
              label={intl.formatMessage({
                id: 'company.display.strategy',
                defaultMessage: '号码策略',
              })}
              width="xl"
              placeholder="请选择号码策略"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择号码策略！" defaultMessage="请选择号码策略！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Divider orientation="left" orientationMargin="0" >号码池</Divider>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormCheckbox.Group
              name="phoneIds"
              options={phones}
            />
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

export default PostForm;
