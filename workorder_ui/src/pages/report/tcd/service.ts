import request from '@/utils/request';
import type {TcdListParams } from './data';


export async function getTcdList (params?: TcdListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/rpt/listTcd?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getTcd (id: number) {
  return request(`/api/cc/rpt/tcd/${id}`, {
    method: 'GET'
  });
}
