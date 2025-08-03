import React, {useEffect, useState} from 'react';
import {Form,
  Avatar,
  Button,
  Card,
  Col,
  Drawer,
  message,
  Row,
  Upload,
} from 'antd';
import {UploadProps} from 'antd/lib/upload';
import {UploadOutlined} from '@ant-design/icons/lib';
import {PostFormValueType} from "@/pages/system/post/components/edit";
import {getAccessToken } from '@/access';
import productImg from '@/pages/robot/rbtsmanager/img/product.png';
import type { RobotChat } from '../data.d';
import styles from './style.less';

export type RobotFormValueType = Record<string, unknown> & Partial<RobotChat>;

export type  RobotFormProps = {
  onCancel: (flag?: boolean, formVals?: RobotFormValueType) => void;
  onSubmit: (values: PostFormValueType) => Promise<void>;
  visible: boolean;
  data: Partial<RobotChat>;
  close: Function;save: (data: Partial<RobotChat>) => void;
  statusOptions: any;
};

const Save: React.FC<RobotFormProps> = (props) => {
  const [photoUrl, setPhotoUrl] = useState(props.data?.photoUrl);

  const [form] = Form.useForm();

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      robotId: props.data.robotId,
      robotName: props.data.robotName,
      status: props.data.status,
      createBy: props.data.createBy,
      createTime: props.data.createTime,
      updateBy: props.data.updateBy,
      updateTime: props.data.updateTime,
      remark: props.data.remark,
    });
  }, [form, props]);

  const saveData = () => {
    form.submit();
  };

  const uploadProps: UploadProps = {
    action: '/jetlinks/file/upload',
    headers: {
      'X-Access-Token': getAccessToken(),
    },
    showUploadList: false,
    onChange(info) {
      if (info.file.status === 'done') {
        setPhotoUrl(info.file.response.result?.id);
        message.success('上传成功');
      }
    },
  };

  return (
    <Drawer
      visible
      title={`${props.data?.robotId ? '编辑' : '新增'}产品`}
      width={500}
      onClose={() => props.close()}
      closable
    >
      <Form labelCol={{ span: 6 }} wrapperCol={{ span: 18 }}>
        <Card title="基本信息" style={{ marginBottom: 20 }} bordered={false}>
          <Form.Item label="图标">
            <>
              <div className={styles.avatar}>
                <Avatar size={80} src={(photoUrl || props.data?.photoUrl) ? `/jetlinks/file/${photoUrl || props.data?.photoUrl}?:X_Access_Token=${getAccessToken()}` : productImg} />
              </div>
              <Upload {...uploadProps} showUploadList={false}>
                <Button>
                  <UploadOutlined />
                  更换图片
                </Button>
              </Upload>
            </>
          </Form.Item>
          <Row gutter={16}>

          </Row>
        </Card>
      </Form>

      <div
        style={{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
        }}
      >
        <Button
          onClick={() => {
            props.close();
          }}
          style={{ marginRight: 8 }}
        >
          关闭
        </Button>
        <Button
          onClick={() => {
            saveData();
          }}
          type="primary"
        >
          保存
        </Button>
      </div>
    </Drawer>
  );
};
export default Save;
