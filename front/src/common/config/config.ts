export type ChainID = 1001 | 8217 | 11155111;

export const supportChainIDs = [1011, 8217, 11155111];

export const chains = {
  // baobab
  1001: {
    ca: {
      raffle: "0x04e244d3835871Cb3F7e2040ECEe33E85Da5C2DD",
      getNftData: "0xf9e50DAA30a2799B2A1E9d83396Db68d1B657C33",
    },
  },
  // klaytn
  8217: {
    ca: {
      raffle: "",
      getNftData: "",
    },
  },
  // sepolia
  11155111: {
    ca: {
      raffle: "0xD43Ce70303eA993BBcA67a846f7D0F383D66223C",
      getNftData: "",
    },
  },
};
