import { HardhatUserConfig, subtask } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import "@nomicfoundation/hardhat-chai-matchers";
import "hardhat-gas-reporter";
import "@openzeppelin/hardhat-upgrades";
import dotenv from "dotenv";
import { chains } from "./ common/config";

dotenv.config();

const SEPOLIA_PK = process.env.SEPOLIA_PK || "";
const SEPOLIA_API = process.env.SEPOLIA_API || "";
const ETHER_SCAN_API = process.env.ETHER_SCAN_API || "";
const VAULT_PK = process.env.VAULT_PK || "";

const config: HardhatUserConfig = {
  solidity: "0.8.18",
  networks: {
    sepolia: {
      url: `https://sepolia.infura.io/v3/${SEPOLIA_API}`,
      accounts: chains["sepolia"].pk,
    },
    baobab: {
      url: "https://archive-en.baobab.klaytn.net/",
      accounts: chains["baobab"].pk,
      gasPrice: 25 * 10 ** 9,
    },
  },
  gasReporter: {
    enabled: true,
  },
  etherscan: {
    apiKey: ETHER_SCAN_API,
  },
};

const { TASK_COMPILE_SOLIDITY_GET_SOURCE_PATHS } = require("hardhat/builtin-tasks/task-names");
subtask(TASK_COMPILE_SOLIDITY_GET_SOURCE_PATHS).setAction(async (_, __, runSuper) => {
  const paths = await runSuper();
  return paths.filter((p: string) => !p.includes("ignore"));
});

export default config;
