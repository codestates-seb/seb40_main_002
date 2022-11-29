const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    ['/api', '/oauth2'],
    createProxyMiddleware({
      target: process.env.REACT_APP_SERVER_URL,
      changeOrigin: true,
    })
  );
  app.use(
    '/dn',
    createProxyMiddleware({
      target: 'http://k.kakaocdn.net',
      changeOrigin: true,
    })
  );
};
