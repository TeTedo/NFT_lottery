const express = require("express");
const app = express();
const PORT = 8000;

app.get("/", (req, res) => {
  try {
    res.status(200).send("server is clear");
  } catch (err) {
    res.status(500).send(err);
  }
});

app.listen(PORT, () => {
  console.log(`server start at ${PORT}`);
});

module.exports = app;
