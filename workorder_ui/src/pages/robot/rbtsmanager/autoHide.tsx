import React, {CSSProperties} from 'react';
import { Tooltip } from 'antd';
import Styles from './index.less';

export type AutoHideProps = {
  title?: string;
  style?: CSSProperties;
};
const AutoHide: React.FC<AutoHideProps> = ({
    title, style,
  }) => {
  return (
    <Tooltip placement="topLeft" title={title} style={{ lineHeight: 17 }}>
        <span className={Styles.col} style={style}>
          {title}
        </span>
    </Tooltip>
  );
};

export default AutoHide;
