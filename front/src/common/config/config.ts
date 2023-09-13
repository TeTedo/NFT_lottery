export type ChainID = 1001 | 8217 | 11155111;

export const supportChainIDs = [1011, 8217, 11155111];

export const chains = {
  // baobab
  1001: {
    ca: {
      undefined: "0x04e244d3835871Cb3F7e2040ECEe33E85Da5C2DD",
      undefined_util: "0xD0EfE1a2b6f1482073130Da4eA541e226b1A648a",
    },
  },
  // klaytn
  8217: {
    ca: {
      undefined: "",
      undefined_util: "",
    },
  },
  // sepolia
  11155111: {
    ca: {
      undefined: "0xD43Ce70303eA993BBcA67a846f7D0F383D66223C",
      undefined_util: "",
    },
  },
};
