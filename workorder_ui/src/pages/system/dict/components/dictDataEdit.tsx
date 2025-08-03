import { EditableProTable,ProColumns } from '@ant-design/pro-components';
import React, {useEffect, useState} from 'react';
import {Drawer, message} from 'antd';

import {DictTypeType} from "../data";

import {FormattedMessage} from "umi";
import {DictDataListParams, DictDataType} from "@/pages/system/dictData/data";
import {addDictData, getDictDataList, removeDictData} from "@/pages/system/dictData/service";
import {getDict} from "@/pages/system/dict/service";


export type DictDataFormValueType = Record<string, unknown> & Partial<DictTypeType>;


export type DictDataFormProps = {
  onCancel: (flag?: boolean, formVals?: DictDataFormValueType) => void;
  visible: boolean;
  values: Partial<DictTypeType>;
  statusOptions: any;
  match?: {
    params: any
  }
};


const DictDataForm: React.FC<DictDataFormProps> = (props) => {
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);
  const [dataSource, setDataSource] = useState<DictDataType[]>([]);
  const [dictType, setDictType] = useState<string>('');

  const onClose = () => {
    props.onCancel();
  };

  useEffect(() => {
    if(props.values.dictType != undefined){
      setDictType(props.values.dictType)
      getDict(props.values.dictType).then((res) => {
        if (res.code === 200) {
           setDataSource(res.data);
        }
      });
    }
  }, [props]);

  const columns: ProColumns<DictDataType>[] = [
    {
      title: <FormattedMessage id="system.DictData.dict_label" defaultMessage="字典标签" />,
      dataIndex: 'dictLabel',
      width: '300',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
    {
      title: <FormattedMessage id="system.DictData.dict_value" defaultMessage="字典键值" />,
      dataIndex: 'dictValue',
      width: '200',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },

    {
      title: <FormattedMessage id="system.DictData.dict_sort" defaultMessage="字典排序" />,
      dataIndex: 'dictSort',
      width: '200',
      valueType: 'digit',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
    {
      title: <FormattedMessage id="system.DictData.list_class" defaultMessage="表格回显样式" />,
      dataIndex: 'listClass',
      valueType: 'select',
      width: '200',
      valueEnum: {
        default: {
            text: 'default',
            status: '默认',
          },
        primary: {
            text: 'primary',
            status: '主要',
          },
        success: {
            text: 'success',
            status: '成功',
          },
        info: {
            text: 'info',
            status: '信息',
          },
        warning: {
            text: 'warning',
            status: '警告',
          },
        danger: {
            text: 'danger',
            status: '危险',
          },
        },
    },
    {
      title: '操作',
      valueType: 'option',
      width: '200',
      render: (text, record, _, action) => [
        <a
          key="editable"
          onClick={() => {
            action?.startEditable?.(record.id);
          }}
        >
          编辑
        </a>,
          <a
            key="delete"
            onClick={async () => {
              // setDataSource(dataSource.filter((item) => item.id !== record.id));
              try {
                const resp = await removeDictData((record.dictCode)+"");
                if(resp.code === 200) {
                  message.success('删除成功');
                  getDict(dictType).then((res) => {
                    if (res.code === 200) {
                      setDataSource(res.data);
                    }
                  });
                }else {
                  message.error('删除失败');
                  return false;
                }
                return true;
              } catch (error) {
                message.error('删除失败请重试！');
                return false;
              }
            }}
          >
            删除
          </a>,
      ],
    },
  ];

  return (
    <Drawer
      title={props.values.dictName}
      width={720}
      visible={props.visible}
      onClose={onClose}
    >
      <EditableProTable<DictDataType>
        rowKey="id"
        headerTitle={props.values.dictType}
        scroll={{
          x: 580,
        }}
        recordCreatorProps={{
          // 每次新增的时候需要Key
          record: () => ({ id: (Math.random() * 1000000).toFixed(0) }),
        }}
        loading={false}
        columns={columns}
        request={async (params) => {
          if (dictType.length === 0) {
            return {
              data: [],
              total: 0,
              success: true,
            };
          }
          const res = await getDictDataList({ dictType, ...params } as DictDataListParams);
          return {
            data: res.rows,
            total: res.total,
            success: true,
          };
        }}
        value={dataSource}
        onChange={setDataSource}
        editable={{
          type: 'multiple',
          editableKeys,
          onChange: setEditableRowKeys,
          onSave: async (rowKey, data, row) => {
            console.log(rowKey, data, row);
            data["dictType"] = dictType;
            try {
              const resp = await addDictData({ ...data });
              if(resp.code === 200) {
                message.success('更新成功');
                getDict(dictType).then((res) => {
                  if (res.code === 200) {
                    setDataSource(res.data);
                  }
                });
              }else {
                message.error('更新失败');
                return false;
              }
              return true;
            } catch (error) {
              message.error('更新失败请重试！');
              return false;
            }
          },
        }}
      />
    </Drawer>
  );
};
export default DictDataForm;
