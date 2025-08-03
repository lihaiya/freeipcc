/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */

export type QaType = {
  qaId: number;
  question: string;
  answer: string;
  qaType: number;
  status: string;
  createBy: string;
  createTime: Date;
  updateBy: string;
  updateTime: Date;
  remark: string;
};

export type QaListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type QaListData = {
  list: QaType[];
  pagination: Partial<QaListPagination>;
};

export type QaListParams = {
  qaId?: string;
  question?: string;
  answer?: string;
  qaType?: string;
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
