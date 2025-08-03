import request from '@/utils/request';
import type { Playback, PlaybackListParams } from './data';


export async function getPlaybackList (params?: PlaybackListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/playback/list?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
export function getAllPlaybackList() {
  return request(`/api/cc/playback/listAll`, {
    method: 'GET',
  });
}

export function getPlayback (id: number) {
  return request(`/api/cc/playback/${id}`, {
    method: 'GET'
  });
}


export async function addPlayback (params: Playback) {
  return request('/api/cc/playback', {
    method: 'POST',
    data: params
  });
}


export async function updatePlayback (params: Playback) {
  return request('/api/cc/playback', {
    method: 'PUT',
    data: params
  });
}


export async function removePlayback (ids: string) {
  return request(`/api/cc/playback/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}

