import { HardhatUserConfig } from "hardhat/config";
// require('@nomiclabs/hardhat-waffle');
// test코드에서 solidity int 리턴이 bigInt로 되있어서 비교가 힘들었음
// 그래서 waffle 에서 toolbox로 바꿈 공식문서에서도 추천
import "@nomicfoundation/hardhat-toolbox";
// https://hardhat.org/hardhat-runner/docs/guides/migrating-from-hardhat-waffle
// https://hardhat.org/hardhat-chai-matchers/docs/overview
import "@nomicfoundation/hardhat-chai-matchers";
import "@openzeppelin/hardhat-upgrades";

const config: HardhatUserConfig = {
  solidity: "0.8.18",
};

export default config;
