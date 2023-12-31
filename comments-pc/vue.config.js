module.exports = {
  devServer: {
    proxy: {
      '/api/': {
        target: 'http://localhost:9081/', // dev
        ws: true,
        pathRewrite: {
          "^/api": "/",
        },
      },
    },
  },
}
