import { supportChainIDs } from "common/config/config";
import dotenv from "dotenv";

export class Env {
  private static instance: Env;
  readonly chain: string;
  readonly explorer: string;

  private constructor() {
    let path: string;
    switch (process.env.NODE_ENV) {
      case "production":
        path = `${__dirname}/../../../.env.prod`;
        break;
      case "development":
        path = `${__dirname}/../../../.env.dev`;
        break;
      default:
        path = `${__dirname}/../../../.env.dev`;
    }
    dotenv.config({ path });

    this.chain = process.env.REACT_APP_CHAIN || "";
    this.explorer = process.env.REACT_APP_EXPLORER || "";

    this._required();
    this._validate();
  }

  static get() {
    if (!this.instance) {
      this.instance = new Env();
      console.log(this.instance);
    }
    return this.instance;
  }

  private _required() {
    if (!this.chain) {
      console.log(this);
      throw new Error("Env: something empty");
    }
  }

  private _validate() {}
}
