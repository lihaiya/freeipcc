export type Playback = {
  id: number;
  name: string;
  companyId: number;
  playback: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type PlaybackPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type PlaybackListData = {
  list: Playback[];
  pagination: Partial<PlaybackPagination>;
};

export type PlaybackListParams = {
  name?: string;
  playback?: string;
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
