import request from '@/utils/request';
import type { IvrFlowType, IvrFlowListParams } from './data';


export async function getIvrFlowList (params?: IvrFlowListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/ivrFlow/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getAllIvrFlowList() {
  return request(`/api/cc/ivrFlow/listAll`, {
    method: 'GET',
  });
}

export function getIvrFlow (ivrId: number) {
  return request(`/api/cc/ivrFlow/${ivrId}`, {
    method: 'GET'
  });
}


export async function addIvrFlow (params: IvrFlowType) {
  return request('/api/cc/ivrFlow', {
    method: 'POST',
    data: params
  });
}


export async function updateIvrFlow (params: IvrFlowType) {
  return request('/api/cc/ivrFlow', {
    method: 'PUT',
    data: params
  });
}


export async function removeIvrFlow (ids: string) {
  return request(`/api/cc/ivrFlow/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
