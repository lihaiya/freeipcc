import request from '@/utils/request';
import type { GroupType, GroupListParams } from './data';


export async function getGroupList (params?: GroupListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/group/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
export function getAllGroupList() {
  return request(`/api/cc/group/listAll`, {
    method: 'GET',
  });
}

export function getGroup (id: number) {
  return request(`/api/cc/group/${id}`, {
    method: 'GET'
  });
}


export async function addGroup (params: GroupType) {
  return request('/api/cc/group', {
    method: 'POST',
    data: params
  });
}


export async function updateGroup (params: GroupType) {
  return request('/api/cc/group', {
    method: 'PUT',
    data: params
  });
}


export async function removeGroup (ids: string) {
  return request(`/api/cc/group/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

// 查询用户及队列已选用户
export function getUserGroup(groupId: number) {
  return request(`/api/cc/group/groupAgents/${groupId}`, {
    method: 'GET',
  });
}
