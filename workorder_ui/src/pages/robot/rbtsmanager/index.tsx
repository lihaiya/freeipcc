import {PlusOutlined,EditOutlined,BugOutlined,CloseOutlined} from '@ant-design/icons';
import WrapContent from '@/components/WrapContent';
import Icon from '@ant-design/icons';
import React, {useEffect, useState} from 'react';
import {
  Card,
  List,
  Tooltip,
  Avatar,
  Button,
  message,
  Popconfirm,
  Switch, Radio,
} from 'antd';
import { FormProps } from 'antd/lib/form';
import Save from './save';
import AutoHide from './autoHide';
import styles from './index.less';


interface Props extends FormProps {
  loading: boolean;
}

interface State {
  saveVisible: boolean;
  currentItem: any;
  supportsType: any[];
  filterType: string[];
  filterName: string;
  debuggerVisible: boolean;
}

const initState: State = {
  saveVisible: false,
  currentItem: {},
  supportsType: [],
  filterType: [],
  filterName: '',
  debuggerVisible: false,
};

const PostTableList: React.FC = () => {
  const [saveVisible, setSaveVisible] = useState(initState.saveVisible);
  const [currentItem, setCurrentItem] = useState(initState.currentItem);


  useEffect(() => {
  }, []);

  const handleDelete = (params: any) => {
    console.log(params.id)
  };

  const changeStatus = (item: any) => {
    let type;
    if (item.state?.value === 'disabled') {
      type = '_start';
    } else if (item.state.value === 'enabled') {
      type = '_shutdown';
    }
    if (!type) return;
  };


  const getData = () => [
    {
      id:''
    },
    {
      id: 1,
      avatar: '密保手机',
      name: `在线客服机器人`,
      state: [<a key="Modify">修改</a>],
    },
  ];

  const result = getData();

  return (
    <WrapContent>
      <div className={styles.filterCardList}>
        <List<any>
          rowKey="id"
          grid={{gutter: 24, xl: 4, lg: 3, md: 3, sm: 2, xs: 1}}
          dataSource={result}
          renderItem={item => {
            if (item && item.id) {
              return (
                <List.Item key={item.id}>
                  <Card
                    hoverable
                    bodyStyle={{paddingBottom: 20}}
                    actions={[
                      <Tooltip key="edit" title="编辑">
                        <Icon
                          component={EditOutlined}
                          onClick={() => {
                            setSaveVisible(true);
                            setCurrentItem(item);
                          }}
                        />
                      </Tooltip>,
                      <Tooltip key="bug" title="调试">
                        <Icon
                          component={BugOutlined}
                          onClick={() => {
                            setCurrentItem(item);
                          }}
                        />
                      </Tooltip>,
                      <Tooltip key="delete" title="删除">
                        <Popconfirm
                          placement="topRight"
                          title="确定删除此组件吗？"
                          onConfirm={() => {
                            handleDelete(item);
                          }}
                        >
                          <Icon component={CloseOutlined}/>
                        </Popconfirm>
                      </Tooltip>,
                    ]}
                  >
                    <Card.Meta
                      avatar={<Avatar size={40}  src={item.avatar}/>}
                      title={
                        <AutoHide title={item.name} style={{ width: '95%', fontWeight: 600 }} />
                      }
                      description={<AutoHide title={item.id} style={{ width: '95%' }} />}
                    />
                    <div className={styles.cardItemContent}>
                      <div className={styles.cardInfo}>
                        <div style={{width: '33%', textAlign: 'center'}}>
                          <p>组件类型</p>
                          <p style={{fontWeight: 600}}>{item.type}</p>
                        </div>
                        <div style={{width: '33%', textAlign: 'center'}}>
                          <p>端口号</p>
                          <p style={{fontWeight: 600}}>{item.configuration?.port}</p>
                        </div>
                        <div style={{width: '33%', textAlign: 'center'}}>
                          <p>启动状态</p>
                          <p style={{color: 'red'}}>
                            <Popconfirm
                              title={`确认${item.state?.value === 'disabled' ? '启动' : '停止'}`}
                              onConfirm={() => {
                                changeStatus(item);
                              }}
                            >
                              <span>
                                <Switch
                                  size="small"
                                  checked={
                                    item.state?.value === 'disabled'
                                      ? false
                                      : item.state?.value === 'enabled'
                                  }
                                />
                              </span>
                            </Popconfirm>
                          </p>
                        </div>
                      </div>
                    </div>
                  </Card>
                </List.Item>
              );
            }
            return (
              <List.Item>
                <Button
                  type="dashed"
                  onClick={() => {
                    setCurrentItem({});
                    setSaveVisible(true);
                  }}
                  className={styles.newButton}
                >
                  <PlusOutlined />
                  新增组件
                </Button>
              </List.Item>
            );
          }}
        />
      </div>
      {saveVisible && (
        <Save
          data={basicInfo}
          close={() => {
            setBasicInfo({});
            setSaveVisible(false);
          }}
          visible={modalVisible}
          values={currentRow || {}}
          statusOptions={statusOptions}
        />
      )}
    </WrapContent>
  );
};

export default PostTableList;

