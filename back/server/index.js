require("dotenv").config({ path: "env/back.env" });
const app = require("../app");
const PORT = process.env.PORT;
const env = process.env.NODE_ENV;
app.listen(PORT, () => {
  console.log(`server started at ${PORT} PORT in ${env} ENV`);
});
