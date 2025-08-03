import request from '@/utils/request';
import type { GatewayType, GatewayListParams } from './data';


export async function getGatewayList (params?: GatewayListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/route/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getGateway (qaId: number) {
  return request(`/api/cc/route/${qaId}`, {
    method: 'GET'
  });
}


export async function addGateway (params: GatewayType) {
  return request('/api/cc/route', {
    method: 'POST',
    data: params
  });
}


export async function updateGateway (params: GatewayType) {
  return request('/api/cc/route', {
    method: 'PUT',
    data: params
  });
}


export async function removeGateway (ids: string) {
  return request(`/api/cc/route/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
