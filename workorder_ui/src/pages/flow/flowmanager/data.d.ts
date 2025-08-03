export type IvrFlowType = {
  id: number;
  name: string;
  status: string;
  content: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type IvrFlowListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type IvrFlowListData = {
  list: IvrFlowType[];
  pagination: Partial<IvrFlowListPagination>;
};

export type IvrFlowListParams = {
  content?: string;
  status?: string;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  remark?: string;
  pageSize?: string;
  currentPage?: string;
  filter?: string;
  sorter?: string;
};
