import {Alert, Button, Checkbox, Col, Divider, Image, message, Row, Spin, Tabs} from 'antd';
import React, { useEffect, useRef, useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom'
import { Link } from 'umi';
import styles from './index.less';
import { SelectLang } from '@@/plugin-locale/SelectLang';
import { catchError, filter, mergeMap } from 'rxjs/operators';
import Service from "@/pages/User/Login3/service";
import {FormattedMessage, useIntl, useModel} from "@umijs/max";
import {
  AlipayCircleOutlined,
  LockOutlined, MobileOutlined,
  TaobaoCircleOutlined,
  UserOutlined,
  WeiboCircleOutlined
} from "@ant-design/icons";
import {LoginForm, ProFormCaptcha, ProFormCheckbox, ProFormText} from "@ant-design/pro-components";
import {getCaptchaImage, login} from "@/services/login";
import {getDict} from "@/pages/system/dict/service";
import {setSessionToken} from "@/access";
import {history} from "@@/core/history";
import SystemConst from "@/utils/const";

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};

const Login: React.FC = () => {
  const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
  const [loading, setLoading] = useState<boolean>(false);
  const [captcha, setCaptcha] = useState<{ key?: string; base64?: string }>({});
  const { initialState, setInitialState } = useModel('@@initialState');
  const [type, setType] = useState<string>('account');
  const intl = useIntl();
  const navigate = useNavigate()
  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      await setInitialState((s) => ({
        ...s,
        currentUser: userInfo,
      }));
    }
  };
  const getCode = async () => {
    Service.getCaptchaImg().then((res) => {
      setCaptcha(res);
      setLoading(false);
    }).catch((error)=>{
      message.error(error)
    });
  };
  useEffect(getCode, []);

  const handleSubmit = async (values: API.LoginParams) => {
    try {
      console.log("history.location.pathname ="+history.location.pathname )
      if (history.location.pathname.indexOf("/home")>0){
        history.push('/home');
      }
      setLoading(true);
      // 登录
      const response = await login({ uuid: captcha.key, ...values, type });
      setLoading(false);
      if (response.code === 200) {
        const defaultLoginSuccessMessage = intl.formatMessage({
          id: 'pages.login.success',
          defaultMessage: '登录成功！',
        });

        const current = new Date();
        const expireTime = current.setTime(current.getTime() + 1000 * 12 * 60 * 60);
        setSessionToken(response.token, response.token, expireTime);
        message.success(defaultLoginSuccessMessage);

        //await fetchUserInfo();

       // const urlParams = new URL(window.location.href).searchParams;
       // console.log("urlParams="+urlParams)
        //navigate('/home')
        history.push('/home');
        //history.push(urlParams.get('redirect') || '/');
        return;
      } else {
        console.log('login failed')
        await getCode()
        //clearSessionToken();
        // 如果失败去设置用户错误信息
        setUserLoginState({status: 'error', type: 'account', massage: response.msg});
        message.error(response.msg);
      }
      //console.log(msg);
      // 如果失败去设置用户错误信息
      // setUserLoginState(msg);
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      console.log(error);
      message.error(defaultLoginFailureMessage);
    }
  };

  const { status, type: loginType } = userLoginState;

  return (
    <Spin delay={500} spinning={loading}>
      <div className={styles.container}>
        <div className={styles.left}>
          <div className={styles.content}>
            <div className={styles.top}>
              <div className={styles.main}>
                <LoginForm
                  logo={<img alt="logo" src="/gsd-logo.png" />}
                  title="钩三点智能"
                  subTitle={intl.formatMessage({ id: 'pages.layouts.userLayout.title' })}
                  initialValues={{
                    autoLogin: true,
                  }}
                  onFinish={async (values) => {
                    await handleSubmit(values as API.LoginParams);
                  }}
                >
                  <Tabs activeKey={type} onChange={setType}>
                    <Tabs.TabPane
                      key="account"
                      tab={intl.formatMessage({
                        id: 'pages.login.accountLogin.tab',
                        defaultMessage: '账户密码登录',
                      })}
                    />
                    <Tabs.TabPane
                      key="mobile"
                      tab={intl.formatMessage({
                        id: 'pages.login.phoneLogin.tab',
                        defaultMessage: '手机号登录',
                      })}
                    />
                  </Tabs>

                  {status === 'error' && loginType === 'account' && (
                    <LoginMessage
                      content={intl.formatMessage({
                        id: 'pages.login.accountLogin.errorMessage',
                        defaultMessage: '账户或密码错误',
                      })}
                    />
                  )}
                  {type === 'account' && (
                    <>
                      <ProFormText
                        name="username"
                        fieldProps={{
                          size: 'large',
                          prefix: <UserOutlined className={styles.prefixIcon} />,
                        }}
                        placeholder={intl.formatMessage({
                          id: 'pages.login.username.placeholder',
                          defaultMessage: '用户名: ',
                        })}
                        rules={[
                          {
                            required: true,
                            message: (
                              <FormattedMessage
                                id="pages.login.username.required"
                                defaultMessage="请输入用户名!"
                              />
                            ),
                          },
                        ]}
                      />
                      <ProFormText.Password
                        name="password"
                        fieldProps={{
                          size: 'large',
                          prefix: <LockOutlined className={styles.prefixIcon} />,
                        }}
                        placeholder={intl.formatMessage({
                          id: 'pages.login.password.placeholder',
                          defaultMessage: '密码: ',
                        })}
                        rules={[
                          {
                            required: true,
                            message: (
                              <FormattedMessage
                                id="pages.login.password.required"
                                defaultMessage="请输入密码！"
                              />
                            ),
                          },
                        ]}
                      />
                      <Row>
                        <Col flex={3}>
                          <ProFormText
                            style={{
                              float: 'right',
                            }}
                            name="code"
                            placeholder={intl.formatMessage({
                              id: 'pages.login.code.placeholder',
                              defaultMessage: '请输入验证码',
                            })}
                            rules={[
                              {
                                required: true,
                                message: (
                                  <FormattedMessage
                                    id="pages.searchTable.updateForm.ruleName.nameRules"
                                    defaultMessage="请输入验证码"
                                  />
                                ),
                              },
                            ]}
                          />
                        </Col>
                        <Col flex={2}>
                          <Image
                            src={captcha.base64}
                            alt="验证码"
                            style={{
                              display: 'inline-block',
                              verticalAlign: 'top',
                              cursor: 'pointer',
                              paddingLeft: '10px',
                              width: '100px',
                            }}
                            preview={false}
                            onClick={getCode}
                          />
                        </Col>
                      </Row>
                    </>
                  )}
                  <div
                    style={{
                      marginBottom: 24,
                    }}
                  >
                  </div>
                </LoginForm>
              </div>
            </div>
            </div>

          <div className={styles.bottom}>
            <div className={styles.view}></div>
            <div className={styles.url}>
              <div style={{ height: 33 }}>2023@钩三点(杭州)
              </div>
            </div>
          </div>
      </div>
          <div className={styles.right}>
            <img src={require('./img/banner2.png')}
              style={{ width: '100%', height: '100%' }}
            />
          </div>
      </div>
    </Spin>
  );
};

export default Login;
