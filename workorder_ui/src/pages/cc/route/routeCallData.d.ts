export type RouteCallType = {
  id: number;
  companyId: number;
  routeNum: string;
  numMax: string;
  numMin: string;
  callerChange: number;
  callerChangeNum: string;
  calledChange: number;
  calledChangeNum: string;
  asrFlag: number;
  hangupFlag: number;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type RouteCallListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type RouteCallListData = {
  list: RouteCallType[];
  pagination: Partial<RouteCallListPagination>;
};

export type RouteCallListParams = {
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
