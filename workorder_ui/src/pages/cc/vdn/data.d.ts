export type VdnType = {
  id: number;
  phone: string;
  companyName: string;
  status: number;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type VdnListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type VdnListData = {
  list: VdnType[];
  pagination: Partial<VdnListPagination>;
};

export type VdnListParams = {
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
