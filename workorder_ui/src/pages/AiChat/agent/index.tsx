import React, {useState} from 'react';
import {Button, Input, Spin, message, AutoComplete} from "antd";
import type { SelectProps } from 'antd/es/select';


import SenderItem from '../components/SenderItem';
import ReceiverItem from '../components/ReceiverItem';
// 引入样式
import styles from './index.less';

const getRandomInt = (max: number, min = 0) => Math.floor(Math.random() * (max - min + 1)) + min;


const searchResult = (query: string) =>
  new Array(getRandomInt(5))
    .join('.')
    .split('.')
    .map((_, idx) => {
      const category = `${query}${idx}`;
      return {
        value: category,
        label: (
          <div
            style={{
              display: 'flex',
              justifyContent: 'space-between',
            }}
          >
            <span>
              Found {query} on{' '}
              <a
                href={`https://s.taobao.com/search?q=${query}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                {category}
              </a>
            </span>
            <span>{getRandomInt(200, 100)} results</span>
          </div>
        ),
      };
    })

const Chat: React.FC = () => {
  const [options, setOptions] = useState<SelectProps<object>['options']>([]);

  const handleSearch = (value: string) => {
    setOptions(value ? searchResult(value) : []);
  };

  const onSelect = (value: string) => {
    console.log('onSelect', value);
  };

  return (
    <div className={styles['chat-container']}>
        <div id="chatItems" className={styles['chat-items']}>
          {false && (
            <div className={styles['chat-loading']}>
              <Spin />
            </div>
          )}
          <div className={styles['chat-item']}>
            <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
          </div>
          <div className={styles['chat-item']}>
            <SenderItem userName={'andy'} item={{content:'你好', sendTime: '2023-08-01', type: '0'}} />
          </div>
        </div>
        <div className={styles['chat-input-operator']}>
          <AutoComplete
            dropdownMatchSelectWidth={252}
            style={{ width: '95%' }}
            options={options}
            onSelect={onSelect}
            onSearch={handleSearch}
          >
            <Input.Search size="large" placeholder="输入问题" enterButton />
          </AutoComplete>
        </div>
    </div>
  );
};
export default Chat;
