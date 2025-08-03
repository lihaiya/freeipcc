import React, { useEffect } from 'react';
import { ProFormDigit, ProFormText, ProFormRadio, ProFormTextArea } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { QaType } from '../data';

/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */


export type PostFormValueType = Record<string, unknown> & Partial<QaType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<QaType>;
  statusOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();

  const { statusOptions } = props;

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      qaId: props.values.qaId,
      question: props.values.question,
      answer: props.values.answer,
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
        id: 'ai.qa.modify',
        defaultMessage: '编辑岗位信息',
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
              name="qaId"
              label={intl.formatMessage({
                id: 'ai.qa.qa_id',
                defaultMessage: 'QA ID',
              })}
              width="xl"
              placeholder="请输入ID"
              disabled
              hidden={!props.values.qaId}
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
              name="question"
              label={intl.formatMessage({
                id: 'ai.qa.question',
                defaultMessage: '问题',
              })}
              width="xl"
              placeholder="请输入问题"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入问题！" defaultMessage="请输入问题！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="answer"
              label={intl.formatMessage({
                id: 'ai.qa.answer',
                defaultMessage: '答案',
              })}
              width="xl"
              placeholder="请输入答案"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入答案！" defaultMessage="请输入答案！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormRadio.Group
              valueEnum={statusOptions}
              name="status"
              label={intl.formatMessage({
                id: 'ai.qa.status',
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

export default PostForm;
