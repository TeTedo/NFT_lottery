const request = require("supertest");
const app = require("../../app");

describe("Server Start", () => {
  it("check server listen is normal", async () => {
    const response = await request(app).get("/");
    expect(response.statusCode).toBe(200);
  });
});
