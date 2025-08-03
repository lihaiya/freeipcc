import style from './index.less';

const Login: React.FC = () => {

  return (
    <div className={style.login}>

      <div className={style.bg1}/>
      <div className={style.gyl}>
        物联网平台
        <div className={style.gy2}>MQTT TCP CoAP HTTP , 多消息协议适配 , 可视化规则引擎
        </div>
      </div>
      {/* style={{ height: enable ? '387px' : '330px' }} */}
      <div className={style.box}>
        <div className={style.box1} >
          <div className={style.header}>用户登录</div>

          <div className={style.item}>
            <div className={style.userLabel}>用户名</div>
            <input
              style={{ borderStyle: 'none none solid none' }}
              type="text"
            />
          </div>
          <div className={style.item}>
            <div className={style.userLabel}>
              密<span style={{ marginLeft: '1em' }} />码
            </div>
            <input
              style={{ borderStyle: 'none none solid none' }}
              type="password"
            />
          </div>
          {
            true ? <div className={style.item}>
              <div className={style.userLabel}>验证码</div>
              <input
                     style={{ borderStyle: 'none none solid none' }}
                     type="text"
              />
            </div> : <div></div>
          }


          <div className={style.remember}>
            <div className={style.remember_box}>
              <input
                type="checkbox"
              />
              <div className={style.text}>记住我</div>
            </div>
          </div>

          <input
            className={style.btn}
            type="button"
            name="登录"
            value="登录"
          />
        </div>
      </div>
    </div>
  );
};

export default Login;
