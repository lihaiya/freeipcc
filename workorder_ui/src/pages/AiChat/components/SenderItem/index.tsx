import { Avatar } from 'antd';
import MessageView from '../MessageView';
import styles from './index.less';

const avatarStyle = { backgroundColor: '#005EFF', verticalAlign: 'middle' };

// @ts-ignore
const SenderItem = ({ userName, item }) => {
  return (
    <div className={styles['sender-item']}>
      {/* sender */}
      <MessageView isSender={true} {...item} />
      <div className={styles['avatar-wrap']}>
      </div>
    </div>
  );
};

export default SenderItem;
