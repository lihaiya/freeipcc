export type DisplayType = {
  id: number;
  companyId: number;
  phone: string;
  type: string;
  name: string;
  strategy: string;
  location: string;
  state: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type DisplayListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type DisplayListData = {
  list: DisplayType[];
  pagination: Partial<DisplayListPagination>;
};

export type DisplayListParams = {
  name?: string;
  companyId?: string;
  phone?: string;
  type?: string;
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
