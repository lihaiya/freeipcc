import request from '@/utils/request';
import {IvrFlowType} from "@/pages/flow/flowmanager/data";
import {NodeType} from "@/pages/flow/flowmap/components/Modal/data";

export async function addIvrFlow (params: IvrFlowType) {
  return request('/api/cc/ivrFlow', {
    method: 'POST',
    data: params
  });
}

export function getIvrFlowStep (flowId: number, nodeId: string) {
  return request(`/api/cc/ivrFlow/flowStep/${flowId}/${nodeId}`, {
    method: 'GET'
  });
}

export function getIvrFlow (id: number) {
  return request(`/api/cc/ivrFlow/${id}`, {
    method: 'GET'
  });
}

export async function addIvrFlowStep (params: NodeType) {
  return request('/api/cc/ivrFlow/addFlowStep', {
    method: 'POST',
    data: params
  });
}

export function removeIvrFlowStep (params: NodeType) {
  return request(`/api/cc/ivrFlow/removeFlowStep`, {
    method: 'POST',
    data: params
  });
}
