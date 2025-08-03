export type CompanyType = {
  id: number;
  name: string;
  contact: string;
  phone: string;
  balance: number;
  billType: number;
  payType: number;
  secretType: number;
  secretKey: string;
  ivrLimit: number;
  agentLimit: number;
  groupLimit: number;
  groupAgentLimit: number;
  recordStorage: number;
  notifyUrl: string;
  state: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type CompanyListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type CompanyListData = {
  list: CompanyType[];
  pagination: Partial<CompanyListPagination>;
};

export type CompanyListParams = {
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
