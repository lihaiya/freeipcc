import request from '@/utils/request';
import type { CompanyType, CompanyListParams } from './data';


export async function getCompanyList (params?: CompanyListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/company/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getAllCompanyList() {
  return request(`/api/cc/company/listAll`, {
    method: 'GET',
  });
}

export function getCompany (qaId: number) {
  return request(`/api/cc/company/${qaId}`, {
    method: 'GET'
  });
}


export async function addCompany (params: CompanyType) {
  console.log('--------------addd company')
  return request('/api/cc/company', {
    method: 'POST',
    data: params
  });
}


export async function updateCompany (params: CompanyType) {
  return request('/api/cc/company', {
    method: 'PUT',
    data: params
  });
}


export async function removeCompany (ids: string) {
  return request(`/api/cc/company/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
