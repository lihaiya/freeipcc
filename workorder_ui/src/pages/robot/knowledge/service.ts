import { downLoadXlsx } from '@/utils/downloadfile';
import request from '@/utils/request';
import type { QaType, QaListParams } from './data';

/* *
 *
 * @author whiteshader@163.com
 * @datetime  2021/09/16
 *
 * */




// 查询岗位信息列表
export async function getQaList (params?: QaListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/aidl/qa/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

// 查询岗位信息详细
export function getQa (qaId: number) {
  return request(`/api/aidl/qa/${qaId}`, {
    method: 'GET'
  });
}

// 新增岗位信息
export async function addQa (params: QaType) {
  return request('/api/aidl/qa', {
    method: 'POST',
    data: params
  });
}

// 修改岗位信息
export async function updateQa (params: QaType) {
  return request('/api/aidl/qa', {
    method: 'PUT',
    data: params
  });
}

// 删除岗位信息
export async function removeQa (ids: string) {
  return request(`/api/aidl/qa/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

// 删除岗位信息
export async function qaEmbTraining () {
  return request(`/api/ai/text/extractDbFeatures`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

// 导出岗位信息
export function exportQa (params?: QaListParams) {
  return downLoadXlsx(`/api/aidl/qa/export`, { params }, `post_${new Date().getTime()}.xlsx`);
}
