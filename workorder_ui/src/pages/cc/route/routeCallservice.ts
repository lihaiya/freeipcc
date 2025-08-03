import request from '@/utils/request';
import type { RouteCallType, RouteCallListParams } from './routeCallData';


export async function getRouteCallList (params?: RouteCallListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/routeCall/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getRouteCall (qaId: number) {
  return request(`/api/cc/routeCall/${qaId}`, {
    method: 'GET'
  });
}


export async function addRouteCall (params: RouteCallType) {
  return request('/api/cc/routeCall', {
    method: 'POST',
    data: params
  });
}


export async function updateRouteCall (params: RouteCallType) {
  return request('/api/cc/routeCall', {
    method: 'PUT',
    data: params
  });
}


export async function removeRouteCall (ids: string) {
  return request(`/api/cc/routeCall/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

// 获取字冠中的网关
export function getGatewayFromRouteCall(routeCallId: number) {
  return request(`/api/cc/routeCall/routeCallGateways/${routeCallId}`, {
    method: 'GET',
  });
}
