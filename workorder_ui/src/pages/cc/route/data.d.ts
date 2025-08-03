export type GatewayType = {
  id: number;
  name: string;
  mediaHost: string;
  mediaPort: string;
  callerPrefix: string;
  calledPrefix: string;
  profile: string;
  sipHeader1: string;
  sipHeader2: string;
  sipHeader3: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type GatewayListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type GatewayListData = {
  list: GatewayType[];
  pagination: Partial<GatewayListPagination>;
};

export type GatewayListParams = {
  name?: string;
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
