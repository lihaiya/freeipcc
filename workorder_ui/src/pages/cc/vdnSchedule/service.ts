import request from '@/utils/request';
import type { VdnScheduleType, VdnScheduleListParams } from './data';


export async function getVdnScheduleList (params?: VdnScheduleListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/vdn/schedule/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export async function getVdnScheduleSingleList (vdnId: string) {
  return request(`/api/cc/vdn/schedule/singleList/${vdnId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getVdnSchedule (id: number) {
  return request(`/api/cc/vdn/schedule/${id}`, {
    method: 'GET'
  });
}


export async function addVdnSchedule (params: VdnScheduleType) {
  return request('/api/cc/vdn/schedule', {
    method: 'POST',
    data: params
  });
}


export async function updateVdnSchedule (params: VdnScheduleType) {
  return request('/api/cc/vdn/schedule', {
    method: 'PUT',
    data: params
  });
}


export async function removeVdnSchedule (ids: string) {
  return request(`/api/cc/vdn/schedule/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
