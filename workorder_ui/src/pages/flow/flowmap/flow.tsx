import { ReactFlowProvider } from 'reactflow';
import React from 'react'
import Sider from './Sider';
import Graph from './Graph';
import Toolbar from './Toolbar';
import Modal from './components/Modal';
// 引入 Provider
import { FlowContextProvider } from './context';

import './flow.css';

const  FlowPage: React.FC = () => {
//export default function FlowPage() {
  return (
    <div className="container">
      <FlowContextProvider>
        <ReactFlowProvider>
          {/* 顶部工具栏 */}
          <Toolbar />
          <div className="main">
            {/* 侧边栏，展示可拖拽的节点 */}
            <Sider />
            {/* 画布，处理核心逻辑 */}
            <Graph />
          </div>
          {/* 弹窗，配置节点数据 */}
          <Modal />
        </ReactFlowProvider>
      </FlowContextProvider>
    </div>
  );
}
export default FlowPage
