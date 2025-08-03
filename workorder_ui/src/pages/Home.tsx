import { ProCard, StatisticCard } from '@ant-design/pro-components';
import RcResizeObserver from 'rc-resize-observer';
import React, { useState } from 'react';
import { Pie, Line,measureTextWidth } from '@ant-design/plots';
import WrapContent from "@/components/WrapContent";

const { Statistic } = StatisticCard;

export default () => {
  const [responsive, setResponsive] = useState(false);

  const DemoLine = () => {
    const data = [
      {
        year: '1991',
        value: 3,
      },
      {
        year: '1992',
        value: 4,
      },
      {
        year: '1993',
        value: 3.5,
      },
      {
        year: '1994',
        value: 5,
      },
      {
        year: '1995',
        value: 4.9,
      },
      {
        year: '1996',
        value: 6,
      },
      {
        year: '1997',
        value: 7,
      },
      {
        year: '1998',
        value: 9,
      },
      {
        year: '1999',
        value: 13,
      },
    ];
    const config = {
      data,
      xField: 'year',
      yField: 'value',
      label: {},
      point: {
        size: 5,
        shape: 'diamond',
        style: {
          fill: 'white',
          stroke: '#5B8FF9',
          lineWidth: 2,
        },
      },
      tooltip: {
        showMarkers: false,
      },
      state: {
        active: {
          style: {
            shadowBlur: 4,
            stroke: '#000',
            fill: 'red',
          },
        },
      },
      interactions: [
        {
          type: 'marker-active',
        },
      ],
    };
    return <Line {...config} />;
  };

  const DemoPie = () => {
    function renderStatistic(containerWidth, text, style) {
      const { width: textWidth, height: textHeight } = measureTextWidth(text, style);
      const R = containerWidth / 2; // r^2 = (w / 2)^2 + (h - offsetY)^2

      let scale = 1;

      if (containerWidth < textWidth) {
        scale = Math.min(Math.sqrt(Math.abs(Math.pow(R, 2) / (Math.pow(textWidth / 2, 2) + Math.pow(textHeight, 2)))), 1);
      }

      const textStyleStr = `width:${containerWidth}px;`;
      return `<div style="${textStyleStr};font-size:${scale}em;line-height:${scale < 1 ? 1 : 'inherit'};">${text}</div>`;
    }

    const data = [
      {
        type: '分类一',
        value: 27,
      },
      {
        type: '分类二',
        value: 25,
      },
      {
        type: '分类三',
        value: 18,
      },
      {
        type: '分类四',
        value: 15,
      },
      {
        type: '分类五',
        value: 10,
      },
      {
        type: '其他',
        value: 5,
      },
    ];
    const config = {
      appendPadding: 10,
      data,
      angleField: 'value',
      colorField: 'type',
      radius: 1,
      innerRadius: 0.64,
      meta: {
        value: {
          formatter: (v) => `${v} ¥`,
        },
      },
      label: {
        type: 'inner',
        offset: '-50%',
        style: {
          textAlign: 'center',
        },
        autoRotate: false,
        content: '{value}',
      },
      statistic: {
        title: {
          offsetY: -4,
          customHtml: (container, view, datum) => {
            const { width, height } = container.getBoundingClientRect();
            const d = Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
            const text = datum ? datum.type : '总计';
            return renderStatistic(d, text, {
              fontSize: 28,
            });
          },
        },
        content: {
          offsetY: 4,
          style: {
            fontSize: '32px',
          },
          customHtml: (container, view, datum, data) => {
            const { width } = container.getBoundingClientRect();
            const text = datum ? `¥ ${datum.value}` : `¥ ${data.reduce((r, d) => r + d.value, 0)}`;
            return renderStatistic(width, text, {
              fontSize: 32,
            });
          },
        },
      },
      // 添加 中心统计文本 交互
      interactions: [
        {
          type: 'element-selected',
        },
        {
          type: 'element-active',
        },
        {
          type: 'pie-statistic-active',
        },
      ],
    };
    return <Pie {...config} />;
  };

  return (
    <WrapContent>
      <ProCard
        title="数据概览"
        extra="2019年9月28日 星期五"
        split={responsive ? 'horizontal' : 'vertical'}
        headerBordered
        bordered
        >
        <ProCard split="horizontal">
          <ProCard split="horizontal">
            <ProCard split="vertical">
              <StatisticCard
                statistic={{
                title: '昨日全部流量',
                  value: 234,
                  description: <Statistic title="较本月平均流量" value="8.04%" trend="down" />,
              }}
              />
              <StatisticCard
              statistic={{
                title: '本月累计流量',
                  value: 234,
                  description: <Statistic title="月同比" value="8.04%" trend="up" />,
              }}
              />
            </ProCard>
            <ProCard split="vertical">
                <StatisticCard
                  statistic={{
                  title: '运行中实验',
                    value: '12/56',
                    suffix: '个',
                }}
                />
                <StatisticCard
                statistic={{
                  title: '历史实验总数',
                    value: '134',
                    suffix: '个',
                }}
                />
            </ProCard>
          </ProCard>
          <StatisticCard
          title="流量走势"
          chart={
            <DemoLine/>
          }
          />
        </ProCard>
        <StatisticCard
        title="流量占用情况"
        chart={
          <DemoPie />
        }
        />
      </ProCard>
  </WrapContent>
  );
};
