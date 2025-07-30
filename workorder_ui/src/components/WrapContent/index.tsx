import styles from './index.less';
import { PageContainer, ProLayout } from '@ant-design/pro-components';
const WrapContent: React.FC = (props) => {
    return (
      <PageContainer content={props.children}>
      </PageContainer>
    )
};
export default WrapContent;
