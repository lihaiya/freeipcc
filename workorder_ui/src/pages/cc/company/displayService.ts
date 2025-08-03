import request from '@/utils/request';
import type { DisplayType, DisplayListParams } from './displayData';


export async function getCompanyPhoneList (params?: DisplayListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/companyDisplay/phoneList?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export async function getCompanyDisplayList (params?: DisplayListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/companyDisplay/displayList?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getPhone (id: number) {
  return request(`/api/cc/companyDisplay/phone/${id}`, {
    method: 'GET'
  });
}

export function getDisplay (id: number) {
  return request(`/api/cc/companyDisplay/display/${id}`, {
    method: 'GET'
  });
}


export async function saveOrUpdatePhone (params: DisplayType) {
  return request('/api/cc/companyDisplay/saveOrUpdatePhone', {
    method: 'POST',
    data: params
  });
}

export async function saveOrUpdateDisplay (params: DisplayType) {
  return request('/api/cc/companyDisplay/saveOrUpdateDisplay', {
    method: 'POST',
    data: params
  });
}

export async function removePhone (ids: string) {
  return request(`/api/cc/companyDisplay/phone/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export async function removeDisplay (ids: string) {
    return request(`/api/cc/companyDisplay/display/${ids}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      }
    });
}

// 查询号码池中的企业号码
export function getPhoneFromDisplay(displayId: number) {
  return request(`/api/cc/companyDisplay/displayPhones/${displayId}`, {
    method: 'GET',
  });
}
