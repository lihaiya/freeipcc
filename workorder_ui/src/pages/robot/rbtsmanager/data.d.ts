/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */

export type RobotChat = {
  robotId: number;
  robotName: string;
  photoUrl: string;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type RobotListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type RobotListData = {
  list: RobotChat[];
  pagination: Partial<RobotListPagination>;
};

export type RobotListParams = {
  robotId: number;
  robotName: string;
  photoUrl: string;
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
