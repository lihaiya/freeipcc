import React, {useEffect} from 'react';
import { ProFormDigit, ProFormText, ProFormUploadButton } from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { Playback } from '../data';

export type PostFormValueType = Record<string, unknown> & Partial<Playback>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<Playback>;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const [form] = Form.useForm();

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      id: props.values.id,
      name: props.values.name,
      playback: props.values.playback,
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
        id: 'cc.playback.modify',
        defaultMessage: '放音文件',
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
                id: 'cc.playback.id',
                defaultMessage: 'playback ID',
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
        </Row>
        <Row gutter={[24, 24]}>
          <Col span={24} order={1}>
            <ProFormUploadButton
              name="upload"
              label="上传文件"
              max={1}
              fieldProps={{
                name: 'file',
                listType: 'picture-card',
                multiple: false,
                beforeUpload(file): boolean {
                  return true;
                 // return get(file, 'size', 0) < 1 * (1024*1024);  //设置大小不能超过两mb
                }
              }}
              accept=".wav,.mp3"
              rules={[{ required: true, message: '请上传文件' },
                {
                  validator: (rule, [value]) =>
                    new Promise<void>((resolve, reject) => {
                      resolve();
                      /**if (get(value, 'size', 0) < 1 * (1024 * 1024)) {
                        resolve();
                      } else {
                        reject(new Error('单个文件不能超过1MB！'));
                      }**/
                    }),
                },]}
              action="/upload.do"
              extra={ //设置提示
                <span>
                  支持格式：.wav .mp3 ，单个文件不能超过1MB。
                </span>
              }
            />
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

export default PostForm;
