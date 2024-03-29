
module.exports = {
    publicPath: "./",   // 打包相对路径
    devServer: {
        open: true,
        host: '0.0.0.0',    // 前端IP地址
        port: 8080,         // 前端端口号
        https: false,
        // 配置不同的后台API地址
        proxy: {
            '/api': {   // 请求的时候用这个/api就可以,无需在加协议、IP和端口; 例： http://localhost:8090/test请求时，只需使用/api/test即可
                target: 'http://localhost:8090',    // 接口服务IP地址
                ws: true,
                changOrigin: true,//允许跨域
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    }
}