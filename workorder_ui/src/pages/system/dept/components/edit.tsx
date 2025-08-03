import React, {useEffect, useState} from 'react';
import {ProFormDigit, ProFormText, ProFormRadio, ProFormTreeSelect, ProFormSelect} from '@ant-design/pro-form';
import { Form, Modal, Row, Col } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { DeptType } from '../data.d';

/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */

export type DeptFormValueType = Record<string, unknown> & Partial<DeptType>;

export type DeptFormProps = {
  onCancel: (flag?: boolean, formVals?: DeptFormValueType) => void;
  onSubmit: (values: DeptFormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<DeptType>;
  deptTree: any;
  companyList: any;
  statusOptions: any;
};

const DeptForm: React.FC<DeptFormProps> = (props) => {
  const [form] = Form.useForm();

  const { statusOptions, deptTree,companyList } = props;
  const [hideCompany, setHideCompany] = useState<boolean>(false);

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      deptId: props.values.deptId,
      companyId: props.values.companyId==undefined?null:props.values.companyId+'',
      parentId: props.values.parentId,
      ancestors: props.values.ancestors,
      deptName: props.values.deptName,
      orderNum: props.values.orderNum,
      leader: props.values.leader,
      phone: props.values.phone,
      email: props.values.email,
      status: props.values.status,
      delFlag: props.values.delFlag,
      createBy: props.values.createBy,
      createTime: props.values.createTime,
      updateBy: props.values.updateBy,
      updateTime: props.values.updateTime,
    });
    setHideCompany(false)
  }, [form, props]);

  const intl = useIntl();
  const handleOk = () => {
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
    form.resetFields();
  };
  const handleFinish = async (values: Record<string, any>) => {
    props.onSubmit(values as DeptFormValueType);
  };

  const pdeptChange =(value: string) =>{
    if(value == '1'){
      setHideCompany(false);
    }else{
      setHideCompany(true);
    }
    form.setFieldsValue({
      companyId: null,
    });
    console.log("----------------dep id="+value)
  }

  return (
    <Modal
      width={640}
      title={intl.formatMessage({
        id: 'system.Dept.modify',
        defaultMessage: '编辑Dept',
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
              name="deptId"
              label={intl.formatMessage({
                id: 'system.Dept.dept_id',
                defaultMessage: '部门id',
              })}
              width="xl"
              placeholder="请输入部门id"
              hidden={true}
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入部门id！" defaultMessage="请输入部门id！" />,
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormSelect
              valueEnum={companyList}
              name="companyId"
              label={intl.formatMessage({
                id: 'system.Dept.company_id',
                defaultMessage: '所属公司',
              })}
              width="xl"
              hidden={props.values.deptId==1 || hideCompany}
              placeholder="请选择所属公司"
              rules={[
                {
                  required: !hideCompany,
                  message: (
                    <FormattedMessage id="请选择所属公司！" defaultMessage="请选择所属公司！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormTreeSelect
                name="parentId"
                label={intl.formatMessage({
                  id: 'system.Dept.parent_dept',
                  defaultMessage: '上级部门:',
                })}
                request={async () => {
                  return deptTree;
                }}
                fieldProps={{
                  onChange:(val) => pdeptChange(val),
                }}
                width="xl"
                placeholder="请选择上级部门"
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage id="请选择上级部门！" defaultMessage="请选择上级部门!" />
                    ),
                  },
                ]}
              />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="ancestors"
              label={intl.formatMessage({
                id: 'system.Dept.ancestors',
                defaultMessage: '祖级列表',
              })}
              width="xl"
              placeholder="请输入祖级列表"
              hidden={true}
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请输入祖级列表！" defaultMessage="请输入祖级列表！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="deptName"
              label={intl.formatMessage({
                id: 'system.Dept.dept_name',
                defaultMessage: '部门名称',
              })}
              width="xl"
              placeholder="请输入部门名称"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请输入部门名称！" defaultMessage="请输入部门名称！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormDigit
              name="orderNum"
              label={intl.formatMessage({
                id: 'system.Dept.order_num',
                defaultMessage: '显示顺序',
              })}
              width="xl"
              placeholder="请输入显示顺序"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请输入显示顺序！" defaultMessage="请输入显示顺序！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="leader"
              label={intl.formatMessage({
                id: 'system.Dept.leader',
                defaultMessage: '负责人',
              })}
              width="xl"
              placeholder="请输入负责人"
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入负责人！" defaultMessage="请输入负责人！" />,
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
                id: 'system.Dept.phone',
                defaultMessage: '联系电话',
              })}
              width="xl"
              placeholder="请输入联系电话"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请输入联系电话！" defaultMessage="请输入联系电话！" />
                  ),
                },
              ]}
            />
          </Col>
        </Row>
        <Row gutter={[16, 16]}>
          <Col span={24} order={1}>
            <ProFormText
              name="email"
              label={intl.formatMessage({
                id: 'system.Dept.email',
                defaultMessage: '邮箱',
              })}
              width="xl"
              placeholder="请输入邮箱"
              rules={[
                {
                  required: false,
                  message: <FormattedMessage id="请输入邮箱！" defaultMessage="请输入邮箱！" />,
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
                id: 'system.Dept.status',
                defaultMessage: '部门状态',
              })}
              labelCol={{ span: 24 }}
              width="xl"
              placeholder="请输入部门状态"
              rules={[
                {
                  required: false,
                  message: (
                    <FormattedMessage id="请输入部门状态！" defaultMessage="请输入部门状态！" />
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

export default DeptForm;
