
export type NodeType = {
  flowId: number;
  nodeId: string;
  pid: string;
  enterValue: string;
  nodeLabel: string;
  optType: string;
  playType: string;
  playFile: string;
  ttsInput: string;
  toTarget: string;
  targetValue: string;
  scriptContent: string;
  dtmfMax: number;
  dtmfTimeout: number;
  dtmfTries: number;
  dtmfTerminators: string;
  flowContent: string;
};


export type NodeListParams = {
  flowId?: number;
  enterValue?: string;
  nodeLabel?: string;
  optType?: string;
  playType?: string;
  playFile?: string;
  ttsInput?: string;
  toTarget?: string;
  scriptContent?: string;
  dtmfLength?: string;
};
