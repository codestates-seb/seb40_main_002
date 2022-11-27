const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://3.37.58.81:8080',
      changeOrigin: true,
    })
  );
};
