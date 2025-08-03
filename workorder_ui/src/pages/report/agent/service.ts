import request from '@/utils/request';
import type { AgentType, AgentListParams } from './data';


export async function getAgentStateList (params?: AgentListParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/api/cc/rpt/listAgentState?${queryString}`, {
    data: params,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    }
  });
}
