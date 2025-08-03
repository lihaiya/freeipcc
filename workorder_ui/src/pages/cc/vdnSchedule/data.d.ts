export type VdnScheduleType = {
  id: number;
  vdnId: number;
  name: string;
  companyId: number;
  type: number;
  levelValue: number;
  startDay: string;
  endDay: string;
  timeRange: string;
  startTime: string;
  endTime: string;
  routeType: string;
  routeValue: string;
  playType: string;
  playValue: number;
  weekRange: string[];
  week: string;
  mon: number;
  tue: number;
  wed: number;
  thu: number;
  fri: number;
  sat: number;
  sun: number;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type VdnScheduleListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type VdnScheduleListData = {
  list: VdnScheduleType[];
  pagination: Partial<VdnScheduleListPagination>;
};

export type VdnScheduleListParams = {
  name?: string;
  vdnId?: number;
  companyId?: number;
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
