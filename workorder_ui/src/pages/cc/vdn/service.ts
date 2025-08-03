import request from '@/utils/request';
import type { VdnType, VdnListParams } from './data';
import {VdnScheduleType} from "@/pages/cc/vdnSchedule/data";


export async function getVdnList (params?: VdnListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/vdn/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

export function getVdn (id: number) {
  return request(`/api/cc/vdn/${id}`, {
    method: 'GET'
  });
}


export async function addVdn (params: VdnType) {
  return request('/api/cc/vdn', {
    method: 'POST',
    data: params
  });
}


export async function updateVdn (params: VdnType) {
  return request('/api/cc/vdn', {
    method: 'PUT',
    data: params
  });
}

export async function removeVdn (ids: string) {
  return request(`/api/cc/vdn/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}


export function getVdnSchedule (vdnId: number) {
  return request(`/api/cc/vdn/schedule/${vdnId}`, {
    method: 'GET'
  });
}

export async function updateVdnSchedule (params: VdnScheduleType) {
  return request('/api/cc/vdn/schedule', {
    method: 'PUT',
    data: params
  });
}

export async function addVdnSchedule (params: VdnScheduleType) {
  return request('/api/cc/vdn/schedule', {
    method: 'POST',
    data: params
  });
}
