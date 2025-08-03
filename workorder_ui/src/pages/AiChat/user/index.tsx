import React, {useState} from 'react';
import { Element } from "react-scroll";
import { Scrollbars } from "react-custom-scrollbars"
import {Button, Input, Spin, message,Space,Tooltip} from "antd";
import type { SelectProps } from 'antd/es/select';
import { AudioOutlined,InfoCircleOutlined,UserOutlined,SendOutlined,QuestionCircleOutlined } from '@ant-design/icons';
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
                {category}
            </span>
            <span>{getRandomInt(200, 100)} results</span>
          </div>
        ),
      };
    })

const suffix = (
  <AudioOutlined
    style={{
      fontSize: 16,
      color: '#1677ff',
    }}
  />
);

const Chat: React.FC = () => {
  const [options, setOptions] = useState<SelectProps<object>['options']>([]);

  const handleSearch = (value: string) => {
    setOptions(value ? searchResult(value) : []);
  };

  const onSelect = (value: string) => {
    console.log('onSelect', value);
  };

  const handleSend = () => {
    console.log('handleSend');
  };

  const keyDown = (e: any)=>{
    console.log('---keydown')
    if (e.keyCode === 13) {
      handleSend()
    }
  }

  return (
    <div className={styles['chat']}>
      <div className={styles['chat-container_package']}>
        <div id="banner" className={styles['chat-banner']}>
          <p>智能客服机器人</p>
        </div>
        <div className={styles['chat-container']}>
            <div id="chatHistory" className={styles['chat-history']}>
              <div id="chatItems" className={styles['MessageItem']}>
                {false && (
                  <div className={styles['chat-loading']}>
                    <Spin />
                  </div>
                )}
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['cmessageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <ReceiverItem userName={'小安'} item={{content:'您好！欢迎来到我们的客服中心。我是客服，有什么我可以帮助您的问题吗？', sendTime: '2023-08-01', type: '0'}}/>
                </div>
                <div className={styles['messageItem-container']}>
                  <SenderItem userName={'andy'} item={{content:'你好', sendTime: '2023-08-01', type: '0'}} />
                </div>
              </div>
            </div>
          <div id="quickMessage" className={styles['quick-message']}></div>
          <div className={styles['chat-input-operator']}>
            <Input
              placeholder="输入你的问题"
              allowClear
              size={"large"}
              onPressEnter={handleSend}
              prefix={<QuestionCircleOutlined className="site-form-item-icon" />}
              suffix={
                <a onClick={handleSend}>
                <SendOutlined  style={{color: 'rgba(0,0,0,.45)'}} />
                </a>

              }></Input>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Chat;
