export type TcdType = {
  id: number;
  companyId: number;
  vdnId: number;
  callId: number;
  groupId: number;
  deviceId: number;
  agentKey: string;
  agentName: string;
  loginType: string;
  caller: string;
  called: string;
  callType: string;
  direction: string;
  callTime: Date;
  endTime: Date;
  answerTime: Date;
  talkTime: number;
  waitTime: number
  ringTime: number;
  recordTime: number;
  rna: string;
  ringStartTime: Date;
  ringEndTime: Date;
  recordStartTime: Date;
  recordEndTime: Date;
  recordFile: string;
  firstQueueTime: Date;
  queueStartTime: Date;
  queueEndTime: Date;
  hangupDir: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type TcdListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type TcdListData = {
  list: TcdType[];
  pagination: Partial<TcdListPagination>;
};

export type TcdListParams = {
  caller?: string;
  called?: string;

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
