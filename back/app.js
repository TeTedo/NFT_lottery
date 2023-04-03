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

module.exports = app;
