import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import type { FormInstance } from 'antd';
import {Button, message, Modal} from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage, useAccess } from 'umi';
import { FooterToolbar } from '@ant-design/pro-layout';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { RouteCallType, RouteCallListParams } from './routeCallData';
import { getRouteCallList, addRouteCall, removeRouteCall, updateRouteCall,getGatewayFromRouteCall } from './routeCallservice';
import UpdateForm from './components/routeCallEdit';
import {getDict} from "@/pages/system/dict/service";


const handleAdd = async (fields: RouteCallType) => {
  const hide = message.loading('正在添加');
  try {
    const resp = await addRouteCall({ ...fields });
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
const handleUpdate = async (fields: RouteCallType) => {
  const hide = message.loading('正在配置');
  try {
    const resp = await updateRouteCall(fields);
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
const handleRemove = async (selectedRows: RouteCallType[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const resp = await removeRouteCall(selectedRows.map((row) => row.id).join(','));
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

const handleRemoveOne = async (selectedRow: RouteCallType) => {
  const hide = message.loading('正在删除');
  if (!selectedRow) return true;
  try {
    const params = [selectedRow.id];
    const resp = await removeRouteCall(params.join(','));
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

  const [selectedRowsState, setSelectedRows] = useState<RouteCallType[]>([]);
  const [currentRow, setCurrentRow] = useState<RouteCallType>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [asrOptions, setAsrOptions] = useState<any>([]);
  const [asrHangupOptions, setAsrHangupOptions] = useState<any>([]);
  const [routeCallList, setRouteCallList] = useState<string[]>();
  const [gatewayIds, setGatewayIds] = useState<string[]>();

  const access = useAccess();

  /** 国际化配置 */
  const intl = useIntl();

  useEffect(() => {
    getDict('cc_route_asr_open').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setAsrOptions(opts);
      }
    });
    getDict('cc_route_asr_hangup').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setAsrHangupOptions(opts);
      }
    });
    getGatewayFromRouteCall(0).then((res) => {
      if (res.code === 200) {
        setRouteCallList(
          res.gateways.map((item: any) => {
            return {
              value: item.id,
              label: item.name,
            };
          }),
        );
      }
    });
  }, []);

  const columns: ProColumns<RouteCallType>[] = [
    {
      title: <FormattedMessage id="cc.routeCall.routeNum" defaultMessage="号码" />,
      dataIndex: 'routeNum',
      valueType: 'text',
      width: '20%',
    },
    {
      title: <FormattedMessage id="cc.routeCall.numMin" defaultMessage="最小" />,
      dataIndex: 'numMin',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.routeCall.numMax" defaultMessage="最长" />,
      dataIndex: 'numMax',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="cc.routeCall.callerChangeNum" defaultMessage="主叫置换" />,
      dataIndex: 'callerChangeNum',
      valueType: 'text',
      width: '15%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="cc.routeCall.callerChange" defaultMessage="主叫置换长度" />,
      dataIndex: 'callerChange',
      valueType: 'text',
      width: '15%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="cc.routeCall.calledChangeNum" defaultMessage="被叫置换" />,
      dataIndex: 'calledChangeNum',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="cc.routeCall.calledChange" defaultMessage="被叫置换长度" />,
      dataIndex: 'calledChange',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="pages.routeCall.titleOption" defaultMessage="操作" />,
      dataIndex: 'option',
      width: '10%',
      valueType: 'option',
      render: (_, record) => [
        <Button
          type="link"
          size="small"
          key="edit"
          hidden={!access.hasPerms('cc:routeCall:edit')}
          onClick={() => {
            const fetchGatewayFromRouteCall = async (routeCallId: number) => {
              const res = await getGatewayFromRouteCall(routeCallId);
              setGatewayIds(res.gatewayIds);
              setRouteCallList(
                res.gateways.map((item: any) => {
                  return {
                    value: item.id,
                    label: item.name,
                  };
                }),
              );
            };
            fetchGatewayFromRouteCall(record.id);
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
          hidden={!access.hasPerms('cc:routeCall:remove')}
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
        <ProTable<RouteCallType>
          headerTitle={intl.formatMessage({
            id: 'pages.searchTable.title',
            defaultMessage: '网关管理',
          })}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="gateWay"
          key="routeList"
          search={{
            labelWidth: 120,
          }}
          toolBarRender={() => [
            <Button
              type="primary"
              key="add"
              hidden={!access.hasPerms('cc:routeCall:add')}
              onClick={async () => {
                setGatewayIds(undefined);
                setCurrentRow(undefined);
                setModalVisible(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
            </Button>,
            <Button
              type="primary"
              key="remove"
              hidden={selectedRowsState?.length === 0 || !access.hasPerms('cc:routeCall:remove')}
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
            getRouteCallList({ ...params } as RouteCallListParams).then((res) => {
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
            hidden={!access.hasPerms('cc:routeCall:remove')}
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
            success = await handleUpdate({ ...values } as RouteCallType);
          } else {
            success = await handleAdd({ ...values } as RouteCallType);
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
        asrOptions={asrOptions}
        asrHangupOptions={asrHangupOptions}
        routeCalls = {routeCallList||[]}
        routeGatewayIds = {gatewayIds||[]}
      />
    </WrapContent>
  );
};

export default PostTableList;
