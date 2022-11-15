// 타입스크립트는 알아서 http-proxy 설정을 지원함 https://egas.tistory.com/39
// const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '',
    createProxyMiddleware({
      target: '',
      changeOrigin: true,
    })
  );
};
