import { HardhatUserConfig } from 'hardhat/config';
// require('@nomiclabs/hardhat-waffle');
// test코드에서 solidity int 리턴이 bigInt로 되있어서 비교가 힘들었음
// 그래서 waffle 에서 toolbox로 바꿈 공식문서에서도 추천
import '@nomicfoundation/hardhat-toolbox';
// https://hardhat.org/hardhat-runner/docs/guides/migrating-from-hardhat-waffle
// https://hardhat.org/hardhat-chai-matchers/docs/overview
import '@nomicfoundation/hardhat-chai-matchers';
import '@openzeppelin/hardhat-upgrades';
import dotenv from 'dotenv';
dotenv.config();

const SEPOLIA_PRIVATE_KEY = process.env.SEPOLIA_PRIVATE_KEY || '';
const SEPOLIA_API = process.env.SEPOLIA_API || '';
const ETHER_SCAN_API = process.env.ETHER_SCAN_API || '';

const config: HardhatUserConfig = {
  solidity: '0.8.18',
  networks: {
    local: {
      url: 'http://127.0.0.1:8545',
    },
    sepolia: {
      url: `https://sepolia.infura.io/v3/${SEPOLIA_API}`,
      accounts: [SEPOLIA_PRIVATE_KEY],
    },
  },
  etherscan: {
    apiKey: ETHER_SCAN_API,
  },
};

export default config;
