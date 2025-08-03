import { Avatar } from 'antd';
import MessageView from '../MessageView';
import styles from './index.less';

const avatarStyle = { backgroundColor: '#005EFF', verticalAlign: 'middle' };

// @ts-ignore
const ReceiverItem = ({ userName, item }) => {
  return (
    <div className={styles['receiver-item']}>
      {/* receiver Avatar size="large" size="large" style={avatarStyle}*/}
      <div className={styles['avatar-wrap']}>
      </div>
      <MessageView isSender={false} {...item} />
    </div>
  );
};

export default ReceiverItem;
