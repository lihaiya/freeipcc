import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import type { FormInstance } from 'antd';
import {Button, message, Modal} from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage, useAccess } from 'umi';
import { FooterToolbar } from '@ant-design/pro-layout';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { GroupType, GroupListParams } from './data';
import {getGroupList, addGroup, removeGroup, updateGroup, getUserGroup, getAllGroupList} from './service';
import UpdateForm from './components/edit';
import {getDict} from "@/pages/system/dict/service";
import {getAllIvrFlowList} from "@/pages/flow/flowmanager/service";


const handleAdd = async (fields: GroupType) => {
  const hide = message.loading('正在添加');
  try {
    const resp = await addGroup({ ...fields });
    hide();
    if(resp.code === 200) {
      message.success('添加成功');
    } else {
      message.error(resp.msg);
    }
    return true;
  } catch (error) {
    hide();
    message.error('添加失败请重试！');
    return false;
  }
};

/**
 * 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: GroupType) => {
  const hide = message.loading('正在配置');
  try {
    const resp = await updateGroup(fields);
    hide();
    if(resp.code === 200) {
      message.success('配置成功');
    } else {
      message.error(resp.msg);
    }
    return true;
  } catch (error) {
    hide();
    message.error('配置失败请重试！');
    return false;
  }
};

/**
 * 删除节点
 *
 * @param selectedRows
 */
const handleRemove = async (selectedRows: GroupType[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const resp = await removeGroup(selectedRows.map((row) => row.id).join(','));
    hide();
    if(resp.code === 200) {
      message.success('删除成功，即将刷新');
    } else {
      message.error(resp.msg);
    }
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const handleRemoveOne = async (selectedRow: GroupType) => {
  const hide = message.loading('正在删除');
  if (!selectedRow) return true;
  try {
    const params = [selectedRow.id];
    const resp = await removeGroup(params.join(','));
    hide();
    if(resp.code === 200) {
      message.success('删除成功，即将刷新');
    } else {
      message.error(resp.msg);
    }
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};


const PostTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  const [selectedRowsState, setSelectedRows] = useState<GroupType[]>([]);
  const [currentRow, setCurrentRow] = useState<GroupType>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [statusOptions, setStatusOptions] = useState<any>([]);
  const [agentStrategyOptions, setAgentStrategyOptions] = useState<any>([]);
  const [overFlowOptions, setOverFlowOptions] = useState<any>([]);
  const [agentIds, setAgentIds] = useState<string[]>();
  const [agentList, setAgentList] = useState<string[]>();

  const [ivrOptions, setIvrOptions] = useState<any>([]);
  const [queueOptions, setQueueOptions] = useState<any>([]);

  const access = useAccess();

  /** 国际化配置 */
  const intl = useIntl();

  useEffect(() => {
    getDict('sys_normal_disable').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setStatusOptions(opts);
      }
    });
    // 溢出处理类型
    getDict('queue_over_handle').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setOverFlowOptions(opts);
      }
    });
    // 转坐席策略
    getDict('route_agent_strategy').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setAgentStrategyOptions(opts);
      }
    });

    getUserGroup(0).then((res) => {
      if (res.code === 200) {
        setAgentList(
          res.users.map((item: any) => {
            return {
              value: item.userId,
              label: item.nickName,
            };
          }),
        );
      }
    });
    getAllIvrFlowList().then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.id] = item.name;
        });
        setIvrOptions(opts);
      }
    });
    getAllGroupList().then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.id] = item.name;
        });
        setQueueOptions(opts);
      }
    });

  }, []);

  const columns: ProColumns<GroupType>[] = [
    {
      title: <FormattedMessage id="cc.group.groupName" defaultMessage="名称" />,
      dataIndex: 'name',
      valueType: 'text',
      width: '15%',
    },
    {
      title: <FormattedMessage id="cc.group.queueNum" defaultMessage="最大排队人数" />,
      dataIndex: 'queueInout',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.group.queueOverHandle" defaultMessage="溢出处理" />,
      dataIndex: 'queueInoutHandle',
      valueType: 'text',
      width: '10%',
      valueEnum: overFlowOptions,
    },
    {
      title: <FormattedMessage id="cc.group.queueOverValue" defaultMessage="溢出转移" />,
      dataIndex: 'queueInoutValue',
      valueType: 'text',
      width: '10%',
      renderText: (value, record, index) => {
        const type = record.queueInoutHandle
        if(type=='1'){
          return queueOptions[value]
        }else if(type=='2'){
          return ivrOptions[value]
        }
      },
    },
    {
      title: <FormattedMessage id="cc.group.overTime" defaultMessage="最大等待时间" />,
      dataIndex: 'queueTimeOut',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.group.overTimeHandle" defaultMessage="超时处理" />,
      dataIndex: 'queueTimeoutHandle',
      valueType: 'text',
      width: '10%',
      valueEnum: overFlowOptions,
    },
    {
      title: <FormattedMessage id="cc.group.overTimeValue" defaultMessage="超时转移" />,
      dataIndex: 'queueTimeoutValue',
      valueType: 'text',
      width: '10%',
      renderText: (value, record, index) => {
        const type = record.queueTimeoutHandle
        if(type=='1'){
          return queueOptions[value]
        }else if(type=='2'){
          return ivrOptions[value]
        }
      },
    },
    {
      title: <FormattedMessage id="cc.group.status" defaultMessage="状态" />,
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: statusOptions,
      hideInSearch: true,
      width: '10%',
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="操作" />,
      dataIndex: 'option',
      width: '15%',
      valueType: 'option',
      render: (_, record) => [
        <Button
          type="link"
          size="small"
          key="edit"
          hidden={!access.hasPerms('cc:group:edit')}
          onClick={() => {
            const fetchAgentsFromGroup = async (groupId: number) => {
              const res = await getUserGroup(groupId);
              console.log("-----------------",res)
              setAgentIds(res.agentIds);
              setAgentList(
                res.users.map((item: any) => {
                  return {
                    value: item.userId,
                    label: item.nickName,
                  };
                }),
              );
            };
            fetchAgentsFromGroup(record.id);
            setModalVisible(true);
            setCurrentRow(record);
          }}
        >
          <FormattedMessage id="pages.searchTable.edit" defaultMessage="编辑" />
        </Button>,
        <Button
          type="link"
          size="small"
          danger
          key="batchRemove"
          hidden={!access.hasPerms('cc:group:remove')}
          onClick={async () => {
            Modal.confirm({
              title: '删除',
              content: '确定删除该项吗？',
              okText: '确认',
              cancelText: '取消',
              onOk: async () => {
                const success = await handleRemoveOne(record);
                if (success) {
                  if (actionRef.current) {
                    actionRef.current.reload();
                  }
                }
              },
            });
          }}
        >
          <FormattedMessage id="pages.searchTable.delete" defaultMessage="删除" />
        </Button>,
      ],
    },
  ];

  return (
    <WrapContent>
      <div style={{ width: '100%', float: 'right' }}>
        <ProTable<GroupType>
          headerTitle={intl.formatMessage({
            id: 'pages.searchTable.title',
            defaultMessage: '信息',
          })}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="greoupId"
          key="groupList"
          search={{
            labelWidth: 120,
          }}
          toolBarRender={() => [
            <Button
              type="primary"
              key="add"
              hidden={!access.hasPerms('cc:group:add')}
              onClick={async () => {
                setAgentIds(undefined);
                setCurrentRow(undefined);
                setModalVisible(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
            </Button>,
            <Button
              type="primary"
              key="remove"
              hidden={selectedRowsState?.length === 0 || !access.hasPerms('cc:group:remove')}
              onClick={async () => {
                const success = await handleRemove(selectedRowsState);
                if (success) {
                  setSelectedRows([]);
                  actionRef.current?.reloadAndRest?.();
                }
              }}
            >
              <DeleteOutlined />
              <FormattedMessage id="pages.searchTable.delete" defaultMessage="删除" />
            </Button>,
          ]}
          request={(params) =>
            getGroupList({ ...params } as GroupListParams).then((res) => {
              return {
                data: res.rows,
                total: res.total,
                success: true,
              };
            })
          }
          columns={columns}
          rowSelection={{
            onChange: (_, selectedRows) => {
              setSelectedRows(selectedRows);
            },
          }}
        />
      </div>
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              <FormattedMessage id="pages.searchTable.chosen" defaultMessage="已选择" />
              <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a>
              <FormattedMessage id="pages.searchTable.item" defaultMessage="项" />
            </div>
          }
        >
          <Button
            key="remove"
            hidden={!access.hasPerms('cc:group:remove')}
            onClick={async () => {
              Modal.confirm({
                title: '删除',
                content: '确定删除该项吗？',
                okText: '确认',
                cancelText: '取消',
                onOk: async () => {
                  setSelectedRows([]);
                  actionRef.current?.reloadAndRest?.();
                  const success = await handleRemove(selectedRowsState);
                  if (success) {
                    setSelectedRows([]);
                    actionRef.current?.reloadAndRest?.();
                  }
                },
              });
            }}
          >
            <FormattedMessage id="pages.searchTable.batchDeletion" defaultMessage="批量删除" />
          </Button>
        </FooterToolbar>
      )}
      <UpdateForm
        onSubmit={async (values) => {
          let success = false;
          if (values.id) {
            success = await handleUpdate({ ...values } as GroupType);
          } else {
            success = await handleAdd({ ...values } as GroupType);
          }
          if (success) {
            setModalVisible(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          setModalVisible(false);
          setCurrentRow(undefined);
        }}
        visible={modalVisible}
        values={currentRow || {}}
        statusOptions={statusOptions}
        agentStrategyOptions = {agentStrategyOptions}
        overFlowOptions = {overFlowOptions}
        agents = {agentList||[]}
        agentIds = {agentIds||[]}
        ivrOptions={ivrOptions||[]}
        queueOptions={queueOptions||[]}
      />
    </WrapContent>
  );
};

export default PostTableList;
