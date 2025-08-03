import React, { useContext, useEffect, useImperativeHandle } from "react";
import {Form, Row, Col} from "antd";
import { FlowContext, Actions } from "../../context";
import {ProFormText} from "@ant-design/pro-form";
import {FormattedMessage, useIntl} from "umi";

function EdgeEditForm(props: any, ref: any) {
  const { state, dispatch } = useContext(FlowContext);
  const { flowData, modalConfig} = state;
  const [form] = Form.useForm();

  //const initialValues = flowData.get(modalConfig.nodeId) || {};
  const initialValues = modalConfig.item || {};

  const intl = useIntl();

  useImperativeHandle(ref, () => ({
    // 将 submit 方法暴露给父组件
    submit: () => {
      return form
        .validateFields()
        .then((values) => {
          dispatch({
            type: Actions.SET_FLOW_EDGE,
            payload: {
              id: modalConfig.itemId,
              ...values,
            },
          });
        })
        .catch((err) => {
          return false;
        });
    },
  }));

  useEffect(() => {
    console.log('--open edge model=',initialValues)
    form.resetFields();
  }, [modalConfig.nodeId, form]);

  return (
    <Form form={form} initialValues={initialValues.data}>
      <Row gutter={[16, 16]}>
        <Col span={24} order={1}>
          <ProFormText
            name='text'
            label={intl.formatMessage({
              id: 'system.User.user_id',
              defaultMessage: '按键值',
            })}
            width="xl"
            placeholder="按键值"
            rules={[
              {
                required: false,
                message: <FormattedMessage id="请输入按键值！" defaultMessage="请输入按键值！" />,
              },
            ]}
          />
        </Col>
      </Row>
    </Form>
  );
}
export default React.forwardRef(EdgeEditForm);
