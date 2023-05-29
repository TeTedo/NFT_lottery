// import { Wallet } from "ethers";
import { Mnemonic } from "ethers/lib/utils";
import { ethers } from "hardhat";
import fs from "fs";
import path from "path";

const AMOUNT = 20;

type Wallet = {
  address: string;
  privateKey: string;
  mnemonic: Mnemonic;
};

async function main() {
  const wallets = [];
  const addressArr: string[] = [];
  const prviateKeyArr: string[] = [];

  for (let i = 0; i < AMOUNT; i++) {
    const wallet = ethers.Wallet.createRandom();
    wallets.push({
      address: wallet.address,
      mnemonic: wallet.mnemonic,
      privateKey: wallet.privateKey,
    } as Wallet);

    addressArr.push(wallet.address);
    prviateKeyArr.push(wallet.privateKey);
  }

  //   const data = JSON.stringify(wallets);
  const data = JSON.stringify({ addressArr, prviateKeyArr, wallets });
  const date = new Date().toISOString().slice(0, 10);

  const name = path.join(__dirname, "../../wallets", `wallets(${date}).json`);
  fs.writeFileSync(name, data);
}

main().catch((error) => {
  console.log(error);
  process.exit(1);
});
