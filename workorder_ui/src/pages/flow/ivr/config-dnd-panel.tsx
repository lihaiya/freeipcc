/* eslint-disable @typescript-eslint/no-unused-vars */
import {NsGraph, uuidv4} from '@antv/xflow'
import { XFlowNodeCommands } from '@antv/xflow'
import { DND_RENDER_ID } from './constant'
import type { NsNodeCmd } from '@antv/xflow'
import type { NsNodeCollapsePanel } from '@antv/xflow'
import { Card } from 'antd'
import React from 'react'

export const onNodeDrop: NsNodeCollapsePanel.IOnNodeDrop = async (node, commands, modelService) => {
  const args: NsNodeCmd.AddNode.IArgs = {
    nodeConfig: { ...node, id: uuidv4()},
  }
  commands.executeCommand(XFlowNodeCommands.ADD_NODE.id, args)
}


const NodeDescription = (props: { name: string }) => {
  return (
    <Card size="small" title="组件介绍" style={{ width: '200px' }} bordered={false}>
      {props.name}： 设置服务热线欢语
    </Card>
  )
}

export const nodeDataService: NsNodeCollapsePanel.INodeDataService = async (meta, modelService) => {
  console.log(meta, modelService)
  return [
    {
      id: '开始',
      header: '开始',
      children: [
        {
          id: '2',
          label: '欢迎语',
          parentId: '1',
          renderKey: DND_RENDER_ID,
          popoverContent: <NodeDescription name="欢迎语" />,
          attrs: {
            attribute: {
              type: 'welcome',
            },
          },
          ports: [
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.TOP,
              tooltip: '输入桩TOP',
            },
            {
              type: NsGraph.AnchorType.OUTPUT,
              group: NsGraph.AnchorGroup.LEFT,
              tooltip: '输出桩LEFT',
            },
            {
              type: NsGraph.AnchorType.OUTPUT,
              group: NsGraph.AnchorGroup.BOTTOM,
              tooltip: '输出桩BOTTOM',
            },
            {
              type: NsGraph.AnchorType.OUTPUT,
              group: NsGraph.AnchorGroup.RIGHT,
              tooltip: '输出桩RIGHT',
            },
          ],
        },
      ],
    },
    {
      id: '流转节点',
      header: '流转节点',
      children: [
        {
          id: '4',
          label: '数据',
          parentId: '2',
          renderKey: DND_RENDER_ID,
          attrs: {
            attribute: {
              type: 'data',
            },
          },
          ports: [
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.LEFT,
              tooltip: '输入桩LEFT',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.TOP,
              tooltip: '输入桩TOP',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.RIGHT,
              tooltip: '输入桩RIGHT',
            },
            {
              type: NsGraph.AnchorType.OUTPUT,
              group: NsGraph.AnchorGroup.BOTTOM,
              tooltip: '输出桩BOTTOM',
            },
          ],
        },
      ],
    },
    {
      id: '结束',
      header: '结束',
      children: [
        {
          id: '6',
          label: '挂机',
          parentId: '5',
          renderKey: DND_RENDER_ID,
          attrs: {
            attribute: {
              type: 'hangup',
            },
          },
          ports: [
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.LEFT,
              tooltip: '输入桩-LEFT',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.TOP,
              tooltip: '输入桩-TOP1',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.TOP,
              tooltip: '输入桩TOP2-2',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.TOP,
              tooltip: '输入桩TOP2-3',
            },
            {
              type: NsGraph.AnchorType.INPUT,
              group: NsGraph.AnchorGroup.RIGHT,
              tooltip: '输入桩-RIGHT',
            },
          ],
        },
      ],
    },
  ]
}

export const searchService: NsNodeCollapsePanel.ISearchService = async (
  nodes: NsNodeCollapsePanel.IPanelNode[] = [],
  keyword: string,
) => {
  const list = nodes.filter(node => node.label.includes(keyword))
  console.log(list, keyword, nodes)
  return list
}
