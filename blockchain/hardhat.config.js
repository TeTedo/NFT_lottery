/** @type import('hardhat/config').HardhatUserConfig */
// require('@nomiclabs/hardhat-waffle');
// test코드에서 solidity int 리턴이 bigInt로 되있어서 비교가 힘들었음
// 그래서 waffle 에서 toolbox로 바꿈 공식문서에서도 추천
// https://hardhat.org/hardhat-runner/docs/guides/migrating-from-hardhat-waffle
require('@nomicfoundation/hardhat-toolbox');
// https://hardhat.org/hardhat-chai-matchers/docs/overview
require('@nomicfoundation/hardhat-chai-matchers');

module.exports = {
  solidity: '0.8.18',
};
