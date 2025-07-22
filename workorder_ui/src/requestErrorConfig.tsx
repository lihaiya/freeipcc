import type { RequestConfig } from '@umijs/max';
import { message, notification,Modal } from 'antd';
import { history, Link } from 'umi';

// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}
// 与后端约定的响应数据格式
interface ResponseStructure {
  success: boolean;
  data: any;
  errorCode?: number;
  errorMessage?: string;
  showType?: ErrorShowType;
}

/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res) => {
      const { success, data, errorCode, errorMessage, showType } =
        res as unknown as ResponseStructure;
      if (!success) {
        const error: any = new Error(errorMessage);
        error.name = 'BizError';
        error.info = { errorCode, errorMessage, showType, data };
        throw error; // 抛出自制的错误
      }
    },
    // 错误接收及处理
    errorHandler: (error: any, opts: any) => {
      const { response } = error;
      console.log("errorHandler="+response.status)
      if (response.status === 401) {
        history.push('/user/login');
        return {};
      }
      if (
        response.status === 400 ||
        response.status === 500 ||
        response.status === 404 ||
        response.status === 403
      ) {
        // 添加clone() 避免后续其它地方用response.text()时报错
        response
          .clone()
          .text()
          .then((resp: string) => {
            if (resp) {
              notification.error({
                key: 'error',
                message: JSON.parse(resp || '{}').message || '服务器内部错误！',
              });
              if (JSON.parse(resp || '{}').code === 'license required') {
                //判断按钮权限
                let buttons = {};
                const buttonString = localStorage.getItem('MENUS_BUTTONS_CACHE');
                buttons = JSON.parse(buttonString || '{}');
                //判读是否是退出登录状态
                if (Object.keys(buttons) && Object.keys(buttons).length !== 0) {
                  Modal.error({
                    title: 'License已到期或者错误',
                    content: (
                      <>
                        {buttons['system/License']?.includes('update') ? (
                          <a
                            onClick={() => {
                              Modal.destroyAll();
                              window.location.href = '/#/init-license';
                            }}
                          >
                            请更新License
                          </a>
                        ) : (
                          '请联系管理员更新license'
                        )}
                      </>
                    ),
                  });
                }else{
                  Modal.error({
                    title: 'License已到期或者错误',
                    content: (
                      <a
                        onClick={() => {
                          Modal.destroyAll();
                          window.location.href = '/#/init-license';
                        }}
                      >
                        请更新License
                      </a>
                    ),
                  });
                }
              } else {
                  response
                    .clone()
                    .json()
                    .then((res: any) => {
                      notification.error({
                        key: 'error',
                        message: `请求错误：${res.message}`,
                      });
                    })
                    .catch(() => {
                      notification.error({
                        key: 'error',
                        message: '系统开小差，请稍后重试',
                      });
                    });
                }
              }
          });
        return response;
      }
      if (!response) {
        notification.error({
          description: '网络异常，请检查网络连接',
          message: '网络异常',
        });
      }
      return response;
    },
  },

  // 请求拦截器
  requestInterceptors: [
    (config) => {
      // 拦截请求配置，进行个性化处理。
      const url = config.url.concat('?token = 123');
      return { ...config, url };
    },
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      // 拦截响应数据，进行个性化处理
      const { data } = response as unknown as ResponseStructure;
      if (!data.success) {
        message.error('请求失败！');
      }
      return response;
    },
  ],
};
