import { ethers } from "ethers";
import { abi } from "./contract/EventTest.json";
import dotenv from "dotenv";
dotenv.config();

const END_POINT = "https://public-en-baobab.klaytn.net";
const PRIVATE_KEY = process.env.PRIVATE_KEY || "";
const CONTRACT_ADDR = "0x381c7d85673f230de554097372e0FC4F14b5B0a6";

const 반복횟수 = 1;
const 반복간격_밀리세컨드단위 = 500;

async function main() {
  const provider = new ethers.providers.JsonRpcProvider(END_POINT);
  const signer = new ethers.Wallet(PRIVATE_KEY, provider);
  const contract = new ethers.Contract(CONTRACT_ADDR, abi, signer);
  for (let i = 0; i < 반복횟수; i++) {
    console.log(`${i + 1} 번째`);
    const tx = await contract.test();
    await tx.wait();
    if (반복횟수 > 1) await sleep(반복간격_밀리세컨드단위);
  }
}

async function sleep(time: number) {
  await new Promise((resolve) => setTimeout(resolve, time));
}

main().catch((error) => {
  console.log(error);
  process.exitCode = 1;
});
