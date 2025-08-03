import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import type { FormInstance } from 'antd';
import {Button, message, Modal} from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage, useAccess } from 'umi';
import { FooterToolbar } from '@ant-design/pro-layout';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { IvrFlowType, IvrFlowListParams } from './data';
import { getIvrFlowList, addIvrFlow, removeIvrFlow, updateIvrFlow } from './service';
import UpdateForm from './components/edit';
import {getDict} from "@/pages/system/dict/service";
import { history } from 'umi'

const handleAdd = async (fields: IvrFlowType) => {
  const hide = message.loading('正在添加');
  try {
    const resp = await addIvrFlow({ ...fields });
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
const handleUpdate = async (fields: IvrFlowType) => {
  const hide = message.loading('正在保存');
  try {
    const resp = await updateIvrFlow(fields);
    hide();
    if(resp.code === 200) {
      message.success('保存成功');
    } else {
      message.error(resp.msg);
    }
    return true;
  } catch (error) {
    hide();
    message.error('保存失败请重试！');
    return false;
  }
};

/**
 * 删除节点
 *
 * @param selectedRows
 */
const handleRemove = async (selectedRows: IvrFlowType[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const resp = await removeIvrFlow(selectedRows.map((row) => row.id).join(','));
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

const handleRemoveOne = async (selectedRow: IvrFlowType) => {
  const hide = message.loading('正在删除');
  if (!selectedRow) return true;
  try {
    const params = [selectedRow.id];
    const resp = await removeIvrFlow(params.join(','));
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

  const [selectedRowsState, setSelectedRows] = useState<IvrFlowType[]>([]);
  const [currentRow, setCurrentRow] = useState<IvrFlowType>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [statusOptions, setStatusOptions] = useState<any>([]);
  const access = useAccess();
  /** 国际化配置 */
  const intl = useIntl();
  const gotoUrl = (record: IvrFlowType) => {
    console.log('---------------flow id = ',record.id)
    const flowId = record.id
    //history.push("/flow/ivr/", {flowId: flowId,name: record.name})
    history.push("/flow/flowmap/", {flowId: flowId,name: record.name})
  }
  useEffect(() => {
    getDict('ivr_flow_status').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setStatusOptions(opts);
      }
    });
  }, []);

  const columns: ProColumns<IvrFlowType>[] = [
    {
      title: <FormattedMessage id="cc.ivrFlow.flowName" defaultMessage="IVR名称" />,
      dataIndex: 'name',
      valueType: 'text',
      width: '20%',
    },
    {
      title: <FormattedMessage id="cc.ivrFlow.remark" defaultMessage="备注" />,
      dataIndex: 'remark',
      valueType: 'text',
      hideInSearch: true,
      width: '20%',
    },
    {
      title: <FormattedMessage id="cc.ivrFlow.status" defaultMessage="状态" />,
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: statusOptions,
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.ivrFlow.updateTime" defaultMessage="更新时间" />,
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInSearch: true,
      width: '15%',
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="操作" />,
      dataIndex: 'option',
      width: '20%',
      valueType: 'option',
      render: (_, record) => [
        <Button
          type="link"
          size="small"
          key="edit"
          onClick={() => {
            setModalVisible(true);
            setCurrentRow(record);
          }}
        >
          <FormattedMessage id="pages.searchTable.edit" defaultMessage="编辑" />
        </Button>,
        <Button
          type="link"
          size="small"
          key="flow"
          onClick={() => {
            gotoUrl(record)
          }}
        >
          <FormattedMessage id="pages.searchTable.edit" defaultMessage="流程" />
        </Button>,
        <Button
          type="link"
          size="small"
          danger
          key="batchRemove"
          hidden={!access.hasPerms('cc:ivrFlow:remove')}
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
        <ProTable<IvrFlowType>
          headerTitle={intl.formatMessage({
            id: 'pages.searchTable.title',
            defaultMessage: '信息',
          })}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="postId"
          key="postList"
          search={{
            labelWidth: 120,
          }}
          toolBarRender={() => [
            <Button
              type="primary"
              key="add"
              hidden={!access.hasPerms('cc:ivrFlow:add')}
              onClick={async () => {
                setCurrentRow(undefined);
                setModalVisible(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
            </Button>,
            <Button
              type="primary"
              key="remove"
              hidden={selectedRowsState?.length === 0 || !access.hasPerms('cc:company:remove')}
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
            getIvrFlowList({ ...params } as IvrFlowListParams).then((res) => {
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
            hidden={!access.hasPerms('cc:company:remove')}
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
            success = await handleUpdate({ ...values } as IvrFlowType);
          } else {
            success = await handleAdd({ ...values } as IvrFlowType);
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
      />
    </WrapContent>
  );
};

export default PostTableList;
