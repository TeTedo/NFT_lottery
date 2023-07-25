import { HardhatUserConfig } from "hardhat/config";
// require('@nomiclabs/hardhat-waffle');
// test코드에서 solidity int 리턴이 bigInt로 되있어서 비교가 힘들었음
// 그래서 waffle 에서 toolbox로 바꿈 공식문서에서도 추천
import "@nomicfoundation/hardhat-toolbox";
// https://hardhat.org/hardhat-runner/docs/guides/migrating-from-hardhat-waffle
// https://hardhat.org/hardhat-chai-matchers/docs/overview
import "@nomicfoundation/hardhat-chai-matchers";
import "hardhat-gas-reporter";
import "@openzeppelin/hardhat-upgrades";
import dotenv from "dotenv";
import { chains } from "./ common/config";
import { network } from "hardhat";
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

export default config;
