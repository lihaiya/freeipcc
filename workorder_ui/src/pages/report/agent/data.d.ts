export type AgentType = {
  id: number;
  companyId: number;
  agentKey: string;
  agentName: string;
  loginType: number;
  beforeState: string;
  beforeTime: Date;
  remark: string;
  state: string;
  stateTime: Date;
  duration: number;
  extension: string;
  busyDesc: string;
};

export type AgentListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type TcdListData = {
  list: AgentType[];
  pagination: Partial<AgentListPagination>;
};

export type AgentListParams = {
  agentKey?: string;
  stateTime?: string;

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
