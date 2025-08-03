import request from '@/utils/request';
import type { IvrFlowType } from './data';


export async function addIvrFlow (params: IvrFlowType) {
  return request('/api/cc/ivrFlow', {
    method: 'POST',
    data: params
  });
}
export function getIvrFlow (id: number) {
  return request(`/api/cc/ivrFlow/${id}`, {
    method: 'GET'
  });
}
