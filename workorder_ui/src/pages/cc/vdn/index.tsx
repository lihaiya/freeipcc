import { CheckCard } from '@ant-design/pro-components';
import { UserOutlined } from '@ant-design/icons';
import React, {useEffect, useRef, useState} from "react";
import { VdnScheduleType} from "@/pages/cc/vdnSchedule/data";
import { VdnType} from "./data";
import ProTable, {ActionType, ProColumns} from "@ant-design/pro-table";
import {FormattedMessage, useAccess, useIntl} from "umi";
import {Button, FormInstance, message, Modal,Avatar } from "antd";
import WrapContent from "@/components/WrapContent";
import {DeleteOutlined, PlusOutlined} from "@ant-design/icons";
import {
  addVdnSchedule,
  getVdnScheduleSingleList,
  removeVdnSchedule,
  updateVdnSchedule
} from "@/pages/cc/vdnSchedule/service";
import {
  addVdn,getVdnList
} from "./service";
import {FooterToolbar} from "@ant-design/pro-layout";
import UpdateForm from "@/pages/cc/vdnSchedule/components/edit";
import UpdateVdnForm from "./components/edit";
import {getDict} from "@/pages/system/dict/service";
import {getAllIvrFlowList} from "@/pages/flow/flowmanager/service";

const PostVdn: React.FC = () => {
  const [selectedVdn, setSelectedVdn] = useState<string>('');
  const [selectedRowsState, setSelectedRows] = useState<VdnScheduleType[]>([]);
  const [currentRow, setCurrentRow] = useState<VdnScheduleType>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [modalVdnVisible, setModalVdnVisible] = useState<boolean>(false);
  const [statusOptions, setStatusOptions] = useState<any>([]);
  const [scheduleList, setScheduleList] = useState<VdnScheduleType[]>([]);
  const [typeOptions, setTypeOptions] = useState<any>([]);
  const [ivrOptions, setIvrOptions] = useState<any>([]);
  const [vdnList, setVdnList] = useState<any>([]);

  /** 国际化配置 */
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formTableRef = useRef<FormInstance>();

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
    getDict('cc_route_type').then((res) => {
      if (res.code === 200) {
        const opts = {};
        res.data.forEach((item: any) => {
          opts[item.dictValue] = item.dictLabel;
        });
        setTypeOptions(opts);
      }
    });
    getVdnList().then((res) => {
      console.log('-------------data=',res)
      const opts: { title: any; description: any; value: any; avatar: any; }[] = [];
      if (res.code === 200) {
        res.rows.forEach((item: any) => {
          const avtext = item.company.name.substring(0, 1);
          let c = {
            title: item.phone,
            description: item.company.name,
            value: item.id,
            avatar: (
              <Avatar style={{backgroundColor: '#7265e6'}} icon={avtext} size="large"/>
            ),
          }
          opts.push(c);
        });
        setVdnList(opts);
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
  }, []);

  const vdnChange = async (checkedValue: any) => {
    console.log('------------value=%s',checkedValue)
    setSelectedVdn(checkedValue)
    if(checkedValue != undefined){
      getVdnScheduleSingleList(checkedValue).then((res) => {
        console.log('-------------data=',res)
        if (res.code === 200) {
          setScheduleList(res.rows);
        }
      });
    }else{
      setScheduleList([]);
    }
  };

  const reloadScheduleList = async (vdnId: any) => {
    console.log('----reload vdnid=',vdnId)
    getVdnScheduleSingleList(vdnId).then((res) => {
      console.log('-------------data=',res)
      if (res.code === 200) {
        setScheduleList(res.rows);
      }
    });
  };

  const reloadVdnList = async () => {
    getVdnList().then((res) => {
      console.log('-------------data=',res)
      const opts: { title: any; description: any; value: any; avatar: any; }[] = [];
      if (res.code === 200) {
        res.rows.forEach((item: any) => {
          const avText = item.company.name.substring(0, 1);
          let c = {
            title: item.phone,
            description: item.name,
            value: item.id,
            avatar: (
                <Avatar style={{backgroundColor: '#7265e6'}} icon={avText} size="large"/>
              ),
          }
          opts.push(c);
        });
        setVdnList(opts);
      }
    });
  };

  const handleAdd = async (fields: VdnScheduleType) => {
    const hide = message.loading('正在添加');
    try {
      const resp = await addVdnSchedule({ ...fields });
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
  const handleUpdate = async (selectedRow: VdnScheduleType) => {
    const hide = message.loading('正在配置');
    try {
      const resp = await updateVdnSchedule(selectedRow);
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
  const handleRemove = async (selectedRows: VdnScheduleType[]) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      const resp = await removeVdnSchedule(selectedRows.map((row) => row.id).join(','));
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

  const handleRemoveOne = async (selectedRow: VdnScheduleType) => {
    const hide = message.loading('正在删除');
    if (!selectedRow) return true;
    try {
      const params = [selectedRow.id];
      const resp = await removeVdnSchedule(params.join(','));
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

  const handleVdnAdd = async (fields: VdnType) => {
    const hide = message.loading('正在添加接入号码');
    try {
      const resp = await addVdn({ ...fields });
      hide();
      if(resp.code === 200) {
        message.success('接入号码添加成功');
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

  const columns: ProColumns<VdnScheduleType>[] = [
    {
      title: <FormattedMessage id="cc.VdnSchedule.VdnScheduleName" defaultMessage="生效日" />,
      dataIndex: 'effectiveRange',
      valueType: 'text',
      width: '25%',
    },
    {
      title: <FormattedMessage id="cc.VdnSchedule.VdnScheduleTime" defaultMessage="工作时间" />,
      dataIndex: 'timeRange',
      valueType: 'text',
      width: '15%',
    },
    {
      title: <FormattedMessage id="cc.VdnSchedule.VdnScheduleRouteType" defaultMessage="路由类型" />,
      dataIndex: 'routeType',
      valueType: 'text',
      width: '10%',
      valueEnum: typeOptions,
    },
    {
      title: <FormattedMessage id="cc.VdnSchedule.VdnScheduleRouteValue" defaultMessage="路由转接" />,
      dataIndex: 'routeValue',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.VdnSchedule.status" defaultMessage="启用状态" />,
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: statusOptions,
      width: '10%',
    },
    {
      title: <FormattedMessage id="cc.VdnSchedule.status" defaultMessage="备注" />,
      dataIndex: 'remark',
      valueType: 'text',
      width: '15%',
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
          hidden={!access.hasPerms('cc:vdn:edit')}
          onClick={() => {
            setModalVisible(true);
            record.weekRange = record.week.split(",")
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
          hidden={!access.hasPerms('cc:vdn:remove')}
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
      <div style={{ width: '15%', display: 'flex', justifyContent: 'flex-start', margin: '0px 10px 20px 0px' }}>
        <Button style={{ width: '100%' }} type="dashed" onClick={() => {
          setModalVdnVisible(true);
        }}>
          <PlusOutlined />
          新增接入号码
        </Button>
      </div>
      <div style={{ width: '100%', float: 'right' }}>
        <CheckCard.Group options={vdnList} size="small" onChange={(checkedValue) => {
          vdnChange(checkedValue);
        }}/>
        <ProTable<VdnScheduleType>
          headerTitle={intl.formatMessage({
            id: 'pages.searchTable.route',
            defaultMessage: '路由策略',
          })}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          key="vdnScheduleList"
          toolBarRender={() => [
            <Button
              type="primary"
              key="add"
              hidden={!access.hasPerms('cc:vdn:add')}
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
              hidden={selectedRowsState?.length === 0 || !access.hasPerms('cc:vdn:remove')}
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
          search={false}
          dataSource={(scheduleList || {})}
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
            hidden={!access.hasPerms('cc:VdnSchedule:remove')}
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
            success = await handleUpdate({ ...values } as VdnScheduleType);
          } else {
            success = await handleAdd({ ...values } as VdnScheduleType);
          }
          if (success) {
            reloadScheduleList(selectedVdn)
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
        typeOptions={typeOptions}
        ivrOptions={ivrOptions}
      />
      <UpdateVdnForm
        onSubmit={async (values) => {
          let success = false;
          success = await handleVdnAdd({ ...values } as VdnType);
          if (success) {
            reloadVdnList()
            setModalVdnVisible(false);
          }
        }}
        onCancel={() => {
          setModalVdnVisible(false);
        }}
        values={{}}
        visible={modalVdnVisible}
        statusOptions={statusOptions}
      />
    </WrapContent>
  );
};
export default PostVdn;


