const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    ['/api', '/oauth2'],
    createProxyMiddleware({
      target: 'http://3.37.58.81:8080',
      changeOrigin: true,
    })
  );
  app.use(
    createProxyMiddleware('/dn', {
      target: 'http://k.kakaocdn.net',
      changeOrigin: true,
    })
  );
};
