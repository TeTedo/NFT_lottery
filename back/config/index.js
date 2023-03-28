require("dotenv").config({ path: "env/back.env" });

module.exports = {
  development: {
    username: process.env.DEV_USERNAME,
    password: process.env.DEV_PASSWORD,
    database: process.env.DEV_DATABASE,
    host: process.env.DB_URL,
    dialect: "mysql",
    timezone: "Asia/Seoul",
    port: 3306,
    dialectOptions: {
      dateStrings: true,
      typeCast: true, // DB에서 가져올 때 시간 설정
    },
  },
  production: {
    username: process.env.PROD_USERNAME,
    password: process.env.PROD_PASSWORD,
    database: process.env.PROD_DATABASE,
    host: process.env.DB_URL,
    dialect: "mysql",
    timezone: "Asia/Seoul",
    port: 3306,
    dialectOptions: {
      dateStrings: true,
      typeCast: true, // DB에서 가져올 때 시간 설정
    },
  },
};
