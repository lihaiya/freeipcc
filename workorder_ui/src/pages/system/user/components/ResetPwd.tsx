import React, { useEffect } from 'react';
import { ProFormText } from '@ant-design/pro-form';
import { Form, Modal } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import {UserType} from "@/pages/system/user/data";

/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */

export type FormValueType = any & Partial<UserType>;

export type UpdateFormProps = {
  onCancel: (flag?: boolean, formVals?: FormValueType) => void;
  onSubmit: (values: FormValueType) => Promise<void>;
  resetPwdModalVisible: boolean;
  values: Partial<UserType>;
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const [form] = Form.useForm();
  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      login_password: '',
      confirm_password: '',
      userId: props.values.userId,
    });
  });

  const intl = useIntl();
  let index = 0;
  const handleOk = () => {
    form.submit();
    if (index > 0) {
      props.onCancel();
    }
    index += 1;
  };
  const handleCancel = () => {
    props.onCancel();
  };
  const handleFinish = (values: Record<string, any>) => {
    props.onSubmit(values as FormValueType);
  };

  const checkPassword = (rule: any, value: string) => {
    const login_password = form.getFieldValue('password');
    if (value === login_password) {
      // 校验条件自定义
      return Promise.resolve();
    }
    return Promise.reject(new Error('两次密码输入不一致'));
  };

  return (
    <Modal
      width={640}
      title={intl.formatMessage({
        id: 'system.User.reset.password',
        defaultMessage: '密码重置',
      })}
      visible={props.resetPwdModalVisible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form
        form={form}
        onFinish={handleFinish}
        initialValues={{
          login_password: '',
          confirm_password: '',
        }}
      >
        <ProFormText
          width="xl"
          name="userId"
          label="用户ID"
          rules={[
            {
              required: true,
              message: 'userId不可为空。',
            },
          ]}
        />
        <ProFormText.Password
          width="xl"
          name="password"
          label="登录密码"
          rules={[
            {
              required: true,
              message: '登录密码不可为空。',
            },
          ]}
        />
        <ProFormText.Password
          width="xl"
          name="confirm_password"
          label="确认密码"
          rules={[
            {
              required: true,
              message: <FormattedMessage id="确认密码" defaultMessage="确认密码" />,
            },
            { validator: checkPassword },
          ]}
        />
      </Form>
    </Modal>
  );
};

export default UpdateForm;
