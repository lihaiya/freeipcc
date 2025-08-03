import type { FormInstance } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage, useAccess } from 'umi';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { AgentType, AgentListParams } from './data';
import { getAgentStateList} from './service';


const PostTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [selectedRowsState, setSelectedRows] = useState<AgentType[]>([]);


  const access = useAccess();

  /** 国际化配置 */
  const intl = useIntl();

  useEffect(() => {
  }, []);

  const columns: ProColumns<AgentType>[] = [
    {
      title: <FormattedMessage id="report.agentStatus.agentKey" defaultMessage="工号" />,
      dataIndex: 'agentKey',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="report.agentStatus.agentName" defaultMessage="坐席" />,
      dataIndex: 'agentName',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.agentStatus.beforeState" defaultMessage="变更前状态" />,
      dataIndex: 'beforeState',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.agentStatus.beforeTime" defaultMessage="状态时间" />,
      dataIndex: 'beforeTime',
      valueType: 'dateTime',
      width: '15%',
    },

    {
      title: <FormattedMessage id="report.agentStatus.state" defaultMessage="当前状态" />,
      dataIndex: 'state',
      valueType: 'text',
      width: '5%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.agentStatus.stateTime" defaultMessage="当前状态时间" />,
      dataIndex: 'stateTime',
      valueType: 'dateTime',
      width: '15%',
    },

    {
      title: <FormattedMessage id="report.agentStatus.duration" defaultMessage="状态时长" />,
      dataIndex: 'duration',
      valueType: 'text',
      width: '5%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.agentStatus.busyDesc" defaultMessage="原因" />,
      dataIndex: 'busyDesc',
      valueType: 'text',
      width: '10%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.agentStatus.remark" defaultMessage="备注" />,
      dataIndex: 'remark',
      valueType: 'text',
      width: '20%',
      hideInSearch: true,
    },
  ];

  return (
    <WrapContent>
      <div style={{ width: '100%', float: 'right' }}>
        <ProTable<AgentType>
          headerTitle="座席状态历史"
          actionRef={actionRef}
          formRef={formTableRef}
          search={{
            labelWidth: 120,
          }}
          request={(params) =>
            getAgentStateList({ ...params } as AgentListParams).then((res) => {
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
    </WrapContent>
  );
};

export default PostTableList;
