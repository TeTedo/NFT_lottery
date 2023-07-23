export type CA = { testNft: string; raffle: string };

export type PK = string[];

export type Chain = { ca: CA; pk: PK };

export type Chains = { [key: string]: Chain };

export const chains: Chains = {
  baobab: {
    ca: {
      testNft: "0x82bD8791f11b79ee24a37c2cb5BbD1A74B8ced24",
      raffle: "0xedE916cA2375F50aEaB50a9cCb92Bb69F8c37438",
    },
    pk: [
      "0x18ed116c9d0626c9885c3312dd1f11cbf5ef779a2f5c234c0d3a10c535e62080",
      "0xb0dde4fd3b2e8b7edb8dae78aa81abf9d4de2571c754763d5c62d45a8b89daa5",
      "0xdc166f558e00aa7a5c9489e3ee393abeea498d38278a7d3d7544bdab44bec87c",
      "0xe0fbd501addb4e0e870adf05b21536d90604a13b3311b6027e4bd2a6f038472d",
      "0xebf37c5f10ab40756789a59945e10797175735c5944fa9c1b8f6f17dfdc9698c",
    ],
  },
  sepolia: {
    ca: {
      testNft: "0xbE43D87cA1717e4F3e30a8c1791144c7B85F0f0D",
      raffle: "0xD43Ce70303eA993BBcA67a846f7D0F383D66223C",
    },
    pk: [
      "0x18ed116c9d0626c9885c3312dd1f11cbf5ef779a2f5c234c0d3a10c535e62080",
      "0xb0dde4fd3b2e8b7edb8dae78aa81abf9d4de2571c754763d5c62d45a8b89daa5",
      "0xdc166f558e00aa7a5c9489e3ee393abeea498d38278a7d3d7544bdab44bec87c",
      "0xe0fbd501addb4e0e870adf05b21536d90604a13b3311b6027e4bd2a6f038472d",
      "0xebf37c5f10ab40756789a59945e10797175735c5944fa9c1b8f6f17dfdc9698c",
    ],
  },
};
