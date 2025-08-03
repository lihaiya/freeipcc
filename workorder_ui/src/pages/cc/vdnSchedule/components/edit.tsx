import React, {useEffect, useState} from 'react';
import {
  ProFormDigit,
  ProFormText,
  ProFormRadio,
  ProFormTextArea,
  ProFormCheckbox,
  ProFormSelect,
  ProFormDateRangePicker,
  ProFormTimePicker
} from '@ant-design/pro-form';
import { Form, Modal, Row, Col  } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { VdnScheduleType } from '../data';
import moment from 'moment';


export type PostFormValueType = Record<string, unknown> & Partial<VdnScheduleType>;

export type PostFormProps = {
  onCancel: (flag?: boolean, formVals?: PostFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<VdnScheduleType>;
  statusOptions: any;
  typeOptions: any;
  ivrOptions: any;
};

const PostForm: React.FC<PostFormProps> = (props) => {
  const { statusOptions } = props;
  const { typeOptions,ivrOptions } = props;

  const [form] = Form.useForm();
  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      id: props.values.id,
      routeType: props.values.routeType,
      routeValue: props.values.routeValue,
      startTime: props.values.startTime,
      endTime: props.values.endTime,
      vdnId: props.values.vdnId,
      companyId: props.values.companyId,
      weekRange:props.values.week?.split(","),
      startDay: props.values.startDay,
      endDay: props.values.endDay,
      levelValue: props.values.levelValue,
      name: props.values.name,
      status: props.values.status,
      createBy: props.values.createBy,
      createTime: props.values.createTime,
      updateBy: props.values.updateBy,
      updateTime: props.values.updateTime,
      remark: props.values.remark,
      timesRange: [props.values.startTime, props.values.endTime],
      dateRange: [props.values.startDay, props.values.endDay],
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
    values.startDay = moment(values.dateRange[0]).format('YYYY-MM-DD')
    values.endDay = moment(values.dateRange[1]).format('YYYY-MM-DD')
    values.startTime = moment(values.timesRange[0]).format('HH:mm:ss')
    values.endTime = moment(values.timesRange[1]).format('HH:mm:ss')
    props.onSubmit(values as PostFormValueType);
  };

  return (
    <Modal
      width={640}
      title={intl.formatMessage({
        id: 'cc.vdnschedule.modify',
        defaultMessage: '路由策略',
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
                id: 'cc.vdnschedule.id',
                defaultMessage: '主键',
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
            <ProFormSelect
              valueEnum={typeOptions}
              name="routeType"
              label={intl.formatMessage({
                id: 'cc.vdnschedule.routeType',
                defaultMessage: '类型',
              })}
              width="xl"
              placeholder="请选择类型"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请选择类型！" defaultMessage="请选择类型！" />
                  ),
                },
              ]}
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormSelect
              valueEnum={ivrOptions}
              name="routeValue"
              label={intl.formatMessage({
                id: 'cc.vdnschedule.routeValue',
                defaultMessage: '目标',
              })}
              width="xl"
              placeholder="请输入目标"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage id="请输入目标！" defaultMessage="请输入目标！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormDateRangePicker
              width="md"
              name="dateRange"
              label="生效日期"
            />
          </Col>
          <Col span={12} order={1}>
            <ProFormTimePicker.RangePicker name="timesRange" label="生效时间" />
          </Col>
        </Row>

        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormCheckbox.Group
              name="weekRange"
              label={intl.formatMessage({
                id: 'cc.vdnschedule.weekRange',
                defaultMessage: '星期',
              })}
              options={[ {label: '周一', value:'mon' }, {label: '周二', value:'tue' }, {label: '周三', value:'wed' }, {label: '周四', value:'thu' }, {label: '周五', value:'fri' }, {label: '周六', value:'sat' }, {label: '周日', value:'sun' }]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={12} order={1}>
            <ProFormRadio.Group
              valueEnum={statusOptions}
              name="status"
              label={intl.formatMessage({
                id: 'cc.vdnschedule.status',
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
          <Col span={12} order={1}>
            <ProFormDigit
              label={intl.formatMessage({
              id: 'cc.vdnschedule.sort',
              defaultMessage: '排序',
            })}
              name="levelValue"

              width="sm"
              rules={[
                {
                  required: true,
                  message: <FormattedMessage id="请输入排序！" defaultMessage="请输入排序！" />,
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
                id: 'cc.vdnschedule.remark',
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
