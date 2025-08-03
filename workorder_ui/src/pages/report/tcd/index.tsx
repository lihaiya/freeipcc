import type { FormInstance } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { useIntl, FormattedMessage } from 'umi';
import WrapContent from '@/components/WrapContent';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { TcdType, TcdListParams } from './data';
import { getTcdList} from './service';
import moment from "moment";


const PostTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [selectedRowsState, setSelectedRows] = useState<TcdType[]>([]);

  /** 国际化配置 */
  const intl = useIntl();

  useEffect(() => {
  }, []);

  const columns: ProColumns<TcdType>[] = [
    {
      title: <FormattedMessage id="report.tcd.caller" defaultMessage="主叫号码" />,
      dataIndex: 'caller',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="report.tcd.callee" defaultMessage="被叫号码" />,
      dataIndex: 'called',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="report.tcd.agentName" defaultMessage="坐席" />,
      dataIndex: 'agentName',
      valueType: 'text',
      width: '10%',
    },
    {
      title: <FormattedMessage id="report.tcd.callTime" defaultMessage="拨打时间" />,
      dataIndex: 'callTime',
      valueType: 'dateTimeRange',
      width: '15%',
      render: function (_, record) {
        return record.callTime ? moment(record.callTime).format('YYYY-MM-DD HH:mm:ss') : ''
      },
      search: {
        transform: (value) => {
          return {
            'params[beginTime]': value[0],
            'params[endTime]': value[1],
          };
        },
      },
    },
    {
      title: <FormattedMessage id="report.tcd.answerTime" defaultMessage="应答时间" />,
      dataIndex: 'recordStartTime',
      valueType: 'dateTime',
      width: '15%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.tcd.endTime" defaultMessage="挂机时间" />,
      dataIndex: 'recordEndTime',
      valueType: 'dateTime',
      width: '15%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.tcd.ringTime" defaultMessage="振铃时长" />,
      dataIndex: 'ringTime',
      valueType: 'text',
      width: '5%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.tcd.waitTime" defaultMessage="排队时长" />,
      dataIndex: 'waitTime',
      valueType: 'text',
      width: '5%',
      hideInSearch: true,
    },
    {
      title: <FormattedMessage id="report.tcd.talkTime" defaultMessage="通话时长" />,
      dataIndex: 'recordTime',
      valueType: 'text',
      width: '5%',
      hideInSearch: true,
    },
  ];

  return (
    <WrapContent>
      <div style={{ width: '100%', float: 'right' }}>
        <ProTable<TcdType>
          headerTitle='通话流水'
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          key="tcdList"
          search={{
            labelWidth: 120,
          }}
          request={(params) =>
            getTcdList({ ...params } as TcdListParams).then((res) => {
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
