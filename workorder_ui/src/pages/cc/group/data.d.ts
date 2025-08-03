export type GroupType = {
  id: number;
  name: string;
  companyId: number;
  afterInterval: number;
  callerDisplayId: number;
  calledDisplayId: number;
  recordType: number;
  levelValue: number;
  ttsEngine: number;
  playContent: string;
  evaluate: number;
  queuePlay: number;
  transferPlay: number;
  callTimeOut: number;
  groupType: number;
  notifyPosition: number;
  notifyRate: number;
  notifyContent: string;
  callMemory: number;
  queueInout: number;
  queueInoutHandle: string;
  queueInoutValue: string;
  queueTimeOut: number;
  queueTimeoutHandle: string;
  queueTimeoutValue: string;
  agentStrategy: number;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type GroupListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type GroupListData = {
  list: GroupType[];
  pagination: Partial<GroupListPagination>;
};

export type GroupListParams = {
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
