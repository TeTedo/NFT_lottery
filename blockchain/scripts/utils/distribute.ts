import { ethers } from "hardhat";
import wallets from "../../wallets/wallets_faucet.json";
import { BigNumber } from "ethers";

const VAULT_ADDR = "0x2FddFf016207264b0b1da314520B472184e258f4";

async function main() {
  const provider = new ethers.providers.WebSocketProvider("wss://public-en-baobab.klaytn.net/ws");
  const gasPrice = await provider.getGasPrice();
  const signers = wallets.prviateKeyArr.map((privateKey) => {
    return new ethers.Wallet(privateKey, provider);
  });

  let total = BigNumber.from(0);
  const promises = signers.map(async (signer) => {
    const balance = await signer.getBalance();
    // const value = balance.mul(96).div(100);
    const value = balance.sub(gasPrice.add(ethers.utils.parseUnits("100", "gwei")).mul(21000));
    const tx = await signer.sendTransaction({ to: VAULT_ADDR, value });
    try {
      await tx.wait();
      total = total.add(value);
    } catch (error) {
      console.log(error);
    }
  });
  await Promise.all(promises);
  console.log("done");
  console.log(ethers.utils.formatEther(total), "sent");
  process.exit(0);
}

main().catch((error) => {
  console.log(error);
  process.exit(1);
});
