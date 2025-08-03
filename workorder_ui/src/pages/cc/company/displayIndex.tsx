import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import type { FormInstance } from 'antd';
import {Button, message, Modal} from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage, useAccess } from 'umi';
import { FooterToolbar } from '@ant-design/pro-layout';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { DisplayType, DisplayListParams } from './displayData';
import { getCompanyDisplayList, saveOrUpdateDisplay, removeDisplay, getPhoneFromDisplay } from './displayService';
import UpdateForm from './components/displayEdit';
import {getDict} from "@/pages/system/dict/service";


const handleAdd = async (fields: DisplayType) => {
  const hide = message.loading('正在添加');
  try {
    const resp = await saveOrUpdateDisplay({ ...fields });
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
const handleUpdate = async (fields: DisplayType) => {
  const hide = message.loading('正在配置');
  try {
    const resp = await saveOrUpdateDisplay(fields);
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
const handleRemove = async (selectedRows: DisplayType[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const resp = await removeDisplay(selectedRows.map((row) => row.id).join(','));
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

const handleRemoveOne = async (selectedRow: DisplayType) => {
  const hide = message.loading('正在删除');
  if (!selectedRow) return true;
  try {
    const params = [selectedRow.id];
    const resp = await removeDisplay(params.join(','));
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

  const [selectedRowsState, setSelectedRows] = useState<DisplayType[]>([]);
  const [currentRow, setCurrentRow] = useState<DisplayType>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [typeOptions, setTypeOptions] = useState<any>([]);
  const [strategyOptions, setStrategyOptions] = useState<any>([]);
  const access = useAccess();
  const [phoneIds, setPhoneIds] = useState<string[]>();
  const [phoneList, setPhoneList] = useState<string[]>();

  /** 国际化配置 */
  const intl = useIntl();

  useEffect(() => {
    getDict('company_phone_display_type').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setTypeOptions(opts);
      }
    });

    getDict('company_display_strategy').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setStrategyOptions(opts);
      }
    });

    getPhoneFromDisplay(0).then((res) => {
      if (res.code === 200) {
        setPhoneList(
          res.phones.map((item: any) => {
            return {
              value: item.id,
              label: item.phone,
            };
          }),
        );
      }
    });
  }, []);

  const columns: ProColumns<DisplayType>[] = [
    {
      title: <FormattedMessage id="company.phone.phone" defaultMessage="号码池" />,
      dataIndex: 'name',
      valueType: 'text',
      width: '20%',
    },
    {
      title: <FormattedMessage id="company.phone.type" defaultMessage="类型" />,
      dataIndex: 'type',
      valueType: 'select',
      valueEnum: typeOptions,
      width: '15%',
    },
    {
      title: <FormattedMessage id="company.phone.location" defaultMessage="号码策略" />,
      dataIndex: 'strategy',
      valueType: 'text',
      valueEnum: strategyOptions,
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="company.phone.companyName" defaultMessage="企业" />,
      dataIndex: 'companyName',
      valueType: 'text',
      width: '20%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="company.phone.updateTime" defaultMessage="更新时间" />,
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      width: '15%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="操作" />,
      dataIndex: 'option',
      width: '10%',
      valueType: 'option',
      render: (_, record) => [
        <Button
          type="link"
          size="small"
          key="edit"
          hidden={!access.hasPerms('cc:companyDisplay:add')}
          onClick={() => {
            const fetchPhoneFromDisplay = async (displayId: number) => {
              const res = await getPhoneFromDisplay(displayId);
              console.log("-----------------",res)
              setPhoneIds(res.phoneIds);
              setPhoneList(
                res.phones.map((item: any) => {
                  return {
                    value: item.id,
                    label: item.phone,
                  };
                }),
              );
            };
            fetchPhoneFromDisplay(record.id);
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
          hidden={!access.hasPerms('cc:company:remove')}
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
        <ProTable<DisplayType>
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
              hidden={!access.hasPerms('cc:companyDisplay:add')}
              onClick={async () => {
                setPhoneIds(undefined);
                setCurrentRow(undefined);
                setModalVisible(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
            </Button>,
            <Button
              type="primary"
              key="remove"
              hidden={selectedRowsState?.length === 0 || !access.hasPerms('cc:companyDisplay:remove')}
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
            getCompanyDisplayList({ ...params } as DisplayListParams).then((res) => {
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
            hidden={!access.hasPerms('cc:companyDisplay:remove')}
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
            success = await handleUpdate({ ...values } as DisplayType);
          } else {
            success = await handleAdd({ ...values } as DisplayType);
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
        typeOptions={typeOptions}
        strategyOptions={strategyOptions}
        phones = {phoneList||[]}
        phoneIds = {phoneIds||[]}
      />
    </WrapContent>
  );
};

export default PostTableList;
