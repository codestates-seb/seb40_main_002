const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'https://e3c1-58-234-129-97.jp.ngrok.io',
      changeOrigin: true,
    })
  );
};
